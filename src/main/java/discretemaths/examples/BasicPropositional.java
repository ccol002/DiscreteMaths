package discretemaths.examples;

import static discretemaths.Proof.hyp;
import static discretemaths.forms.Form.$;
import static discretemaths.forms.Form.and;
import static discretemaths.forms.Form.implies;
import static discretemaths.forms.Form.not;

import discretemaths.Proof;
import discretemaths.forms.Form;


public class BasicPropositional {

	public static void main(String[] args)throws Exception {
				
		
		Form f = and($("A"),and($("C"),$("B")));//A ^ (C ^ B)
		//System.out.println(f);
		

	/*1*/	hyp(f)       //A ^ (C ^ B)
	/*2*/	.andE1(1)
		.end().print();  // A
		

	/*1*/	hyp(f)               //A ^ (C ^ B)
	/*2*/		.subhyp($("A"))
	/*3*/		.andE2(1)
	/*4*/		.andE2(3)
				.end()
	/*5*/	.impliesI(2,4)
		.end().print();          //A => B
		
		
		Proof p = 
				/*1*/	hyp(not($("P")))               //!P
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
						.end().print();                // P => Q
				
		
		
		Form g = not(implies($("P"),$("Q")));  //!(P => Q)
		 

		/*1*/	hyp(g)                           //!(P => Q)
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
				.end().print();                  // Q => P

		
		Form h = and(implies($("P"),$("Q")), implies($("Q"),$("P"))); //(P=>Q)^(Q=>P)
	      /*1*/ hyp(h)
	      /*2*/ .andE1(1)
	      /*3*/ .andE2(1)
	      /*4*/  .biimpliesI(3, 2)
	               .end().print();      // Q <=> P
	      
	      
	      Form x = implies(implies($("P"), $("Q")),implies($("P"),$("R")));

	      hyp(x).
	      subhyp(implies($("P"),$("Q"))).
	      subhyp($("Q")).
	      impliesE(1, 2).end().
	      impliesI(3, 4).end().
	      impliesI(3, 4).
	      end().print();
	}

}
