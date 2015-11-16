package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.And;
import discretemaths.forms.Form;

public class AndI extends Rule{

	private int line1;
	private int line2;
	
	public AndI(int line1, int line2)
	{
		this.line1 = line1;
		this.line2 = line2;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p1 = proof.refer(line1);
		Form p2 = proof.refer(line2);
	    if (p1.hasSubt())
			throw new InvalidRuleException("AndI - Don't know how to handle substitution "+ line1);
	    else if (p2.hasSubt())
			throw new InvalidRuleException("AndI - Don't know how to handle substitution "+ line2);
	    else
	    		return new And(proof.refer(line1).clone(),proof.refer(line2).clone());	 
	}
	
	public String toString()
	{
		return line1 + "," + line2 + ", And Introduction";
	}
	
}










