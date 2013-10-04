package fr.esiea.esieaddress.service.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import fr.esiea.esieaddress.model.contact.Contact;

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
public class Test {
	private static final String MY_ACCESS_TOKEN = "CAACEdEose0cBAPt0raLVorpj27dhDKsTR11IOGm3D1hPOXyMwRLNELpL7ZCdeIYUVpUZBhqrLuTfTXk6ZBGQ1491O59IHLRyUgsTKgRmRWZBQ418ZC8O7lrIAZBFtjMPhkeZAyskM58xYUx9QOiee9gVRdmOv8h5FPARzZCQFf9IwTqrAlhwZAOpM7NRHVZBZBqM1EZD";
	//"208719949301687|Ri3-_E5j6Yl_jjfIVp8IEkN8VXU";
//    @Autowired

	public static void main(String[] args) {

		FBService fbService = new FBService();
		FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);

		final Contact contact = facebookClient.fetchObject("me", Contact.class);
		//final Contact contact = fbService.FBContactToContact(me);
		System.out.println(contact);

		// FQL query which asks Facebook for your friends' names,
		// profile picture URLs, and network affiliations

		String query =
				"SELECT uid,email,birthday_date,last_name, first_name,current_address,pic_with_logo FROM user WHERE uid = me()" +
						"OR uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";

		// Executes an API call with result mapped to a list of classes we've defined.
		// Note that you can pass in an arbitrary number of Parameters - here we
		// send along the query as well as the "give me HTTPS URLs" flag

		List<Contact> users =
				facebookClient.executeFqlQuery(query, Contact.class,
						Parameter.with("return_ssl_resources", "true"));
		for (Contact user : users)
			System.out.println(user);
	}
}
