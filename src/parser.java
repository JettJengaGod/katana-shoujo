import java.io.*;
import java.util.*;
public class parser 
{
	String all;
	private String path;
	public ArrayList<String> Story = new ArrayList<String>();
	public boolean done = false;
	public parser(String file) throws IOException
	{
		all="";
		path = file;
		readStuff();
		while(done == false)
		{
			Story.add(parse());
		}
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
	public String parse()
	{
		char sent;
		if(!all.isEmpty())
		{
			sent = all.charAt(0);
			int start = all.indexOf('<');
			int end = all.indexOf('>');
			if(sent == 'S')
			{
				String story = all.substring(0,start)+']'+all.substring(start+1, end);
				all = all.substring(end+1);
				return(story);
				
			}
			if (sent == 'O')
			{
				String options = "";
				options+=all.substring(0,start)+']'+all.substring(start+1, end);
				all = all.substring(end+1);
				while(all.charAt(0)!='*')
				{					
					start = all.indexOf('<');
					end = all.indexOf('>');
					options+=all.substring(1,start)+']'+all.substring(start+1, end);
					all = all.substring(end+1);
				}
				all = all.substring(1);
				return options;
			}
			
		}
		done = true;
		return "";
	}
}
