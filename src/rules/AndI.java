package rules;

import forms.And;
import forms.Form;

public class AndI extends Rule{

	private int line1;
	private int line2;
	
	public AndI(int line1, int line2)
	{
		this.line1 = line1;
		this.line2 = line2;
	}
	
	public Form evaluate(Proof proof) throws Exception
	{
		return new And(proof.refer(line1).clone(),proof.refer(line2).clone());	 
	}
	
	public String toString()
	{
		return line1 + "," + line2 + ", And Introduction";
	}
	
}










