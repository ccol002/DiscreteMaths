package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Exists;
import discretemaths.forms.Forall;
import discretemaths.forms.Form;
import discretemaths.forms.Implies;

public class ExistsE extends Rule{

	private int line1;
	private int line2;
	
	//filled in during evaluation
	private String occursFreeCheck="";
	
	public ExistsE(int line1, int line2)
	{
		this.line1 = line1;
		this.line2 = line2;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form exists = proof.refer(line1);
		if (exists.getClass() != Exists.class)
			throw new InvalidRuleException("ExistsE - Expected exitential quantifier in line " +line1);
		else if (exists.getSubstNew()!=null || exists.getSubstOld()!=null)
			throw new InvalidRuleException("ExistsE - Unexpected variable substitution found at line "+ line1);
			
		
		Form forall = proof.refer(line2);
		if (forall.getClass() != Forall.class)
			throw new InvalidRuleException("ExistsE - Expected universal quantifier in line " +line2);
		else if (forall.getSubstNew()!=null || forall.getSubstOld()!=null)
			throw new InvalidRuleException("ExistsE - Unexpected variable substitution found at line "+ line2);
		
		
		Form impl = ((Forall)forall).getSub();
		if (impl.getClass() != Implies.class)
			throw new InvalidRuleException("ExistsE - Expected implication within universal quantifier at line " +line2);
		else if (impl.getSubstNew()!=null || impl.getSubstOld()!=null)
			throw new InvalidRuleException("ExistsE - Unexpected variable substitution found at line "+ line2);
		
		Form left = ((Implies)impl).getLeft();
		if (!left.equals(((Exists)exists).getSub()))
			throw new InvalidRuleException("ExistsE - Mismatch in existential quantifier and implication at lines "+line1+ " and " +line2);
		
		Form right = ((Implies)impl).getRight();
		String var = ((Exists)exists).getVar();
		if (right.occursFree(var))
			throw new InvalidRuleException("ExistsE - Variable " + var+ " should not occur free in right side of implication at line "+line2);
		
		occursFreeCheck = "["+var+"\\"+right+"]";
		
		return right.clone();		
	}
	
	public String toString()
	{
		return line1+"," + line2 + ", Exists Elimination "+ occursFreeCheck;
	}
	
}










