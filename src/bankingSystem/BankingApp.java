package bankingSystem;
import java.sql.*;
import java.util.Scanner;

public class BankingApp {

	public static void main(String[] args) {
		
		final String url = "jdbc:mysql://localhost:3306/Banking_system";
		final String user1 = "root";
		final String password = "12345";
		String email;
		String accountNumber;
		
		try {
		Connection connection = DriverManager.getConnection(url, user1, password);
		Scanner sc = new Scanner(System.in);
		
		User user = new User(connection, sc);
		Accounts accounts = new Accounts(connection, sc);
		AccountsManager accountsManager = new AccountsManager(connection, sc);
		
		while(true) {
			
			System.out.println("***********welcome to our Banking System************");
			System.out.println();
			System.out.println("do you want to...");
			System.out.println("1. Register");
			System.out.println("2. login");
			System.out.println("3. Exit");
			
			System.out.print("enter your choice: ");
			int ch = sc.nextInt();
			
			switch(ch) {
			
			case 1:
				user.register();
				break;
				
			case 2:
				email = user.login();
			
				if(email!= null) {
					System.out.println();
					System.out.println("user logged in!!");
					
				
				if(!accounts.accountExist(email)) {
					
					System.out.println();
					System.out.println("1. open a new account");
					System.out.println("2. Exit");
					
					System.out.print("enter your choice: ");
					int ch2 = sc.nextInt();
					
					if(ch2 == 1) {
						accountNumber = accounts.openAccount(email);
						System.out.println("your account has been created successfully");
						System.out.println("your account number is: "+ accountNumber);
					}else break;
				}
			
			
				accountNumber = accounts.getAccountNumber(email);
				int ch1 = 0;
				
				while(ch1 != 5) {
					System.out.println("1. Money Deposit");
					System.out.println("2. Money Withdraw");
					System.out.println("3. Money Transfer");
					System.out.println("4. Check Balance");
					System.out.println("5. Logout");
					
					System.out.println("Enter your choice: ");
					ch1 = sc.nextInt();
					
					switch(ch1) {
					
					case 1: 
						accountsManager.creditMoney(accountNumber);
						break;
						
					case 2:
						accountsManager.debitMoney(accountNumber);
						break;
						
					case 3:
						accountsManager.transferMoney(accountNumber);
						break;
						
					case 4:
						accountsManager.checkBalance(accountNumber);
						break;
						
					case 5:
						break;
					
					default:
						System.out.println("enter a valid choice");
						break;
					}
					
				}
				
			}else {
				System.out.println("invalid email or password");
			}
				
			case 3:
				System.out.println("Thankyou for using our application");
				return;
				
			default:
				System.out.println("enter a valid choice");
				break;
			}
				
		}
		}catch(SQLException e) {
			System.out.println(e);
		    }

	}

}
