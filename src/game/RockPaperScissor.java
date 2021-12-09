package game;

import static input.Input.nextInt;

import java.util.Scanner;

import auth.User;
import exceptions.InvalidInputException;

public class RockPaperScissor extends Game
{
	private static final String[] ROCK_STR = {"------------", "|  |  |  |  |\\", "|  |  |  |  |_|", "|  |  |  |  |_|", "\\_/\\_/\\_/\\_/"};
	
	private static final String[] PAPER_STR = {"~~~~~~~       ", "|     |       ", "|     |       ", "|     |       ", "~~~~~~~       "};
	
	private static final String[] SCISSOR_STR = {"\\    /        ", " \\  /         ", " _\\/          ", "|_|_|         ", "              "};
	
	private static final String[][] STRS = {ROCK_STR, PAPER_STR, SCISSOR_STR};
	
	public RockPaperScissor(User user)
	{
		super("rock_paper_scissor", user);
	}
	
	public void start(Scanner sc)
	{
		final int ROCK = 0, PAPER = 1, SCISSOR = 2;
		System.out.println("\n***************__ WELCOME " + user.getUsername() + " TO ROCK,PAPER,SCISSORS!! __********************");
		int user_points = 0, opp_points = 0;
		while (true)
		{
			System.out.println("\n\nEnter your move\n1-Rock\n2-Paper\n3-Scissor\n4-Return to main menu");
			int move = -1;
			try
			{
				move = nextInt(1, 4, sc);
			}
			catch (InvalidInputException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			
			if (move == 4)
				break;
			
			move -= 1; //0 index user's move
			
			int rand = (int) (Math.random() * 3);
			
			draw(move, rand);
			
			if (move == rand)
			{
				System.out.println("It's a tie!!");
				user_points++;
				opp_points++;
			}
			else if (move == ROCK && rand == SCISSOR || move == SCISSOR && rand == PAPER || move == PAPER && rand == ROCK)
			{
				System.out.println("You won!! wohoo");
				user_points++;
			}
			else
			{
				System.out.println("You lost! Better luck next time!");
				opp_points++;
			}
		}
		
		System.out.println("You won " + user_points + " number of games");
		System.out.println("CPU won " + opp_points + " number of games");
		if (user_points > opp_points)
			System.out.println("YOU WON AGAINST THE CPU!!");
		else if (user_points == opp_points)
			System.out.println("THE GAME WAS A TIE.");
		else
			System.out.println("THE CPU WON. BETTER LUCK NEXT TIME!");
		System.out.println("You scored " + (user_points - opp_points) + " points");
		super.registerScore(user_points - opp_points);
	}
	
	private void draw(int user_move, int opp_move)
	{
		System.out.println("USER\t\t\tCPU");
		for (int i = 0; i < 5; i++)
		{
			System.out.println(STRS[user_move][i] + "\t\t" + STRS[opp_move][i]);
		}
	}
}