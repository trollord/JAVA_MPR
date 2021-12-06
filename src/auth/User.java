package auth;

import static input.Input.nextLine;

import java.util.Random;
import java.util.Scanner;

import exceptions.InvalidDetailsException;
import exceptions.InvalidUsernameException;

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
		String username = nextLine(sc);
		System.out.print("Enter your password : ");
		String pass = nextLine(sc);
		try
		{
			login(username, pass);
		}
		catch (InvalidDetailsException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void login(String username, String pass) throws InvalidDetailsException
	{
		Authenticator auth = new Authenticator();
		
		if (auth.isValid(username, pass))
		{
			this.username = username;
			logged = true;
			System.out.println("Successfully logged into " + username);
		}
		else
			throw new InvalidDetailsException("Invalid username/password");
	}
	
	public void register(Scanner sc)
	{
		Authenticator auth = new Authenticator();
		String username;
		
		while (true)
		{
			System.out.print("\nEnter a username : ");
			username = nextLine(sc);
			if (username.equals("q"))
				return;
			try
			{
				auth.checkValidity(username);
			}
			catch (InvalidUsernameException e)
			{
				System.out.println(e.getMessage());
				System.out.println("Enter q to return to main menu");
				continue;
			}
			break;
		}
		System.out.print("Enter a password : ");
		String pass = nextLine(sc);
		auth.register(username, pass);
		System.out.println("Account successfully registered!");
	}
	
	public void setUpGuest()
	{
		Random rand = new Random();
		username = "Guest_" + Math.abs(rand.nextInt());
		logged = true;
		System.out.println("Guest account " + username + " successfully created!");
	}
	
	public boolean isLogged()
	{
		return logged;
	}
}
