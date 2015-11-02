package forms;

public abstract class Unary extends Form {

	protected Form sub;
	
	protected Unary()
	{}
	
	protected Unary(Form sub) throws InvalidFormException
	{
		if (sub==null) throw new InvalidFormException("Parameter cannot be null");
		this.sub = sub;
	}
	
	public Form clone()
	{
		try{
			Unary u = (Unary)this.getClass().newInstance();
			u.setSub(sub.clone());
			return u.cloneHelper(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public void setSub(Form sub)
	{
		this.sub = sub;
	}
	
	public Form getSub()
	{
		return sub;
	}
	
	public boolean isWellFormed()
	{
		return super.isWellFormed() && sub != null;
	}
	
	public boolean occursFree(String x)
	{
		return sub.occursFree(x);
	}
}
