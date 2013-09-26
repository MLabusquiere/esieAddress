package fr.esiea.esieaddress.controllers;

import fr.esiea.esieaddress.service.validation.csv.CsvService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;

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
public class CsvUploadCtrl {

    private static final Logger LOGGER = Logger.getLogger(CsvUploadCtrl.class);
    private static final double FILE_SIZE_MAX = 30000;
    private static String URI;

    @Autowired
    private CsvService csvService;

    static {
        try {
        URI = Files.createTempDirectory("csvImport").toString();
        }catch (Exception e)    {
            URI = null;
            LOGGER.error("Can't innitialise the directory to import file");
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void upload(MultipartHttpServletRequest files, final HttpServletRequest request) throws IOException {
        //TODO Make it less verbose and may use a buffer to make it safer
        Map<String, MultipartFile> multipartFileMap = files.getMultiFileMap().toSingleValueMap();
        Set<String> fileNames = multipartFileMap.keySet();

        for (String fileName : fileNames) {

            MultipartFile multipartFile = multipartFileMap.get(fileName);
            String originalFilename = multipartFile.getOriginalFilename();

            if (checkFileName(originalFilename) && multipartFile.getSize() < FILE_SIZE_MAX) {

                LOGGER.info("[CONTROLLER] A file is created at " + URI);

                try(Reader contactsFile = new InputStreamReader(multipartFile.getInputStream()))    {

                   csvService.ReadContactCSV(contactsFile);

                }finally { }
            }
        }
    }

    private boolean checkFileName(String fileName) {
        final int index = fileName.lastIndexOf('.');
        return index != -1 && fileName.substring(index + 1).toLowerCase().equals("csv");
    }
}