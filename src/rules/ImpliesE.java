package rules;

import forms.Implies;
import forms.Form;

public class ImpliesE extends Rule{

	private int impLine;
	private int leftLine;
	
	public ImpliesE(int impLine, int leftLine)
	{
		this.impLine = impLine;
		this.leftLine = leftLine;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(impLine);
		if (p.getClass() != Implies.class)
			throw new InvalidRuleException("Did not find a implication at line "+ impLine);
		else if (((Implies)p).getLeft().equals(proof.refer(leftLine)))
			return ((Implies)p).getRight().clone();
		else 
			throw new InvalidRuleException("Did not find matching statement for implication elimination at line "+ leftLine);
	}
	
	public String toString()
	{
		return impLine + "," + leftLine + ", Implies Elimination";
	}
	
}










