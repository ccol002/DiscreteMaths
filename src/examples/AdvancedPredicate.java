package examples;

import static forms.Form.*;
import static rules.Proof.begin;
import forms.Form;


public class AdvancedPredicate {

	public static void main(String[] args)throws Exception {
		
		Form f = exists("x","X",exists("y", "Y", $("P")));
		
		begin()
	/*1*/	.hyp(f)
	/*2*/		.subhyp(exists("y", "Y", $("P")))
	/*3*/			.subhyp($("P"))
	/*4*/				.substAdd(3, "x")
	/*5*/				.existsI(4, "x", "X")
	/*6*/				.substAdd(5, "y")					
	/*7*/				.existsI(6, "y", "Y")
					.end()
	/*8*/			.impliesI(3, 7)
	/*9*/			.forallI(8, "y", "Y")
	/*10*/			.existsE(2, 9)
				.end()
	/*11*/		.impliesI(2, 10)
	/*12*/		.forallI(11, "x", "X")
	/*13*/		.existsE(1, 12)			
		.end().printStepped().printStepped().printStepped();
		
	}

}
