package fr.esiea.esieaddress.controllers;

import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.contact.Contact;
import fr.esiea.esieaddress.service.crud.ICrudService;
import fr.esiea.esieaddress.service.exception.ServiceException;
import fr.esiea.esieaddress.service.csv.CsvService;
import fr.esiea.esieaddress.service.validation.exception.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

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
@RequestMapping("/import")
public class ImportCtrl {

	private static final Logger LOGGER = Logger.getLogger(ImportCtrl.class);
	private static final double FILE_SIZE_MAX = 30000;
	@Autowired
	private ICrudService<Contact> contactCrudService;
	@Autowired
	private CsvService csvService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
    @Secured("ROLE_USER")
	public void upload(MultipartHttpServletRequest files, final HttpServletRequest request) throws DaoException, ServiceException, FileNotFoundException {
		LOGGER.info("[IMPORT] Start to import contact");

		//TODO Make it less verbose and may use a buffer to make it safer
		Map<String, MultipartFile> multipartFileMap = files.getMultiFileMap().toSingleValueMap();
		Set<String> fileNames = multipartFileMap.keySet();

		for (String fileName : fileNames) {

			MultipartFile multipartFile = multipartFileMap.get(fileName);
			String originalFilename = multipartFile.getOriginalFilename();

			if (checkFileName(originalFilename) && multipartFile.getSize() < FILE_SIZE_MAX) {

				InputStream inputStream = null;

				try {
					inputStream = multipartFile.getInputStream();
				} catch (IOException e) {
					throw new FileNotFoundException(e.toString());
				}

				try (Reader contactsFile = new InputStreamReader(inputStream)) {
					Map<String,Object> modelErrors = new HashMap<>();
					LOGGER.debug("[IMPORT] File is reading");
					Collection<Contact> contacts = csvService.ReadContactCSV(contactsFile);
					for (Contact contact : contacts) {
						try {
							contactCrudService.insert(contact);
						} catch (ValidationException e) {
							Object modelError = e.getModel();
							LOGGER.warn("found an error in contact " + modelError);
							modelErrors.put(contact.getId(),(Map)modelError);
						}
					}

					if (!modelErrors.isEmpty())
						throw new ValidationException(modelErrors);
				} catch (IOException e) {
					throw new FileNotFoundException(e.toString());
				} finally {
					if (inputStream != null)
						try {
							inputStream.close();
						} catch (IOException e) {
							LOGGER.error("[IMPORT] Impossible to close the file " + inputStream.toString());
						}
				}
			}
		}
	}

	private boolean checkFileName(String fileName) {
		final int index = fileName.lastIndexOf('.');
		return index != -1 && fileName.substring(index + 1).toLowerCase().equals("csv");
	}
}
