package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;

public class SubstAdd extends Rule{

	private int line;
	private String var;
	
	public SubstAdd(int line, String var)
	{
		this.line = line;
		this.var = var;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		return proof.refer(line).clone().subst(var, var); 
	}
	
	public String toString()
	{
		return line + ", Law P = P[x <- x]";
	}
	
}










