package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;

public class Lemma extends Rule{

	public Lemma ()
	{}
	
	//lemmata are not checked
	public Form evaluate(Proof proof)throws InvalidRuleException
	{
		throw new InvalidRuleException("Lemma is not evaluatable");
	}
	
	public String toString()
	{
		return "Lemma";
	}
}
