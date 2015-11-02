package rules;

import forms.ForAll;
import forms.Form;

public class ForallI extends Rule{

	private int line;
	private String var; 
	private String type;
	
	//filled in during evaluation
	private String occursFreeCheck="";
	
	public ForallI(int line, String var, String type)
	{
		this.line = line;
		this.var = var;
		this.type = type;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(line);
		if (p.getSubstNew()!=null || p.getSubstOld()!=null)
			throw new InvalidRuleException("ForallE - Unexpected variable substitution found at line "+ line);
		else {
			int currentDepth = proof.getCurrentDepth()-1;
			for (int i = proof.getCurrentLine(); i>0; i--){
				if (proof.getDepth(i) <= currentDepth)
					if (proof.getReason(i).getClass() == SubHyp.class
					 || proof.getReason(i).getClass() == Hyp.class){
						if (proof.getLine(i).occursFree(var))
							throw new Exception(var + " occurs free in line " + i+1);
						else
							occursFreeCheck += var + "\\" + i + ",";
					}
			}
			if (occursFreeCheck.length()>0)
				occursFreeCheck = "[" + occursFreeCheck.substring(0,occursFreeCheck.length()-1) + "]";
			
			Form forall = new ForAll(var, type,p.clone());
			return forall;
		}
	}
	
	public String toString()
	{
		return line + ", Forall Introduction "+occursFreeCheck;
	}
	
}










