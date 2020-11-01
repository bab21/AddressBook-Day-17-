package com.capgemini.addressbook;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

public class AddressBookServiceTest {

	@Test
	public void givenDatabase_readContacts_ShouldMatch() {
		AddressBook addressBook=new AddressBook();
		List<Contact> contactList=addressBook.readContacts();
		assertEquals(3,contactList.size());
	}
	
	@Test
	public void givenContact_WhenUpdated_ShouldSync() throws SQLException {
		AddressBook addressBook=new AddressBook();
		addressBook.editContact("Nancy");
		List<Contact> contactList=addressBook.readContacts();
		System.out.println(contactList);
		boolean ans=addressBook.checkAddressBookDataInSyncWithDB("Nancy");
		assertTrue(ans);
	}
	@Test
	public void givenContact_WhenDeleted_SHouldMatch() {
		AddressBook addressBook=new AddressBook();
		List<Contact> contactList=addressBook.readContacts();
		System.out.println("Data list size before deletion"+contactList.size());
		addressBook.deleteContact("Nancy");
		contactList=addressBook.readContacts();
		System.out.println("Size after deletion"+contactList.size());
		
	}
	@Test
	public void givenAddressBookId_getContactsSortedByCity_ShouldMatch() {
		AddressBook addressBook=new AddressBook();
		List<Contact> contactList=addressBook.getSortedContactListByCity(4);
		assertTrue(contactList.get(0).getCity().equals("Kolkata"));
	}

}
