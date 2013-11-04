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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

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



    @Value("${controller.facebook.redirect_url}")
    private String redirectUrl;
    @Value("${controller.facebook.base_post_url}")
    private String postUrlBase ;
    @Value("${controller.facebook.redirect_user_page}")
    private String redirectUserPage;
    @Autowired
    ICrudUserDao userDao;

    @Autowired
    private IAuthenticationService authService;

    @Deprecated
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
        User one = userDao.getOneByMail(user.getMail());
        if(null == one) {
            userDao.insert(user); //insert a new user
        }else {
            user.setId(one.getId());
            if(one.equals(user))
                userDao.save(user); //Update the user
        }
//Authorities
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        for (String authority : user.getProfile().getRoleList()) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }

        //Make the autentication
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword(),authorities);
        token.setDetails(user.getId());
        SecurityContextHolder.getContext().setAuthentication(token);

    }

    @Override
    public String getRedirectUserPage() {
        return redirectUserPage;
    }

    private String getAccessToken(String code) {
        /**
         * TODO Sheety code change it
         */
        if (!code.isEmpty()) {
            //If we received a valid code, we can continue to the next step
            //Next we want to get the access_token from Facebook using the code we got,
            //use the following url for that, in this url,
            //client_id-our app id(same as above),  redirect_uri-same as above, client_secret-same as                //above, code-the code we just got

            // Create an instance of HttpClient.
            HttpClient client = new HttpClient();
            // Create a method instance.
            String url = postUrlBase.concat("&code=" + code);
            GetMethod method = new GetMethod(url);
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
                byte[] responseBody = method.getResponseBody();
                // Deal with the response.Use caution: ensure correct character encoding and is
                // not binary data
                String responseBodyString = new String(responseBody);
                LOGGER.info("token : " +responseBodyString);
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
