package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;
import discretemaths.forms.Implies;
import discretemaths.forms.Or;

public class OrE extends Rule{

	private int orLine;
	private int impLine1;
	private int impLine2;
	
	public OrE(int orLine, int impLine1, int impLine2)
	{
		this.orLine = orLine;
		this.impLine1 = impLine1;
		this.impLine2 = impLine2;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form or = proof.refer(orLine);
		Form imp1 = proof.refer(impLine1);
		Form imp2 = proof.refer(impLine2);
		
		if (or.getClass() != Or.class)
			throw new InvalidRuleException("OrE - Did not find a disjunction at line "+ orLine);
		else if (imp1.getClass() != Implies.class)
			throw new InvalidRuleException("OrE - Did not find an implication at line "+ impLine1);
		else if (imp2.getClass() != Implies.class)
			throw new InvalidRuleException("OrE - Did not find an implication at line "+ impLine2);
		else if (!((Or)or).getLeft().equals(((Implies)imp1).getLeft()))
			throw new InvalidRuleException("OrE - Mismatch in disjunction and implication at lines "+ orLine + "," + impLine1);
		else if (!((Or)or).getRight().equals(((Implies)imp2).getLeft()))
			throw new InvalidRuleException("OrE - Mismatch in disjunction and implication at lines "+ orLine + "," + impLine2);
		else if (!((Implies)imp1).getRight().equals(((Implies)imp2).getRight()))
			throw new InvalidRuleException("OrE - Mismatch in implications at lines "+ impLine1 + "," + impLine2);
		else
			return ((Implies)imp1).getRight().clone();	 
	}
	
	public String toString()
	{
		return orLine+ "," +impLine1 + "," + impLine2 + ", Or Elimination";
	}
	
}










