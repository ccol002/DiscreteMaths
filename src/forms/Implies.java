package forms;

public class Implies extends Binary{

	public Implies ()
	{
		super();
	}
	
	public Implies (Form left, Form right)throws InvalidFormException
	{
		super(left,right);
	}
	
	public String toString()
	{
		return toStringHelper("(" + left + " => " + right + ")");
	}
	
	public boolean equals(Object o)
	{
		if (o.getClass() != Implies.class)
			return false;
		else if (((Implies)o).left.equals(left) && ((Implies)o).right.equals(right))
			return true;
		else
			return false;
	}
}
