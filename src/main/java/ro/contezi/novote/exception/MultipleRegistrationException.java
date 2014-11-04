package ro.contezi.novote.exception;

public class MultipleRegistrationException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public MultipleRegistrationException() {
	}

	public MultipleRegistrationException(String s) {
		super(s);
	}

	public MultipleRegistrationException(Throwable cause) {
		super(cause);
	}

	public MultipleRegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

}
