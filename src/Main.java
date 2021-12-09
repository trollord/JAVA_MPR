
import static input.Input.nextInt;

import java.util.Scanner;

import auth.User;
import exceptions.InvalidInputException;
import game.HangMan;
import game.Leaderboard;
import game.MazeGame;
import game.RockPaperScissor;
import game.TicTacToe;

public class Main
{
	public static void init(Scanner sc, User user)
	{
		final int exitCode = 6;
		
		int ch;
		do
		{
			System.out.println("\nWelcome, " + user.getUsername() + "!");
			while (true)
			{
				System.out.println("Please select what you would like to do : ");
				System.out.println("1 - Rock Paper Scissors");
				System.out.println("2 - X and O");
				System.out.println("3 - Maze Game");
				System.out.println("4 - Hang man");
				System.out.println("5 - Leaderboards");
				System.out.println(exitCode + " - Logout");
				
				try
				{
					ch = nextInt(1, exitCode, sc);
				}
				catch (InvalidInputException e)
				{
					System.out.println(e.getMessage());
					continue;
				}
				break;
			}
			switch (ch)
			{
				case 1:
					RockPaperScissor rps = new RockPaperScissor(user);
					rps.start(sc);
					break;
				case 2:
					TicTacToe ttt = new TicTacToe(user);
					ttt.start(sc);
					break;
				case 3:
					MazeGame mg = new MazeGame(user);
					mg.start(sc);
					break;
				case 4:
					HangMan hm = new HangMan(user);
					hm.start(sc);
					break;
				case 5:
					Leaderboard lb = new Leaderboard(user);
					lb.start(sc);
					break;
				case exitCode:
					break;
			}
		}
		while (ch != exitCode);
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		User user = new User();
		
		loop: while (true)
		{
			System.out.println("\n***************__ WELCOME TO THE GAMING LOUNGE!! __********************");
			int ch;
			while (true)
			{
				System.out.println("\nPlease choose an option");
				System.out.println("1 - Login");
				System.out.println("2 - Register");
				System.out.println("3 - Play as guest");
				System.out.println("4 - Exit");
				
				try
				{
					ch = nextInt(1, 4, sc);
				}
				catch (InvalidInputException e)
				{
					System.out.println(e.getMessage());
					continue;
				}
				break;
			}
			
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
				
				case 4:
					break loop;
			}
			if (user.isLogged())
				init(sc, user);
		}
		sc.close();
	}
}
