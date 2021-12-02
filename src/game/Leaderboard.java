package game;

import static input.Input.nextInt;

import java.util.Scanner;

import auth.User;
import exceptions.InvalidInputException;

public class Leaderboard extends Game
{
	public Leaderboard(User user)
	{
		super("leaderboard", user);
	}

	@Override
	public void start(Scanner sc)
	{
		System.out.println("***************__ WELCOME " + user.getUsername() + "!!__********************");
		System.out.println("HERE YOU CAN SEE THE LEADERBOARDS OF THE GAMES THAT YOU CAN PLAY IN THIS LOUNGE\n");
		int ch = 0;
		do
		{
			System.out.println("\nPlease select an option");
			System.out.println("1 - Rock Paper Scissors Leaderboard");
			System.out.println("2 - X and O Leaderboard");
			System.out.println("3 - Maze Game Leaderboard");
			System.out.println("4 - Hang Man Leaderboard");
			System.out.println("5 - Return to main menu");
			try
			{
				ch = nextInt(1,5, sc);
			}
			catch(InvalidInputException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			
			switch(ch)
			{
				case 1:
					new RockPaperScissor(user).displayLeaderboard();
					break;
				case 2:
					new TicTacToe(user).displayLeaderboard();
					break;
				case 3:
					new MazeGame(user).displayLeaderboard();
					break;
				case 4:
					new HangMan(user).displayLeaderboard();
					break;
				case 5:
					break;
			}
		}
		while(ch != 5);
	}
}
