package com.capgemini.addressbook;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class AddressBookServiceTest {

	@Test
	public void givenDatabase_readContacts_ShouldMatch() {
		AddressBook addressBook=new AddressBook();
		List<Contact> contactList=addressBook.readContacts();
		assertEquals(3,contactList.size());
	}
	

}
