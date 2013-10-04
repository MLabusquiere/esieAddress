package fr.esiea.esieaddress.controllers.login.security;


import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.crud.implementation.UserCrudService;
import fr.esiea.esieaddress.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;


public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = Logger.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private UserCrudService userService;

    public CustomAuthenticationProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

        String mail = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());
        User userToAuthenticate;

        try {
            userToAuthenticate = userService.getOneByMail(mail);

            if (userToAuthenticate == null)
                throw new BadCredentialsException("Bad Login");

            if (!userToAuthenticate.getPassword().equals(password))
                throw new BadCredentialsException("Bad password");

            //Authorities
            Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

            for (String authority : userToAuthenticate.getProfile().getRoleList()) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mail, password, authorities);

            token.setDetails(userToAuthenticate.getId());

            return token;

        } catch (DaoException | ServiceException e) {

            LOGGER.error("Authentication exception");
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;  //To indicate that this authenticationprovider can handle the auth request. since there's currently only one way of logging in, always return true
    }

}