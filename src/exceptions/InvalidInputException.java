package exceptions;

public class InvalidInputException extends Exception
{
	public InvalidInputException()
	{
		super("\nPlease enter a valid input\n");
	}
	
	public InvalidInputException(String msg)
	{
		super(msg);
	}
}
