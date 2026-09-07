package discretemaths.forms;

public class False extends Atomic{

	public String toString() {
		return toStringHelper("false");
	}
	
	public boolean occursFree(String x)
	{
		return false;
	}

}
