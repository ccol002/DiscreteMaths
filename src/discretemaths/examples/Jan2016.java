package discretemaths.examples;

import discretemaths.forms.Form;
import static discretemaths.Proof.*;
import static discretemaths.forms.Form.*;

public class Jan2016 {

	public static void main(String[] args)throws Exception {
		
		Form f = not(exists("x","X",implies($("P"),$("Q"))));
		
		hyp(f)
	/*1*/	.subhyp(not($("P")))
	/*2*/		.subhyp($("P"))
	/*3*/			.subhyp(not($("Q")))
	/*4*/				.copy(3)
					.end()
	/*5*/			.impliesI(4, 5)
	/*6*/			.subhyp(not($("Q")))
	/*7*/				.copy(2)
					.end()
	/*8*/			.impliesI(7, 8)
	/*9*/			.notI(6, 9)
	/*10*/			.notE(10)
				.end()
	/*11*/		.impliesI(3, 11)
	/*12*/		.substAdd(12, "x")
	/*13*/		.existsI(13, "x", "X")
			.end()
	/*14*/	.impliesI(2, 14)
	/*15*/	.subhyp(not($("P")))
	/*16*/		.copy(1)
			.end()
	/*17*/	.impliesI(16, 17)
	/*18*/	.notI(15, 18)
	/*19*/	.notE(19)
	/*20*/	.forallI(20, "x", "X")
	.end()
	.print();
		
		
	
	}

}
