package fr.esiea.esieaddress.dao.implementation;

import fr.esiea.esieaddress.dao.ICrudUserDao;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.dao.exception.UpdateException;
import fr.esiea.esieaddress.model.user.User;
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
public class UserDao implements ICrudUserDao {
	@Autowired
	private Map<String, User> database;

	@Override
	public Collection<User> getAll() throws DaoException {
		return database.values();  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void remove(String idUser) throws DaoException {
		database.remove(idUser);
	}

	@Override
	public void save(User user) throws DaoException {
		if (null == database.remove(user.getId()))
			throw new UpdateException();
		insert(user);
	}

	@Override
	public void insert(User user) throws DaoException {
		user.generateId();
		database.put(user.getId(), user);
	}

	@Override
	public User getOne(String userId) throws DaoException {
		return database.get(userId);
	}

	public void setDatabase(Map<String, User> database) {
		this.database = database;
	}

	@Override
	public User getOneByMail(String mail) throws DaoException {
		final Collection<User> users = database.values();
		for (User user : users) {
			if (user.getMail() == mail)
				return user;
		}
		return null;
	}
}
