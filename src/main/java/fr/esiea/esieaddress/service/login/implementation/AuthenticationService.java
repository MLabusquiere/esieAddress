package fr.esiea.esieaddress.service.login.implementation;

import fr.esiea.esieaddress.service.crud.ICrudService;
import fr.esiea.esieaddress.service.exception.security.InvalidLoginException;
import fr.esiea.esieaddress.service.exception.security.NotConnectedException;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.exception.ServiceException;
import fr.esiea.esieaddress.service.login.IAuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
@Service
public class AuthenticationService implements IAuthenticationService {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class);
    @Autowired
    @Qualifier("userCrudService")
    private ICrudService<User> userService;

    @Autowired
    @Qualifier(value = "authenticationManager")
    AuthenticationManager authenticationManager;

    public void login(Authentication token) throws ServiceException {
        try {

            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (BadCredentialsException ex) {
            throw new InvalidLoginException();
        }
    }

    public User getCurrentAccount() throws NotConnectedException, DaoException {
        //TODO refaire ce code
        // We send a 204 error /!\
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (!(details instanceof String))
            throw new NotConnectedException();
        String id = (String) details;
        if (id.equals("anonymousUser"))
            throw new NotConnectedException();
        final User userConnected;
        try {
            userConnected = userService.getOne(id);
        } catch (ServiceException e) {
            throw new NotConnectedException();
        }

        LOGGER.info("[Service] Querying get the current account " + userConnected);
        return userConnected;
    }

    @Override
    public void logout() {

        SecurityContext context = SecurityContextHolder.getContext();

        LOGGER.info("[Service] Querying to logout User : \"" + context.getAuthentication().getName().toString() + "\"");

        if (context.getAuthentication() != null)
            SecurityContextHolder.clearContext();
    }
}
