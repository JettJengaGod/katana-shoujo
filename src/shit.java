import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
/*This is the shitty file that I chose to do fucking everything in cause I'm too lazy to seprate it out and I wanted to use shitty globals.
 * Enjoy.
 */
public class shit extends JApplet implements KeyListener, MouseListener
{
	//Globals
    int state; //What type of thing we are at in the game. 1 is story panel 2 is options 3 is end
	public  int SCREENWIDTH = 1280;// Screen width
	public  int SCREENHEIGHT = 720;// Screen height
	int index = 0; //Where we are in the story
	ArrayList<String> options = new ArrayList<String>(); //An array list of the current options. 
	String working;//The text of the current line from the text. Still needs to be parsed into tag name and quote.
	parser p; //The object that holds the story in an array list p.Story
	Image textBox;//Image that holds the text and the name.
	Image obox;
	Image bg;
	Image c,c1,c2;
	int points; //How many points your character is at.
	String tag; //The tag that shows what the current line is
	String quoteC = "", nameC = ""; //The lines we are currently displaying on the screen for the name and quote 
	public void init()
	{
		options.add("");
		options.add("");
		options.add("");//These 3 lines are to populate options.
		state=1;//The first line will always be a story panel so the state is 1.
		try {
			obox = ImageIO.read(new File("options.png"));
			textBox =ImageIO.read(new File("Text Boxes.png"));//reads in the text boxes file.
			p = new parser("TestRoute.txt");//reads in the story file.
			progress();//the function that calls going through to the next line 
		} catch (IOException e) {
			e.printStackTrace();
		}
		resize(SCREENWIDTH,SCREENHEIGHT); //Makes the applet the size we want it
		addMouseListener(this); //Lets us use mouse
		addKeyListener(this); //Lets us use keyboard
	}
	public void paint(Graphics g)//function that puts everything on the screen
	{
		Graphics2D scene = (Graphics2D)g;//Not really sure what this is. Blame Victor
		//use scene to draw the background
		
		Font q = new Font("Arial",Font.PLAIN,40);//Makes the font we use for options and story text
		g.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);//Clears the screen
		g.setFont(q); //Sets the default font
		if(state == 1)//Story text
		{
			drawTag(g);
			g.drawImage(textBox, 0,0,null); //draw the story boxes
			Font n = new Font("Arial",Font.PLAIN,48);//Makes the font for the names
			g.setFont(n); //Sets the name font
			scene.drawString(nameC,53,430); //Draws the current name
			g.setFont(q);//sets the quote font 
			if(!quoteC.contains("&"))
			{
				scene.drawString(quoteC, 40, 520);//Draws the current qote font
			}
			else
			{
				scene.drawString(quoteC.substring(0,quoteC.indexOf('&')), 40, 520);
				quoteC = quoteC.substring(quoteC.indexOf('&')+1);
				int i = 1;
				while(quoteC.contains("&"))
				{
					scene.drawString(quoteC.substring(0,quoteC.indexOf('&')), 40, 520+50*i);
					quoteC = quoteC.substring(quoteC.indexOf('&')+1);
					i++;
				}
				scene.drawString(quoteC, 40, 520+50*i);
			}
		}
		else if(state == 2)//Draws the options
		{
			scene.drawImage(obox, 0, 0, null);
			scene.drawString(options.get(0), 220, 180);
			scene.drawString(options.get(1), 220, 358);
			scene.drawString(options.get(2), 220, 527);
		}
		else if(state == 3)//Game is over
		{

			scene.drawString("The end", 40, 520); //End screen
		}
		
	}
	public void drawTag(Graphics g)
	{
		try {
			String b = tag.substring(tag.indexOf("B"), tag.indexOf("B")+1);
			bg = ImageIO.read(new File(b+".png"));
			g.drawImage(bg,0,0,null);
			if(tag.contains("T"))
			{
				int ch1x = 200,ch1y = 200,ch2x = 600,ch2y = 200;
				tag = tag.substring(tag.indexOf("T"));
				String ch1 = tag.substring(0, 2);
				c1 = ImageIO.read(new File(ch1+".png"));
				String ch2 = tag.substring(2, 5);
				c2 = ImageIO.read(new File(ch2+".png"));
				g.drawImage(c1,ch1x,ch1y,null);
				g.drawImage(c2,ch2x,ch2y,null);
			}
			else
			{
				int cx = 400, cy = 200;
				String ch = tag.substring(tag.indexOf('C'));
				c = ImageIO.read(new File(ch+".png"));
				g.drawImage(c,cx,cy,null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void progress() //Function that goes to the next step in the story
	{
		String name; //Name of the char 
		String quote;//Current quote
		if(index<p.Story.size()-1) //Makes sure no index out of bounds errors happen (they shouldn't anyway)
		{
			working = p.Story.get(index); //Sets up the string we are working with
			if(working.equals("ending"))//checks if we are at the end
			{
				state = 3; //Sets end state
				System.out.println("End");//For the text part of game
				return; //Exits
			}
			tag = working.substring(0,working.indexOf(']')); //Makes the tag
			if(working.contains(":")) //If there is a person talking
			{
				name = working.substring(working.indexOf(']')+1,working.indexOf(':')); //Set the person's name
				quote = working.substring(working.indexOf(':')+1); //Set the quote
				display(name,quote); //Shows the quote on the screen
				index = next(tag,index); //Sets index  
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
				index = next(tag,index);
			}
			
		}
	}
	public  void display(String name, String quote)
	{
		state = 1;//Lets the game know it's in a quote
		System.out.println(name + ":" + quote);
		nameC=name;
		quoteC = quote;
		repaint();
	}
	public  void display(String working)
	{
		state = 2;//Lets the game know options should be appearing
		int i = 0;
		while(working.contains(">"))
		{
			working = working.substring(working.indexOf(']')+1);
			options.set(i, working.substring(0,working.indexOf('>')));
			System.out.println(working.substring(0,working.indexOf('>')));
			working = working.substring(working.indexOf('>')+1);
			i++;
		}
	}
	public  int next(String tag,int index) 
	{
		if(tag.startsWith("S"))
			return index+1;
		
		return 0;
	}
	public  int next(String tag,int index, int choice) 
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
	            int i = choice;
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
			return lookFor("SP"+points,index);
		}
	}

	public void choose(int x, int y)
	{
		int bW = 874, bH = 81;
		int ox = 216, o1y = 128, o2y = 297, o3y = 466;
		if(ox < x && x < ox+bW)
		{
			if(o1y < y && y < o1y + bH)
			{
				index = next(tag,index,1);
				progress();
			}
			if(o2y < y && y < o2y + bH)
			{
				index = next(tag,index,2);
				progress();
			}
			if(o3y < y && y < o3y + bH)
			{
				index = next(tag,index,3);
				progress();
			}
			
		}
	}
	private  int lookFor(String string) {
		for(int i = 0; i <p.Story.size(); i++)
		{
			if(p.Story.get(i).contains(string))
			{
				return i;
			}
		}
		return 100;
	}
	private  int lookFor(String string,int current) {
		for(int i = current; i <p.Story.size(); i++)
		{
			if(p.Story.get(i).startsWith(string))
			{
				return i;
			}
		}
		return 100;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(state == 1)
		{
			progress();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state == 1)
		{
			progress();
		}
		else if (state == 2)
		{
			choose(e.getX(),e.getY());
		}
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
