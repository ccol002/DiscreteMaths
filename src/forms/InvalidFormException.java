package forms;

public class InvalidFormException extends Exception{

	static final long serialVersionUID = -733936279453765888L;

	public InvalidFormException(String message) {
        super(message);
    }
}
