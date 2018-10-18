package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.And;
import discretemaths.forms.Form;

public class AndE1 extends Rule{

	private int line;
	
	public AndE1(int line)
	{
		this.line = line;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		int[] lines = {line};
		if (checkSubHyp(proof, lines)){
			throw new InvalidRuleException("Trying to use line(s) from sub proof to outer proof");
		}

		Form p = proof.refer(line);
		if (p.getClass() != And.class)
			throw new InvalidRuleException("AndE1 - Did not find a conjunction at line "+ line);
		else if (p.hasSubt())
			throw new InvalidRuleException("AndE1 - Don't know how to handle substitution "+ line);
		else
			return ((And)p).getLeft().clone();	 
	}
	
	public String toString()
	{
		return line + ", And Elimination 1";
	}
	
}










