package fr.esiea.esieaddress.service.crud;

import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.IModel;
import fr.esiea.esieaddress.model.user.User;
import fr.esiea.esieaddress.service.exception.NotUniqueEmailException;
import fr.esiea.esieaddress.service.exception.ServiceException;

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
public interface ICrudService<T extends IModel> {

	Collection<T> getAll() throws ServiceException, DaoException;

	void remove(String id) throws ServiceException, DaoException;

	void save(T model) throws ServiceException, DaoException;

	void insert(T model) throws ServiceException, DaoException, NotUniqueEmailException;

	T getOne(String Id) throws ServiceException, DaoException;

    T getOneByEmail(String mail) throws ServiceException, DaoException;
}
