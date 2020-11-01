package com.capgemini.addressbook;

import java.time.LocalDate;

public class Contact {
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private int zip;
	private long phoneNumber;
	private String email;
	private ContactType contactType;
	private LocalDate dateAdded;
	
	public enum ContactType{
		Family,Friend;
	}
	public Contact(String firstName,String lastName,String address,String city,String state,int zip,long phoneNumber,String email,ContactType contactType) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.address=address;
		this.city=city;
		this.state=state;
		this.zip=zip;
		this.phoneNumber=phoneNumber;
		this.email=email;
		this.contactType=contactType;
	}
	
	public Contact(int id,String firstName,String lastName,String address,String city,String state,int zip,long phoneNumber,String email,ContactType contactType){
		this.id=id;
		this.firstName=firstName;
		this.lastName=lastName;
		this.address=address;
		this.city=city;
		this.state=state;
		this.zip=zip;
		this.phoneNumber=phoneNumber;
		this.email=email;
		this.contactType=contactType;
	}
    public Contact(int id,String firstName,String lastName,String address,String city,String state,int zip,long phoneNumber,String email,ContactType contactType,LocalDate dateAdded) {
    	this(id, firstName, lastName, address, city, state, zip, phoneNumber, email, contactType);
		this.dateAdded=dateAdded;
    }
	
	public boolean equals(Object object) {
		if (object == this) { 
            return true; 
        } 
  
        if (!(object instanceof Contact)) { 
            return false; 
        } 
        Contact contact = (Contact) object; 
        if(this.firstName.equals(contact.firstName) && this.lastName.equals(contact.lastName))
        		return true;
        else return false;		
	}
	
	public String toString() {
		return firstName+","+lastName+","+city+","+state+","+zip+","+email+","+phoneNumber;
	}
    public void setId(int id) {
		this.id=id;
	}
    public int getId() {
    	return this.id;
    }
	public void setFirstName(String first_name) {
		this.firstName=first_name;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public void setLast_Name(String last_name) {
		this.lastName=last_name;
	}
	public String getLast_Name(){
		return lastName;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public String getAddress() {
		return address;
	}
	public void setCity(String city) {
		this.city=city;
	}
	public String getCity() {
		return city;
	}
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state;
	}
	public void setZip(int zip) {
		this.zip=zip;
		
	}
	public int getZip() {
		return this.zip;
	}
	public void setPhoneNumber(long phone_number){
		this.phoneNumber=phone_number;
	}
	public long getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public String getEmail() {
		return this.email;
	}
	public void setContactType(ContactType contactType) {
		this.contactType=contactType;
	}
	public ContactType getContactType() {
		return this.contactType;
	}
}
