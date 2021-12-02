package game;

import java.util.Scanner;
// ADD AN USER DEFINED EXCEPTION !!!

import auth.User;

public class TicTacToe extends Game
{
	public TicTacToe(User user)
	{
		super("tic_tac_toe", user);
	}
	
	static boolean continueGame;
	
	@Override
	public void start(Scanner sc)
	{
		System.out.println("***************__ WELCOME " + user.getUsername() + " TO X AND O!! __********************");
		int grid[][] = new int[3][3];
		int winCount = 0;
		continueGame = true;
		while (continueGame)
		{
			System.out.println("PLAYER ONE");
			player(1, grid, sc);
			if (continueGame)
			{
				System.out.println("PLAYER TWO");
				player(-1, grid, sc);
			}
			winCount++;
		}
		
		if (winCount == 9)
		{
			System.out.println("IT IS A TIE");
		}
	}
	
	private void player(int player, int[][] grid, Scanner sc)
	{
		System.out.println("ENTER The coordinate x(1-3) :");
		int x = sc.nextInt();
		x = x - 1;
		System.out.println("ENTER The coordinate y(1-3) :");
		int y = sc.nextInt();
		y = y - 1;
		while (grid[x][y] != 0)
		{
			System.out.println("\nTHE COORDINATES ENTERED BY YOU ARE INVALID!!! ENTER THE COORDINATES AGAIN !!!!");
			System.out.println("ENTER The coordinate x(1-3) :");
			x = sc.nextInt();
			x = x - 1;
			System.out.println("ENTER The coordinate y(1-3) :");
			y = sc.nextInt();
			y = y - 1;
		}
		grid[x][y] = player;
		displaygrid(grid);
		if (checkwin(grid) == 0)
		{
			continueGame = true;
		}
		else if (checkwin(grid) == 1)
		{
			System.out.println("PLAYER ONE WINs");
			continueGame = false;
		}
		else if (checkwin(grid) == -1)
		{
			System.out.println("PLAYER TWO WINs");
			continueGame = false;
		}
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
		if (grid[0][0] + grid[1][1] + grid[2][1] == 3)
			return 1;
		else if (grid[0][0] + grid[1][1] + grid[2][1] == -3)
			return -1;
		else if (grid[0][2] + grid[1][1] + grid[2][2] == -3)
			return -1;
		else if (grid[0][2] + grid[1][1] + grid[2][2] == 3)
			return 1;
		
		return 0;
	}
}