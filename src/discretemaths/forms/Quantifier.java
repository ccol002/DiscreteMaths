package discretemaths.forms;

public abstract class Quantifier extends Form {

	protected String var;
	protected String type;
	protected Form sub;
	
	protected Quantifier()
	{}
	
	protected Quantifier(String var, String type, Form sub) throws InvalidFormException
	{
		if (var==null) throw new InvalidFormException("Variable cannot be null");
		if (type==null) throw new InvalidFormException("Type cannot be null");
		if (sub==null) throw new InvalidFormException("Parameter cannot be null");
		
		this.var = var;
		this.type = type;
		this.sub = sub;
	}
	
	public void setVar(String var)
	{
		this.var = var;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setSub(Form sub)
	{
		this.sub = sub;
	}
	
	public String getVar()
	{
		return var;
	}
	
	public String getType()
	{
		return type;
	}
	
	public Form getSub()
	{
		return sub;
	}
	
	public boolean isWellFormed()
	{
		return var != null 
			&& type != null
			&& sub != null;
	}
	
	public boolean occursFree(String x)
	{
		if (var.equals(x))
			return false;
		else 
			return sub.occursFree(x);
	}
	
	public Form clone()
	{
		try{
			Quantifier q = (Quantifier)this.getClass().newInstance();
			q.setSub(sub.clone());
			q.setVar(String.valueOf(var));
			q.setType(String.valueOf(type));
			return q.cloneHelper(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
