import java.io.*;
import java.util.*;
public class parser 
{
	private String all = "";
	private String path;
	private ArrayList<String> Story = new ArrayList();
	private ArrayList<String> Options = new ArrayList();
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
			if(sent == 's')
			{
				
			}
		}
	}
}
