package fr.esiea.esieaddress.service.login.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import fr.esiea.esieaddress.dao.ICrudUserDao;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.contact.Contact;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.exception.ServiceException;
import fr.esiea.esieaddress.service.login.IAuthenticationService;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Copyright (c) 2013 ESIEA M. Labusquiere D. Déïs
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * Inspired from http://dncodes.blogspot.fr/2012/06/java-spring-security-authenticate.html
 */
@Service
public class FacebookAuthenticationService implements IFacebookAuthentication {

    private static final Logger LOGGER = Logger.getLogger(FacebookAuthenticationService.class);

    @Value("${controller.facebook.app.id}")
    public String FACEBOOK_APP_ID;
    @Value("${controller.facebook.redirect.url}")
    public String FACEBOOK_REDIRECT_URL;
    @Value("${controller.facebook.exchange.key}")
    public String FACEBOOK_EXCHANGE_KEY;
    @Value("${controller.facebook.secret.key}")
    public String FACEBOOK_SECRET_KEY;

    private final String redirectUrl = "https://www.facebook.com/dialog/oauth/?"
            + "client_id=" + FACEBOOK_APP_ID
            + "&redirect_uri=" + FACEBOOK_REDIRECT_URL
            + "&scope=email,publish_stream,user_about_me,friends_about_me"
            + "&state=" + FACEBOOK_EXCHANGE_KEY
            + "&display=page"
            + "&response_type=code";

    private final String postUrlBase = "https://graph.facebook.com/oauth/access_token?"
            + "client_id=" + FACEBOOK_APP_ID
            + "&redirect_uri=" + FACEBOOK_REDIRECT_URL
            + "&client_secret=" + FACEBOOK_SECRET_KEY; //Add the cade at the end

    @Autowired
    ICrudUserDao userDao;

    @Autowired
    private IAuthenticationService authService;

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public void handleFacebookRedirect(String code) throws DaoException, ServiceException {
        String accessToken = getAccessToken(code);
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

        User user = facebookClient.fetchObject("me", User.class);
        user.setAccountFacebook(true);
        //Update or create the contact
        userDao.save(user);
        //Make the autentication
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword());
        authService.login(token);

    }

    private String getAccessToken(String code) {
        if (!code.isEmpty()) {
            //If we received a valid code, we can continue to the next step
            //Next we want to get the access_token from Facebook using the code we got,
            //use the following url for that, in this url,
            //client_id-our app id(same as above),  redirect_uri-same as above, client_secret-same as                //above, code-the code we just got

            // Create an instance of HttpClient.
            HttpClient client = new HttpClient();
            // Create a method instance.
            GetMethod method = new GetMethod(postUrlBase.concat( "&code=" + code));
            // Provide custom retry handler is necessary
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, false));
            try {
                // Execute the method.
                int statusCode = client.executeMethod(method);
                if (statusCode != HttpStatus.SC_OK) {
                    LOGGER.error("Method failed: " + method.getStatusLine());
                }
                // Read the response body.
                String responseBodyString = String.valueOf( method.getResponseBody());

                if (responseBodyString.contains("access_token")) {
                    //success
                    String[] mainResponseArray = responseBodyString.split("&");
                    //like //{"access_token= AAADD1QFhDlwBADrKkn87ZABAz6ZCBQZ//DZD ","expires=5178320"}
                    String accesstoken = "";
                    for (String string : mainResponseArray) {
                        if (string.contains("access_token")) {
                            return string.replace("access_token=", "").trim();
                        }
                    }

                }
            } catch (IOException e) {
                LOGGER.error("Fatal transport error", e);
            } finally {
                // Release the connection.
                method.releaseConnection();
            }
        }
        //failed
        return "";

    }


}
