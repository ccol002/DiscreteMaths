package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Exists;
import discretemaths.forms.Form;

public class ExistsI extends Rule{

	private int line;
	private String var;
	private String type;
	
	public ExistsI(int line, String var, String type)
	{
		this.line = line;
		this.var = var;
		this.type = type;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		Form p = proof.refer(line);
		if (p.getSubstNew()==null || p.getSubstOld()==null)
			throw new InvalidRuleException("ExistsI - Did not find a valid substitution at line "+ line);
		else if (!p.getSubstOld().equals(var))
			throw new InvalidRuleException("ExistsI - Variable "+var + " did not match substituted variable "+p.getSubstOld()+" at line "+ line);
		else 
			return new Exists(var,type,p.clone().subst(null, null));		
	}
	
	public String toString()
	{
		return line + ", Exists Introduction";
	}
	
}










