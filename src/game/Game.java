package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import auth.SQLDetails;
import auth.User;

//maze_game  rock_paper_scissor  x_and_o
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
		registerScore(user, value);
	}
	
	protected void registerScore(User u, int value)
	{
		try
		{
			Connection con = DriverManager.getConnection(SQLDetails.url + GAME_NAME, SQLDetails.db_user, SQLDetails.passwd);
			String s1 = "insert into data(p_name,p_score) values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(s1);
			pstmt.setString(1, u.getUsername());
			pstmt.setInt(2, value);
			pstmt.executeUpdate();
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	protected void displayLeaderboard()
	{
		System.out.println("*********_THE " + GAME_NAME.toUpperCase() + " LEADERBOARD_**********");
		System.out.println("NAME\t\t" + "POINTS\t");
		System.out.printf("_____________________________________\n");
		try
		{
			Connection con = DriverManager.getConnection(SQLDetails.url + GAME_NAME, SQLDetails.db_user, SQLDetails.passwd);
			String query = "SELECT * FROM data ORDER BY p_score DESC LIMIT 5";
			Statement stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(query);
			while (rs2.next())
			{
				String one = rs2.getString("p_name");
				Integer two = rs2.getInt("p_score");
				System.out.println(one + "\t\t" + two + "\t");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	public abstract void start(Scanner sc);
}
