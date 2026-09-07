package discretemaths.rules;

import java.util.List;

import discretemaths.Proof;
import discretemaths.forms.Form;

public class Lemma extends Rule{

	private int line;
	private Proof lemma;
	
	public Lemma ()
	{}
	
	public Lemma (int line, Proof proof)
	{
		this.line = line;
		this.lemma = proof;
	}
	
	//when lemma is a tautology
	public Lemma (Proof proof)
	{
		this.line = -1;
		this.lemma = proof;
	}
	
	//lemmata are not checked
	public Form evaluate(Proof proof)throws Exception
	{
		if (this.lemma == null)
			throw new InvalidRuleException("Lemma without proof is not evaluatable");
		else if (!this.lemma.isWellFormed())
			throw new InvalidRuleException("Proof provided with lemma is not well formed");
		else{
			List<Form> hyps = this.lemma.getHyp();
			if (hyps.size()>1)
				throw new InvalidRuleException("Proof provided with lemma has more than one hypothsis");
			else if (hyps.size()==1 && hyps.get(0).equals(proof.getLine(line)))
				return this.lemma.getConclusion().clone();
			else if (hyps.size()==1)//mismatch
				throw new InvalidRuleException("Lemma did not match in the context... but it might be my fault as I'm not yet very intelligent in matching!");
			else //(hyps.size()==0)
				return this.lemma.getConclusion().clone();
		}
	}
	
	public String toString()
	{
		if (lemma == null)
			return "Lemma";
		else
			return line + ", Lemma: " + lemma;
	}
}
