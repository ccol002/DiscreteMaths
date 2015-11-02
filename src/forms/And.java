package forms;

public class And extends Binary{

	public And ()
	{
		super();
	}
	
	public And (Form left, Form right)throws InvalidFormException
	{
		super(left,right);
	}
	
	public String toString()
	{
		return toStringHelper("(" + left + " ^ " + right + ")");
	}
	
	public boolean equals(Object o)
	{
		if (o.getClass() != And.class)
			return false;
		else if (((And)o).left.equals(left) && ((And)o).right.equals(right))
			return true;
		else
			return false;
	}
	
}
