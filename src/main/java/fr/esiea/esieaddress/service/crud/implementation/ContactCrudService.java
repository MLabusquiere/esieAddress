package fr.esiea.esieaddress.service.crud.implementation;

import fr.esiea.esieaddress.dao.Dao;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.Contact;
import fr.esiea.esieaddress.service.crud.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
public class ContactCrudService implements ICrudService<Contact> {

    @Autowired
    private Dao<Contact> dao;

    @Override
    public Collection<Contact> getAll() throws DaoException {
        return dao.getAll();
    }

    @Override
    public void remove(String idContact) throws DaoException  {
        dao.remove(idContact);
    }

    @Override
    public void save(Contact contact) throws DaoException  {
        dao.save(contact);
    }

    @Override
    public void insert(Contact contact) throws DaoException {
        dao.insert(contact);
    }

    @Override
    public Contact getOne(String contactId) throws DaoException  {
        return dao.getOne(contactId);
    }

}
