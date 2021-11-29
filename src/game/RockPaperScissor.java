package game;

import java.util.Scanner;

import auth.User;

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
		System.out.println("***************__ WELCOME " + user.getUsername() + " TO ROCK,PAPER,SCISSORS!! __********************");
		int user_points = 0, opp_points = 0;
		while (true)
		{
			System.out.println("\n\nEnter your move\n1-Rock\n2-Paper\n3-Scissor\n4-Quit");
			int move = sc.nextInt();
			if (move == 4)
				break;
			else if (move <= 0 || move > 4)
				System.out.println("\nInvalid move!\n");
			else
			{
				move -= 1; //0 index user's move
				
				int rand = (int) (Math.random() * 3);
				
				draw(move, rand);
				
				if (move == rand)
				{
					System.out.println("It's a tie!!");
					user_points++;
					opp_points++;
				}
				if (move == ROCK && rand == SCISSOR || move == SCISSOR && rand == PAPER || move == PAPER && rand == ROCK)
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
		}
		
		if (user_points > opp_points)
			System.out.println("You won the game! your points are: " + user_points);
		else if (user_points == opp_points)
			System.out.println("Its a draw! your points are: " + user_points);
		else
			System.out.println("Opponent won the game by a margin of: " + (opp_points - user_points));
		
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