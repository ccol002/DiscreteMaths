package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;

public class SubHyp extends Rule{

	public SubHyp ()
	{}
	
	public Form evaluate(Proof proof)throws InvalidRuleException
	{
		throw new InvalidRuleException("SubHyp is not evaluatable");
	}
	
	public String toString()
	{
		return "SubHyp";
	}
}
