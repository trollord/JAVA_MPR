package auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.InvalidUsernameException;

public class Authenticator
{
	public boolean isValid(String u_name, String p_pass)
	{
		try
		{
			Connection con = DriverManager.getConnection(SQLDetails.url + "user_information", SQLDetails.db_user, SQLDetails.passwd);
			
			Statement stmt = con.createStatement();
			String query = "Select u_name,p_pass from register";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				String one = rs.getString("u_name");
				String two = rs.getString("p_pass");
				if (u_name.equals(one) && p_pass.equals(two))
					return true;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public void checkValidity(String u_name) throws InvalidUsernameException
	{
		if (u_name.equals("q") || u_name.equals("guest") || u_name.equals("return"))
			throw new InvalidUsernameException("Please select another username. This username is reserved");
		try
		{
			Connection con = DriverManager.getConnection(SQLDetails.url + "user_information", SQLDetails.db_user, SQLDetails.passwd);
			
			Statement stmt = con.createStatement();
			String query = "Select u_name from register";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				String one = rs.getString("u_name");
				if (u_name.equals(one))
				{
					throw new InvalidUsernameException("Username already taken. Please select another username!");
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void register(String username, String pin)
	{
		try
		{
			Connection con = DriverManager.getConnection(SQLDetails.url + "user_information", SQLDetails.db_user, SQLDetails.passwd);
			String s1 = "insert into register(u_name,p_pass) values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(s1);
			// String query = "Select name,rollno from stud";
			pstmt.setString(1, username);
			pstmt.setString(2, pin);
			pstmt.executeUpdate();
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
}
