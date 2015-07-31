package com.lotus.phonebookapp.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.lotus.phonebookapp.beans.Contact;
import com.lotus.phonebookapp.dao.PhonebookDAO;
import com.lotus.phonebookapp.dao.PhonebookOJDBDAO;

public class Main {
	private static Scanner scan = new Scanner(System.in);
	private static String name;
	private static List<Contact> contacts;

	public static void main(String[] args) {
		while (true) {
			System.out
					.println("[1]List all contacts\n[2]Show contact by name\n[3]search for contacts\n[4]Delete contact\n[5]Create contact\n[6]Update contact");
			int option = scan.nextInt();
			executeOption(option);
		}

	}

	private static void executeOption(int option) {
		PhonebookDAO phonebookDAO = PhonebookOJDBDAO.getInstance();
		switch (option) {
		case 1:

			getListOfContacts(phonebookDAO);
			break;
		case 2:
			showContactByName(phonebookDAO);
			break;
		case 3:
			searchContact(phonebookDAO);
			break;
		case 4:
			deleteContact(phonebookDAO);
			break;
		case 5:
			createContact(phonebookDAO);
			break;
		case 6:
			updateContact(phonebookDAO);
			break;
		default:
			break;

		}
	}

	private static void searchContact(PhonebookDAO phonebookDAO) {

		System.out.println("Enter a keyword to arch on contacts: ");
		name = scan.next();
		contacts = phonebookDAO.search(name);
		for (Contact con : contacts) {
			System.out.println(con + " "
					+ phonebookDAO.getCompanyByContact(con));
		}
		System.out.println();

	}

	private static void showContactByName(PhonebookDAO phonebookDAO) {
		System.out.println("Enter name to show: ");
		name = scan.next();
		System.out
				.println(phonebookDAO.getByName(name)
						+ " "
						+ phonebookDAO.getCompanyByContact(phonebookDAO
								.getByName(name)));
		System.out.println();
	}

	private static void updateContact(PhonebookDAO phonebookDAO) {
		System.out.println("Enter name:");
		String name = scan.next();
		System.out.println("Enter a new number:");
		String newNumber = scan.next();
		phonebookDAO.update(name, newNumber);
	}

	private static void createContact(PhonebookDAO phonebookDAO) {

		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		try {

			System.out.println("Enter name:");
			String name = scan.next();

			System.out.println("Enter number:");
			String number = scan.next();


			System.out.println("(true or false) VIP?");
			boolean ifVIP = scan.nextBoolean();
			

			System.out.println("Enter birthday: mm/dd/yyyy");
			String birthday = scan.next();
			Date bday = df.parse(birthday);
			
			System.out.println("Enter company code :");
			long companyId = scan.nextInt();
			
			Contact contact = new Contact.Builder(name, number, ifVIP, companyId).birthday(bday).build();
			phonebookDAO.create(contact);	

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void deleteContact(PhonebookDAO phonebookDAO) {
		System.out.println("Enter name to delete: ");
		String nameToDelete = scan.next();
		phonebookDAO.delete(nameToDelete);
	}

	private static void getListOfContacts(PhonebookDAO phonebookDAO) {
		contacts = phonebookDAO.contactList();
		for (Contact con : contacts) {
			System.out.println(con + " "
					+ phonebookDAO.getCompanyByContact(con));
		}
		System.out.println();
	}

}
