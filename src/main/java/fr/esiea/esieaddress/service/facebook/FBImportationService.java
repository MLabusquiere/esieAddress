package fr.esiea.esieaddress.service.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import fr.esiea.esieaddress.dao.ICrudDao;
import fr.esiea.esieaddress.dao.exception.DaoException;
import fr.esiea.esieaddress.model.contact.Contact;
import fr.esiea.esieaddress.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class FBImportationService {

    private static final String QUERY = "SELECT uid,email,birthday_date,last_name, first_name,current_address,pic_with_logo FROM user WHERE uid = me()" +
            "OR uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";

    @Autowired
    @Qualifier("contactDao")
    private ICrudDao<Contact> contactDao;

    public void SynchroniseFBContacts(String accessToken) throws ServiceException, DaoException {

        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

        List<Contact> contacts =
                facebookClient.executeFqlQuery(QUERY, Contact.class,
                        Parameter.with("return_ssl_resources", "true"));

        for (Contact contact : contacts)  {
            Contact contactToCheck = contactDao.getOneByEmail(contact.getEmail());
            if(null == contactToCheck)  {
                contactDao.insert(contact);
                continue;
            }

            if( ! contact.equals(contactToCheck))
                contactDao.save(contact);

        }


    /*    for (User user :facebookClient.executeFqlQuery(QUERY, User.class,
                Parameter.with("return_ssl_resources", "true")))  {
            System.out.println(user);

        }
    */
    }
}
