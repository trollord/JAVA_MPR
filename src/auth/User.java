package auth;

import java.util.Scanner;

public class User
{
	private String username;
	private boolean logged;
	
	public String getUsername()
	{
		return username;
	}
	
	public void login(Scanner sc)
	{
		System.out.print("Enter username : ");
		String username = sc.next();
		System.out.print("Enter your password : ");
		String pass = sc.next();
		
		Authenticator auth = new Authenticator();
		
		if (auth.isValid(username, pass))
		{
			this.username = username;
			logged = true;
		}
		else
			System.out.println("Invalid username/password");
	}
	
	public void register(Scanner sc)
	{
		Authenticator auth = new Authenticator();
		String username;
		
		while (true)
		{
			System.out.print("\nEnter a username : ");
			username = sc.next();
			if (username.equals("q"))
				return;
			if (auth.exists(username))
				System.out.println("Username already taken.\nPlease use another username.\nEnter q to return to main menu");
			else
				break;
		}
		
		System.out.print("Enter a password : ");
		String pass = sc.next();
		auth.register(username, pass);
	}
	
	public void setUpGuest()
	{
		username = "Guest";
		logged = true;
	}
	
	public boolean isLogged()
	{
		return logged;
	}
}
