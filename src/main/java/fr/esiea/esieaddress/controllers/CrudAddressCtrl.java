package fr.esiea.esieaddress.controllers;

import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.Address;
import fr.esiea.esieaddress.model.Contact;
import fr.esiea.esieaddress.service.crud.ICrudService;
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
@RequestMapping("/addresses")
public class CrudAddressCtrl {

    @Autowired
    @Qualifier("serviceValidationDecorator")
    ICrudService<Contact> crudService;

    private final static Logger LOGGER = Logger.getLogger(CrudAddressCtrl.class);

    @RequestMapping(value="/{id}/{baseLabel}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addAddress(@RequestParam String id,@RequestParam String baseLabel,@RequestBody Address address) throws ServiceException, DaoException {
        Contact contact = crudService.getOne(id);
        //Trow automatically an exception if the id is not found

        String  label = String.valueOf(baseLabel);
        int acc= 1;

        while(!contact.addAddress(label, address))  {
            //Generate a new label until is unique
            label = baseLabel.concat(String.valueOf(acc));
            acc++;
        }
    }

}
