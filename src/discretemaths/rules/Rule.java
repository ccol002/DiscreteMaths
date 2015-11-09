package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;


public abstract class Rule {

	private boolean end = false;
	
	public void setEnd()
	{
		end = true;
	}
	
	public boolean isEnd()
	{
		return end;
	}
	
	//returns the resulting line after applying the rule
	public abstract Form evaluate(Proof proof) throws Exception; 
	
	public abstract String toString();
}
