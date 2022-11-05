package com.src.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

import com.src.model.Code;
import com.src.model.Retail;

public class DAOImplementation implements DAOInterface {
	Connection con = null;
	Statement stmt = null;
	String dbpassword ="Gaurav@02";
	String dbUsername ="root";
	
	public Statement getMyStatement() {
		String url = "jdbc:mysql://localhost:3306/QR";
		String username = dbUsername;
		String password = dbpassword;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return stmt;
	}

	public void closeConnections() {

		try {
			con.close();
			stmt.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

	}
/*
 * Creation Of Specific Database
 */
	public boolean createDatabase(String dbName) {
		String url = "jdbc:mysql://localhost:3306";
		String username = dbUsername;
		String password = dbpassword;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		String query = "create database " + dbName;
		boolean bo = false;
		try {
			bo = stmt.execute(query);
			System.out.println(query + bo);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		closeConnections();
		return bo;

	}
/*
 * Get The List Of the Partners Who Are Availiable
 * Status= Online
 */
	public ArrayList<Partners> getPartnerdb(){
		//String Customer Email
		stmt = getMyStatement();
		ArrayList<Partners> availablePartners = new ArrayList<Partners>();
		try {
		JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet();
		jdbc.setUrl("jdbc:mysql://localhost:3306/rideon");
		jdbc.setUsername(dbUsername);
		jdbc.setPassword(dbpassword);		
		jdbc.setCommand("select * from partners where partnerStatus='Online'");
		jdbc.execute();
			while(jdbc.next())
			{
				LocalDateTime str = jdbc.getTimestamp(12).toLocalDateTime();
				Partners partner = new Partners(jdbc.getString(1),jdbc.getString(2),jdbc.getString(3),jdbc.getLong(4), jdbc.getInt(5),  jdbc.getDouble(6), jdbc.getDouble(7), jdbc.getString(8),jdbc.getString(9),jdbc.getString(10),jdbc.getString(11), str, jdbc.getString(13));
				availablePartners.add(partner);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return availablePartners;
	}
	
/*
 * Return An UnverifiedUser Object From Database based On The email 
 */
	
	public UnverifiedUser getUnverifiedUser(String email){
		ArrayList<Partners> availablePartners = new ArrayList<Partners>();
		UnverifiedUser user=null;
		try {
		JdbcRowSet jdbc = RowSetProvider.newFactory().createJdbcRowSet();
		jdbc.setUrl("jdbc:mysql://localhost:3306/rideon");
		jdbc.setUsername(dbUsername);
		jdbc.setPassword(dbpassword);		
		jdbc.setCommand("select * from unverifiedUser where email='"+email+"'");
		jdbc.execute();
			while(jdbc.next())
			{	
				user = new UnverifiedUser(jdbc.getString(1),jdbc.getString(2),jdbc.getString(3), jdbc.getString(4), jdbc.getLong(5),jdbc.getString(6),jdbc.getString(7),jdbc.getString(8),jdbc.getString(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

/*
 * Add a Customer Into The Database By taking UnverifiedUser as Input
 */
	public void insertCustomers(UnverifiedUser user){
		String query="insert into customers values('"+user.getFname()+"','"+user.getLname()+"','"+user.getEmail()+"',"+user.getPhone()+",0,0,now(),'"+user.getPassword()+"'";
		RideOnDAOIntreface ride = new RideOnDAOImplementation();
		ride.executeStatement(query);
		query="insert into users values('"+user.getEmail()+"','"+user.getPassword()+"','"+user.getUserType()+"')";
		ride.executeStatement(query);
		query="insert into Customers values('"+user.getFname()+"','"+user.getLname()+"','"+user.getEmail()+"',"+user.getPhone()+","+0+","+5+",now(),'"+user.getPassword()+"')";
		ride.executeStatement(query);
	}
	
/*
 * Add a Partner Into The Database By taking UnverifiedUser as Input
 */
	public void insertPartner(UnverifiedUser user){
		String query="insert into partners values('"+user.getFname()+"','"+user.getLname()+"','"+user.getEmail()+"',"+user.getPhone()+",0,0,0.0,'Online','"+user.getVehicleMake()+"','"+user.getVehicleType()+"','"+user.getVehicleNumber()+"',now(),'"+user.getPassword()+"'";
		RideOnDAOIntreface ride = new RideOnDAOImplementation();
		ride.executeStatement(query);
		query="insert into users values('"+user.getEmail()+"','"+user.getPassword()+"','"+user.getUserType()+"')";
		ride.executeStatement(query);
		query="insert into Partners values('"+user.getFname()+"','"+user.getLname()+"','"+user.getEmail()+"',"+user.getPhone()+","+0+","+5+",now(),'"+user.getPassword()+"')";
		ride.executeStatement(query);
	}

/*
 * Used To Execute Mysql Query
 */
	public boolean executeStatement(String query) {
		System.out.println(query);
		stmt = getMyStatement();
		boolean result = false;
		try {
			result = stmt.execute(query);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		closeConnections();
		return result;
	}

/*
 * Class To check Weather A user Is verified is 
 * 
 */
	public String checkUser(Users user) {
		String result = "Not Verified";
		JdbcRowSet jdbc = null;
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			jdbc = RowSetProvider.newFactory().createJdbcRowSet();
			jdbc.setUrl("jdbc:mysql://localhost:3306/rideon");
			jdbc.setUsername(dbUsername);
			jdbc.setPassword(dbpassword);
			String query = "select * from Users where userID='" + user.getUserID() + "'and userPassword='" + user.getUserPassword()
					+ "' and userType='" + user.getuserType() + "'";
			jdbc.setCommand(query);
			System.out.println(query);
			jdbc.execute();
			while (jdbc.next()) {
				String s = jdbc.getString(1);
				if(!s.isEmpty())
				result ="Verified";	
			}
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		} catch (ClassNotFoundException e) {
			 
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean insert(Code code) {
		String query = "insert into product values('"+code.getBrand()+"','"+code.getModel()+"','"+code.getDescription()+"','"+code.getManufacturerName()+"','"+code.getManufacturerLocation()+"','"+code.getQr()+"')";
		return executeStatement(query);
	}

	@Override
	public boolean insert(Retail retail) {
		String query = "insert into product values('"+retail.getName()+"','"+retail.getEmail()+"','"+retail.getLocation()+"','"+retail.getPassword()+"')";
		return executeStatement(query);
	}

}
