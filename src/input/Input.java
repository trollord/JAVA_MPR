package input;

import java.util.Scanner;

import exceptions.InvalidInputException;

public class Input
{
	public int test;
	
	public static int nextInt(Scanner sc)
	{
		return Integer.parseInt(sc.nextLine());
	}
	
	//Returns integer if it is within range, otherwise throws error
	public static int nextInt(int start, int end, Scanner sc) throws InvalidInputException
	{
		int n = nextInt(sc);
		if (n < start || n > end)
			throw new InvalidInputException();
		return n;
	}
	
	public static float nextFloat(Scanner sc)
	{
		return Float.parseFloat(sc.nextLine());
	}
	
	public static double nextDouble(Scanner sc)
	{
		return Double.parseDouble(sc.nextLine());
	}
	
	public static String nextLine(Scanner sc)
	{
		return sc.nextLine();
	}
}
