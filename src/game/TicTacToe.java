package game;

import static input.Input.nextInt;

import java.util.Scanner;

import auth.User;
import exceptions.InvalidDetailsException;
import exceptions.InvalidInputException;

public class TicTacToe extends Game
{
	public TicTacToe(User user)
	{
		super("tic_tac_toe", user);
	}
	
	@Override
	public void start(Scanner sc)
	{
		System.out.println("\n***************__ WELCOME " + user.getUsername() + " TO X AND O!! __********************");
		User user2 = new User();
		do
		{
			System.out.println("This game can only be played by 2 players. Please find a friend to play this game with.");
			System.out.println("Enter 'guest' if you want to play as a guest. Enter 'return' to return to main menu.");
			System.out.print("Player 2, please enter your username : ");
			String username = sc.nextLine();
			if (username.equals("guest"))
			{
				user2.setUpGuest();
			}
			else if (username.equals("return"))
			{
				return;
			}
			else
			{
				System.out.print("Player 2, please enter your password : ");
				String pass = sc.nextLine();
				try
				{
					user2.login(username, pass);
				}
				catch (InvalidDetailsException e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		while (!user2.isLogged());
		int user1_points = 0, user2_points = 0;
		int ch = -1;
		while (true)
		{
			System.out.println("\nEnter your choice");
			System.out.println("1 - Start new game");
			System.out.println("2 - Return to main menu");
			try
			{
				ch = nextInt(1, 2, sc);
			}
			catch (InvalidInputException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			
			if (ch == 2)
				break;
			
			int grid[][] = new int[3][3];
			int moveCount = 0;
			int xPlays = 1;
			while (true)
			{
				if (xPlays == 1)
					System.out.println(user.getUsername() + ", YOUR TURN");
				else
					System.out.println(user2.getUsername() + ", YOUR TURN");
				try
				{
					player(xPlays, grid, sc);
					int winner = checkwin(grid);
					if (winner == 1)
					{
						user1_points++;
						System.out.println(user.getUsername() + " YOU WON!");
						break;
					}
					else if (winner == -1)
					{
						user2_points++;
						System.out.println(user2.getUsername() + " YOU WON!");
						break;
					}
				}
				catch (InvalidInputException e)
				{
					System.out.println(e.getMessage());
					continue;
				}
				
				xPlays *= -1;
				moveCount++;
				
				if (moveCount == 9)
				{
					System.out.println("IT IS A TIE");
					break;
				}
			}
		}
		
		if (user1_points > user2_points)
			System.out.println(user.getUsername() + ", you won the game! You scored " + (user1_points - user2_points));
		else if (user1_points == user2_points)
			System.out.println("Both players won the same number of games. Both players scored 0 points");
		else
			System.out.println(user2.getUsername() + ", you won the game! You scored " + (user2_points - user1_points));
		
		super.registerScore(user, user1_points - user2_points);
		super.registerScore(user2, user2_points - user1_points);
	}
	
	private void player(int player, int[][] grid, Scanner sc) throws InvalidInputException
	{
		System.out.println("ENTER The coordinate x(1-3) :");
		int x = nextInt(1, 3, sc);
		x = x - 1;
		System.out.println("ENTER The coordinate y(1-3) :");
		int y = nextInt(1, 3, sc);
		y = y - 1;
		if (grid[x][y] != 0)
			throw new InvalidInputException("BLOCK ALREADY OCCUPIED! PLEASE ENTER COORDINATES OF AN UNOCCUPIED BLOCK!");
		grid[x][y] = player;
		displaygrid(grid);
	}
	
	public static void displaygrid(int[][] grid)
	{
		char[][] printGrid = new char[3 * 3 + 2][3 * 3 + 2];
		for (int i = 0; i < printGrid.length; i++)
			for (int j = 0; j < printGrid[i].length; j++)
				printGrid[i][j] = ' ';
			
		//First vertical grid line
		for (int y = 0; y < printGrid[0].length; y++)
			printGrid[3][y] = '|';
		
		//Second vertical grid line
		for (int y = 0; y < printGrid[0].length; y++)
			printGrid[7][y] = '|';
		
		//First horizontal grid line
		for (int x = 0; x < printGrid.length; x++)
			printGrid[x][3] = '-';
		
		//Second horizontal grid line
		for (int x = 0; x < printGrid.length; x++)
			printGrid[x][7] = '-';
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (grid[i][j] == 1)
					putX(i * 4, j * 4, printGrid);
				else if (grid[i][j] == -1)
					putO(i * 4, j * 4, printGrid);
			}
		}
		
		for (int y = 0; y < printGrid[0].length; y++)
		{
			for (int x = 0; x < printGrid.length; x++)
				System.out.print(printGrid[x][y]);
			System.out.println();
		}
	}
	
	private static void putX(int x, int y, char[][] printGrid)
	{
		char[][] X = {{'\\', ' ', '/'}, {' ', '\\', ' '}, {'/', ' ', '\\'}};
		
		for (int dx = 0; dx < 3; dx++)
			for (int dy = 0; dy < 3; dy++)
				printGrid[x + dx][y + dy] = X[dy][dx];
	}
	
	private static void putO(int x, int y, char[][] printGrid)
	{
		char[][] O = {{' ', '=', ' '}, {'|', 'O', '|'}, {' ', '=', ' '}};
		
		for (int dx = 0; dx < 3; dx++)
			for (int dy = 0; dy < 3; dy++)
				printGrid[x + dx][y + dy] = O[dy][dx];
	}
	
	public static int checkwin(int[][] grid)
	{
		for (int i = 0; i < 3; i++)
		{
			if (grid[i][0] + grid[i][1] + grid[i][2] == 3)
				return 1;
			else if (grid[i][0] + grid[i][1] + grid[i][2] == -3)
				return -1;
			else if (grid[0][i] + grid[1][i] + grid[2][i] == -3)
				return -1;
			else if (grid[0][i] + grid[1][i] + grid[2][i] == 3)
				return 1;
		}
		if (grid[0][0] + grid[1][1] + grid[2][2] == 3)
			return 1;
		else if (grid[0][0] + grid[1][1] + grid[2][2] == -3)
			return -1;
		else if (grid[0][2] + grid[1][1] + grid[2][0] == -3)
			return -1;
		else if (grid[0][2] + grid[1][1] + grid[2][0] == 3)
			return 1;
		
		return 0;
	}
}