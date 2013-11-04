package fr.esiea.esieaddress.service.validation;

import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.IModel;
import fr.esiea.esieaddress.service.crud.ICrudService;
import fr.esiea.esieaddress.service.exception.ServiceException;
import fr.esiea.esieaddress.service.validation.exception.ValidationException;
import org.apache.log4j.Logger;
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

public class ServiceValidationDecorator<T extends IModel> implements ICrudService<T> {
	private static final Logger LOGGER = Logger.getLogger(ServiceValidationDecorator.class);

	private ICrudService<T> crudService;

	@Autowired
	private IValidationService validationService;

	@Override
	public Collection<T> getAll() throws ServiceException, DaoException {
		return crudService.getAll();
	}

	@Override
	public void remove(String id) throws ServiceException, DaoException {
		crudService.remove(id);
	}

	@Override
	public void save(T model) throws ServiceException, DaoException {
		LOGGER.info("[VALIDATION] Do the validation on model " + model.getId());

		final Map<String, Object> errorMap = validationService.validate(model);

		if (errorMap.isEmpty())
			crudService.save(model);
		else
			throw new ValidationException(errorMap);
	}

	@Override
	public void insert(T model) throws ServiceException, DaoException {
		LOGGER.info("[VALIDATION] Do the validation on model " + model.getId());

		final Map<String, Object> errorMap = validationService.validate(model);

		if (errorMap.isEmpty())
			crudService.insert(model);
		else
			throw new ValidationException(errorMap);
	}

	@Override
	public T getOne(String id) throws ServiceException, DaoException {
		return crudService.getOne(id);
	}

	public void setCrudService(ICrudService<T> crudService) {
		this.crudService = crudService;
	}
}
