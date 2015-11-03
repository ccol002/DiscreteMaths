package discretemaths.forms;

public class True extends Atomic{

	public String toString() {
		return toStringHelper("true");
	}
	
	public boolean occursFree(String x)
	{
		return false;
	}
}
