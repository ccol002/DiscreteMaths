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

/**
 * Builds a natural deduction proof one step at a time and pretty-prints it.
 * <p>
 * A proof is started with {@link #begin()} or {@link #hyp(Form...)} and then
 * extended by chaining calls to the rule methods below (e.g. {@link #andI(int, int)},
 * {@link #impliesI(int, int)}), each of which appends one line to the proof and
 * checks that the rule was applied correctly. A sub-proof is opened with
 * {@link #subhyp(Form)} and closed with {@link #end()}.
 * <p>
 * For example:
 * <pre>{@code
 * hyp(and($("A"), $("B")))
 *   .andE1(1)
 *   .end()
 *   .print();
 * }</pre>
 * Once built, call {@link #print()} (or {@link #printStepped()} to reveal the
 * proof one sub-proof depth at a time) to display it.
 */
public class Proof {

	private List<Form> proof = new ArrayList<Form>();
	private List<Rule> reasons = new ArrayList<Rule>();
	private List<Integer> depths = new ArrayList<Integer>();
	private int depth = 1;

	private int step = 1;

	private Proof()
	{}

	/**
	 * @return the (1-based) line number of the last line added to the proof so far
	 */
	//non-zero based index
	public int getCurrentLine()
	{
		return proof.size();
	}

	/**
	 * @return the current sub-proof nesting depth (1 = top level, no open sub-proofs)
	 */
	public int getCurrentDepth()
	{
		return depth;
	}

	/**
	 * @param i 1-based line number
	 * @return the formula on line {@code i}
	 * @throws Exception if {@code i} is not a valid line number
	 */
	public Form getLine(int i) throws Exception
	{
		if (i<1)
			throw new Exception("Numbering system is not zero-based");
		else if (i>getCurrentLine())
			throw new Exception("Invalid line number: " + i);

		return proof.get(i-1);
	}


	/**
	 * Looks up the formula on line {@code i}, but first checks that line {@code i}
	 * is still in scope from the current point in the proof (i.e. it isn't inside
	 * a sub-proof that has already been closed with {@link #end()}).
	 *
	 * @param i 1-based line number to refer to
	 * @return the formula on line {@code i}
	 * @throws Exception if {@code i} is invalid, or is not visible from the current line
	 */
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

	/**
	 * @param i 1-based line number
	 * @return the rule (justification) that produced line {@code i}
	 * @throws Exception if {@code i} is not a valid line number
	 */
	public Rule getReason(int i) throws Exception
	{
		if (i<1)
			throw new Exception("Numbering system is not zero-based");
		else if (i>getCurrentLine())
			throw new Exception("Invalid line number: " + i);

		return reasons.get(i-1);
	}

	/**
	 * @param i 1-based line number
	 * @return the sub-proof nesting depth of line {@code i}
	 * @throws Exception if {@code i} is not a valid line number
	 */
	public int getDepth(int i) throws Exception
	{
		if (i<1)
			throw new Exception("Numbering system is not zero-based");
		else if (i>getCurrentLine())
			throw new Exception("Invalid line number: " + i);

		return depths.get(i-1);
	}

	/**
	 * Applies a rule, appending the resulting formula (or, if the rule is
	 * invalid, an error line) to the proof. This is the method the rule
	 * "syntactic sugar" methods below (e.g. {@link #andI(int, int)}) delegate to.
	 *
	 * @param r the rule to apply
	 * @return this proof, for chaining
	 */
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

	/**
	 * @return the top-level hypotheses this proof was started from (the lines
	 *         introduced via {@link #hyp(Form...)})
	 */
	public List<Form> getHyp()
	{
		List<Form> hyps = new ArrayList<Form>();
		for (int i=0; i<proof.size(); i++)
			if (reasons.get(i).getClass() == Hyp.class)
				hyps.add(proof.get(i));
		return hyps;
	}

	/**
	 * @return the hypotheses from {@link #getHyp()}, comma-separated, as shown
	 *         on the left of the turnstile in {@link #printStatement()}
	 */
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

	/**
	 * @return the formula on the last line of the proof (its conclusion)
	 */
	public Form getConclusion()
	{
		return proof.get(proof.size()-1);
	}

	/**
	 * Prints the proof statement, i.e. "hypotheses |- conclusion".
	 *
	 * @return this proof, for chaining
	 */
	public Proof printStatement()
	{
		String proofStat = "Proof Statement: " + getHypString()+" |- " + getConclusion();
		System.out.println(proofStat);
		return this;
	}

	/**
	 * Prints the full proof, including every sub-proof.
	 *
	 * @return this proof, for chaining
	 */
	public Proof print()
	{
		return print(Integer.MAX_VALUE);
	}

	/**
	 * Prints the proof one sub-proof depth at a time: the first call shows only
	 * the top level (sub-proof lines are left blank), and each subsequent call
	 * reveals one further level of nesting. Useful for walking through a proof
	 * step by step, e.g. when presenting it to students.
	 *
	 * @return this proof, for chaining
	 */
	//shows only up to current step depth
	public Proof printStepped()
	{
		return print(this.step++);
	}

	/**
	 * Resets the depth counter used by {@link #printStepped()} back to the start.
	 *
	 * @return this proof, for chaining
	 */
	//resets step count
	public Proof resetSteps()
	{
		this.step = 1;
		return this;
	}

	/**
	 * @return true if the proof has no open sub-proofs and at least one line;
	 *         see {@link #isWellFormed(boolean)} for a version that explains why not
	 */
	public boolean isWellFormed()
	{
		return isWellFormed(false);
	}

	/**
	 * @param verbose if true, prints an explanation to standard out when the proof is not well-formed
	 * @return true if the proof has no open sub-proofs (every {@link #subhyp(Form)} has a matching {@link #end()}) and at least one line
	 */
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

	/**
	 * Prints the proof statement followed by every line up to and including
	 * the given sub-proof depth; lines nested deeper than {@code step} are
	 * shown with just their line number. Called by {@link #print()} (with
	 * {@code step} set to show everything) and {@link #printStepped()}.
	 *
	 * @param step the maximum sub-proof depth to reveal
	 * @return this proof, for chaining
	 */
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

	/**
	 * @return the proof statement, as printed by {@link #printStatement()}
	 */
	public String toString()
	{
		return getHypString() + " |- " + getConclusion();
	}

	/**
	 * Starts a new, empty proof (or sub-proof) with no hypotheses. Typically used
	 * to open the first sub-proof, e.g. {@code begin().subhyp(...)}.
	 * <p>
	 * Unlike the rule methods below, {@code begin()} does not itself add a line
	 * to the proof.
	 *
	 * @return a new proof
	 */
	//note that begin() does not contribute to an additional line in the proof
	public static Proof begin()
	{
		return new Proof();
	}

	/**
	 * Starts a new proof from one or more top-level hypotheses.
	 *
	 * @param forms the hypotheses to assume; each becomes a line justified by {@code Hyp}
	 * @return a new proof containing one line per hypothesis
	 * @throws InvalidRuleException if any of the given formulae is malformed
	 */
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

	/**
	 * Closes the innermost open sub-proof (started with {@link #subhyp(Form)}).
	 * <p>
	 * Like {@link #begin()}, {@code end()} does not itself add a line to the proof.
	 *
	 * @return this proof, for chaining
	 * @throws Exception if there is no open sub-proof to close
	 */
	//note that end() does not contribute to an additional line in the proof
	public Proof end() throws Exception
	{
		if (depth==0)
			throw new Exception("too many ends!");
		else depth--;
		reasons.get(reasons.size()-1).setEnd();
		return this;
	}

	/**
	 * Opens a new sub-proof by assuming {@code p} as an additional hypothesis.
	 * Must be matched by a later call to {@link #end()}.
	 *
	 * @param p the formula to assume for this sub-proof
	 * @return this proof, for chaining
	 * @throws InvalidRuleException if {@code p} is malformed
	 */
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

	/**
	 * Adds {@code p} as a line justified by an unchecked lemma (i.e. asserted
	 * without proof). Prefer {@link #lemma(int, Proof)} or {@link #lemma(Proof)}
	 * when you have an actual proof of {@code p} to cite.
	 *
	 * @param p the formula to assert
	 * @return this proof, for chaining
	 * @throws InvalidRuleException if {@code p} is malformed
	 */
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

	/**
	 * Applies a previously built proof as a lemma, instantiated against line
	 * {@code line} of the current proof.
	 *
	 * @param line the line in the current proof matching the lemma's hypothesis
	 * @param proof a completed proof to cite as justification
	 * @return this proof, for chaining
	 */
	public Proof lemma(int line, Proof proof)
	{
		return rule(new Lemma(line, proof));
	}

	/**
	 * Applies a previously built proof as a lemma, for the case where {@code proof}
	 * has no hypotheses of its own (i.e. it proves a tautology).
	 *
	 * @param proof a completed, hypothesis-free proof to cite as justification
	 * @return this proof, for chaining
	 */
	//for when lemma is a tautology
	public Proof lemma(Proof proof)
	{
		return rule(new Lemma(proof));
	}

	/**
	 * Repeats an earlier, still-visible line.
	 *
	 * @param source the line to copy
	 * @return this proof, for chaining
	 */
	//syntactic sugar for rules
	public Proof copy(int source)
	{
		return rule(new Copy(source));
	}

	/**
	 * And Elimination (left): from {@code A ^ B} on line {@code conjunction}, derives {@code A}.
	 *
	 * @param conjunction line holding the conjunction
	 * @return this proof, for chaining
	 */
	public Proof andE1(int conjunction)
	{
		return rule(new AndE1(conjunction));
	}

	/**
	 * And Elimination (right): from {@code A ^ B} on line {@code conjunction}, derives {@code B}.
	 *
	 * @param conjunction line holding the conjunction
	 * @return this proof, for chaining
	 */
	public Proof andE2(int conjunction)
	{
		return rule(new AndE2(conjunction));
	}

	/**
	 * And Introduction: from {@code A} on line {@code a} and {@code B} on line {@code b}, derives {@code A ^ B}.
	 *
	 * @param a line holding the left conjunct
	 * @param b line holding the right conjunct
	 * @return this proof, for chaining
	 */
	public Proof andI(int a, int b)
	{
		return rule(new AndI(a, b));
	}

	/**
	 * Implies Elimination (modus ponens): from {@code A => B} on line {@code implication}
	 * and {@code A} on line {@code truth}, derives {@code B}.
	 *
	 * @param implication line holding the implication
	 * @param truth line holding the antecedent
	 * @return this proof, for chaining
	 */
	public Proof impliesE(int implication, int truth)
	{
		return rule(new ImpliesE(implication,truth));
	}

	/**
	 * Implies Introduction: closes a sub-proof running from line {@code start}
	 * (the assumed antecedent) to line {@code end} (the derived consequent),
	 * deriving {@code start => end}.
	 *
	 * @param start line where the sub-proof's hypothesis was assumed
	 * @param end line concluding the sub-proof
	 * @return this proof, for chaining
	 */
	public Proof impliesI(int start, int end)
	{
		return rule(new ImpliesI(start,end));
	}

	/**
	 * Or Elimination: given {@code A | B} on line {@code disjunction} and two
	 * sub-proofs establishing {@code A => C} and {@code B => C}, derives {@code C}.
	 *
	 * @param disjunction line holding the disjunction
	 * @param implicationLeft line holding {@code A => C}
	 * @param implicationRight line holding {@code B => C}
	 * @return this proof, for chaining
	 */
	public Proof orE(int disjunction, int implicationLeft, int implicationRight)
	{
		return rule(new OrE(disjunction,implicationLeft,implicationRight));
	}

	/**
	 * Or Introduction (left): from {@code A} on line {@code left}, derives {@code A | rightFormula}.
	 *
	 * @param left line holding the known disjunct
	 * @param rightFormula the formula to introduce on the right of the disjunction
	 * @return this proof, for chaining
	 */
	public Proof orI1(int left, Form rightFormula)
	{
		return rule(new OrI1(left,rightFormula));
	}

	/**
	 * Or Introduction (right): from {@code B} on line {@code right}, derives {@code leftFormula | B}.
	 *
	 * @param right line holding the known disjunct
	 * @param leftFormula the formula to introduce on the left of the disjunction
	 * @return this proof, for chaining
	 */
	public Proof orI2(int right, Form leftFormula)
	{
		return rule(new OrI2(right,leftFormula));
	}

	/**
	 * Biimplies Introduction: from {@code A => B} on line {@code implicationRight}
	 * and {@code B => A} on line {@code implicationLeft}, derives {@code A <=> B}.
	 *
	 * @param implicationRight line holding {@code A => B}
	 * @param implicationLeft line holding {@code B => A}
	 * @return this proof, for chaining
	 */
	public Proof biimpliesI(int implicationRight, int implicationLeft)
	{
		return rule(new BiimpliesI(implicationRight,implicationLeft));
	}

	/**
	 * Biimplies Elimination (left): from {@code A <=> B} on line {@code biimplication}, derives {@code A => B}.
	 *
	 * @param biimplication line holding the biimplication
	 * @return this proof, for chaining
	 */
	public Proof biimpliesE1(int biimplication)
	{
		return rule(new BiimpliesE1(biimplication));
	}

	/**
	 * Biimplies Elimination (right): from {@code A <=> B} on line {@code biimplication}, derives {@code B => A}.
	 *
	 * @param biimplication line holding the biimplication
	 * @return this proof, for chaining
	 */
	public Proof biimpliesE2(int biimplication)
	{
		return rule(new BiimpliesE2(biimplication));
	}

	/**
	 * Not Elimination: from {@code !!A} on line {@code doubleNegation}, derives {@code A}.
	 *
	 * @param doubleNegation line holding the double negation
	 * @return this proof, for chaining
	 */
	public Proof notE(int doubleNegation)
	{
		return rule(new NotE(doubleNegation));
	}

	/**
	 * Not Introduction: given {@code A => B} on line {@code implicationPositive}
	 * and {@code A => !B} on line {@code implicationNegative} (i.e. two sub-proofs
	 * deriving a contradiction from the same assumption {@code A}), derives {@code !A}.
	 *
	 * @param implicationPositive line holding {@code A => B}
	 * @param implicationNegative line holding {@code A => !B}
	 * @return this proof, for chaining
	 */
	public Proof notI(int implicationPositive, int implicationNegative)
	{
		return rule(new NotI(implicationPositive,implicationNegative));
	}

	/**
	 * Forall Elimination: from {@code Ax:X.A} on line {@code forall}, derives {@code A}
	 * (instantiated for an arbitrary/unconstrained {@code x}).
	 *
	 * @param forall line holding the universally quantified formula
	 * @return this proof, for chaining
	 */
	public Proof forallE(int forall)
	{
		return rule(new ForallE(forall));
	}

	/**
	 * Exists Introduction: from {@code A} on line {@code source} (typically after a
	 * matching {@link #substAdd(int, String)}), derives {@code Ex:type.A}.
	 *
	 * @param source line holding the formula to existentially generalise
	 * @param var the variable to bind
	 * @param type the type/domain of {@code var}
	 * @return this proof, for chaining
	 */
	public Proof existsI(int source,String var, String type)
	{
		return rule(new ExistsI(source,var,type));
	}

	/**
	 * Exists Elimination: given {@code Ex:X.A} on line {@code exists} and a proof of
	 * {@code Ax:X.(A => C)} on line {@code forall} (with {@code x} not free in {@code C}),
	 * derives {@code C}.
	 *
	 * @param exists line holding the existentially quantified formula
	 * @param forall line holding the universally quantified implication
	 * @return this proof, for chaining
	 */
	public Proof existsE(int exists,int forall)
	{
		return rule(new ExistsE(exists,forall));
	}

	/**
	 * Forall Introduction: from {@code A} on line {@code source}, derives
	 * {@code Ax:type.A}, provided {@code var} does not occur free in any hypothesis
	 * still in scope.
	 *
	 * @param source line holding the formula to universally generalise
	 * @param var the variable to bind
	 * @param type the type/domain of {@code var}
	 * @return this proof, for chaining
	 */
	public Proof forallI(int source,String var, String type)
	{
		return rule(new ForallI(source,var,type));
	}

	/**
	 * Substitutes {@code var} for itself in line {@code i} (i.e. records the trivial
	 * law {@code P = P[var <- var]}), typically as a step immediately before
	 * {@link #existsI(int, String, String)} or {@link #forallI(int, String, String)}.
	 *
	 * @param i line to substitute into
	 * @param var the variable being substituted
	 * @return this proof, for chaining
	 */
	public Proof substAdd(int i, String var)
	{
		return rule(new SubstAdd(i, var));
	}

	/**
	 * Reverses a substitution previously recorded by {@link #substAdd(int, String)}.
	 *
	 * @param i line to remove the substitution annotation from
	 * @return this proof, for chaining
	 */
	public Proof substRem(int i)
	{
		return rule(new SubstRem(i));
	}
}
