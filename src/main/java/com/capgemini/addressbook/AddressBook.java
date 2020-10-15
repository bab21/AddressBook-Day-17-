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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class AddressBook {
	
	public static Map<String,AddressBook> hm= new HashMap<String, AddressBook>(); 
	public static String HOME=System.getProperty("user.dir");
	
	
	ArrayList<Contact> contactList;
	String addressBookName;
	String ADDRESS_BOOK_FILE;
	File file;
	
	public AddressBook() {
		contactList = new ArrayList<Contact>();
	}
	public AddressBook(String addressBookName)  throws IOException{
		contactList = new ArrayList<Contact>();
		this.addressBookName=addressBookName;
		this.ADDRESS_BOOK_FILE=HOME+"/"+addressBookName+".json";
		Path addressBookPath=Paths.get(ADDRESS_BOOK_FILE);
		try {
			Files.createFile(addressBookPath);
		}
		catch(IOException e) {
			System.out.println("File not accessible");
		}		
	}
	
   
	public  Contact createContact(){
        System.out.println("Enter details for creating and adding contact");
		Scanner s=new Scanner(System.in);
		
		System.out.println("Enter first name");
		String first_name=s.next();
		System.out.println("Enter last name");
		String lastName=s.next();
		System.out.println("Enter address");
		String address=s.next();
		System.out.println("Enter city name");
		String city=s.next();
		System.out.println("Enter state name");
		String state=s.next();
		System.out.println("Enter email address");
		String email=s.next();
		System.out.println("Enter phone number");
		long phone_number=s.nextLong();
		System.out.println("Enter zip code");
		int zip=s.nextInt();
		
		Contact contact=new Contact(first_name,lastName,address,city,state,zip,phone_number,email);
		
		
		return contact;
	}
	public void updateAddressBookFile() {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(contactList);
			FileWriter writer = new FileWriter(ADDRESS_BOOK_FILE);
			writer.write(json);
			writer.close();
		}
		catch(Exception e) {
			
		}
	}
	
	public void addContact(Contact contact) {
		List<Contact> result = contactList.stream().filter(c->c.equals(contact)).collect(Collectors.toList()); 
		if(result.size()>0)
		{
			System.out.println("This contact already exits in this particular address book");
		}
		else{
			this.contactList.add(contact);
			updateAddressBookFile();	 
		}
		
		
		if(AddressBookMain.cityToContact.containsKey(contact.getCity())) {
			AddressBookMain.cityToContact.get(contact.getCity()).add(contact);
		}
		else {
			AddressBookMain.cityToContact.put(contact.getCity(), new ArrayList<Contact>());
			AddressBookMain.cityToContact.get(contact.getCity()).add(contact);
		}
		
		if(AddressBookMain.stateToContact.containsKey(contact.getState()))
			AddressBookMain.stateToContact.get(contact.getState()).add(contact);
		else {
			AddressBookMain.stateToContact.put(contact.getState(), new ArrayList<Contact>());
			AddressBookMain.stateToContact.get(contact.getState()).add(contact);	
		}
		updateAddressBookFile();
		
	}
	public void editContact() {
		
		Scanner s=new Scanner(System.in);
		System.out.println("Enter first name of contact to be edited");
		String first_name=s.next();
		
		int index=0;
		int i,n;
		
		n=contactList.size();
		
		for(i=0;i<n;i++) {
			if(contactList.get(i).getFirstName().equals(first_name))
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
					   contactList.get(index).setLast_Name(s.next());
					   break;
				case 2:System.out.println("Enter Address for editing");
				       contactList.get(index).setAddress(s.next());
				   	   break;
				case 3:System.out.println("Enter city for editing");
				       contactList.get(index).setCity(s.next());
					   break;
				case 4:System.out.println("Enter state for editing");
				       contactList.get(index).setState(s.next());
					   break;
				case 5:System.out.println("Enter Zip for editing");
				       contactList.get(index).setZip(s.nextInt());
					   break;
				case 6:System.out.println("Enter Phone Number for editing");
				       contactList.get(index).setPhoneNumber(s.nextLong());
					   break;
				case 7:System.out.println("Enter email for editing");
				       contactList.get(index).setEmail(s.next());
					   break;
			   	   
			}
			updateAddressBookFile();
		}	
	}
	public void deleteContact() {
		int index=0;
		int i,n;
		Scanner s=new Scanner(System.in);
		System.out.println("Enter name of contact to be deleted");
		String first_name=s.next();
		n=contactList.size();
		
		for(i=0;i<n;i++) {
			if(contactList.get(i).getFirstName().equals(first_name))
				index=i;
			
		}
		contactList.remove(index);
		updateAddressBookFile();
		return ;
	}
	public void showContacts(){
		
		    try {
			Gson gson = new Gson();
			BufferedReader br = new BufferedReader(new FileReader(ADDRESS_BOOK_FILE));
			Contact[] contact = gson.fromJson(br, Contact[].class);
			List<Contact> contactList = Arrays.asList(contact);
			for (Contact c : contactList) {
				System.out.println("FirstName : " + c.getFirstName());
				System.out.println("LastName : " + c.getLast_Name());
				System.out.println("Address : " + c.getAddress());
				System.out.println("City : " + c.getCity());
				System.out.println("State : " + c.getState());
				System.out.println("Zip : " + c.getZip());
				System.out.println("Phone Number : " + c.getPhoneNumber());
				System.out.println("Email : " + c.getEmail());
				System.out.println("**********************************");
			}
		    }
		    catch(Exception e) {
		    	e.printStackTrace();
		    }
	}
	public  static void getSortedContactListByName(String AddressBookName) {
		 hm.get(AddressBookName).contactList
		 .stream() 
         .sorted((c1, c2)->c1.getFirstName().compareTo(c2.getFirstName())) 
         .map(contact->contact.toString())
         .forEach(System.out::println); 
		
	}
	public static void getSortedContactListByCity(String AddressBookName){
		hm.get(AddressBookName).contactList
		 .stream() 
        .sorted((c1, c2)->c1.getCity().compareTo(c2.getCity())) 
        .map(contact->contact.toString())
        .forEach(System.out::println); 
	}
	public static void getSortedContactListByState(String AddressBookName){
		hm.get(AddressBookName).contactList
		 .stream() 
        .sorted((c1, c2)->c1.getState().compareTo(c2.getState())) 
        .map(contact->contact.toString())
        .forEach(System.out::println); 
	}
	public static void getSortedContactListByZip(String AddressBookName) {
		hm.get(AddressBookName).contactList
		 .stream() 
       .sorted((c1, c2)->((Integer)c1.getZip()).compareTo((Integer)c2.getZip())) 
       .map(contact->contact.toString())
       .forEach(System.out::println); 
	}
	
}
