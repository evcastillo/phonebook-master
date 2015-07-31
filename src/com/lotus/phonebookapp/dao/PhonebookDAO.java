package com.lotus.phonebookapp.dao;

import java.util.List;

import com.lotus.phonebookapp.beans.Company;
import com.lotus.phonebookapp.beans.Contact;

public interface PhonebookDAO {

	List<Contact> contactList();
	
	Company getCompanyByContact(Contact contact);
	
	Contact getByName(String name);

	List<Contact> search(String keyword);

	void delete(String contact);

	void update(String contact, String newNumber);

	void create(Contact contact);
	
}
