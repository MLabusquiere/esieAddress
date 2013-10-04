package fr.esiea.esieaddress.controllers.login;

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

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.httpclient.methods.GetMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
 * Inspired from http://dncodes.blogspot.fr/2012/06/java-spring-security-authenticate.html
 */
@Controller
@RequestMapping("/login")
public class FBLoggin {
    private static final Logger LOGGER = Logger.getLogger(FBLoggin.class);
    /*
     * @Value not working in controller in spring 3.3
     */
    @Autowired
    private FacebookConst FBConst;

    @RequestMapping(value = "/facebookAuthentication", method= RequestMethod.GET)
    public void getFacebookLogin(HttpServletRequest request,HttpServletResponse response) {
        LOGGER.info("Send a request to facebook");
        String url="https://www.facebook.com/dialog/oauth/?"
                + "client_id=" + FBConst.FACEBOOK_APP_ID
                + "&redirect_uri=" + FBConst.FACEBOOK_REDIRECT_URL
                + "&scope=email,publish_stream,user_about_me,friends_about_me"
                + "&state=" + FBConst.FACEBOOK_EXCHANGE_KEY
                + "&display=page"
                + "&response_type=code";
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param code
     */
    @RequestMapping(value = "/facebookRedirect", method= RequestMethod.GET)
    public void getFacebookLogin(@RequestParam("code") String code) {
        /**
         * TODO Sheety code change it
         */
        LOGGER.info("Receive a request from facebook");
        if( ! code.isEmpty())    {
            //If we received a valid code, we can continue to the next step
            //Next we want to get the access_token from Facebook using the code we got,
            //use the following url for that, in this url,
            //client_id-our app id(same as above),  redirect_uri-same as above, client_secret-same as                //above, code-the code we just got
            String url="https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + FBConst.FACEBOOK_APP_ID
                    + "&redirect_uri=" + FBConst.FACEBOOK_REDIRECT_URL
                    + "&client_secret=" + FBConst.FACEBOOK_SECRET_KEY
                    + "&code=" + code;
            // Create an instance of HttpClient.
            HttpClient client = new HttpClient();
            // Create a method instance.
            GetMethod method = new GetMethod(url);
            // Provide custom retry handler is necessary
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, false));
            try {
                // Execute the method.
                int statusCode = client.executeMethod(method);
                if (statusCode != HttpStatus.SC_OK) {
                    System.err.println("Method failed: " + method.getStatusLine());
                }
                // Read the response body.
                byte[] responseBody = method.getResponseBody();
                // Deal with the response.Use caution: ensure correct character encoding and is
                // not binary data
                String responseBodyString=String.valueOf(responseBody);
                //will be like below,                                 //access_token=AAADD1QFhDlwBADrKkn87ZABAz6ZCBQZ//DZD&expires=5178320
                //now get the access_token from the response
                if(responseBodyString.contains("access_token")){
                    //success
                    String[] mainResponseArray=responseBodyString.split("&");
                    //like //{"access_token= AAADD1QFhDlwBADrKkn87ZABAz6ZCBQZ//DZD ","expires=5178320"}
                    String accesstoken = "";
                    for (String string : mainResponseArray) {
                        if(string.contains("access_token")){
                            accesstoken=string.replace("access_token=", "").trim();
                        }
                    }

                    LOGGER.info("Congratulation " + accesstoken);

                       /*
                       //now we have the access token :)
                       //Great. Now we have the access token, I have used restfb to get the user details here
                       FacebookClient facebookClient = new DefaultFacebookClient(accesstoken);
                       User user = facebookClient.fetchObject("me", User.class);
                       //In this user object, you will have the details you want from Facebook,  Since we have    the  access token with us, can play around and see what more can be done
                       //CAME UP TO HERE AND WE KNOW THE USER HAS BEEN AUTHENTICATED BY FACEBOOK, LETS AUTHENTICATE HIM IN OUR APPLICATION
                       //NOW I WILL CALL MY doAutoLogin METHOD TO AUTHENTICATE THE USER IN MY SPRING SECURITY CONTEXT
                       */
                }else{
                    //failed
                    return;
                }

            } catch (IOException e) {
                LOGGER.error("Fatal transport error",e);
            } finally {
                // Release the connection.
                method.releaseConnection();
            }
        }else{
            //failed
            return;
        }
    }
}