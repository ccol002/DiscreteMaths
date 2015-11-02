package forms;

public class Exists extends Quantifier{

	public Exists()
	{}
	
	public Exists (String var, String type, Form sub)throws InvalidFormException
	{
		super(var, type, sub);
	}
	
	public String toString()
	{
		return toStringHelper("E" +var + ":" + type + "." + sub.toString());
	}
	
	public boolean equals(Object o)
	{
		if (o.getClass() != Exists.class)
			return false;
		else if (((Exists)o).type.equals(type) && ((Exists)o).sub.equals(sub))
			return true;
		else
			return false;
	}
}
