package fr.esiea.esieaddress.service;

import fr.esiea.esieaddress.dao.ICrudDao;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.crud.ICrudService;
import fr.esiea.esieaddress.service.exception.ServiceException;
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
public class UserService implements ICrudService<User>{

    @Autowired
    ICrudDao<User> userDao;

    public User findByEmail(String email) throws ServiceException, DaoException {
        return getOne(email);
    }

    @Override
    public Collection<User> getAll() throws ServiceException, DaoException {
        return userDao.getAll();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(String idUser) throws ServiceException, DaoException {
        remove(idUser);
    }

    @Override
    public void save(User user) throws ServiceException, DaoException {
        save(user);
    }

    @Override
    public void insert(User user) throws ServiceException, DaoException {
        insert(user);
    }

    @Override
    public User getOne(String userId) throws ServiceException, DaoException {
        return getOne(userId);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
