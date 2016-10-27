package discretemaths.examples;

import discretemaths.forms.Form;
import static discretemaths.Proof.*;
import static discretemaths.forms.Form.*;

public class AdvancedPredicate {

	public static void main(String[] args)throws Exception {
		
		Form f = exists("x","X",exists("y", "Y", $("P")));
		

	/*1*/	 hyp(f)
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
		
		
		Form g = and(not(exists("x","X",$("P"))),forall("x","X", implies($("Q"),$("P"))));
		

	/*1*/	hyp(g)
	/*2*/		.subhyp(exists("x", "X", $("Q")))
	/*3*/			.subhyp($("Q"))
	/*4*/				.andE2(1)
	/*5*/				.forallE(4)
	/*6*/				.substRem(5)
	/*7*/				.impliesE(6, 3)
	/*8*/				.substAdd(7, "x")
	/*9*/				.existsI(8, "x", "X")
					.end()
	/*10*/			.impliesI(3, 9)
	/*11*/			.forallI(10, "x", "X")
	/*12*/			.existsE(2, 11)
				.end()
	/*13*/		.impliesI(2, 12)
	/*14*/		.subhyp(exists("x", "X", $("Q")))
	/*15*/			.andE1(1)
				.end()
	/*16*/		.impliesI(14, 15)	
	/*17*/		.notI(13,16)
			.end().print();
	}

}
