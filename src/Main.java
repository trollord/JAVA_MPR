
import java.util.Scanner;

import auth.User;
import game.RockPaperScissor;

public class Main
{
	public static void init(Scanner sc, User user)
	{
		System.out.println("\nWelcome, " + user.getUsername() + "!");
		System.out.println("Please select what you would like to do : ");
		System.out.println("1 - Rock Paper Scissors");
		System.out.println("2 - X and O");
		System.out.println("3 - Maze Game");
		System.out.println("4 - Hang man");
		
		int ch = sc.nextInt();
		switch (ch)
		{
			case 1:
				RockPaperScissor rps = new RockPaperScissor(user);
				rps.start(sc);
				break;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		User user = new User();
		while (true)
		{
			System.out.println("\nPlease choose an option");
			System.out.println("1 - Login");
			System.out.println("2 - Register");
			System.out.println("3 - Play as guest");
			int ch = sc.nextInt();
			switch (ch)
			{
				case 1:
					user.login(sc);
					break;
				case 2:
					user.register(sc);
					break;
				
				case 3:
					user.setUpGuest();
					break;
			}
			if (user.isLogged())
				break;
		}
		
		init(sc, user);
		
		sc.close();
	}
}
