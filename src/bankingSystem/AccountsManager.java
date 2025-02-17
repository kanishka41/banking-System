package bankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountsManager {
	
	private Connection connection;
	private Scanner sc;
	
	public AccountsManager(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
	}
	
	public void creditMoney(String accountNumber) {
		
		System.out.println("enter amount you want to deposit: ");
		double amount = sc.nextDouble();
		sc.nextLine();
		
		System.out.println("enter your pin: ");
		String security_pin = sc.nextLine();
		
		String sql = "select * from accounts where account_number = ? and security_pin = ?";
		
		try {
			
			connection.setAutoCommit(false);
			
			if(accountNumber != null) {
				
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, accountNumber);
			st.setString(2, security_pin);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				
				String update = "update accounts set balance = balance + ? where account_number = ?";
				
				PreparedStatement st1 = connection.prepareStatement(update); 
				st1.setDouble(1, amount);
				st1.setString(2, accountNumber);
				int rowAffect = st1.executeUpdate();
				
				if(rowAffect > 0) {
					System.out.println("Rs."+amount+" successfully created!!");
					connection.commit();
					connection.setAutoCommit(true);
				}else {
					System.out.println("transaction failed");
					connection.rollback();
					connection.setAutoCommit(true);
				}
				
			}else System.out.println("wrong security pin");
		}
			
		}catch(SQLException e) {
		System.out.println(e);
		}
		
	}
	
	
	
	public void debitMoney(String accountNumber) {
		
		sc.nextLine();
		System.out.println("enter money to withdraw: ");
		double amount = sc.nextDouble();
		sc.nextLine();
		
		System.out.println("enter your pin: ");
		String security_pin = sc.nextLine();
		
		String sql = "select * from accounts where account_number = ? and security_pin = ?";
		
		try {
			
			connection.setAutoCommit(false);
			
			if(accountNumber != null) {
				
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, accountNumber);
			st.setString(2, security_pin);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				
				String update = "update accounts set balance = balance - ? where account_number = ?";
				
				PreparedStatement st1 = connection.prepareStatement(update); 
				st1.setDouble(1, amount);
				st1.setString(2, accountNumber);
				int rowAffect = st1.executeUpdate();
				
				if(rowAffect > 0) {
					System.out.println("Rs."+amount+" successfully debited!!");
					connection.commit();
					connection.setAutoCommit(true);
				}else {
					System.out.println("transaction failed");
					connection.rollback();
					connection.setAutoCommit(true);
				}
				
			}else System.out.println("wrong security pin");
		}
			
		}catch(SQLException e) {
		System.out.println(e);
		}
		
	}
	
	
	public void transferMoney(String accountNumber) {
		
		
	}
	
	
	public void checkBalance(String accountNumber){
        sc.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = sc.nextLine();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: "+balance);
                sc.nextLine();
            }else{
                System.out.println("Invalid Pin!");
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }
		
	}
	


