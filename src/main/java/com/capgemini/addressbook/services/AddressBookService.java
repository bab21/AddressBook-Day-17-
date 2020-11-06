package com.capgemini.addressbook.services;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.capgemini.addressbook.exception.AddressBookException;
import com.capgemini.addressbook.model.AddressBook;
import com.capgemini.addressbook.model.Contact;

public class AddressBookService {
	private AddressBookDBService addressBookDBService;
	public List<Contact> allContacts;
	
	public static Map<String,AddressBook> addressBookDirectory= new HashMap<String, AddressBook>(); 
	public static Map<String,List<Contact>> cityToContact= new HashMap<String,List<Contact>>(); 
	public static Map<String,List<Contact>> stateToContact= new HashMap<String,List<Contact>>(); 
	
	public AddressBookService() throws IOException{
		addressBookDBService=AddressBookDBService.getInstance();
		AddressBook addressBook1=new AddressBook("book1","Family");
		AddressBook addressBook2=new AddressBook("book2","Friend");
		AddressBook addressBook3=new AddressBook("book3","Family");
		addressBookDirectory.put("book1",addressBook1);
		addressBookDirectory.put("book2",addressBook2);
		addressBookDirectory.put("book3",addressBook3);
		
	}
	public AddressBookService(List<Contact> contactList) throws IOException {
		this();
		allContacts=contactList;
	}
	public void addContact(String firstName,String lastName,String address,String city,String state,int zip,long phoneNumber,String email,Contact.ContactType contactType,int addressBookId,LocalDate dateAdded) {
		
		Contact contact=new Contact(firstName,lastName,address,city,state,zip,phoneNumber,email,contactType,dateAdded);
		
		if(!addressBookDBService.checkContactExits(contact)) {
			addressBookDBService.addContact(contact,addressBookId);
		}
		else {
			System.out.println("Contact already exits");
			return;
		}
	
		if(cityToContact.containsKey(city)) {
			cityToContact.get(city).add(contact);
		}
		else {
			cityToContact.put(city, new ArrayList<Contact>());
			cityToContact.get(city).add(contact);
		}

		if(stateToContact.containsKey(state)){
			contact=new Contact(firstName,lastName,address,city,state,zip,phoneNumber,email,contactType);
			stateToContact.get(contact.getState()).add(contact);
		}
		else {
			stateToContact.put(state, new ArrayList<Contact>());
			stateToContact.get(state).add(contact);	
		}
		
	}
	public void editContact(String firstName, String addressBookName) throws SQLException {
		Scanner s=new Scanner(System.in);
		int index=0;
		int i,n;
		
		while(true) {
			System.out.println("Choose Any One");
			System.out.println("1.Edit Last name");
			System.out.println("2.Edit Address");
			System.out.println("3.Edit City");
			System.out.println("4.Edit State");
			System.out.println("5.Edit Zip");
			System.out.println("6.Edit Phone Number");
			System.out.println("7.Edit Email");
			System.out.println("8.Exit");
			int choice=s.nextInt();
			
			if(choice==8)
				return;
			
			switch(choice) {
				case 1:System.out.println("Enter last name for editing");
					   String lastName=s.next();
					   addressBookDBService.updateContactWithLastName(firstName,lastName);
					   break;
				case 2:System.out.println("Enter Address for editing");
				       String address=s.next();
				       addressBookDBService.updateContactWithAddress(firstName,address);
				   	   break;
				case 3:System.out.println("Enter city for editing");
				       String city=s.next();
				       addressBookDBService.updateContactWithCity(firstName,city);
					   break;
				case 4:System.out.println("Enter state for editing");
				       String state=s.next();
				       addressBookDBService.updateContactWithState(firstName,state);
					   break;
				case 5:System.out.println("Enter Zip for editing");
				       int zip=s.nextInt();
				       addressBookDBService.updateContactWithZip(firstName,zip);
					   break;
				case 6:System.out.println("Enter Phone Number for editing");
				       long phoneNumber=s.nextLong();
				       addressBookDBService.updateContactWithPhoneNumber(firstName,phoneNumber);
					   break;
				case 7:System.out.println("Enter email for editing");
				       String email=s.next();
				       addressBookDBService.updateContactWithEmail(firstName,email);
					   break;   
			}
		}	
	}
	public void deleteContact(String firstName,String addressBookName) {
		List<Contact> contactList=addressBookDirectory.get(addressBookName).contactList;
		for(int i=0;i<contactList.size();i++) {
			if(contactList.get(i).getFirstName().equals(firstName)) {
				contactList.remove(i);
			}
		}
		addressBookDBService.deleteContact(firstName);
		return ;
	}
	public List<Contact> readContacts(String addressBookName) throws AddressBookException{
		addressBookDirectory.get(addressBookName).contactList=addressBookDBService.readContacts(addressBookName);
		return addressBookDirectory.get(addressBookName).contactList;
	}
	public List<Contact> getContactInDateRange(LocalDate startDate,LocalDate endDate){
		return addressBookDBService.getContactInDateRange(startDate,endDate);
	}
	public List<Contact> getSortedContactListByName(int addressBookId) {
		 return addressBookDBService.SortedContactByFirstName(addressBookId);
	}
	public List<Contact> getSortedContactListByCity(int addressBookId){
		return addressBookDBService.getSortedContactByCity(addressBookId);
	}
	public List<Contact> getSortedContactListByState(int addressBookId){
		return addressBookDBService.getSortedContactByState(addressBookId);
	}
	public List<Contact> getSortedContactListByZip(int addressBookId) {
		return addressBookDBService.getSortedContactByZip(addressBookId);

	}
	public boolean checkAddressBookDataInSyncWithDB(String firstName,String addressBookName) throws AddressBookException  {
		List<Contact> contactList=addressBookDBService.getContactData(firstName,addressBookName);
		System.out.println("returned "+contactList);
		System.out.println("value in list"+getContactData(firstName,addressBookName));
		return contactList.get(0).equals(getContactData(firstName,addressBookName));
	}
	private Contact getContactData(String firstName,String addressBookName) {
		return addressBookDirectory.get(addressBookName).contactList.stream()
				.filter(contactListItem->contactListItem.getFirstName().equals(firstName))
				.findFirst()
				.orElse(null);
	}
	public List<Contact> getContactsByCity(String city) {
		return addressBookDBService.getContactByCity(city);
	}
	public void addContactList(List<Contact> contacts,String addressBookName) {
		Map<Integer,Boolean> contactAdditionStatus=new HashMap<Integer,Boolean>();
		contacts.forEach(contact->{
			Runnable task=()->{
				System.out.println("Contact Being Added: "+Thread.currentThread().getName());
				try {
					this.addContactToDB(contact,addressBookName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				contactAdditionStatus.put(contact.hashCode(), true);
				System.out.println("Contact Added"+Thread.currentThread().getName());
				
			};
			Thread thread=new Thread(task,contact.getFirstName());
			thread.start();
		});
		while(contactAdditionStatus.containsValue(false)) {
			try{Thread.sleep(10);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addContactToDB(Contact contact,String addressBookName) {
		addressBookDBService.addContactToDB(contact);
		addressBookDirectory.get(addressBookName).contactList.add(contact);
	}
	public int countEntries() {
		return allContacts.size();
	}
	public void addContact(Contact contact) {
		try {
			this.allContacts.add(contact);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
