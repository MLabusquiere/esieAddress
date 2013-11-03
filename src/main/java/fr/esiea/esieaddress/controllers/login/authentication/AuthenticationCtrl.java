package fr.esiea.esieaddress.controllers.login.authentication;

import fr.esiea.esieaddress.service.exception.security.InvalidLoginException;
import fr.esiea.esieaddress.service.exception.security.NeedToBeAuthenticatedException;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.exception.ServiceException;
import fr.esiea.esieaddress.service.login.IAuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
@Controller
@RequestMapping()
public class AuthenticationCtrl {

    public static final String FACEBOOK_AUTHENTICATION_URL = "rest/login/authenticationFacebook";

    @Autowired
    private IAuthenticationService authService;

    private static final Logger LOGGER = Logger.getLogger(AuthenticationCtrl.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody User user, HttpServletResponse response) throws ServiceException, DaoException {

        LOGGER.info("[Controller] Querying to log in User \"" + user.toString() + "\"");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword());
        authService.login(token);

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User currentAccount() throws DaoException, ServiceException {

        LOGGER.info("[Controller] Querying to get User connected");

        return authService.getCurrentAccount();

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public void logout() throws ServiceException, DaoException {
        //Should may override spring security logout
        LOGGER.info("[Controller] Logout request");
        authService.logout();
    }
}
