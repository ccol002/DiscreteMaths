package discretemaths.examples;

import discretemaths.Proof;
import discretemaths.forms.Form;
import static discretemaths.Proof.*;
import static discretemaths.forms.Form.*;


public class BasicPropositional {

	public static void main(String[] args)throws Exception {
		
		Form f = and($("A"),and($("C"),$("B")));//A ^ (C ^ B)
		//System.out.println(f);
		
		begin()
	/*1*/	.hyp(f)
	/*2*/	.andE1(1)
		.end().print();
		
		begin()
	/*1*/	.hyp(f)
	/*2*/		.subhyp($("A"))
	/*3*/		.andE2(1)
	/*4*/		.andE2(3)
				.end()
	/*5*/	.impliesI(2,4)
		.end().print();
		
		
		Proof p = begin()
				/*1*/	.hyp(not($("P")))
				/*2*/		.subhyp($("P"))
				/*3*/			.subhyp(not($("Q")))
				/*4*/				.copy(2)
								.end()
				/*5*/			.impliesI(3,4)
				/*6*/			.subhyp(not($("Q")))
				/*7*/				.copy(1)
								.end()
				/*8*/			.impliesI(6,7)
				/*9*/			.notI(5,8)
				/*10*/			.notE(9)
							.end()
				/*11*/		.impliesI(2,10)
						.end().print();
				
		
		
		Form g = not(implies($("P"),$("Q")));//!(P->Q)
		 
		begin()
		/*1*/	.hyp(g)
		/*2*/		.subhyp($("Q"))
		/*3*/			.subhyp(not($("P")))
		/*4*/				.lemma(3,p)
						.end()
		/*5*/			.impliesI(3,4)
		/*6*/			.subhyp(not($("P")))
		/*7*/				.copy(1)
						.end()
		/*8*/			.impliesI(6,7)
		/*9*/			.notI(5,8)
		/*10*/			.notE(9)
					.end()
		/*11*/		.impliesI(2,10)		
				.end().print();
		
	}

}
