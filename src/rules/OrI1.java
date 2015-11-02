package rules;

import forms.Or;
import forms.Form;

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
		return new Or(proof.refer(line).clone(),form);	 
	}
	
	public String toString()
	{
		return line+ ", Or Introduction 1";
	}
	
}










