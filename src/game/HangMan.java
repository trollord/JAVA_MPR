package game;

import static input.Input.nextChar;
import static input.Input.nextInt;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import auth.User;
import exceptions.InvalidInputException;

public class HangMan extends Game
{
	public HangMan(User user)
	{
		super("hang_man", user);
	}
	
	static Random rand = new Random();
	
	public void start(Scanner sc)
	{
		System.out.println("\n***************__ WELCOME " + user.getUsername() + " TO HANG MAN!! __********************");
		int ch;
		while (true)
		{
			System.out.println("Enter the category");
			System.out.println("1 - Movies");
			System.out.println("2 - Sports");
			System.out.println("3 - Cars");
			System.out.println("4 - Actors");
			System.out.println("5 - TV series");
			System.out.println("6 - Return to main menu");
			try
			{
				ch = nextInt(1, 6, sc);
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
				movie(sc);
				break;
			case 2:
				sports(sc);
				break;
			case 3:
				cars(sc);
				break;
			case 4:
				actors(sc);
				break;
			case 5:
				tvseries(sc);
				break;
			case 6:
				break;
		}
	}
	public static void drawHangman(int n)
	{
		switch (n)
		{
			case 1:
				System.out.println("  ____");
				System.out.println(" |    |");
				System.out.println(" |      ");
				System.out.println(" |     ");
				System.out.println(" |        ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
			case 2:
				System.out.println("  ____");
				System.out.println(" |   _|_");
				System.out.println(" |  |o o|");
				System.out.println(" |  |___|   ");
				System.out.println(" |        ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
			case 3:
				System.out.println("  ____");
				System.out.println(" |   _|_");
				System.out.println(" |  |o o|");
				System.out.println(" |  |___|   ");
				System.out.println(" |    |    ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |        ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
			case 4:
				System.out.println("  ____");
				System.out.println(" |   _|_");
				System.out.println(" |  |o o|");
				System.out.println(" |  |___|   ");
				System.out.println(" |    |    ");
				System.out.println(" |    |\\   ");
				System.out.println(" |    | \\  ");
				System.out.println(" |    |  \\ ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
			case 5:
				System.out.println("  ____");
				System.out.println(" |   _|_");
				System.out.println(" |  |o o|");
				System.out.println(" |  |___|   ");
				System.out.println(" |    |    ");
				System.out.println(" |   /|\\   ");
				System.out.println(" |  / | \\  ");
				System.out.println(" | /  |  \\ ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |        ");
				System.out.println(" |        ");
				System.out.println(" |        ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
			case 6:
				System.out.println("  ____");
				System.out.println(" |   _|_");
				System.out.println(" |  |o o|");
				System.out.println(" |  |___|   ");
				System.out.println(" |    |    ");
				System.out.println(" |   /|\\   ");
				System.out.println(" |  / | \\  ");
				System.out.println(" | /  |  \\ ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |     \\   ");
				System.out.println(" |      \\  ");
				System.out.println(" |       \\ ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
			case 7:
				System.out.println("  ____");
				System.out.println(" |   _|_");
				System.out.println(" |  |x x|");
				System.out.println(" |  |_-_|   ");
				System.out.println(" |    |    ");
				System.out.println(" |   /|\\   ");
				System.out.println(" |  / | \\  ");
				System.out.println(" | /  |  \\ ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |   / \\   ");
				System.out.println(" |  /   \\  ");
				System.out.println(" | /     \\ ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
			case 8:
				System.out.println("  ____");
				System.out.println(" |   _|_");
				System.out.println(" |  |o o|");
				System.out.println(" |  |\\_/|   ");
				System.out.println(" |    |    ");
				System.out.println(" |   /|\\   ");
				System.out.println(" |  / | \\  ");
				System.out.println(" | /  |  \\ ");
				System.out.println(" |    |   ");
				System.out.println(" |    |   ");
				System.out.println(" |   / \\   ");
				System.out.println(" |  /   \\  ");
				System.out.println(" | /     \\ ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println(" |       ");
				System.out.println("--------------------");
				break;
		}
	}
	public void movie(Scanner sc)
	{
		String[] list = {"revenant", "spiderman", "hulk", "avengers", "percy jackson", "titanic", "mission impossible", "127 hours"};
		int r = rand.nextInt(list.length);
		begin(list[r], list[r].length(), sc);
	}
	public void sports(Scanner sc)
	{
		String[] list = {"cricket", "hockey", "football", "basketball", "throwball", "tennis", "handball", "kabbadi", "sprinting", "paintball", "table tennis"};
		int r = rand.nextInt(list.length);
		begin(list[r], list[r].length(), sc);
	}
	public void cars(Scanner sc)
	{
		String[] list = {"bmw", "ferrari", "lamborghini", "porsche", "koeinsenegg", "range rover", "jeep", "bugghati"};
		int r = rand.nextInt(list.length);
		begin(list[r], list[r].length(), sc);
	}
	public void actors(Scanner sc)
	{
		String[] list = {"brad pitt", "shah rukh khan", "matt damon", "salman khan", "hritik roshan", "hema malini", "kareena kapoor", "madhuri dixit"};
		int r = rand.nextInt(list.length);
		begin(list[r], list[r].length(), sc);
	}
	public void tvseries(Scanner sc)
	{
		String[] list = {"tarak mehta ka ulta chasma", "super dancer", "dance plus", "masterchef", "big boss", "kapil sharma", "dance champions", "dance india dance", "baalveer", "*shaktimaan*"};
		int r = rand.nextInt(list.length);
		begin(list[r], list[r].length(), sc);
	}
	
	public void begin(String s, int l, Scanner sc)
	{
		boolean[] uncovered = new boolean[l];
		int deaths = 1;
		int hint = rand.nextInt(l);
		uncovered[hint] = true;
		boolean wrong = true;
		long start = System.currentTimeMillis();
		outer: while (true)
		{
			wrong = true;
			drawHangman(deaths);
			System.out.println("Enter your guess (One letter only). Enter q to quit to main menu");
			print_title(s, l, uncovered, hint);
			System.out.println();
			char g = ' ';
			try
			{
				g = nextChar(sc);
				if (!(g >= 'a' && g <= 'z' || g >= 'A' && g <= 'Z'))
					throw new InvalidInputException();
			}
			catch (InvalidInputException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			
			if(g == 'Q' || g == 'q')
				return;
			
			for (int i = 0; i < l; i++)
			{
				if (g == s.charAt(i) || g - 'A' + 'a' == s.charAt(i))
				{
					uncovered[i] = true;
					wrong = false;
				}
			}
			if (wrong)
			{
				deaths++;
			}
			if (deaths == 7)
			{
				System.out.println("Game over. You lost");
				System.out.println("The word was " + s);
				System.out.println("You didn't score any points");
				drawHangman(7);
				break;
			}
			for (int i = 0; i < uncovered.length; i++)
			{
				if (!uncovered[i])
					continue outer;
			}
			System.out.println("YOU WON!");
			System.out.println("You guessed the word correctly! The word was " + s);
			drawHangman(8);
			
			long time = System.currentTimeMillis() - start;
			System.out.println("\nYou finished the game in " + time / 1000 + "s.");
			System.out.println("You guessed wrong " + (deaths - 1) + " number of times.");
			int score = (int) (300_000 / time / deaths); //300s = 5m
			System.out.println("You scored " + score + " points");
			super.registerScore(score);
			break;
		}
	}
	
	private void print_title(String s, int l, boolean[] uncovered, int hint)
	{
		for (int i = 0; i < l; i++)
		{
			if (s.charAt(i) == ' ')
			{
				System.out.print(" ");
				uncovered[i] = true;
			}
			else if (!(uncovered[i]) && (i != hint))
				System.out.print("-");
			else
				System.out.print(s.charAt(i));
		}
	}
}
