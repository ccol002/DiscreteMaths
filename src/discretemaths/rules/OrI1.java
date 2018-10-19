package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;
import discretemaths.forms.Or;

public class OrI1 extends Rule{

	private int line;
	private Form form;
	
	public OrI1(int line, Form form)
	{
		this.line = line;
		this.form = form;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		int[] lines = {line};
		if (checkSubHyp(proof, lines)){
			throw new InvalidRuleException("Trying to use line(s) from sub proof to outer proof");
		}

		return new Or(proof.refer(line).clone(),form);	 
	}
	
	public String toString()
	{
		return line+ ", Or Introduction 1";
	}
	
}










