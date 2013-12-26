import java.util.*;

public class AIStack
{
	private ArrayList<String> stack;
	
	public AIStack()
	{
		stack = new ArrayList<String>();
	}
	
	public void push(String cmd)
	{
		stack.add(0,cmd);
	}
	
	public String pop()
	{
		String tmp = stack.get(0);
		stack.remove(0);
		return tmp;
	}
	
	public String read()
	{
		if(stack.size()!=0)
			return stack.get(0);
		else
			return null;
	}
	
	public void remove(String str)
	{
		for(int i=0;i<stack.size();i++)
		{
			if(stack.get(i).equals(str))
				stack.remove(i);
		}
	}
	
	public ArrayList<String> getStack()
	{
		return stack;
	}
}
