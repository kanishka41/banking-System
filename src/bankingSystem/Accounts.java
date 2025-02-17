package bankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Random;

public class Accounts {

	private Connection connection;
	private Scanner sc;
	
	
	public Accounts(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
	}
	
	public String openAccount(String email) {
		if(!accountExist(email)) {
			
			String openAccountQuery = "insert into accounts values (?,?,?,?,?)";
			
			sc.nextLine();
			System.out.println("enter your full name: ");
			String name = sc.nextLine();
			System.out.println("enter initial amount: ");
			double balance = sc.nextDouble();
			sc.nextLine();
			
			System.out.println("create a security pin: ");
			String password = sc.nextLine();
			
			try {
				String accountNumber = generateAccountNumber();
				
				PreparedStatement st = connection.prepareStatement(openAccountQuery);	
				st.setString(1, accountNumber);
				st.setString(2, name);
				st.setString(3, email);
				st.setDouble(4, balance);
				st.setString(5, password);
				
				int rowAffect = st.executeUpdate();
				
				if(rowAffect > 0) return accountNumber;
				else throw new RuntimeException("account creation failed due to some reason");
				
			}catch(SQLException e) {
				System.out.println(e);
			}
		}
		 throw new RuntimeException("account already existed");
		
	}
	
	
	public String getAccountNumber(String email) {
		
		String sql = "select account_number from accounts where email = ?";
		
		try {
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, email);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) return rs.getString(1);
			else return null;
			
		}catch(SQLException e) { 
			System.out.println(e);
		}
		throw new RuntimeException("Account doesn't exist");
	}
	
	
	
	public String generateAccountNumber() {
		
		Random random = new Random();
		
		StringBuilder accNum = new StringBuilder();
		
		for(int i=0; i<10; i++) {
			accNum.append(random.nextInt(10));	
			}
		
		return accNum.toString();
	}
	
	
	public boolean accountExist(String email) {
		
		String sql = "select * from accounts where email = ?";
		
		try {
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, email);
		ResultSet rs = st.executeQuery();
		if(rs.next()) return true;
		else return false;
		}catch(SQLException e) {
			System.out.println(e);
		}
		return false;
	}

}
