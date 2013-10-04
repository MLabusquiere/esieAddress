package fr.esiea.esieaddress.dao.implementation;

import fr.esiea.esieaddress.dao.ICrudDao;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.dao.exception.UpdateException;
import fr.esiea.esieaddress.model.contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

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
public class ContactDao implements ICrudDao<Contact> {

    @Autowired
    private Map<String,Contact> database;

    @Override
    public Collection<Contact> getAll() throws DaoException {
        return database.values();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(String idContact) throws DaoException {
       database.remove(idContact);
    }

    @Override
    public void save(Contact contact) throws DaoException {
        if(null == database.remove(contact.getId()))
            throw new UpdateException();
        insert(contact);
    }

    @Override
    public void insert(Contact contact) throws DaoException {
        contact.generateId();
        database.put(contact.getId(),contact);
    }

    @Override
    public Contact getOne(String contactId) throws DaoException {
        return database.get(contactId);
    }

    public void setDatabase(Map<String, Contact> database) {
        this.database = database;
    }
}
