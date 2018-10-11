package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Biimplies;
import discretemaths.forms.Form;
import discretemaths.forms.Implies;

public class BiimpliesI extends Rule{

	private int line1;
	private int line2;
	
	public BiimpliesI(int line1, int line2)
	{
		this.line1 = line1;
		this.line2 = line2;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form one = proof.refer(line1);
		Form two =	proof.refer(line2);
		
		if (one.getClass() != Implies.class)
			throw new InvalidRuleException("BiimpliesI - Implication expected at line " + line1);
		else if (two.getClass() != Implies.class)
			throw new InvalidRuleException("BiimpliesI - Implication expected at line " + line2);
	    if (one.hasSubt())
			throw new InvalidRuleException("BiimpliesI - Don't know how to handle substitution "+ line1);
	    else if (two.hasSubt())
			throw new InvalidRuleException("BiimpliesI - Don't know how to handle substitution "+ line2);
		
		else if (
			((Implies)one).getLeft().equals(((Implies)two).getRight()) 
			&& ((Implies)one).getRight().equals(((Implies)two).getLeft()))
			return new Biimplies(((Implies)one).getLeft().clone(),((Implies)one).getRight().clone());
		else
			throw new InvalidRuleException("Mismatch in implications at line " + line1 + " and " + line2);	 
	}
	
	public String toString()
	{
		return line1 + "," + line2 + ", Biimplies Introduction";
	}
	
}










