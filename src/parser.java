import java.io.*;
import java.util.*;
public class parser 
{
	private String all = "";
	private String path;
	private ArrayList<String> Story = new ArrayList<String>();
	private ArrayList<String> Options = new ArrayList<String>();
	public parser(String file)
	{
		path = file;
	}
	public static void main(String[] args)
	{
	}
	public void readStuff() throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		try 
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		    	all += line;
		    }
		} finally {
		    br.close();
		}
	}
	public void parse()
	{
		char sent;
		while (all != "")
		{
			sent = all.charAt(0);
			int start = all.indexOf('<');
			int end = all.indexOf('>');
			if(sent == 's')
			{
				Story.add(all.substring(1,start)+']'+all.substring(start+1, end));
			}//Story is an ArrayList of all the strings that are not options. Each string begins with a tag saying where it is the tag ends with ']'
			else if (sent == 'o')
			{
				Options.add(all.substring(1,start)+']'+all.substring(start+1, end));
			}//Options is an ArrayList of all the strings that are options. Each string begins with a tag saying where it is the tag ends with ']'

			all = all.substring(end+1);
		}
	}
}
