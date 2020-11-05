package discretemaths.examples;

import static discretemaths.Proof.hyp;
import static discretemaths.forms.Form.$;
import static discretemaths.forms.Form.forall;

import discretemaths.forms.Form;


public class BasicPredicate {

	public static void main(String[] args)throws Exception {
		
		Form f = forall("x","X",$("P"));
		
		
	/*1*/	hyp(f)
	/*2*/	.forallE(1)
	/*3*/	.existsI(2,"x","X")
		.end().print();
		
	}

}
