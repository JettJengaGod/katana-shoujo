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
			int start = 0;
			int end = 0;
			if(sent == 'S')
			{
				start = all.indexOf('<');
				end = all.indexOf('>');
				String story = all.substring(0,start)+']'+all.substring(start+1, end);
				all = all.substring(end+1);
				return(story);
				
			}
			if (sent == 'O')
			{
				start = all.indexOf('<');
				end = all.indexOf('>');
				String options = "";
				options+=all.substring(1,start)+']'+all.substring(start+1, end+1);
				all = all.substring(end+1);
				while(all.charAt(0)!='*')
				{					
					start = all.indexOf('<');
					end = all.indexOf('>');
					options+=all.substring(0,start)+']'+all.substring(start+1, end+1);
					all = all.substring(end+1);
				}
				all = all.substring(1);
				return options;
			}
			if(sent == 'A')
			{
				String a = all.substring(0,2);
				all = all.substring(2);
				if (all.length()>2)
				return(a);
			}
			if(sent == 'E')
			{
				all = all.substring(1);
				return("ending");
			}
			
		}
		done = true;
		return "";
	}
}
