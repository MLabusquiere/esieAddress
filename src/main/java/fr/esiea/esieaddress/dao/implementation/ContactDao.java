package fr.esiea.esieaddress.dao.implementation;

import fr.esiea.esieaddress.dao.ICrudDao;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.dao.exception.UpdateException;
import fr.esiea.esieaddress.model.contact.Contact;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashMap;
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

    private static final Logger LOGGER = Logger.getLogger(ContactDao.class);

    @Autowired
	private DatabaseMock database;



    @Override
    public Collection<Contact> getAll() throws DaoException {
        return database.values(getCurrendUserId());
    }

	@Override
	public void remove(String idContact) throws DaoException {
        database.remove(getCurrendUserId(),idContact);
	}

	@Override
	public void save(Contact contact) throws DaoException {
        if (null == database.remove(getCurrendUserId(),contact.getId()))
			throw new UpdateException();
		insert(contact);
	}

	@Override
	public void insert(Contact contact) throws DaoException {
		contact.generateId();
		database.put(getCurrendUserId(), contact.getId(), contact);
	}

	@Override
	public Contact getOne(String contactId) throws DaoException {
		return database.get(getCurrendUserId(),contactId);
	}

    public static String getCurrendUserId() {
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        LOGGER.info("Id " +id);
        if( null == id || id.isEmpty()) {
            String message = "Conception architecture default : No user connected but there is an access to a database";
            LOGGER.error(message);
            throw new RuntimeException(message);
        }
        return id;
    }
}
