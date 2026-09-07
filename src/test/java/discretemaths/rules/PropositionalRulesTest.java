package discretemaths.rules;

import static discretemaths.Proof.begin;
import static discretemaths.Proof.hyp;
import static discretemaths.forms.Form.$;
import static discretemaths.forms.Form.biimplies;
import static discretemaths.forms.Form.implies;
import static discretemaths.forms.Form.not;
import static discretemaths.forms.Form.or;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import discretemaths.Proof;
import discretemaths.forms.False;
import discretemaths.forms.Form;
import org.junit.jupiter.api.Test;

/**
 * Exercises the propositional rules (And, Or, Implies, Not, Biimplies) via
 * {@link Proof}, doubling as worked examples of each rule's usage.
 */
class PropositionalRulesTest {

	private final Form a = $("A");
	private final Form b = $("B");
	private final Form c = $("C");

	@Test
	void andIntroductionAndElimination() throws Exception {
		Proof proof = hyp(a, b)
				.andI(1, 2)   // 3: A ^ B
				.andE1(3)     // 4: A
				.andE2(3)     // 5: B
				.end();

		assertEquals(a, proof.getLine(4));
		assertEquals(b, proof.getLine(5));
	}

	@Test
	void orIntroduction() throws Exception {
		Proof proof = hyp(a)
				.orI1(1, b)   // 2: A v B
				.orI2(1, b)   // 3: B v A
				.end();

		assertEquals(or(a, b), proof.getLine(2));
		assertEquals(or(b, a), proof.getLine(3));
	}

	@Test
	void orElimination() throws Exception {
		Proof proof = hyp(or(a, b), implies(a, c), implies(b, c))
				.orE(1, 2, 3)   // 4: C
				.end();

		assertEquals(c, proof.getConclusion());
	}

	@Test
	void impliesIntroduction() throws Exception {
		// A => A, by assuming A and handing it straight back
		Proof proof = begin()
				.subhyp(a)
				.copy(1)
				.end()
				.impliesI(1, 2)   // 3: A => A
				.end();

		assertEquals(implies(a, a), proof.getConclusion());
	}

	@Test
	void impliesElimination() throws Exception {
		// modus ponens: A, A => B |- B
		Proof proof = hyp(a, implies(a, b))
				.impliesE(2, 1)   // 3: B
				.end();

		assertEquals(b, proof.getConclusion());
	}

	@Test
	void notIntroduction() throws Exception {
		// A => C and A => !C together give !A (proof by contradiction)
		Proof proof = begin()
				.subhyp(a)
				.lemma(c)
				.end()
				.impliesI(1, 2)      // 3: A => C
				.subhyp(a)
				.lemma(not(c))
				.end()
				.impliesI(4, 5)      // 6: A => !C
				.notI(3, 6)           // 7: !A
				.end();

		assertEquals(not(a), proof.getConclusion());
	}

	@Test
	void notElimination() throws Exception {
		Proof proof = hyp(not(not(a)))
				.notE(1)   // 2: A
				.end();

		assertEquals(a, proof.getConclusion());
	}

	@Test
	void biimpliesIntroductionAndElimination() throws Exception {
		Proof proof = hyp(implies(a, b), implies(b, a))
				.biimpliesI(1, 2)   // 3: A <=> B
				.biimpliesE1(3)     // 4: A => B
				.biimpliesE2(3)     // 5: B => A
				.end();

		assertEquals(biimplies(a, b), proof.getLine(3));
		assertEquals(implies(a, b), proof.getLine(4));
		assertEquals(implies(b, a), proof.getLine(5));
	}

	@Test
	void invalidRuleApplicationProducesAnErrorLineInsteadOfThrowing() throws Exception {
		// line 1 is not a conjunction, so andE1 cannot be applied
		Proof proof = hyp(a)
				.andE1(1)
				.end();

		assertInstanceOf(False.class, proof.getConclusion());
		assertInstanceOf(Err.class, proof.getReason(2));
	}
}
