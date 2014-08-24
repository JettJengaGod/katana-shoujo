import java.io.*;
import java.util.*;
public class parser 
{
	String all; //the string that holds the whole story
	private String path; //the file path
	public ArrayList<String> Story = new ArrayList<String>(); //Semi parsed story
	public boolean done = false; // if we went through the whole file
	public parser(String file) throws IOException
	{
		all=""; 
		path = file;
		readStuff();
		while(!done)//not done
		{
			Story.add(parse());//keep parsing
		}
	}
	public void readStuff() throws IOException //reads in the file to all 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path))); // make a reader to read the file
		try 
		{
		    String line; //the current line
		    while ((line = br.readLine()) != null) //if it's not null read it again
		    {
		    	all += line; //add it to the screen
		    }
		} finally {
			
		    br.close();//end the buffer reader
		}
	}
	public String parse() //puts the tags and quotes in the array list
	{
		char sent; //first char
		if(!all.isEmpty()) //we ain't done
		{
			sent = all.charAt(0);//make it the first char
			int start = 0; //preliminary value
			int end = 0;// preliminary value
			if(sent == 'S')//it's a story line
			{
				start = all.indexOf('<'); //where the quote starts
				end = all.indexOf('>');//where the quote ends
				String story = all.substring(0,start)+']'+all.substring(start+1, end);//parses the string 
				all = all.substring(end+1); //cuts out the line we just added 
				return(story); //adds the line we just parsed
				
			}
			if (sent == 'O')//options
			{
				start = all.indexOf('<');//location of first option
				end = all.indexOf('>');
				String options = "";
				options+=all.substring(1,start)+']'+all.substring(start+1, end+1); //gets first option
				all = all.substring(end+1);//erases what we just added
				while(all.charAt(0)!='*') //we haven't hit the end of options
				{					
					start = all.indexOf('<'); //location of second option
					end = all.indexOf('>');
					options+=all.substring(0,start)+']'+all.substring(start+1, end+1);//adds next option
					all = all.substring(end+1);//erase what we just added
				}
				all = all.substring(1);//erases the *
				return options;//adds a string with all the options 
			}
			if(sent == 'A')//story arc line
			{
				String a = all.substring(0,2); //return what it's called
				all = all.substring(2); //remove what we just added
				if (all.length()>2)//dont' worry bout this
				return(a);//adds "A2" or something like it
			}
			if(sent == 'E')//ending
			{
				all = all.substring(1);
				return("ending");
			}
			
		}
		done = true;
		return "";
	}
}
