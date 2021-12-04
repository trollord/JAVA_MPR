package game;

import static input.Input.nextChar;
import static input.Input.nextInt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import auth.User;
import exceptions.InvalidInputException;

public class MazeGame extends Game
{
	private static long startTime;
	
	public MazeGame(User user)
	{
		super("maze_game", user);
	}
	
	@Override
	public void start(Scanner sc)
	{
		System.out.println("\n***************__ WELCOME " + user.getUsername() + " TO MAZE GAME!! __********************");
		int diff = 0;
		while (true)
		{
			System.out.println("\nChoose a difficulty");
			System.out.println("1 - Easy (10x10 maze)");
			System.out.println("2 - Medium (20x20 maze)");
			System.out.println("3 - Hard (30x30 maze)");
			System.out.println("4 - Return to main menu");
			try
			{
				diff = nextInt(1, 4, sc);
			}
			catch (InvalidInputException e)
			{
				System.out.println(e.getMessage());
				continue;
			}
			break;
		}
		
		Maze maze = null;
		switch (diff)
		{
			case 1:
				maze = new Maze(11, 11);
				break;
			case 2:
				maze = new Maze(21, 21);
				break;
			case 3:
				maze = new Maze(31, 31);
				break;
			case 4:
				return;
		}
		startTime = System.currentTimeMillis();
		while (true)
		{
			try
			{
				maze.display();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			char c;
			while (true)
			{
				try
				{
					c = nextChar(sc);
				}
				catch (InvalidInputException e)
				{
					System.out.println(e.getMessage());
					continue;
				}
				break;
			}
			switch (c)
			{
				case 'w':
				case 'W':
					maze.moveUp();
					break;
				case 'A':
				case 'a':
					maze.moveLeft();
					break;
				case 'S':
				case 's':
					maze.moveDown();
					break;
				case 'D':
				case 'd':
					maze.moveRight();
					break;
				case 'Z':
				case 'z':
					maze.undo();
					break;
				case 'Q':
				case 'q':
					return;
				default:
					System.out.println("Please enter a valid move");
			}
			if (maze.gameWon())
			{
				try
				{
					maze.display();
					System.out.println("\nCONGRATULATIONS! YOU WON!");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				break;
			}
		}
		
		long time = System.currentTimeMillis() - startTime;
		int score = (int) (300_000 / time) * diff * diff; //300s = 5m
		System.out.println("\nYou finished the game in " + time / 1000 + "s.");
		System.out.println("You scored " + score + " points");
		super.registerScore(score);
	}
}

class Maze
{
	private static final char TOP_RIGHT = '\u00BF', BOTTOM_LEFT = '\u00C0', BOTTOM_RIGHT = '\u00D9', TOP_LEFT = '\u00DA', VERTICAL = '\u00B3', HORIZONTAL = '\u00C4', BLOCK = '\u00DB', BLANK = ' ', UNDEFINED = (char) -1;
	private static final char PLAYER_CHAR = 'O';
	
	private Stack<State> states;
	
	private final int width, height;
	
	private char[][] tiles;
	private boolean[][] hooks; //These are the positions where the user can "jump" to
	
	private BufferedWriter bw;
	private int xPos = 1, yPos = 1;
	
	private Direction dir = Direction.RIGHT; //Stores the direction in which the player last moved in
	
	public Maze(int width, int height)
	{
		tiles = new char[width][height];
		hooks = new boolean[width][height];
		states = new Stack<State>();
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		this.width = width;
		this.height = height;
		
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				tiles[x][y] = BLANK;
			
		fillX(0, 0, height - 1);
		fillX(width - 1, 0, height - 1);
		fillY(0, 0, width - 1);
		fillY(height - 1, 0, width - 1);
		tiles[0][1] = HORIZONTAL;
		tiles[width - 1][height - 2] = BLANK;
		
		genMaze(0, 0, width - 1, height - 1);
		
		genHooks();
		hooks[width - 1][height - 2] = true; //Last hook that player needs to enter into, to win the game
	}
	
	public boolean gameWon()
	{
		return xPos == width - 1 && yPos == height - 2;
	}
	
	private void genHooks()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (getTile(x, y) == BLANK)
				{
					//If you expand the statement below, you'll find that it's just checking whether 3 tiles in an L-shape are blank or not
					//If there's an L-shape at x,y it is a hook
					if ((getTile(x - 1, y) == BLANK || getTile(x + 1, y) == BLANK) && (getTile(x, y - 1) == BLANK || getTile(x, y + 1) == BLANK))
						hooks[x][y] = true;
				}
			}
		}
	}
	
	private void genMaze(int x1, int y1, int x2, int y2)
	{
		Random rand = new Random();
		int mx = (x1 + x2) / 2, my = (y1 + y2) / 2;
		
		//Make sure that mx and my are on even indices, since walls only exist at even indices
		if(mx % 2 != 0)
			mx++;
		if(my % 2 != 0)
			my++;
		
		if(!(mx > x1 && mx < x2 && my > y1 && my < y2))
			return;
		
		fillX(mx, y1 + 1, y2 - 1);
		fillY(my, x1 + 1, x2 - 1);
		
		int rx1 = rand.nextInt(x2 - mx - 1) + mx + 1;
		while (rx1 == (mx + x2) / 2 && x2 - mx != 2 || rx1 % 2 == 0)
			rx1 = rand.nextInt(x2 - mx - 1) + mx + 1;
		
		int rx2 = rand.nextInt(mx - x1 - 1) + x1 + 1;
		while (rx2 == (x1 + mx) / 2 && mx - x1 != 2 || rx2 % 2 == 0)
			rx2 = rand.nextInt(mx - x1 - 1) + x1 + 1;
		
		int ry1 = rand.nextInt(y2 - my - 1) + my + 1;
		while (ry1 == (my + y2) / 2 && y2 - my != 2 || ry1 % 2 == 0)
			ry1 = rand.nextInt(y2 - my - 1) + my + 1;
		
		int ry2 = rand.nextInt(my - y1 - 1) + y1 + 1;
		while (ry2 == (y1 + my) / 2 && my - y1 != 2 || ry2 %2 == 0)
			ry2 = rand.nextInt(my - y1 - 1) + y1 + 1;
		
		int choice = rand.nextInt(4);
		if (choice != 0)
			tiles[rx1][my] = BLANK;
		if (choice != 1)
			tiles[rx2][my] = BLANK;
		if (choice != 2)
			tiles[mx][ry1] = BLANK;
		if (choice != 3)
			tiles[mx][ry2] = BLANK;
		
		genMaze(x1, y1, mx, my);
		genMaze(mx, y1, x2, my);
		genMaze(x1, my, mx, y2);
		genMaze(mx, my, x2, y2);
	}
	
	private void fillX(int x, int ymin, int ymax)
	{
		for (int y = ymin; y <= ymax; y++)
			tiles[x][y] = BLOCK;
	}
	
	private void fillY(int y, int xmin, int xmax)
	{
		for (int x = xmin; x <= xmax; x++)
			tiles[x][y] = BLOCK;
	}
	
	//Moves the player in the given direction.
	//If there is a hook in that direction, returns true and xPos and yPos are set successfully.
	//If there is no hook, false is returned and xPos and yPos are left unchanged.
	public boolean move(Direction dir)
	{
		states.push(new State(copy(tiles), xPos, yPos, this.dir));
		int x = xPos, y = yPos;
		do
		{
			switch (dir)
			{
				case RIGHT:
					x++;
					break;
				case LEFT:
					x--;
					break;
				case UP:
					y--;
					break;
				case DOWN:
					y++;
					break;
			}
			if (!isBlank(x, y))
			{
				states.pop(); //If user didn't move, don't save this state as it will be a repeat
				return false;
			}
			else if (isHook(x, y))
				break;
		}
		while (true);
		
		int dx = x - xPos, dy = y - yPos;
		int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);
		dx /= steps;
		dy /= steps;
		tiles[xPos][yPos] = tileForDirections(this.dir, dir);
		do
		{
			xPos += dx;
			yPos += dy;
			if (isBlank(xPos, yPos))
				tiles[xPos][yPos] = tileForDirection(dir);
		}
		while (!isHook(xPos, yPos));
		
		this.dir = dir;
		return true;
	}
	
	private char tileForDirections(Direction d1, Direction d2)
	{
		if (d1 == Direction.RIGHT && d2 == Direction.DOWN || d1 == Direction.UP && d2 == Direction.LEFT)
			return TOP_RIGHT;
		else if (d1 == Direction.UP && d2 == Direction.RIGHT || d1 == Direction.LEFT && d2 == Direction.DOWN)
			return TOP_LEFT;
		else if (d1 == Direction.DOWN && d2 == Direction.RIGHT || d1 == Direction.LEFT && d2 == Direction.UP)
			return BOTTOM_LEFT;
		else if (d1 == Direction.RIGHT && d2 == Direction.UP || d1 == Direction.DOWN && d2 == Direction.LEFT)
			return BOTTOM_RIGHT;
		else if (d1 == d2 && d1 == Direction.RIGHT || d1 == Direction.LEFT)
			return HORIZONTAL;
		else if (d1 == d2 && d1 == Direction.UP || d1 == Direction.DOWN)
			return VERTICAL;
		
		return 'E';
	}
	
	private char tileForDirection(Direction d)
	{
		if (d == Direction.RIGHT || d == Direction.LEFT)
			return HORIZONTAL;
		return VERTICAL;
	}
	
	public void moveRight()
	{
		move(Direction.RIGHT);
	}
	
	public void moveLeft()
	{
		move(Direction.LEFT);
	}
	
	public void moveUp()
	{
		move(Direction.UP);
	}
	
	public void moveDown()
	{
		move(Direction.DOWN);
	}
	
	public void undo()
	{
		if (states.size() > 0)
		{
			State state = states.pop();
			tiles = state.tiles;
			xPos = state.xPos;
			yPos = state.yPos;
			dir = state.dir;
		}
	}
	
	//Returns false if indices given are out of bounds, or if tile is BLANK
	public boolean isBlank(int xPos, int yPos)
	{
		return xPos >= 0 && xPos < tiles.length && yPos >= 0 && yPos < tiles[xPos].length && tiles[xPos][yPos] == BLANK;
	}
	
	//Returns false if indices given are out of bounds, or if tile is a hook
	public boolean isHook(int xPos, int yPos)
	{
		return xPos >= 0 && xPos < tiles.length && yPos >= 0 && yPos < tiles[xPos].length && hooks[xPos][yPos];
	}
	
	//Returns true if indices given are out of bounds, or if tile is a BLOCK
	private boolean isBlock(int xPos, int yPos)
	{
		return xPos < 0 || xPos >= tiles.length || yPos < 0 || yPos >= tiles[xPos].length || tiles[xPos][yPos] == BLOCK;
	}
	
	public void display() throws IOException
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (x == xPos && y == yPos)
					bw.write(PLAYER_CHAR);
				else
					bw.write(tiles[x][y]);
			}
			
			if(y == 2)
				bw.write("    CONTROLS :");
			else if(y == 3)
				bw.write("    W - UP, A - LEFT, S - DOWN, D - RIGHT, Z - UNDO");
			else if(y == 4)
				bw.write("    ENTER 'Q' TO RETURN TO MAIN MENU");
			if(y == height-2) //If we're on the second last line, print "END"
				bw.write(" EXIT");
			bw.write('\n');
		}
		
		bw.flush();
	}
	
	private Direction oppositeOf(Direction dir)
	{
		switch (dir)
		{
			case LEFT:
				return Direction.RIGHT;
			case RIGHT:
				return Direction.LEFT;
			case UP:
				return Direction.DOWN;
			case DOWN:
				return Direction.UP;
		}
		
		return null;
	}
	
	private List<Direction> listOfDirs()
	{
		List<Direction> list = new ArrayList<Direction>();
		list.add(Direction.UP);
		list.add(Direction.DOWN);
		list.add(Direction.LEFT);
		list.add(Direction.RIGHT);
		
		return list;
	}
	
	private char getTile(int x, int y)
	{
		if (x >= 0 && x < width && y >= 0 && y < height)
			return tiles[x][y];
		return UNDEFINED;
	}
	
	private void setTile(int x, int y, char c)
	{
		if (x >= 0 && y >= 0 && x < width && y < height)
			tiles[x][y] = c;
	}
	
	private char[][] copy(char[][] tiles)
	{
		char[][] result = new char[tiles.length][tiles[0].length];
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[i].length; j++)
				result[i][j] = tiles[i][j];
		return result;
	}
	
	enum Direction
	{
		LEFT, RIGHT, UP, DOWN;
	}
	
	class State
	{
		char[][] tiles;
		int xPos, yPos;
		Direction dir;
		
		public State(char[][] tiles, int xPos, int yPos, Direction dir)
		{
			this.tiles = tiles;
			this.xPos = xPos;
			this.yPos = yPos;
			this.dir = dir;
		}
	}
}
