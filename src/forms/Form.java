package forms;

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
	
}
