package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Biimplies;
import discretemaths.forms.Form;
import discretemaths.forms.Implies;

public class BiimpliesE2 extends Rule{

	private int line;
	
	public BiimpliesE2(int line)
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
		if (p.getClass() != Biimplies.class)
			throw new InvalidRuleException("BiimpliesE2 - Did not find a biimplication at line "+ line);
		else if (p.hasSubt())
			throw new InvalidRuleException("BiimpliesE2 - Don't know how to handle substitution "+ line);
		else
			return new Implies(((Biimplies)p).getRight().clone(),((Biimplies)p).getLeft().clone());	 
	}
	
	public String toString()
	{
		return line + ", Biimplies Elimination 2";
	}
	
}










