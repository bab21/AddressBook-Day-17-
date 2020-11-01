package com.capgemini.addressbook;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import com.capgemini.addressbook.Contact.ContactType;

public class AddressBookDBService {
	private static AddressBookDBService addressBookDBService=null;
	
	private AddressBookDBService() {
		
	}
	
	public static AddressBookDBService getInstance() {
		if(addressBookDBService==null)
			addressBookDBService=new AddressBookDBService();
		return addressBookDBService;
			
	}
	public static void main(String[] args) throws SQLException {
		AddressBookDBService obj=AddressBookDBService.getInstance();
		Connection connection=obj.getConnection();
	}
	
	private Connection getConnection() throws SQLException{
		listDrivers();
		String jdbcURL="jdbc:mysql://localhost:3306/address_book_service?allowPublicKeyRetrieval=true&useSSL=false";
		String userName="root";
		String password="nancy21@Bab";
		Connection connection;
	    System.out.println("Connecting to database:"+jdbcURL);
	    
		connection=DriverManager.getConnection(jdbcURL,userName,password);
		System.out.println("Connection is successful!!!!"+connection);
		return connection;
	}
	private  void listDrivers() {
		Enumeration<Driver> driverList=DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass=(Driver) driverList.nextElement();
			System.out.println("  "+driverClass.getClass().getName());
		}
	}
	public List<Contact> readContacts() {
		String sql="select * from contact";
		List<Contact> contactList=new ArrayList<>();
		try(Connection connection=this.getConnection()){
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String firstName=resultSet.getString("firstName");
				String lastName=resultSet.getString("lastName");
			    String address=resultSet.getString("address");
				String city=resultSet.getString("city");
				String state=resultSet.getString("state");
			    int zip=resultSet.getInt("zip");
				long phoneNumber=resultSet.getLong("phoneNumber");;
				String email=resultSet.getString("email");
				ContactType contactType=ContactType.valueOf(resultSet.getString("contact_type"));
				Contact contact=new Contact(id,firstName,lastName,address,city,state,zip,phoneNumber,email,contactType);
				contactList.add(contact);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	

	public void addContact(Contact contact,int addressBookId) {
		String sql=String.format("insert into contact(firstName,lastName,address,city,state,zip,phoneNumber,email,addressBookId,contactType)+"
				+ "values ('%s','%s','%s','%s','%s','%d','%d','%s','%d','%s')",
				contact.getFirstName(),contact.getLast_Name(),contact.getAddress(),
				contact.getCity(),contact.getState(),contact.getZip(),contact.getPhoneNumber(),contact.getEmail(),addressBookId,contact.getContactType().toString());
		
		try(Connection connection =this.getConnection()){
			Statement statement=connection.createStatement();
			int rowsAffected=statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean checkContactExits(Contact contact) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void updateContactWithLastName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		
	}

	public void updateContactWithAddress(String firstName, String address) {
		// TODO Auto-generated method stub
		
	}

	public void updateContactWithCity(String firstName, String city) {
		// TODO Auto-generated method stub
		
	}

	public void updateContactWithState(String firstName, String state) {
		// TODO Auto-generated method stub
		
	}

	public void updateContactWithZip(String firstName, int zip) {
		// TODO Auto-generated method stub
		
	}

	public void updateContactWithPhoneNumber(String firstName, long phoneNumber) {
		// TODO Auto-generated method stub
		
	}

	public void updateContactWithEmail(String firstName, String email) {
		// TODO Auto-generated method stub
		
	}

	public void deleteContact(String firstName) {
		// TODO Auto-generated method stub
		
	}

	public List<Contact> SortedContactByFirstName(String addressBookName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contact> getSortedContactByCity(String addressBookName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contact> getSortedContactByState(String addressBookName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contact> getSortedContactByZip(String addressBookName) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
