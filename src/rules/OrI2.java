package rules;

import forms.Or;
import forms.Form;

public class OrI2 extends Rule{

	private int line;
	private Form form;
	
	public OrI2(int line, Form form)
	{
		this.line = line;
		this.form = form;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		return new Or(form,proof.refer(line).clone());	 
	}
	
	public String toString()
	{
		return line+ ", Or Introduction 2";
	}
	
}










