package auth;

import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Authenticator
{
	String url = "jdbc:mysql://localhost:3306/gaming_palacio";
	String user = "root";
	String passwd = "admin";
	
	public boolean isValid(String u_name, String p_pass)
	{
		int c = 0;
		try
		{
			
			Connection con = DriverManager.getConnection(url, user, passwd);
			
			Statement stmt = con.createStatement();
			String query = "Select u_name,p_pass from register";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				String one = rs.getString("u_name");
				String two = rs.getString("p_pass");
				if (u_name.equals(one) && p_pass.equals(two))
				{
					c = 1;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		if (c == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean exists(String u_name)
	{
		int c = 0;
		try
		{
			
			Connection con = DriverManager.getConnection(url, user, passwd);
			
			Statement stmt = con.createStatement();
			String query = "Select u_name from register";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				String one = rs.getString("u_name");
				if (u_name.equals(one))
				{
					c = 1;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		if (c == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void register(String username, String pin)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e)
		{
			
			System.out.println(e.getMessage());
		}
		
		try
		{
			Connection con = DriverManager.getConnection(url, user, passwd);
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
