package rules;

import forms.ForAll;
import forms.Form;

public class ForallE extends Rule{

	private int line;
	
	public ForallE(int line)
	{
		this.line = line;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(line);
		if (p.getClass() != ForAll.class)
			throw new InvalidRuleException("ForallE - Did not find a forall quantifier at line "+ line);
		else if (p.getSubstNew()!=null || p.getSubstOld()!=null)
			throw new InvalidRuleException("ForallE - Unexpected variable substitution found at line "+ line);
		else {
			Form sub = ((ForAll)p).getSub().clone();
			sub.subst(((ForAll)p).getVar().toString(), ((ForAll)p).getVar().toString());
			return sub;
		}
	}
	
	public String toString()
	{
		return line + ", Forall Elimination";
	}
	
}










