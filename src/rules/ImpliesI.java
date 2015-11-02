package rules;

import forms.Implies;
import forms.Form;

public class ImpliesI extends Rule{

	private int startLine;
	private int endLine;
	
	
	public ImpliesI(int startLine, int endLine)
	{
		this.startLine = startLine;
		this.endLine = endLine;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Rule r = proof.getReason(startLine);
		if (r.getClass() != SubHyp.class)
			throw new InvalidRuleException("ImpliesI - Did not find an opening subhyp at "+ startLine);
		else if (!proof.getReason(endLine).isEnd())
			throw new InvalidRuleException("ImpliesI - Did not find end of subproof at "+ endLine + " (did you forget to call .end()?)");	
		else
			return new Implies(proof.refer(startLine).clone(), proof.refer(endLine).clone());	 
	}
	
	public String toString()
	{
		return startLine + "-" + endLine + ", Implies Introduction";
	}
	
}










