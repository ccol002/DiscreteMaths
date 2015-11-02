package rules;

import forms.Biimplies;
import forms.Implies;
import forms.Form;

public class BiimpliesE1 extends Rule{

	private int line;
	
	public BiimpliesE1(int line)
	{
		this.line = line;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(line);
		if (p.getClass() != Biimplies.class)
			throw new InvalidRuleException("BiimpliesE1 - Did not find a biimplication at line "+ line);
		else
			return new Implies(((Biimplies)p).getLeft().clone(),((Biimplies)p).getRight().clone());	 
	}
	
	public String toString()
	{
		return line + ", Biimplies Elimination 1";
	}
	
}










