package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;

public class SubstRem extends Rule{

	private int line;
	
	public SubstRem(int line)
	{
		this.line = line;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(line);
		
		if (p.getSubstNew() == null 
		 || p.getSubstOld() == null)
			throw new InvalidRuleException("Invalid substitution found at line " + line);
		else if (!proof.refer(line).getSubstNew().equals(proof.refer(line).getSubstOld()))
			throw new InvalidRuleException("Variable name mismatch found at line " + line);
		else
			return p.clone().removeSubst(); 
	}
	
	public String toString()
	{
		return line + ", Law P = P[x <- x]";
	}
	
}










