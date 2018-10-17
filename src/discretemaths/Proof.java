package discretemaths;

import java.util.ArrayList;
import java.util.List;

import discretemaths.forms.False;
import discretemaths.forms.Form;
import discretemaths.rules.AndE1;
import discretemaths.rules.AndE2;
import discretemaths.rules.AndI;
import discretemaths.rules.BiimpliesE1;
import discretemaths.rules.BiimpliesE2;
import discretemaths.rules.BiimpliesI;
import discretemaths.rules.Copy;
import discretemaths.rules.Err;
import discretemaths.rules.ExistsE;
import discretemaths.rules.ExistsI;
import discretemaths.rules.ForallE;
import discretemaths.rules.ForallI;
import discretemaths.rules.Hyp;
import discretemaths.rules.ImpliesE;
import discretemaths.rules.ImpliesI;
import discretemaths.rules.InvalidRuleException;
import discretemaths.rules.Lemma;
import discretemaths.rules.NotE;
import discretemaths.rules.NotI;
import discretemaths.rules.OrE;
import discretemaths.rules.OrI1;
import discretemaths.rules.OrI2;
import discretemaths.rules.Rule;
import discretemaths.rules.SubHyp;
import discretemaths.rules.SubstAdd;
import discretemaths.rules.SubstRem;

public class Proof {

	private List<Form> proof = new ArrayList<Form>();
	private List<Rule> reasons = new ArrayList<Rule>();
	private List<Integer> depths = new ArrayList<Integer>();
	private int depth = 1;
	
	private int step = 1;

	private Proof()
	{}
	
	//non-zero based index
	public int getCurrentLine()
	{
		return proof.size();
	}
	
	public int getCurrentDepth()
	{
		return depth;
	}
	
	public Form getLine(int i) throws Exception
	{
		if (i<1)
			throw new Exception("Numbering system is not zero-based");
		else if (i>getCurrentLine())
			throw new Exception("Invalid line number: " + i);
		
		return proof.get(i-1);
	}
	
	
	//checks if line i is visible from the current end of the proof as it is being built
	//1 based index
	public Form refer(int i) throws Exception
	{
		Form f = getLine(i);
		int lineDepth = getDepth(i);
		
		int allowedDepth = getCurrentDepth();
		//iterate from current line till referred line
		//to get the allowedDepth
		for (int j = depths.size(); j >=i; j--)
			if (getDepth(j) < allowedDepth)
				allowedDepth = getDepth(j);
		
		if (lineDepth < allowedDepth)
			throw new Exception("Line " + i + " is not visible from the current line of the proof");
		
		return f;
	}

	public Rule getReason(int i) throws Exception
	{
		if (i<1)
			throw new Exception("Numbering system is not zero-based");
		else if (i>getCurrentLine())
			throw new Exception("Invalid line number: " + i);
		
		return reasons.get(i-1);
	}
	
	public int getDepth(int i) throws Exception
	{
		if (i<1)
			throw new Exception("Numbering system is not zero-based");
		else if (i>getCurrentLine())
			throw new Exception("Invalid line number: " + i);
		
		return depths.get(i-1);
	}

	public Proof rule(Rule r)
	{
		try{
			proof.add(r.evaluate(this));
			reasons.add(r);
		}catch(Exception ex)
		{
			proof.add(new False());
			reasons.add(new Err("Problem encountered at line " + proof.size() + ": " + ex.getMessage()));
		}
		depths.add(depth);
		return this;
	}
	
	public List<Form> getHyp()
	{
		List<Form> hyps = new ArrayList<Form>();
		for (int i=0; i<proof.size(); i++)
			if (reasons.get(i).getClass() == Hyp.class)
				hyps.add(proof.get(i));
		return hyps;
	}
	
	public String getHypString()
	{
		String s = "";
		for (Form f: getHyp())
			s += f + ", ";

		//remove extra comma
		if (s.endsWith(", "))
			s = s.substring(0,s.length()-2);
		
		return s;
	}
	
	public Form getConclusion()
	{
		return proof.get(proof.size()-1);
	}
	
	public Proof printStatement()
	{
		String proofStat = "Proof Statement: " + getHypString()+" |- " + getConclusion();
		System.out.println(proofStat);
		return this;
	}
	
	public Proof print()
	{
		return print(Integer.MAX_VALUE);
	}
	
	//shows only up to current step depth
	public Proof printStepped()
	{
		return print(this.step++);
	}
	
	//resets step count
	public Proof resetSteps()
	{
		this.step = 1;
		return this;
	}
	
	public boolean isWellFormed()
	{
		return isWellFormed(false);
	}
	
	public boolean isWellFormed(boolean verbose)
	{
		if (depth != 0) {
			if (verbose) 
				System.out.println("Incomplete proof! (Did you forget an end?)");
			return false;
		}
		if (proof.size() == 0){
			if (verbose)
				System.out.println("Empty proof! Nothing to show");
			return false;
		}
		
		return true;
	}
	
	//generic print
	public Proof print(int step)
	{
		if (!isWellFormed(true))
			return this;

		printStatement();

		for (int i=0; i<proof.size(); i++)
		{
			//creates space according to depth
			String depthString = "";
			while (depthString.length() < depths.get(i)*2-2)
				depthString += " ";

			//formats line number
			String number = "" + (i+1);
			if (number.length()<2)
				number = " "+number;

			if (depths.get(i) > step)
				System.out.println(depthString+number+ ": ");
			else {
				String line = proof.get(i).toString();
				while (line.length() < 30)
					line += " ";

				System.out.println(depthString+number+ ": " + line + reasons.get(i));
			}
		}
		System.out.println();
		return this;
	}
	
	public String toString()
	{
		return getHypString() + " |- " + getConclusion();
	}

	//note that begin() does not contribute to an additional line in the proof
	public static Proof begin()
	{
		return new Proof();
	}

	public static Proof hyp(Form... forms) throws InvalidRuleException
	{
		Proof pf = new Proof();
		
		for (Form p:forms){
			if (!p.isWellFormed())
				throw new InvalidRuleException("Malformed formula: " + p);
			pf.proof.add(p);
			pf.reasons.add(new Hyp());
			pf.depths.add(pf.depth);
		}
		return pf;
	}

	//note that end() does not contribute to an additional line in the proof
	public Proof end() throws Exception
	{
		if (depth==0)
			throw new Exception("too many ends!");
		else depth--;
		reasons.get(reasons.size()-1).setEnd();
		return this;
	}

	public Proof subhyp(Form p) throws InvalidRuleException
	{
		depth++;
		
		if (!p.isWellFormed())
			throw new InvalidRuleException("Malformed formula: " + p);
		proof.add(p);
		reasons.add(new SubHyp());
		depths.add(depth);
		return this;
	}

	//lemma without info
	public Proof lemma(Form p) throws InvalidRuleException
	{
		if (!p.isWellFormed())
			throw new InvalidRuleException("Malformed formula: " + p);
		proof.add(p);
		reasons.add(new Lemma());
		depths.add(depth);
		return this;
	}
	
	public Proof lemma(int line, Proof proof)
	{
		return rule(new Lemma(line, proof));
	}
	
	//for when lemma is a tautology
	public Proof lemma(Proof proof)
	{
		return rule(new Lemma(proof));
	}

	//syntactic sugar for rules
	public Proof copy(int source)
	{
		return rule(new Copy(source));
	}
	
	public Proof andE1(int conjunction)
	{
		return rule(new AndE1(conjunction));
	}

	public Proof andE2(int conjunction)
	{
		return rule(new AndE2(conjunction));
	}
	
	public Proof andI(int a, int b)
	{
		return rule(new AndE2(a , b));
	}

	public Proof impliesE(int implication, int truth)
	{
		return rule(new ImpliesE(implication,truth));
	}

	public Proof impliesI(int start, int end)
	{
		return rule(new ImpliesI(start,end));
	}

	public Proof orE(int disjunction, int implicationLeft, int implicationRight)
	{
		return rule(new OrE(disjunction,implicationLeft,implicationRight));
	}

	public Proof orI1(int left, Form rightFormula)
	{
		return rule(new OrI1(left,rightFormula));
	}

	public Proof orI2(int right, Form leftFormula)
	{
		return rule(new OrI2(right,leftFormula));
	}

	public Proof biimpliesI(int implicationRight, int implicationLeft)
	{
		return rule(new BiimpliesI(implicationRight,implicationLeft));
	}

	public Proof biimpliesE1(int biimplication)
	{
		return rule(new BiimpliesE1(biimplication));
	}

	public Proof biimpliesE2(int biimplication)
	{
		return rule(new BiimpliesE2(biimplication));
	}

	public Proof notE(int doubleNegation)
	{
		return rule(new NotE(doubleNegation));
	}

	public Proof notI(int implicationPositive, int implicationNegative)
	{
		return rule(new NotI(implicationPositive,implicationNegative));
	}
	
	public Proof forallE(int forall)
	{
		return rule(new ForallE(forall));
	}
	
	public Proof existsI(int source,String var, String type)
	{
		return rule(new ExistsI(source,var,type));
	}
	
	public Proof existsE(int exists,int forall)
	{
		return rule(new ExistsE(exists,forall));
	}
	
	public Proof forallI(int source,String var, String type)
	{
		return rule(new ForallI(source,var,type));
	}
	
	public Proof substAdd(int i, String var)
	{
		return rule(new SubstAdd(i, var));
	}
	
	public Proof substRem(int i)
	{
		return rule(new SubstRem(i));
	}
}
