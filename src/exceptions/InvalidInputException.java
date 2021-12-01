package exceptions;

public class InvalidInputException extends Exception
{
	@Override
	public String getMessage()
	{
		return "\nPlease enter a valid input\n";
	}
}
