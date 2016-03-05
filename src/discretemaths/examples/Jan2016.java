package discretemaths.examples;

import discretemaths.forms.Form;
import static discretemaths.Proof.*;
import static discretemaths.forms.Form.*;

public class Jan2016 {

	public static void main(String[] args)throws Exception {
		
		Form f = not(exists("x","X",implies($("P"),$("Q"))));
		
		begin()
	/*1*/	.subhyp(not($("P")))
	/*2*/		.subhyp($("P"))
	/*3*/			.subhyp(not($("Q")))
	/*4*/				.copy(2)
					.end()
	/*5*/			.impliesI(3, 4)
	/*6*/			.subhyp(not($("Q")))
	/*7*/				.copy(1)
					.end()
	/*8*/			.impliesI(6, 7)
	/*9*/			.notI(5, 8)
	/*10*/			.notE(9)
				.end()
	/*11*/		.impliesI(2, 10)
	/*12*/		.substAdd(11, "x")
	/*13*/		.existsI(12, "x", "X")
			.end()
	/*14*/	.impliesI(1, 13)
	/*15*/	.subhyp(not($("P")))
	/*16*/		.hyp(f)
			.end()
	/*17*/	.impliesI(15, 16)
	/*18*/	.notI(14, 17)
	/*19*/	.notE(18)
	/*20*/	.forallI(19, "x", "X")
	.end()
	.print();
		
		
	
	}

}
