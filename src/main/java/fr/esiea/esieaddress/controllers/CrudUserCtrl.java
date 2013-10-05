package fr.esiea.esieaddress.controllers;

import fr.esiea.esieaddress.controllers.exception.NotUniqueEmailException;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.crud.ICrudService;
import fr.esiea.esieaddress.service.crud.ICrudUserService;
import fr.esiea.esieaddress.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/users")
public class CrudUserCtrl {

    @Autowired
    @Qualifier("userValidationDecorator")
    ICrudService<User> crudValidationService;

    @Autowired
    @Qualifier("userCrudService")
    ICrudUserService crudService;

    private final static Logger LOGGER = Logger.getLogger(CrudAddressCtrl.class);

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void login(@RequestBody User user) throws ServiceException, DaoException, NotUniqueEmailException {

        LOGGER.info("[Controller] Querying to create new user : " + user.toString() + "\"");
        if(checkUniqueEmail(user.getMail()))
            crudValidationService.insert(user);
        else
            throw new NotUniqueEmailException();

    }

    private boolean checkUniqueEmail(String mail) throws ServiceException, DaoException {
        return null == crudService.getOneByMail(mail);
    }


}
