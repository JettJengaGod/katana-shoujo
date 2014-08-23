import java.io.IOException;


public class shit {
	static parser p;
	static int points;
	public static void main(String [] args) throws IOException
	{
		p = new parser("shit.txt");
		points = 0;
	}
	public static void progress()
	{
		int index = 0;
		String working = p.Story.get(0);
		String tag;
		String name;
		String quote;
		while(index<p.Story.size()){
			tag = working.substring(0,working.indexOf(']'));
			if(working.contains(":"))
			{
				name = working.substring(working.indexOf(']')+1,working.indexOf(':'));
				quote = working.substring(working.indexOf(':')+1);
				display(name,quote);
			}
			else if(working.contains(">"))
			{
				name = working.substring(working.indexOf(']')+1);
				display(name);
			}
			else
			{
				name = "";
				quote = working.substring(working.indexOf(']')+1);
				display(name,quote);
			}
			
			index = next(tag,index);
			working = p.Story.get(index);
		}
	}
	public static void display(String name, String quote)
	{
		System.out.println(name + ":" + quote);
	}
	public static void display(String working)
	{
		while(working.contains(">"))
		{
			System.out.println(working.substring(working.indexOf(0),working.indexOf('>')));
			working = working.substring(working.indexOf('>')+1);
		}
	}
	public static int next(String tag,int index)
	{
		if(tag.startsWith("S"))
			return index+1;
		
		return next(tag,index,1);
	}
	public static int next(String tag,int index, int choice)
	{
		if(tag.startsWith("C"))
		{
			return lookFor("A"+index);
		}
		else 
		{
			if(tag.charAt(1)=='-')
			{
				points -= 1;
			}
			else if(tag.charAt(1)=='1')
			{
				points += 1;
			}
			return lookFor("P"+points,index);
		}
	}
	private static int lookFor(String string) {
		for(String s : p.Story)
		{
			if(s.startsWith(string))
			{
				return p.Story.indexOf(s);
			}
		}
		return 0;
	}
	private static int lookFor(String string,int current) {
		for(int i = current; i <p.Story.size(); i++)
		{
			if(p.Story.get(i).startsWith(string))
			{
				return i;
			}
		}
		return 0;
	}
}
