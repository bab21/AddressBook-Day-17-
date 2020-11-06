package com.capgemini.addressbook;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.addressbook.exception.AddressBookException;
import com.capgemini.addressbook.model.Contact;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddressBookServiceTest {
//	@Before
//	public void setUp() {
//		RestAssured.baseURI="http://localhost";
//		RestAssured.port=3000;
//	}
//	
//	public  Contact[] getContactList() {
//		Response response=RestAssured.get("/contact");
//		System.out.println("Contact Data at jsonserver:\n"+response.asString());
//		Contact[] arrayOfContact=new Gson().fromJson(response.asString(),Contact[].class);
//		return arrayOfContact;
//	}
//	//UC22 Json Server....
//	@Test
//	public void givenContacts_ReadContactFromJson_ShouldMatchSize() {
//		Contact[] contactList=getContactList();
//		AddressBook addressBook=new AddressBook(Arrays.asList(contactList));
//		int entries=addressBook.countEntries();
//		assertEquals(3,entries);
//	}
//	@Test
//	public void givenDatabase_readContacts_ShouldMatch() throws AddressBookException {
//		AddressBook addressBook=new AddressBook();
//		List<Contact> contactList=addressBook.readContacts();
//		assertEquals(3,contactList.size());
//	}
//	
//	@Test
//	public void givenContactName_WhenUpdated_ShouldSyncWithDB() throws SQLException, AddressBookException {
//		AddressBook addressBook=new AddressBook();
//		addressBook.editContact("Nancy");
//		List<Contact> contactList=addressBook.readContacts();
//		boolean ans=addressBook.checkAddressBookDataInSyncWithDB("Nancy");
//		assertTrue(ans);
//	}
//	@Test
//	public void givenContact_WhenDeleted_SHouldMatch() throws AddressBookException {
//		AddressBook addressBook=new AddressBook();
//		List<Contact> contactList=addressBook.readContacts();
//		System.out.println("Data list size before deletion"+contactList.size());
//		addressBook.deleteContact("Nancy");
//		contactList=addressBook.readContacts();
//		System.out.println("Size after deletion"+contactList.size());
//		
//	}
//	@Test
//	public void givenAddressBookId_getContactsSortedByCity_ShouldMatch() {
//		AddressBook addressBook=new AddressBook();
//		List<Contact> contactList=addressBook.getSortedContactListByCity(4);
//		assertTrue(contactList.get(0).getCity().equals("Kolkata"));
//	}
//	
//	@Test
//	public void givenDateRange_WhenContactDataRetrieved_ShouldMatch(){
//		AddressBook addressBook=new AddressBook();
//		LocalDate startDate=LocalDate.of(2018, 07, 01);
//		LocalDate endDate=LocalDate.now();
//		List<Contact> contactList=addressBook.getContactInDateRange(startDate, endDate);
//		assertEquals(2,contactList.size());
//	}
//	@Test
//	public void givenCity_WhenContactDataRetrieved_ShouldMatch() {
//		AddressBook addressBook=new AddressBook();
//		List<Contact> contactList=addressBook.getContactsByCity("Patna");
//		assertEquals(2,contactList.size());
//	}
//	@Test
//	public void givenContact_WhenAdded_ShouldShowSizeIncrease() throws AddressBookException {
//		AddressBook addressBook=new AddressBook();
//		int sizeBeforeAdd=addressBook.readContacts().size();
//		LocalDate dateAdded=LocalDate.now();
//		addressBook.addContact("Alisha", "Sinha", "Indrapuri", "Patna", "Bihar", 800024, 7766554433L, "alisha@gmail", Contact.ContactType.Friend, 6, dateAdded);
//		int sizeAfterAdd=addressBook.readContacts().size();
//		assertTrue(sizeBeforeAdd==sizeAfterAdd-1);
//	}
//	@Test
//	public void givenMultipleContacts_WhenAdded_ShouldShowSizeIncrease() throws AddressBookException {
//		AddressBook addressBook=new AddressBook();
//		int sizeBeforeAdd=addressBook.readContacts().size();
//		System.out.println("size before"+sizeBeforeAdd);
//		
//		LocalDate dateAdded=LocalDate.now();
//		Contact[] contactList= {
//				new Contact("Minni", "Singh", "Indrapuri", "Patna", "Bihar", 800724, 7766554433L, "alisha@gmail", Contact.ContactType.Friend,dateAdded),
//				new Contact("Mack", "Singh", "Indrapuri", "Patna", "Bihar", 800624, 7766599433L, "alisha@gmail", Contact.ContactType.Friend,dateAdded),
//		};
//		addressBook.addContactList(Arrays.asList(contactList));
//		int sizeAfterAdd=addressBook.readContacts().size();
//		System.out.println("size after"+sizeAfterAdd);
//		
//		assertTrue(sizeBeforeAdd==sizeAfterAdd-2);
//	}
}
