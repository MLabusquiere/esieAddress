package fr.esiea.esieaddress.dao.implementation;

import fr.esiea.esieaddress.model.contact.Contact;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
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
    /*This is not working
	private static final  Map<String,Map<String, Contact>> database = new HashMap<String,Map<String, Contact>>(){

        public Map<String, Contact> get(Object key) {
           //simule mongo collection by adding an empty collection if there is not yet
            System.out.println("indeside");
            if(! super.containsKey(key));
                this.put((String)key, new HashMap<String, Contact>());
            return super.get(key);
        }
    };
    Figureout why (should be generic problem)
    */
@Repository
public class DatabaseMock {

    private static final  Map<String,Map<String, Contact>> database = new HashMap<String,Map<String, Contact>>();

    public Contact get(String collectionId, String contactId) {
        if(database.containsKey(collectionId))
            return database.get(collectionId).get(contactId);
        return null;
    }

    public Collection<Contact> values(String collectionId) {
        if(database.containsKey(collectionId))
            return database.get(collectionId).values();
        return Collections.EMPTY_LIST;
    }

    public Contact remove(String collectionId, String idContact) {
        if(database.containsKey(collectionId))
            return database.get(collectionId).remove(idContact);
        return null;
    }

    public void put(String collectionId, String idContact, Contact contact) {
        if(!database.containsKey(collectionId))
            database.put(collectionId,new HashMap<String,Contact>());
        database.get(collectionId).put(idContact,contact);
    }
}
