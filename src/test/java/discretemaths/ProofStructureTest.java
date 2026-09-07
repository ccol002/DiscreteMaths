package discretemaths;

import static discretemaths.Proof.begin;
import static discretemaths.Proof.hyp;
import static discretemaths.forms.Form.$;
import static discretemaths.forms.Form.and;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import discretemaths.forms.Form;
import org.junit.jupiter.api.Test;

/**
 * Exercises {@link Proof}'s own bookkeeping (hypotheses, sub-proofs,
 * well-formedness) rather than any specific inference rule.
 */
class ProofStructureTest {

	@Test
	void hypothesesAndConclusionAreTracked() throws Exception {
		Form a = $("A");
		Form b = $("B");
		Proof proof = hyp(a, b).andI(1, 2).end();

		assertEquals("A, B", proof.getHypString());
		assertEquals(and(a, b), proof.getConclusion());
		assertEquals("A, B |- (A ^ B)", proof.toString());
	}

	@Test
	void copyRepeatsAnEarlierLine() throws Exception {
		Form a = $("A");
		Proof proof = hyp(a).copy(1).end();

		assertEquals(a, proof.getLine(2));
	}

	@Test
	void lemmaAssertsAFormulaWithoutChecking() throws Exception {
		Form a = $("A");
		Form b = $("B");
		Proof proof = hyp(a).lemma(b).end();

		assertEquals(b, proof.getConclusion());
	}

	@Test
	void aProofWithAnUnclosedSubProofIsNotWellFormed() throws Exception {
		Proof proof = begin().subhyp($("A"));

		assertFalse(proof.isWellFormed());
	}

	@Test
	void aCompletedProofIsWellFormed() throws Exception {
		Proof proof = hyp($("A")).end();

		assertTrue(proof.isWellFormed());
	}

	@Test
	void invalidLineNumbersAreRejected() throws Exception {
		Proof proof = hyp($("A")).end();

		assertThrows(Exception.class, () -> proof.getLine(0));
		assertThrows(Exception.class, () -> proof.getLine(99));
	}

	@Test
	void closingAnAlreadyClosedProofIsRejected() throws Exception {
		Proof proof = hyp($("A")).end();

		assertThrows(Exception.class, proof::end);
	}
}
