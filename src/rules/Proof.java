package rules;

import java.util.ArrayList;
import java.util.List;

import forms.False;
import forms.Form;

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
			reasons.add(new rules.Error("Problem encountered at line " + proof.size() + ": " + ex.getMessage()));
		}
		depths.add(depth);
		return this;
	}
	
	public Proof printStatement()
	{
		String proofStat = "Proof Statement: ";
		boolean hyp = false;
		for (int i=0; i<proof.size(); i++)
			if (reasons.get(i).getClass() == Hyp.class){
				proofStat += proof.get(i) + ", ";
				hyp = true;
			}
		
		//remove extra comma
		if (hyp)
			proofStat = proofStat.substring(0,proofStat.length()-2);
	
		proofStat += " |- " + proof.get(proof.size()-1);
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
	
	//generic print
	public Proof print(int step)
	{
		if (depth != 0) {
			System.out.println("Incomplete proof! (Did you forget an end?)");
			return this;
		}
		if (proof.size() == 0){
			System.out.println("Empty proof! Nothing to show");
			return this;
		}

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

	//note that begin() does not contribute to an additional line in the proof
	public static Proof begin()
	{
		return new Proof();
	}

	public Proof hyp(Form p) throws InvalidRuleException
	{
		if (!p.isWellFormed())
			throw new InvalidRuleException("Malformed formula: " + p);
		proof.add(p);
		reasons.add(new Hyp());
		depths.add(depth);
		return this;
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

	public Proof lemma(Form p) throws InvalidRuleException
	{
		if (!p.isWellFormed())
			throw new InvalidRuleException("Malformed formula: " + p);
		proof.add(p);
		reasons.add(new Lemma());
		depths.add(depth);
		return this;
	}

	//syntactic sugar for rules
	public Proof copy(int i)
	{
		return rule(new Copy(i));
	}
	
	public Proof andE1(int i)
	{
		return rule(new AndE1(i));
	}

	public Proof andE2(int i)
	{
		return rule(new AndE2(i));
	}

	public Proof impliesE(int i, int j)
	{
		return rule(new ImpliesE(i,j));
	}

	public Proof impliesI(int i, int j)
	{
		return rule(new ImpliesI(i,j));
	}

	public Proof orE(int i, int j, int k)
	{
		return rule(new OrE(i,j,k));
	}

	public Proof orI1(int i, Form p)
	{
		return rule(new OrI1(i,p));
	}

	public Proof orI2(int i, Form p)
	{
		return rule(new OrI2(i,p));
	}

	public Proof biimpliesI(int i, int j)
	{
		return rule(new BiimpliesI(i,j));
	}

	public Proof biimpliesE1(int i)
	{
		return rule(new BiimpliesE1(i));
	}

	public Proof biimpliesE2(int i)
	{
		return rule(new BiimpliesE2(i));
	}

	public Proof notE(int i)
	{
		return rule(new NotE(i));
	}

	public Proof notI(int i, int j)
	{
		return rule(new NotI(i,j));
	}
	
	public Proof forallE(int i)
	{
		return rule(new ForallE(i));
	}
	
	public Proof existsI(int i,String var, String type)
	{
		return rule(new ExistsI(i,var,type));
	}
	
	public Proof existsE(int i,int j)
	{
		return rule(new ExistsE(i,j));
	}
	
	public Proof forallI(int i,String var, String type)
	{
		return rule(new ForallI(i,var,type));
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
