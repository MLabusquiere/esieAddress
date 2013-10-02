package fr.esiea.esieaddress.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.esiea.esieaddress.model.view.ContactView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
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
public class Contact extends Model {

	//@NotNull(message = "{fr.esiea.esieaddress.model.contact.lastname.notNull}")
	//@Pattern(regexp="/^[[:alpha:]\\s'\"\\-_&@!?()\\[\\]-]*$/u", message="{fr.esiea.esieaddress.model.contact.lastname}")

	@JsonView(ContactView.LightView.class)
	@Size(max = 20, message = "{fr.esiea.esieaddress.model.contact.lastname.size}")
	private String lastname;

	//@NotNull(message = "{fr.esiea.esieaddress.model.contact.firstname.notNull}")
	//@Pattern(regexp="/^[[:alpha:]\\s'\"\\-_&@!?()\\[\\]-]*$/u",message="{fr.esiea.esieaddress.model.contact.firstname}")
	@JsonView(ContactView.LightView.class)
	@Size(max = 20, message = "{fr.esiea.esieaddress.model.contact.firstname.size}")
	private String firstname;

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
			, message = "{fr.esiea.esieaddress.model.contact.email}")
	//Norme RFC2822 - http://www.regular-expressions.info/email.html
	@JsonView(ContactView.FullView.class)
	private String email;

	@JsonView(ContactView.FullView.class)
	private String phone;

	@Past(message = "{fr.esiea.esieaddress.model.contact.dateOfBirth.path}")
	@JsonView(ContactView.FullView.class)
	private Date dateOfBirth;

	//May a map to specify an type of an address
	@Valid
	@JsonView(ContactView.FullView.class)
	private Map<String, Address> addresses = new HashMap<String, Address>();

	@JsonView(ContactView.LightView.class)
	private boolean actif;

	public Contact() {
	}

	public boolean addAddress(String label, Address address) {
		if (true == addresses.containsValue(label))
			return false;
		addresses.put(label, address);
		return true;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Map<String, Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Map<String, Address> addresses) {
		this.addresses = addresses;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return "Contact{" +
				"lastname='" + lastname + '\'' +
				", firstname='" + firstname + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", addresses=" + addresses +
				", actif=" + actif +
				'}';
	}
}

