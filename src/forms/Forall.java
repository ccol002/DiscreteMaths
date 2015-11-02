package forms;

public class Forall extends Quantifier{

	public Forall()
	{}
	
	public Forall (String var, String type, Form sub)throws InvalidFormException
	{
		super(var, type, sub);
	}
	
	public String toString()
	{
		return toStringHelper("A" +var + ":" + type + "." + sub);
	}
	
	public boolean equals(Object o)
	{
		if (o.getClass() != Forall.class)
			return false;
		else if (((Forall)o).type.equals(type) && ((Forall)o).sub.equals(sub))
			return true;
		else
			return false;
	}
}
