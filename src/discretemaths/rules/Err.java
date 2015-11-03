package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;

public class Err extends Rule{

	private String error;
	
	public Err(String error)
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
