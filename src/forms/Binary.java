package forms;

public abstract class Binary extends Form {

	protected Form left;
	protected Form right;
	
	protected Binary()
	{}
	
	protected Binary(Form left, Form right) throws InvalidFormException
	{
		if (left == null || right==null)
			throw new InvalidFormException("Parameters cannot be null");
		this.left = left;
		this.right = right;
	}
	
	public void setLeft(Form left)
	{
		this.left = left;
	}
	
	public void setRight(Form right)
	{
		this.right = right;
	}
	
	public Form getLeft()
	{
		return left;
	}
	
	public Form getRight()
	{
		return right;
	}
	
	public boolean isWellFormed()
	{
		return super.isWellFormed() && left != null && right != null;
	}
	
	public Form clone()
	{
		try{
			Binary b = (Binary)this.getClass().newInstance();
			b.setLeft(left.clone());
			b.setRight(right.clone());
			return b.cloneHelper(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public boolean occursFree(String x)
	{
		return left.occursFree(x) && right.occursFree(x);
	}
	
}
