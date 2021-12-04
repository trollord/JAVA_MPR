package input;

import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.InvalidInputException;

public class Input
{
	public int test;
	
	public static int nextInt(Scanner sc) throws InvalidInputException
	{
		try
		{
			return Integer.parseInt(sc.nextLine());
		}
		catch (NumberFormatException e)
		{
			throw new InvalidInputException();
		}
	}
	
	//Returns integer if it is within range, otherwise throws error
	public static int nextInt(int start, int end, Scanner sc) throws InvalidInputException
	{
		int n = nextInt(sc);
		if (n < start || n > end)
			throw new InvalidInputException();
		return n;
	}
	
	public static char nextChar(Scanner sc) throws InvalidInputException
	{
		String str = sc.nextLine();
		if(str.length() != 1)
			throw new InvalidInputException("Please enter a single character");
		return str.charAt(0);
	}
	
	public static float nextFloat(Scanner sc) throws InvalidInputException
	{
		try
		{
			return Float.parseFloat(sc.nextLine());
		}
		catch (NumberFormatException e)
		{
			throw new InvalidInputException();
		}
	}
	
	public static double nextDouble(Scanner sc) throws InvalidInputException
	{
		try
		{
			return Double.parseDouble(sc.nextLine());
		}
		catch (NumberFormatException e)
		{
			throw new InvalidInputException();
		}
	}
	
	public static String nextLine(Scanner sc)
	{
		return sc.nextLine();
	}
}
