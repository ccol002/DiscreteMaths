package rules;

public class InvalidRuleException extends Exception{

	private static final long serialVersionUID = 1861040015152215581L;

	public InvalidRuleException(String message) {
        super(message);
    }
}
