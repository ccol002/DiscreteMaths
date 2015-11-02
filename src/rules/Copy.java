package rules;

import forms.Form;

public class Copy extends Rule{

	private int line;
	
	public Copy(int line)
	{
		this.line = line;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		return proof.refer(line).clone(); 
	}
	
	public String toString()
	{
		return line + ", Copy";
	}
	
}










