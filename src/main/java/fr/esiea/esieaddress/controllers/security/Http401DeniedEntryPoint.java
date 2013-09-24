package fr.esiea.esieaddress.controllers.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Http401DeniedEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = Logger.getLogger(Http401DeniedEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        LOGGER.info("Denied Entry Point");
        HttpServletResponse httpResponse = response;
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

    }

}