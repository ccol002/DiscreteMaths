package discretemaths.forms;

public class Biimplies extends Binary{

	public Biimplies ()
	{
		super();
	}
	
	public Biimplies (Form left, Form right)throws InvalidFormException
	{
		super(left,right);
	}
	
	public String toString()
	{
		return toStringHelper("(" + left + " <=> " + right + ")");
	}
	
	public boolean equals(Object o)
	{
		if (o.getClass() != Biimplies.class)
			return false;
		else if (((Biimplies)o).left.equals(left) && ((Biimplies)o).right.equals(right))
			return true;
		else
			return false;
	}
}
