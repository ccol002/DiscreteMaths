package discretemaths.rules;

import discretemaths.Proof;
import discretemaths.forms.Form;


public abstract class Rule {

	private boolean end = false;

	public boolean checkSubHyp(Proof proof, int[] line){
		int prevdepth = -1;
		for(int i = 0; i < line.length; i++) {
			try {
				prevdepth = proof.getDepth(line[i]);
				//System.out.println(line[i]);
			} catch (Exception e) {
				System.out.println(e);
			}
			int cdepth = proof.getCurrentDepth();
			//System.out.println(cdepth + " <= depth");
			//System.out.println(prevdepth + " <= previous Depth");
			if (prevdepth > cdepth) {
				return true;
			}
		}
		return false;
	}

	public void setEnd()
	{
		end = true;
	}
	
	public boolean isEnd()
	{
		return end;
	}
	
	//returns the resulting line after applying the rule
	public abstract Form evaluate(Proof proof) throws Exception; 
	
	public abstract String toString();
}
