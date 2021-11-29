package game;

import java.util.Scanner;

import auth.User;

public abstract class Game
{
	protected final String GAME_NAME;
	protected User user;
	
	public Game(String GAME_NAME, User user)
	{
		this.GAME_NAME = GAME_NAME;
		this.user = user;
	}
	
	protected void registerScore(int value)
	{
		
	}
	
	protected void displayLeaderboard()
	{
		
	}
	
	protected abstract void start(Scanner sc);
}
