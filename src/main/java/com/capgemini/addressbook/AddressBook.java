package com.capgemini.addressbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import com.capgemini.addressbook.Contact.ContactType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class AddressBook {
	private ArrayList<Contact> contactList;
	private String addressBookName;
	private AddressBookType addressBookType;
	
    private AddressBookDBService addressBookDBService;
    
	public enum AddressBookType{
		FAMILY,FRIEND;
	}
	
	public static Map<String,AddressBook> addressBookDirectory= new HashMap<String, AddressBook>(); 
	public static Map<String,List<Contact>> cityToContact= new HashMap<String,List<Contact>>(); 
	public static Map<String,List<Contact>> stateToContact= new HashMap<String,List<Contact>>(); 
	
	
	public AddressBook(){
		contactList = new ArrayList<Contact>();
		addressBookDBService=AddressBookDBService.getInstance();
	}
	public AddressBook(String addressBookName)  throws IOException{
		this();
		this.addressBookName=addressBookName;
		addressBookDBService=AddressBookDBService.getInstance();
	}

	public void addContact(String firstName,String lastName,String address,String city,String state,int zip,long phoneNumber,String email,Contact.ContactType contactType,int addressBookId) {
		
		Contact contact=new Contact(firstName,lastName,address,city,state,zip,phoneNumber,email,contactType);
		
		if(!addressBookDBService.checkContactExits(contact)) {
			addressBookDBService.addContact(contact,addressBookId);
		}
		else {
			System.out.println("Contact already exits");
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
	public void editContact(String firstName) {
		Scanner s=new Scanner(System.in);
		int index=0;
		int i,n;
		
		n=contactList.size();
		
		for(i=0;i<n;i++) {
			if(contactList.get(i).getFirstName().equals(firstName))
				index=i;
			
		}
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
					   contactList.get(index).setLast_Name(lastName);
					   addressBookDBService.updateContactWithLastName(firstName,lastName);
					   break;
				case 2:System.out.println("Enter Address for editing");
				       String address=s.next();
				       contactList.get(index).setAddress(address);
				       addressBookDBService.updateContactWithAddress(firstName,address);
				   	   break;
				case 3:System.out.println("Enter city for editing");
				       String city=s.next();
				       contactList.get(index).setCity(city);
				       addressBookDBService.updateContactWithCity(firstName,city);
					   break;
				case 4:System.out.println("Enter state for editing");
				       String state=s.next();
				       contactList.get(index).setState(state);
				       addressBookDBService.updateContactWithState(firstName,state);
					   break;
				case 5:System.out.println("Enter Zip for editing");
				       int zip=s.nextInt();
				       contactList.get(index).setZip(zip);
				       addressBookDBService.updateContactWithZip(firstName,zip);
					   break;
				case 6:System.out.println("Enter Phone Number for editing");
				       long phoneNumber=s.nextLong();
				       contactList.get(index).setPhoneNumber(phoneNumber);
				       addressBookDBService.updateContactWithPhoneNumber(firstName,phoneNumber);
					   break;
				case 7:System.out.println("Enter email for editing");
				       String email=s.next();
				       contactList.get(index).setEmail(email);
				       addressBookDBService.updateContactWithEmail(firstName,email);
					   break;   
			}
		}	
	}
	public void deleteContact(String firstName) {
		int index=0;
		int i,n;
		n=contactList.size();
		
		for(i=0;i<n;i++) {
			if(contactList.get(i).getFirstName().equals(firstName))
				index=i;
			
		}
		contactList.remove(index);
		addressBookDBService.deleteContact(firstName);
		return ;
	}
	public List<Contact> readContacts(){
		return addressBookDBService.readContacts();
	}
	public List<Contact> getSortedContactListByName(String addressBookName) {
		 return addressBookDBService.SortedContactByFirstName(addressBookName);
	}
	public List<Contact> getSortedContactListByCity(String addressBookName){
		return addressBookDBService.getSortedContactByCity(addressBookName);
	}
	public List<Contact> getSortedContactListByState(String AddressBookName){
		return addressBookDBService.getSortedContactByState(addressBookName);
	}
	public List<Contact> getSortedContactListByZip(String addressBookName) {
		return addressBookDBService.getSortedContactByZip(addressBookName);

	}
	
}
