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

import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.service.exception.ServiceException;
import fr.esiea.esieaddress.service.login.facebook.IFacebookAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class FBLoggin {

    private static final Logger LOGGER = Logger.getLogger(FBLoggin.class);

    @Autowired
    private IFacebookAuthentication fbAuth;

	@RequestMapping(value = "/facebookAuthentication", method = RequestMethod.GET)
	public void getFacebookLogin(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Send a request to facebook");

		try {
			response.sendRedirect(fbAuth.getRedirectUrl());
		} catch (IOException e) {
			LOGGER.error("Imposible to redirect the user to the facebook page login",e);
            throw new RuntimeException(e);
		}
	}

	/**
	 * @param code
	 */
	@RequestMapping(value = "/facebookRedirect", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void getFacebookLogin(@RequestParam("code") String code) throws ServiceException, DaoException {
		/**
		 * TODO Sheety code change it
		 */
		LOGGER.info("Receive a request from facebook");
        fbAuth.handleFacebookRedirect(code);
	}
}