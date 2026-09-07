package discretemaths.rules;

import static discretemaths.Proof.hyp;
import static discretemaths.forms.Form.$;
import static discretemaths.forms.Form.exists;
import static discretemaths.forms.Form.forall;
import static discretemaths.forms.Form.implies;
import static org.junit.jupiter.api.Assertions.assertEquals;

import discretemaths.Proof;
import discretemaths.forms.Form;
import org.junit.jupiter.api.Test;

/**
 * Exercises the predicate/quantifier rules (Forall, Exists, and the
 * substitution rules that connect them) via {@link Proof}.
 */
class PredicateRulesTest {

	@Test
	void forallEliminationThenExistsIntroduction() throws Exception {
		Form p = $("P");
		Proof proof = hyp(forall("x", "X", p))
				.forallE(1)              // 2: (P)[x <- x]
				.existsI(2, "x", "X")    // 3: Ex:X.P
				.end();

		assertEquals(exists("x", "X", p), proof.getConclusion());
	}

	@Test
	void substAddEnablesExistsIntroduction() throws Exception {
		Form p = $("P");
		Proof proof = hyp(p)
				.substAdd(1, "x")        // 2: (P)[x <- x]
				.existsI(2, "x", "X")    // 3: Ex:X.P
				.end();

		assertEquals(exists("x", "X", p), proof.getConclusion());
	}

	@Test
	void substAddThenSubstRemRoundTrips() throws Exception {
		Form p = $("P");
		Proof proof = hyp(p)
				.substAdd(1, "x")   // 2: (P)[x <- x]
				.substRem(2)        // 3: P
				.end();

		assertEquals(p, proof.getConclusion());
	}

	@Test
	void forallIntroductionWhenVariableNotFreeInHypotheses() throws Exception {
		// P(y) does not mention "x", so x can be generalised
		Form py = $("P", "y");
		Proof proof = hyp(py)
				.forallI(1, "x", "X")   // 2: Ax:X.P(y)
				.end();

		assertEquals(forall("x", "X", py), proof.getConclusion());
	}

	@Test
	void existsElimination() throws Exception {
		Form px = $("P", "x");
		Form qy = $("Q", "y");
		Proof proof = hyp(
					exists("x", "X", px),
					forall("x", "X", implies(px, qy)))
				.existsE(1, 2)   // 3: Q(y)
				.end();

		assertEquals(qy, proof.getConclusion());
	}
}
