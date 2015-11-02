package forms;

public class ForAll extends Quantifier{

	public ForAll()
	{}
	
	public ForAll (String var, String type, Form sub)throws InvalidFormException
	{
		super(var, type, sub);
	}
	
	public String toString()
	{
		return toStringHelper("A" +var + ":" + type + "." + sub);
	}
	
	public boolean equals(Object o)
	{
		if (o.getClass() != ForAll.class)
			return false;
		else if (((ForAll)o).type.equals(type) && ((ForAll)o).sub.equals(sub))
			return true;
		else
			return false;
	}
}
