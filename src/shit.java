import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class shit {
	static parser p;
	static int points;
	public static void main(String [] args) throws IOException
	{
		p = new parser("shit.txt");
		points = 0;
		progress();
	}
	public static void progress() throws IOException
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
	public static int next(String tag,int index) throws IOException
	{
		if(tag.startsWith("S"))
			return index+1;
		
		return next(tag,index,1);
	}
	public static int next(String tag,int index, int choice) throws IOException
	{
		if(tag.startsWith("C"))
		{
			return lookFor("A"+choice)+1;
		}
		else 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        System.out.println("Enter Choice: 1, 2 ,3"+ tag);
	        try{
	            int i = Integer.parseInt(br.readLine());
	            switch(i){
	            	case 1:
	            	{
	            		points += Integer.parseInt(tag.substring(1,tag.indexOf('P',1)));
	            		break;
	            	}
	            	case 2:
	            	{
	            		points += Integer.parseInt(tag.substring(tag.indexOf('P',1),tag.indexOf('P',tag.indexOf('P',1))));
	            		break;
	            	}
	            	case 3:
	            	{
	            		points += Integer.parseInt(tag.substring(tag.indexOf('P',tag.indexOf('P',tag.indexOf('P',1)))));
	            		break;
	            	}
	            	default: points = 0;
	            	break;
	            }
	            System.out.println(points);
				
	        }catch(NumberFormatException nfe){
	            System.err.println("Invalid Format!");
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
		System.out.println(string);
		for(int i = current; i <p.Story.size(); i++)
		{
			if(p.Story.get(i).startsWith(string))
			{
				return i;
			}
		}
		return 100;
	}
}
