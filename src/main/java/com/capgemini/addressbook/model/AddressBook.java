package com.capgemini.addressbook.model;

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
import java.sql.SQLException;
import java.util.stream.IntStream;
import java.time.LocalDate;

import com.capgemini.addressbook.exception.AddressBookException;
import com.capgemini.addressbook.model.Contact.ContactType;
import com.capgemini.addressbook.services.AddressBookDBService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class AddressBook{
	public List<Contact> contactList;
	private String addressBookName;
	private AddressBookType addressBookType;
	
    private AddressBookDBService addressBookDBService;
    
	public enum AddressBookType{
		Family,Friend;
	}
	public AddressBook(){
		contactList = new ArrayList<Contact>();
		addressBookDBService=AddressBookDBService.getInstance();
	}
	public AddressBook(List<Contact> contactList) {
		this.contactList=contactList;
	}
	public AddressBook(String addressBookName)  throws IOException{
		this();
		this.addressBookName=addressBookName;
	}
	public AddressBook(String addressBookName,String addressBookType) throws IOException {
		this(addressBookName);
		this.addressBookType=AddressBookType.valueOf(addressBookType);
	}
    public int countEntries() {
    	return contactList.size();
    }
}
