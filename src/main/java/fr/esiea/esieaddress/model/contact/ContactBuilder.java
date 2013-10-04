package fr.esiea.esieaddress.model.contact;

import java.util.ArrayList;
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
public class ContactBuilder {

	private String lastname;
	private String firstname;
	private String email;
	private String phone;
	private String dateOfBirth;
	private List<Address> addresses = new ArrayList<Address>();
	private boolean actif;

	public ContactBuilder setLastname(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public ContactBuilder setFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public ContactBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public ContactBuilder setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public ContactBuilder setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public ContactBuilder addAddresses(Address address) {
		this.addresses.add(address);
		return this;
	}

	public ContactBuilder setActif(boolean actif) {
		this.actif = actif;
		return this;
	}

	public Contact build() {
		Contact contact = new Contact();

		contact.setLastname(this.lastname);
		contact.setFirstname(this.firstname);
		contact.setEmail(this.email);
		contact.setPhone(this.phone);
		contact.setDateOfBirth(this.dateOfBirth);
		contact.setActif(this.actif);
		contact.setAddresses(this.addresses);

		return contact;
	}
}
