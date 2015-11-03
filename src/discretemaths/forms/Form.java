package discretemaths.forms;

public abstract class Form {
	
	protected String substNew;
	protected String substOld;
	
	public Form subst(String substNew, String substOld)
	{
		this.substNew = substNew;
		this.substOld = substOld;
		return this;
	}
	
	public String getSubstNew()
	{
		return substNew;
	}
	
	public String getSubstOld()
	{
		return substOld;
	}
	
	public abstract Form clone();
	
	public Form cloneHelper(Form f)
	{
		subst(f.getSubstNew(), f.getSubstOld());
		return this;
	}
	
	public abstract String toString();
	
	public String toStringHelper(String s)
	{
		String subst = "["+substOld+" <- "+substNew+"]";
		
		if (substNew==null || substOld==null)
			return s;
		else if (s.startsWith("("))
			return s+subst;
		else 
			return "("+s+")"+subst;
	}
	
	public boolean isWellFormed()
	{
		if (substNew ==null && substOld != null)
			return false;
		else if (substNew !=null && substOld == null)
			return false;
		else
			return true;
	}
	
	public abstract boolean occursFree(String x);
	
	
	public static Form $(String prop, String... vars)
	{
		return new Pred(prop, vars);
	}
	
	public static Form and(Form p1, Form p2) throws InvalidFormException
	{
		return new And(p1,p2);
	}
	
	public static Form or(Form p1, Form p2) throws InvalidFormException
	{
		return new Or(p1,p2);
	}
	
	public static Form implies(Form p1, Form p2) throws InvalidFormException
	{
		return new Implies(p1,p2);
	}
	
	public static Form Biimplies(Form p1, Form p2) throws InvalidFormException
	{
		return new Biimplies(p1,p2);
	}
	
	public static Form not(Form p1) throws InvalidFormException
	{
		return new Not(p1);
	}
	
	public static Form forall(String var, String type, Form form) throws InvalidFormException
	{
		return new Forall(var, type, form);
	}
	
	public static Form exists(String var, String type, Form form) throws InvalidFormException
	{
		return new Exists(var, type, form);
	}
}
