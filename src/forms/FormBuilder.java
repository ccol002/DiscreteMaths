package forms;


public class FormBuilder {
	
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
		return new ForAll(var, type, form);
	}
	
	public static Form exists(String var, String type, Form form) throws InvalidFormException
	{
		return new Exists(var, type, form);
	}
	
	
}






















