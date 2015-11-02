package forms;

public class Pred extends Atomic{
//if no variables are defined, this class works as a proposition
//if used in the context of quantification with no variables, 
//then it is assumed that it represents a whole formula rather than a proposition
	
	private String label;
	private String[] vars;
	
	public Pred (String label, String... vars)
	{
		this.label = label;
		this.vars = vars;
	}
	
	public Form clone()
	{
		Pred p = new Pred(label.toString(), vars.clone());
		return p.cloneHelper(this);
	}
	
	public String toString()
	{
		String s;
		if (vars.length == 0)
			s=label;
		else {
			s = label + "(";
			for (int i=0; i<vars.length; i++)
				s += vars[i] + ",";
			s = s.substring(0,s.length()-1);
			s += ")";
		}	
		return toStringHelper(s);
	}
	
	public boolean occursFree(String x)
	{
		//NOTE: this is disputable 
		//if we consider a predicate without variables as a proposition then we should return false
		//however we have decided to allow variable-less predicates to represent formulae
		if (vars.length==0)
			return true;
		
		for (int i=0; i<vars.length; i++)
			if (vars[i].equals(x))
				return true;
		
		return false;
	}
	
	//TODO Is this right? Do I need to compare vars?
	public boolean equals(Object o)
	{
		if (o.getClass() != Pred.class)
			return false;
		else if (((Pred)o).label.equals(label) 
				&& ((Pred)o).vars.length == vars.length)
			return true;
		else
			return false;
	}
}
