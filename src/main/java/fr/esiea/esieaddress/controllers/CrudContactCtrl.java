package fr.esiea.esieaddress.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.Contact;
import fr.esiea.esieaddress.model.view.ContactView;
import fr.esiea.esieaddress.service.crud.ICrudService;
import fr.esiea.esieaddress.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
@RequestMapping("/contacts")
public class CrudContactCtrl {

    @Autowired
    @Qualifier("serviceValidationDecorator")
    ICrudService<Contact> crudService;

    private final static Logger LOGGER = Logger.getLogger(CrudContactCtrl.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public void getAll(HttpServletResponse servletResponse) throws ServiceException, DaoException, IOException {

        LOGGER.info("[Controller] Querying Contact list");
        /*
         * Make the list with only id, firstname, lastname, actif
         */
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithView(ContactView.LightView.class).writeValue(servletResponse.getOutputStream(),crudService.getAll());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Contact getById(@PathVariable("id") String contactId) throws ServiceException, DaoException {

        LOGGER.info("[Controller] Querying Contact with id : \"" + contactId + "\"");
        return crudService.getOne(contactId);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Contact contact) throws ServiceException, DaoException {

        LOGGER.info("[Controller] Querying to create new contact : "+ contact.toString());
        crudService.insert(contact);

    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(@RequestBody Contact contact) throws ServiceException, DaoException {

        LOGGER.info("[Controller] Querying to edit Contact : \"" + contact.toString());
        crudService.save(contact);

    }


    @RequestMapping(value = "/{idContact}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String idContact) throws ServiceException, DaoException {

        LOGGER.info("[Controller] Querying to delete Contact with id : \"" + idContact);
        crudService.remove(idContact);

    }

}
