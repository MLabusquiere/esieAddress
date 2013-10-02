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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class FBLoggin {
    private static final Logger LOGGER = Logger.getLogger(FBLoggin.class);

    private final String FACEBOOK_APP_ID = "208719949301687";
    private final String FACEBOOK_REDIRECT_URL = "http://localhost:8080/esieAddress/rest/login/facebookRedirect";
    private final String FACEBOOK_EXCHANGE_KEY="5a7150fef9431de5a4ab5b6867c0be2b";


    @RequestMapping(value = "/facebookAuthentication", method= RequestMethod.GET)
    public void getFacebookLogin(HttpServletRequest request,HttpServletResponse response) {
        LOGGER.info("Send a request to facebook");
        String url="https://www.facebook.com/dialog/oauth/?"
                + "client_id=" + FACEBOOK_APP_ID
                + "&redirect_uri=" + FACEBOOK_REDIRECT_URL
                + "&scope=email,publish_stream,user_about_me,friends_about_me"
                + "&state=" + FACEBOOK_EXCHANGE_KEY
                + "&display=page"
                + "&response_type=code";
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/facebookRedirect", method= RequestMethod.GET)
    public void getFacebookLogin() {
        LOGGER.info("Receive a request from facebook");

    }
}
