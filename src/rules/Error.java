package rules;

import forms.Form;

public class Error extends Rule{

	private String error;
	
	public Error(String error)
	{
		this.error = error;
	}
	
	public Form evaluate(Proof proof) throws Exception {
		throw new InvalidRuleException("Error is not evaluatable");
	}

	public String toString() {
		return error;
	}

	
}
