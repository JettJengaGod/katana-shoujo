import java.io.IOException;


public class shit {
	static parser p;
	static int points;
	public static void main(String [] args) throws IOException
	{
		p = new parser("shit.txt");
		points = 0;
		progress();
	}
	public static void progress()
	{
		int index = 0;
		String working = p.Story.get(0);
		String tag;
		String name;
		String quote;
		while(index<p.Story.size()-1){
			working = p.Story.get(index);
			tag = working.substring(0,working.indexOf(']'));
			if(working.contains(":"))
			{
				name = working.substring(working.indexOf(']')+1,working.indexOf(':'));
				quote = working.substring(working.indexOf(':')+1);
				display(name,quote);
			}
			else if(working.contains(">"))
			{
				display(working);
			}
			else
			{
				name = "";
				quote = working.substring(working.indexOf(']')+1);
				display(name,quote);
			}
			System.out.println(index);
			index = next(tag,index);
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
			working = working.substring(working.indexOf(']')+1);
			System.out.println(working.substring(0,working.indexOf('>')));
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
			return lookFor("A"+choice)+1;
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
			return lookFor("SP"+points,index);
		}
	}
	private static int lookFor(String string) {
		for(int i = 0; i <p.Story.size(); i++)
		{
			if(p.Story.get(i).contains(string))
			{
				return i;
			}
		}
		return 100;
	}
	private static int lookFor(String string,int current) {
		for(int i = current; i <p.Story.size(); i++)
		{
			System.out.println(p.Story.get(i));
			if(p.Story.get(i).startsWith(string))
			{
				System.out.println("**");
				return i;
			}
		}
		return 100;
	}
}
