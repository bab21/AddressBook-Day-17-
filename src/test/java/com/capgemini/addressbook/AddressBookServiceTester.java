package com.capgemini.addressbook;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.addressbook.exception.AddressBookException;
import com.capgemini.addressbook.model.AddressBook;
import com.capgemini.addressbook.model.Contact;
import com.capgemini.addressbook.services.AddressBookService;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookServiceTester {
	AddressBookService addressBookService;
	@Before
	public void setUp() throws IOException {
		addressBookService=new AddressBookService();
		RestAssured.baseURI="http://localhost";
		RestAssured.port=3000;
	}
	
	public  Contact[] getContactList() {
		Response response=RestAssured.get("/contact");
		System.out.println("Contact Data at jsonserver:\n"+response.asString());
		Contact[] arrayOfContact=new Gson().fromJson(response.asString(),Contact[].class);
		System.out.println(arrayOfContact[0].getId());
		return arrayOfContact;
	}
	//UC22 Json Server....
//	@Test
//	public void givenContacts_ReadContactFromJson_ShouldMatchSize() throws IOException {
//		Contact[] contactList=getContactList();
//		AddressBookService  addressBookService=new AddressBookService(Arrays.asList(contactList));
//		int entries=addressBookService.countEntries();
//		assertEquals(3,entries);
//	}
	//UC23 Json Server...
//	@Test
//	public void givenMultipleContact_WhenAddedToJsonServer_ShouldMatchSize() throws IOException {
//		List<Contact> contactList=new ArrayList<Contact>(Arrays.asList(this.getContactList()));
//		AddressBookService addressBookService=new AddressBookService(contactList);
//		LocalDate dateAdded=LocalDate.now();
//		Contact[] contacts= {
//				new Contact("Surbhi", "Singh", "Indrapuri", "Patna", "Bihar", 800724, 7766554433L, "alisha@gmail", Contact.ContactType.Friend,dateAdded),
//				new Contact("Chinki", "Singh", "Indrapuri", "Patna", "Bihar", 800624, 7766599433L, "alisha@gmail", Contact.ContactType.Friend,dateAdded)
//		};
//		for(int i=0;i<contacts.length;i++) {
//			Response response=addContactToJsonServer(contacts[i]);
//			int statusCode=response.getStatusCode();
//			assertEquals(201,statusCode);
//			
//			contacts[i]=new Gson().fromJson(response.asString(), Contact.class);
//			addressBookService.addContact(contacts[i]);
//		}
//		long entries=addressBookService.countEntries();
//		assertEquals(11,entries);	
//	}
	
//	@Test
//	public void givenNewCityForContact_WhenUpdated_ShouldMatch200Response() throws IOException {
//		List<Contact> contactList=new ArrayList<Contact>(Arrays.asList(this.getContactList()));
//		AddressBookService addressBookService;
//		System.out.println("contact name:"+contactList.get(6).firstName);
//		addressBookService=new AddressBookService(contactList);
//		
//		addressBookService.updateWithCity("Chinki","Mumbai");
//		Contact contact=addressBookService.getContact("Chinki");
//		
//		String contactJson=new Gson().toJson(contact);
//		RequestSpecification request=RestAssured.given();
//		request.header("Content-Type","application/json");
//		request.body(contactJson);
//		Response response=request.put("/contact/"+contact.getId());
//		int statusCode=response.getStatusCode();
//		assertEquals(200,statusCode);
//		
//	}
	
	@Test
	public void givenContactToDelete_WhenDeleted_ShouldMatch200ResponseAndCount() throws IOException {
		List<Contact> contactList=new ArrayList<Contact>(Arrays.asList(this.getContactList()));
		AddressBookService addressBookService;
		addressBookService=new AddressBookService(contactList);
		int sizeBeforeDeletion=contactList.size();
		
		Contact contact=addressBookService.getContact("Ram");
		RequestSpecification request=RestAssured.given();
		request.header("Content-Type","application/json");
		Response response=request.delete("/contact/"+contact.getId());
		int statusCode=response.getStatusCode();
		assertEquals(200,statusCode);
		
		int sizeAfterDeletion=this.getContactList().length;
		addressBookService.deleteContact(contact.firstName);
		long entries=addressBookService.countEntries();
		assertTrue(sizeBeforeDeletion==sizeAfterDeletion+1);
		
	}
	private Response addContactToJsonServer(Contact contact) {
		String contactJson=new Gson().toJson(contact);
		RequestSpecification request=RestAssured.given();
		request.header("Content-Type","application/json");
		request.body(contactJson);
		return request.post("/contact");
	}
//	@Test
//	public void givenDatabase_readContacts_ShouldMatch() throws AddressBookException, IOException {
//		List<Contact> contactList=addressBookService.readContacts("book1");
//		System.out.println(contactList);
//		assertEquals(14,contactList.size());
//	}
	
//	@Test
//	public void givenContactName_WhenUpdated_ShouldSyncWithDB() throws SQLException, AddressBookException {
//		addressBookService.editContact("Nisha","book1");
//		List<Contact> contactList=addressBookService.readContacts("book1");
//		boolean ans=addressBookService.checkAddressBookDataInSyncWithDB("Nisha","book1");
//		assertTrue(ans);
//	}
//	@Test
//	public void givenContact_WhenDeleted_SHouldMatch() throws AddressBookException {
//		List<Contact> contactList=addressBookService.readContacts("book1");
//		System.out.println("Data list size before deletion"+contactList.size());
//		addressBookService.deleteContact("Nisha","book1");
//		contactList=addressBookService.readContacts("book1");
//		System.out.println("Size after deletion"+contactList.size());
//		
//	}
//	@Test
//	public void givenAddressBookId_getContactsSortedByCity_ShouldMatch() {
//		List<Contact> contactList=addressBookService.getSortedContactListByCity(4);
//		assertTrue(contactList.get(0).getCity().equals("Kolkata"));
//	}
//	
//	@Test
//	public void givenDateRange_WhenContactDataRetrieved_ShouldMatch(){
//		LocalDate startDate=LocalDate.of(2018, 07, 01);
//		LocalDate endDate=LocalDate.now();
//		List<Contact> contactList=addressBookService.getContactInDateRange(startDate, endDate);
//		assertEquals(12,contactList.size());
//	}
//	@Test
//	public void givenCity_WhenContactDataRetrieved_ShouldMatch() {
//		List<Contact> contactList=addressBookService.getContactsByCity("Patna");
//		assertEquals(13,contactList.size());
//	}
//	@Test
//	public void givenContact_WhenAdded_ShouldShowSizeIncrease() throws AddressBookException {
//	
//		int sizeBeforeAdd=addressBookService.readContacts("book2").size();
//		System.out.println("size before"+sizeBeforeAdd);
//		LocalDate dateAdded=LocalDate.now();
//		addressBookService.addContact("Seeta", "Singh", "Indrapuri", "Patna", "Bihar", 800024, 7766554433L, "alisha@gmail", Contact.ContactType.Friend, 6, dateAdded);
//		int sizeAfterAdd=addressBookService.readContacts("book2").size();
//		System.out.println("size after"+sizeAfterAdd);
//		assertTrue(sizeBeforeAdd==sizeAfterAdd-1);
//	}
//	@Test
//	public void givenMultipleContacts_WhenAdded_ShouldShowSizeIncrease() throws AddressBookException {
//		
//		int sizeBeforeAdd=addressBookService.readContacts("book2").size();
//		System.out.println("size before"+sizeBeforeAdd);
//		
//		LocalDate dateAdded=LocalDate.now();
//		Contact[] contactList= {
//				new Contact("Rinky", "Singh", "Indrapuri", "Patna", "Bihar", 800724, 7766554433L, "alisha@gmail", Contact.ContactType.Friend,dateAdded),
//				new Contact("Reta", "Singh", "Indrapuri", "Patna", "Bihar", 800624, 7766599433L, "alisha@gmail", Contact.ContactType.Friend,dateAdded),
//		};
//		addressBookService.addContactList(Arrays.asList(contactList),"book2");
//		int sizeAfterAdd=addressBookService.readContacts("book2").size();
//		System.out.println("size after"+sizeAfterAdd);
//		
//		assertTrue(sizeBeforeAdd==sizeAfterAdd-2);
//	}
}
