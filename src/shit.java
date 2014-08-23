import java.io.IOException;


public class shit {
	static parser p;
	public static void main(String [] args) throws IOException
	{
		p = new parser("shit.txt");
	}
	public static void progress()
	{
		String working = p.Story.get(0);
		String tag;
		String name;
		String quote;
		tag = working.substring(0,working.indexOf(']'));
		name = working.substring(working.indexOf(']')+1,working.indexOf(':'));
		quote = working.substring(working.indexOf(':')+1);
		//display(name: quote);
	}
	public static String next(String tag)
	{
		
		return null;
	}
}
