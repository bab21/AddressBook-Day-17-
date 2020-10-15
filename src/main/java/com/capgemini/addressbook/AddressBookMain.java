package com.capgemini.addressbook;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain {
	public static Map<String,List<Contact>> cityToContact= new HashMap<String,List<Contact>>(); 
	public static Map<String,List<Contact>> stateToContact= new HashMap<String,List<Contact>>(); 
	
	public static void main(String[] args) {
		
		
		System.out.println("Welcome to Address Book Program.");
		Scanner s=new Scanner(System.in);
		
		
		do {
			System.out.println("1. Add a person details in particular address book");
			System.out.println("2. Edit a person details in particular address book");
			System.out.println("3. Delete a person details in particular address book");
			System.out.println("4. Show all contacts in particular address book");
			System.out.println("5. Create a new  address book");
			System.out.println("6. Search Contact by City Name");
			System.out.println("7. Search Contact by State name");
			System.out.println("8. Number of Persons by City Name");
			System.out.println("9. Number of Persons by State Name");
			System.out.println("10.Get sorted contacts By Name in a address book");
			System.out.println("11.Get sorted contacts By City in a address book");
			System.out.println("12.Get sorted contacts By State in a address book");
			System.out.println("13.Get sorted contacts By Zip in a address book");
			System.out.println("14. Exit");
			
			System.out.println("Enter your choice");
			int choice=s.nextInt();
			if(choice==14)
				break;
			
			switch(choice) {
			case 1:showAllAddressBooksName();
				   System.out.println("Enter the name of particular address book to add person details");
				   String addressBook_name1=s.next();
				   AddressBook addressbook1=AddressBook.hm.get(addressBook_name1);
				   Contact contact=addressbook1.createContact();
				   addressbook1.addContact(contact);
				   break;
			case 2:showAllAddressBooksName();
			       System.out.println("Enter the name of particular address book to edit person details");
			       String addressBook_name2=s.next();
			       AddressBook addressbook2=AddressBook.hm.get(addressBook_name2);
			       addressbook2.editContact();
			       System.out.println("Editing done");
			       break;
			case 3:showAllAddressBooksName();
			   	   System.out.println("Enter the name of particular address book to delete person details");
			       String addressBookName3=s.next();
			       AddressBook addressbook3=AddressBook.hm.get(addressBookName3);
			       addressbook3.deleteContact();
			       System.out.println("deletion done");
			       break;
			case 4:showAllAddressBooksName();
		   	       System.out.println("Enter the name of particular address book to show all contact details");
		           String addressBookName4=s.next();
		           AddressBook addressbook4=AddressBook.hm.get(addressBookName4);
		           addressbook4.showContacts();
		           break;
			case 5:System.out.println("Enter name of address book to be created");
				   String name=s.next();
				   try {
				   AddressBook addressBook=new AddressBook(name);
				   AddressBook.hm.put(name,addressBook);}
				   catch(IOException e) {
					   System.out.println("Error while creating address book");
				   }
				   System.out.println("Address Book created successfully");
				   
				   break;
			case 6:System.out.println("Enter the city name");
				   String city=s.next();
				   searchByCityName(city);
				   break;
			case 7:System.out.println("Enter the state name");
			       String state=s.next();
			       searchByStateName(state);
			       break;
			case 8:System.out.println("Enter city name");
				   System.out.println(cityToContact.get(s.next()).size());
				   break;
			case 9:System.out.println("Enter state name");
				   System.out.println(stateToContact.get(s.next()).size());
				   break;
			case 10:showAllAddressBooksName();
				    System.out.println("Enter address book");
				    AddressBook.getSortedContactListByName(s.next());
				    break;
			case 11:showAllAddressBooksName();
				    System.out.println("Enter address book");
				    AddressBook.getSortedContactListByCity(s.next());
				    break;
			case 12:showAllAddressBooksName();
				    System.out.println("Enter address book");
				    AddressBook.getSortedContactListByState(s.next());
				    break;
			case 13:showAllAddressBooksName();
				    System.out.println("Enter address book");
				    AddressBook.getSortedContactListByZip(s.next());
				    break;
		    
		    default:System.out.println("Do nothing");	
		    	    break;
			}	
		}
	    while(true);
		
		
	}
	
	public static void showAllAddressBooksName() {
		
		for (Map.Entry<String,AddressBook> entry : AddressBook.hm.entrySet())  
            System.out.println(entry.getKey()); 
	}
	
	public static void searchByCityName(String city) {
		
		List<Contact> contacts=cityToContact.get(city);
		contacts.stream().map(contact -> contact.toString()).forEach(System.out::println); 
	}
	
	public static void searchByStateName(String state) {
		
		List<Contact> contacts=stateToContact.get(state);
		contacts.stream().map(contact -> contact.toString()).forEach(System.out::println); 	
	}
	
	

}
