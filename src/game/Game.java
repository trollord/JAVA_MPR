package game;

import java.util.Scanner;
import auth.User;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

//maze_game  rock_paper_scissor  x_and_o
public abstract class Game {

	String url = "jdbc:mysql://localhost:3306/";
	String db_user = "root";
	String passwd = "Mandar@165";
	protected final String GAME_NAME;
	protected User user;

	public Game(String GAME_NAME, User user) {
		this.GAME_NAME = GAME_NAME;
		this.user = user;
	}

	protected void registerScore(int value) {

		try {
			Connection con = DriverManager.getConnection(url + GAME_NAME, db_user, passwd);
			String s1 = "insert into data(p_name,p_score) values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(s1);
			pstmt.setString(1, user.getUsername());
			pstmt.setInt(2, value);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	protected void displayLeaderboard() {
		System.out.println("*********_THE LEADERBOARD_**********");
		System.out.println("NAME\t--" + "POINTS\t");
		System.out.printf("_____________________________________\n");
		try {
			Connection con = DriverManager.getConnection(url + GAME_NAME, db_user, passwd);
			String query = "SELECT * FROM data ORDER BY p_score DESC LIMIT 5";
			Statement stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(query);
			while (rs2.next()) {
				String one = rs2.getString("p_name");
				Integer two = rs2.getInt("p_score");
				System.out.println(one + "\t--" + two + "\t");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	public abstract void start(Scanner sc);
}
