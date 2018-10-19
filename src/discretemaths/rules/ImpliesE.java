package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;
import discretemaths.forms.Implies;

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
		int[] lines = {impLine, leftLine};
		if (checkSubHyp(proof, lines)){
			throw new InvalidRuleException("Trying to use line(s) from sub proof to outer proof");
		}

		Form p = proof.refer(impLine);
		Form q = proof.refer(leftLine);
		if (p.getClass() != Implies.class)
			throw new InvalidRuleException("ImpliesE - Did not find a implication at line "+ impLine);
		else if (p.hasSubt())
			throw new InvalidRuleException("ImpliesE - Don't know how to handle substitution "+ impLine);
	    else if (q.hasSubt())
			throw new InvalidRuleException("ImpliesE - Don't know how to handle substitution "+ leftLine);
		
		else if (((Implies)p).getLeft().equals(q))
			return ((Implies)p).getRight().clone();
		else 
			throw new InvalidRuleException("ImpliesE - Did not find matching statement at line "+ leftLine);
	}
	
	public String toString()
	{
		return impLine + "," + leftLine + ", Implies Elimination";
	}
	
}










