package examples;

import forms.Form;
import static forms.Form.*;
import static rules.Proof.*;

public class AdvancedPropositional {

	public static void main(String[] args) throws Exception{
		
		Form p = $("P");
		Form pnot = not($("P"));
		Form notpornot = not(or($("P"),not($("P"))));
		
		begin()
/*1*/		.subhyp(notpornot)
/*2*/			.subhyp(p)
/*3*/			.orI1(2,pnot)
				.end()
/*4*/		.impliesI(2, 3)
/*5*/			.subhyp(p)
/*6*/			.copy(1)
				.end()
/*7*/		.impliesI(5, 6)
/*8*/		.notI(4, 7)
/*9*/		.orI2(8, p)
			.end()
/*10*/		.impliesI(1, 9)
/*11*/		.subhyp(notpornot)
				.end()
/*12*/		.impliesI(11, 11)	
/*13*/		.notI(10, 12)
/*14*/		.notE(13)
		.end().printStepped().printStepped().printStepped();
		
	}

}

















