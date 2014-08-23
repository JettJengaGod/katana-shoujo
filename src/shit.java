import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class shit extends JFrame
{
	//Don't know why this needs to be here.
	private static final long serialVersionUID = 1L;
    //Create and set up the window.
    static JFrame frame = new JFrame("Katana Shoujo");
    
	public static int SCREENWIDTH = 1280;
	public static int SCREENHEIGHT = 720;
	static parser p;
	static int points;
	public static void main(String [] args) throws IOException
	{
		p = new parser("TestRoute.txt");
		points = 0;
		run();
		progress();
		
	}
	private static void createFrame() 
	{
		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel emptyLabel = new JLabel("it's not like I waited for you, b-baka", JLabel.CENTER);
        emptyLabel.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);      
    }
	public void paint(Graphics g)
	{
		Graphics2D scene = (Graphics2D)g;
		//use scene to draw the background
		
		frame.paint(g);
		
		
		
		
	}
	public static void run()
	{
		createFrame();
	}
	public static void progress() throws IOException
	{
		int index = 0;
		String working;
		String tag;
		String name;
		String quote;
		while(index<p.Story.size()-1){
			working = p.Story.get(index);
			if(working.equals("ending"))
			{
				System.out.println("End");
				return;
			}
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
			String c1 = tag.substring(1);
			String c2 = c1.substring(c1.indexOf('P')+1);
			String c3 = c2.substring(c2.indexOf('P')+1);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
	            		points += Integer.parseInt(c2.substring(0,c2.indexOf('P')));
	            		break;
	            	}
	            	case 3:
	            	{
	            		points += Integer.parseInt(c3);
	            		break;
	            	}
	            	default: points = 0;
	            	break;
	            }
				
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
