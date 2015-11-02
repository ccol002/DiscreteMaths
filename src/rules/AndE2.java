package rules;

import forms.And;
import forms.Form;

public class AndE2 extends Rule{

	private int line;
	
	public AndE2(int line)
	{
		this.line = line;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(line);
		if (p.getClass() != And.class)
			throw new InvalidRuleException("AndE2 - Did not find a conjunction at line "+ line);
		else
			return ((And)p).getRight().clone();	 
	}
	
	public String toString()
	{
		return line + ", And Elimination 2";
	}
	
}










