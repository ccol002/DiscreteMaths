package rules;

import forms.And;
import forms.Form;

public class AndE1 extends Rule{

	private int line;
	
	public AndE1(int line)
	{
		this.line = line;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(line);
		if (p.getClass() != And.class)
			throw new InvalidRuleException("AndE1 - Did not find a conjunction at line "+ line);
		else
			return ((And)p).getLeft().clone();	 
	}
	
	public String toString()
	{
		return line + ", And Elimination 1";
	}
	
}










