package fr.esiea.esieaddress.service.security;

import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.UserService;
import fr.esiea.esieaddress.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
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

import java.util.List;

@Service
public class SecurityService implements UserDetailsService,
        AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private UserService userService;

    public SecurityService() {
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(
            String email) throws UsernameNotFoundException {
        User user = null; //userName is an email
        try {
            user = userService.findByEmail(email);
        } catch (ServiceException e) { //TODO Be more explicit
            LOGGER.debug("",e);
        } catch (DaoException e) {
            LOGGER.debug("",e);
        }

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return user;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserDetails(
            OpenIDAuthenticationToken token) throws UsernameNotFoundException {

        String email = null;

        List<OpenIDAttribute> attributes = token.getAttributes();
        for (OpenIDAttribute attribute : attributes) {
            if (attribute.getName().equals("email")) {
                email = attribute.getValues().get(0);
            }
        }

        return loadUserByUsername(email);
    }
}

