package examples;

import static forms.Form.*;
import static rules.Proof.begin;
import forms.Form;


public class BasicPredicate {

	public static void main(String[] args)throws Exception {
		
		Form f = forall("x","X",$("P"));
		
		begin()
	/*1*/	.hyp(f)
	/*2*/	.forallE(1)
	/*3*/	.existsI(2,"x","X")
		.end().print();
		
	}

}
