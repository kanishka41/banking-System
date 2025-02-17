package bankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	
	private Connection connection;
	private Scanner sc;
  
	public User(Connection connection, Scanner sc){
		this.connection = connection;
		this.sc = sc;	
	}
	
	
	public void register() {
		
		sc.nextLine();
		System.out.println("enter your full name: ");
		String name = sc.nextLine();
		
		
		System.out.println("enter your email: ");
		String email = sc.nextLine();
		
		System.out.println("enter your password: ");
		String password = sc.nextLine();
		
		if(userExist(email)) { System.out.println("this email is already in use!! try with another email!!!"); return; }
		
		String sql = "insert into user values(?,?,?)";
		
		try {
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, email);
			st.setString(3, password);
			
			int rowAffect = st.executeUpdate();
			
			if(rowAffect>0)
			{ System.out.println("registration successful");
			System.out.println(); }
			
			else System.out.println("registration failed");

		}catch(SQLException e) {
			System.out.println(e);
		}			
	 }
	
		
	public String login() {
		
		sc.nextLine();		
		System.out.println("enter your email: ");
		String email = sc.nextLine();
		
		System.out.println("enter password: ");
		String password = sc.nextLine();

		String loginQuery = "select * from user where email = ? and password = ?";
		
		try {
			PreparedStatement st = connection.prepareStatement(loginQuery);
			st.setString(1, email);
			st.setString(2, password);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) return email;
			else throw new RuntimeException("Incorrect email or password");
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		throw new RuntimeException("Incorrect email or password");
	}
	
	
	
	public boolean userExist(String email) {
		
		String sql = "select * from user where email = ? ";
		
		try {
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if(rs.next()) return true;
			return false;
		}catch(SQLException e) {
			System.out.println(e);
		}
		return false;
	}
	
	
}


