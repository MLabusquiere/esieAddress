package fr.esiea.esieaddress.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import fr.esiea.esieaddress.model.view.ContactView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class Contact implements IModel {

    @JsonView(ContactView.LightView.class)
    private String id;

    @NotNull(message="{fr.esiea.esieaddress.model.contact.lastName.notNull}")
    //@Pattern(regexp="/^[[:alpha:]\\s'\"\\-_&@!?()\\[\\]-]*$/u", message="{fr.esiea.esieaddress.model.contact.lastName}")

    @JsonView(ContactView.LightView.class)
    @Size(max = 20, message="{fr.esiea.esieaddress.model.contact.lastName.size}")
    private String lastName;

    @NotNull(message="{fr.esiea.esieaddress.model.contact.firstName.notNull}")
    //@Pattern(regexp="/^[[:alpha:]\\s'\"\\-_&@!?()\\[\\]-]*$/u",message="{fr.esiea.esieaddress.model.contact.firstName}")
    @JsonView(ContactView.LightView.class)
    @Size(max = 20,message="{fr.esiea.esieaddress.model.contact.firstName.size}")
    private String firstName;

    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            ,message="{fr.esiea.esieaddress.model.contact.email}")
    //Norme RFC2822 - http://www.regular-expressions.info/email.html
    @JsonView(ContactView.FullView.class)
    private String email;

    @Past(message="{fr.esiea.esieaddress.model.contact.dateOfBirth.path}")
    @JsonView(ContactView.FullView.class)
    private Date dateOfBirth;
    //May a map to specify an type of an address

    @Valid
    @JsonView(ContactView.FullView.class)
    private Map<String,Address> addresses = new HashMap<String,Address>();

    @JsonView(ContactView.LightView.class)
    private boolean actif;

    public Contact() {}

    public boolean addAddress(String label,Address address) {
        if(true == addresses.containsValue(label))
            return false;
        addresses.put(label, address);
        return true;

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

