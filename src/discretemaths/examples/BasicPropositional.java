package discretemaths.examples;

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
	}

}
