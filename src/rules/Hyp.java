package rules;

import forms.Form;

public class Hyp extends Rule{

	public Hyp ()
	{}
	
	public Form evaluate(Proof proof)throws InvalidRuleException
	{
		throw new InvalidRuleException("Hyp is not evaluatable");
	}
	
	public String toString()
	{
		return "Hyp";
	}
}
