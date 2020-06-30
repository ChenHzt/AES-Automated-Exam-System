package logic;

public class CustomException extends Exception {

	// @param message is the exception message

	public CustomException(final String message) {
		super(message);
	}

	// @param message is the exception message
	// @param cause is the cause of the original exception

	public CustomException(final String message, final Throwable cause) {
		super(message, cause);

	}
}