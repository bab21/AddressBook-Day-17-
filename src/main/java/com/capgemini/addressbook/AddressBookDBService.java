package com.capgemini.addressbook;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.time.LocalDate;
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
	public List<Contact> getContactData(String name) {
		try(Connection connection=this.getConnection()){
			PreparedStatement preparedStatement=connection.prepareStatement("select * from contact where firstName=?");  
			preparedStatement.setString(1,name);
			ResultSet resultSet=preparedStatement.executeQuery(); 
			return getContactData(resultSet);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Contact> getContactData(ResultSet resultSet){
		List<Contact> contactList=new ArrayList<>();
			
		try {
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
	     }
		catch(SQLException e) {
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
		String sql=String.format("select * from contact where name='%s'",contact.getFirstName());
		try(Connection connection =this.getConnection()){
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			if(resultSet.getRow()>0)
				return true;
			else return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public List<Contact> queryDatabase(String sql) {
		try(Connection connection=this.getConnection()){
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			return getContactData(resultSet);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void updateDatabase(String sql) {
		try(Connection connection=this.getConnection()){
			Statement statement=connection.createStatement();
			statement.executeUpdate(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateContactWithLastName(String firstName, String lastName) throws SQLException {
		String sql=String.format("update contact set lastName='%s' where firstName='%s'",lastName,firstName);
		updateDatabase(sql);
		
	}

	public void updateContactWithAddress(String firstName, String address) {
		String sql=String.format("update contact set address='%s' where firstName='%s'",address,firstName);
		updateDatabase(sql);
		
	}

	public void updateContactWithCity(String firstName, String city) {
		String sql=String.format("update contact set city='%s' where firstName='%s'",city,firstName);
		updateDatabase(sql);
	}

	public void updateContactWithState(String firstName, String state) {
		String sql=String.format("update contact set state='%s' where firstName='%s'",state,firstName);
		updateDatabase(sql);
	}

	public void updateContactWithZip(String firstName, int zip) {
		String sql=String.format("update contact set zip='%s' where firstName='%s'",zip,firstName);
		updateDatabase(sql);
	}

	public void updateContactWithPhoneNumber(String firstName, long phoneNumber) {
		String sql=String.format("update contact set phoneNumber='%s' where firstName='%s'",phoneNumber,firstName);
		updateDatabase(sql);
	}

	public void updateContactWithEmail(String firstName, String email) {
		String sql=String.format("update contact set email='%s' where firstName='%s'",email,firstName);
		updateDatabase(sql);
	}

	public void deleteContact(String firstName) {
		String sql=String.format("delete from contact where firstName='%s'",firstName);
		try(Connection connection=this.getConnection()){
			Statement statement=connection.createStatement();
			statement.executeUpdate(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Contact> SortedContactByFirstName(int addressBookId) {
		String sql=String.format("select * from contact "
				+ "where address_book_id='%d'"
				+ "order by firstName", addressBookId);
		return queryDatabase(sql);
		
	}

	public List<Contact> getSortedContactByCity(int addressBookId) {
		String sql=String.format("select * from contact "
				+ "where address_book_id='%d'"
				+ "order by city", addressBookId);
		return queryDatabase(sql);
	}

	public List<Contact> getSortedContactByState(int addressBookId) {
		String sql=String.format("select * from contact "
				+ "where address_book_id='%d'"
				+ "order by state", addressBookId);
		return queryDatabase(sql);
	}

	public List<Contact> getSortedContactByZip(int addressBookId) {
		String sql=String.format("select * from contact "
				+ "where address_book_id='%d'"
				+ "order by zip", addressBookId);
		return queryDatabase(sql);
	}

	public List<Contact> getContactInDateRange(LocalDate startDate, LocalDate endDate) {
		String sql=String.format("select * from contact where date_added BETWEEN '%s' and '%s' ;",Date.valueOf(startDate),Date.valueOf(endDate));
		return queryDatabase(sql);
	}

}
