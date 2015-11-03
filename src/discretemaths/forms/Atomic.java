package discretemaths.forms;

public abstract class Atomic extends Form{

	public boolean isWellFormed() {
		return super.isWellFormed();
	}

	public abstract boolean occursFree(String x);

	public Form clone()
	{
		try{
			Form f =  (Form)this.getClass().newInstance();
			return f.cloneHelper(this);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
}
