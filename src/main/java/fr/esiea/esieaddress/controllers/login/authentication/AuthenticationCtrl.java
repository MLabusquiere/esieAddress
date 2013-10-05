package fr.esiea.esieaddress.controllers.login.authentication;

import fr.esiea.esieaddress.controllers.exception.security.InvalidLoginException;
import fr.esiea.esieaddress.controllers.exception.security.NeedToBeAuthenticatedException;
import fr.esiea.esieaddress.controllers.exception.security.NotConnectedException;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.crud.ICrudUserService;
import fr.esiea.esieaddress.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
	@Autowired
	@Qualifier("userCrudService")
	private ICrudUserService userService;

	@Autowired
	@Qualifier(value = "authenticationManager")
	AuthenticationManager authenticationManager;

	private static final Logger LOGGER = Logger.getLogger(AuthenticationCtrl.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void login(@RequestBody User userToLog) throws InvalidLoginException {

		LOGGER.info("[Controller] Querying to log in User \"" + userToLog.toString() + "\"");

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userToLog.getMail(), userToLog.getPassword());

		try {

			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (BadCredentialsException ex) {
			throw new InvalidLoginException();
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public User currentAccount() throws DaoException, NotConnectedException {
		LOGGER.info("[Controller] Querying to get User connected in User");
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

		LOGGER.info("[Controller] Querying get the current account " + userConnected);
		return userConnected;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public void logout() throws InvalidLoginException, NeedToBeAuthenticatedException, DaoException {
		//Should may override spring security logout

		SecurityContext context = SecurityContextHolder.getContext();

		LOGGER.info("[Controller] Querying to log out User : \""
				+ context.getAuthentication().getName().toString() + "\"");

		if (context.getAuthentication() != null)
			SecurityContextHolder.clearContext();
	}
}
