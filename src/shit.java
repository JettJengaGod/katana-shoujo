

import java.io.BufferedReader;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;


/*This is the shitty file that I chose to do fucking everything in cause I'm too lazy to seprate it out and I wanted to use shitty globals.
 * Enjoy.
 */
public class shit extends Applet implements KeyListener, MouseListener, Runnable
{
	//Globals
    int state; //What type of thing we are at in the game. 1 is story panel 2 is options 3 is end
	public  int SCREENWIDTH = 1280;// Screen width
	public  int SCREENHEIGHT = 720;// Screen height
	int index = 0; //Where we are in the story
//	AudioPlayer ap;//THIS SHIT DOESN'T WORK WHAT THE FUCK DEHOWE
	ArrayList<String> options = new ArrayList<String>(); //An array list of the current options. 
	String working;//The text of the current line from the text. Still needs to be parsed into tag name and quote.
	parser p; //The object that holds the story in an array list p.Story
	Image textBox;//Image that holds the text and the name.
	Image obox;
	Image bg;
	Image oldbg; // For fading
	Image c,c1,c2;
	Image buffer; // For double buffering
	Graphics bufferGraphics;
	int points; //How many points your character is at.
	String tag; //The tag that shows what the current line is
	String quoteC = "", nameC = ""; //The lines we are currently displaying on the screen for the name and quote
	long timer;
	boolean screenShakeUpdate = false;
	int screenShakeCounter = 0;
	int SCREENSHAKESTRENGTH = 10;
	int SCREENSHAKETIMELIMIT = 60; // ~1 second
	int screenShakeX = 0;
	int screenShakeY = 0;
	boolean fadeUpdate = false;
	float fadeCounter = 60f;
	float FADETIMELIMIT = 16f;
	boolean fadeTrack = true;
	Random r;
	FontMetrics fm;
	
	public void init()
	{	
		r = new Random();
//		ap = new AudioPlayer();//THIS SHIT DOESN'T WORK WHAT THE FUCK DEHOWE
		timer = System.currentTimeMillis();
		options.add("");
		options.add("");
		options.add("");//These 3 lines are to populate options.
		state=1;//The first line will always be a story panel so the state is 1.
		try {
			obox = ImageIO.read(new File("options.png"));
			textBox =ImageIO.read(new File("Text Boxes.png"));//reads in the text boxes file.
			p = new parser("Introduction.txt");//reads in the story file.
			progress();//the function that calls going through to the next line 
		} catch (IOException e) {
			e.printStackTrace();
		}

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("sspr.ttf")));
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resize(SCREENWIDTH,SCREENHEIGHT); //Makes the applet the size we want it
		addMouseListener(this); //Lets us use mouse
		addKeyListener(this); //Lets us use keyboard
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void update (Graphics g) 
	{
		// initialize buffer 
		if (buffer == null) 
		{
			buffer = createImage (this.getSize().width, this.getSize().height); 
			bufferGraphics = buffer.getGraphics (); 
		}
		
		//bufferGraphics.fillRect (0, 0, this.getSize().width, this.getSize().height); 
		paint (bufferGraphics); 
	
		// draw image on the screen 
		g.drawImage (buffer, 0, 0, this);
	}
	
	public void paint(Graphics g)//function that puts everything on the screen
	{
		Graphics2D scene = (Graphics2D)g;//Not really sure what this is. Blame Victor
		//use scene to draw the background
		
		scene.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		Font q = new Font("Source Sans Pro",Font.PLAIN,40);//Makes the font we use for options and story text
		g.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);//Clears the screen
		g.setFont(q); //Sets the default font
		
		g.setColor(Color.white);
		
		if(state == 1)//Story text
		{
			drawTag(g);
			g.drawImage(textBox, 0,0,null); //draw the story boxes
			Font n = new Font("Arial",Font.BOLD,48);//Makes the font for the names
			g.setFont(n); //Sets the name font
			scene.drawString(nameC,53,435); //Draws the current name
			g.setFont(q);//sets the quote font 
			
			fm = g.getFontMetrics();
			
			ArrayList<String> lines = new ArrayList<String>();
			String line = "";
			
			for(int i = 0; i < quoteC.length(); i++)
			{
				line += quoteC.charAt(i);
				if(fm.stringWidth(line) > 1180)
				{
					String lineCopy = line;
					line = line.substring(0, lineCopy.lastIndexOf(' '));
					i -= lineCopy.length() - lineCopy.lastIndexOf(' ') - 1;
					lines.add(line);
					line = "";
				}
			}
			lines.add(line);
			
			for(int i = 0; i < lines.size(); i++)
			{
				scene.drawString(lines.get(i), 40, 535 + 65*i);
			}
		}
		else if(state == 2)//Draws the options
		{
			scene.drawImage(bg, 0, 0, null);
			scene.drawImage(obox, 0, 0, null);//options thing
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
			System.out.println(tag);
			String b = tag.substring(tag.indexOf("B"), tag.indexOf("B")+2);//gets the file name of the background file
			System.out.println(b);
			bg = ImageIO.read(new File(b+".png"));//reads in the background file
			
			if(oldbg == null)
			{
				oldbg = bg;
				g.drawImage(bg, screenShakeX, screenShakeY, null);
			}
			
			else if(oldbg != bg && oldbg != null)
			{
				System.out.println(fadeCounter);
				
				if(fadeTrack)
				{
					fadeCounter = 0;
					fadeTrack = false;
				}
				if(fadeCounter == FADETIMELIMIT)
				{
					oldbg = bg;
					g.drawImage(bg, screenShakeX, screenShakeY,null);//draws the background image
				}
				else
				{
					g.drawImage(oldbg, screenShakeX, screenShakeY,null);//draws the background image
					
					float opacity = fadeCounter/FADETIMELIMIT;
					if(opacity > 1.0f)
						opacity = 1.0f;
					((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
					((Graphics2D)g).drawImage(bg, screenShakeX, screenShakeY,null);//draws the background image
					((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				}
			}
			else
				g.drawImage(bg, screenShakeX, screenShakeY,null);//draws the background image
			
			if(tag.contains("H"))
				screenShakeUpdate = true;
			
			if(tag.contains("T"))//Two chars?
			{
				//CHANGE THIS LINE 
				int ch1x = 200,ch1y = 200,ch2x = 600,ch2y = 200; //Where they are idk how big they are and where they should go
				String tagCopy = tag; // Need to make a copy because we still need to access the background component later
				tagCopy = tagCopy.substring(tag.indexOf("T"));//gets the rest of the string after the tag of where they are
				String ch1 = tagCopy.substring(1, 4);//gets the name of the first character file
				System.out.println(ch1+"***");
				c1 = ImageIO.read(new File(ch1+".png"));//reads in the first character image
				String ch2 = tagCopy.substring(4);//second char file
				c2 = ImageIO.read(new File(ch2+".png"));//reads in
				g.drawImage(c1,ch1x,ch1y,null);//draws c1
				g.drawImage(c2,ch2x,ch2y,null);//draws c2
			}
			else//only one char
			{
				//CHANGE THIS LINE
				int cx = 400, cy = 200;// location of the char 
				String ch = tag.substring(tag.indexOf('C'));//gets name of char
				c = ImageIO.read(new File(ch+".png"));//reads in
				g.drawImage(c,cx,cy,null);//draws c
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void progress() //Function that goes to the next step in the story
	{
		System.out.println("Progress called");
//		ap.play("/Resources/Music/Scrollsound.mp3"); //THIS SHIT DOESN'T WORK WHAT THE FUCK DEHOWE
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
			if(working.contains("&")) //If there is a person talking
			{
				name = working.substring(working.indexOf(']')+1,working.indexOf('&')); //Set the person's name
				quote = working.substring(working.indexOf('&')+1); //Set the quote
				display(name,quote); //Shows the quote on the screen
				index = next(tag,index); //Sets index  
			}
			else if(working.contains(">"))//if it's an option
			{
				display(working); //call the options display
			}
			else //no name story line
			{
				name = "";
				quote = working.substring(working.indexOf(']')+1);
				display(name,quote);
				index = next(tag,index);//sets up to be called again
			}
		}
	}
	public  void display(String name, String quote)
	{
		state = 1;//Lets the game know it's in a quote
		System.out.println(name + ":" + quote); //Console shit can be removed whenever
		nameC=name; // sets the global current name so paint knows what to show
		quoteC = quote;// sets global quote
	}
	public  void display(String working)
	{
		state = 2;//Lets the game know options should be appearing
		int i = 0;
		while(working.contains(">"))//if there are still options
		{
			working = working.substring(working.indexOf(']')+1); //takes out the tag
			options.set(i, working.substring(0,working.indexOf('>')));//sets the array list
			System.out.println(working.substring(0,working.indexOf('>')));//console shit can be removed whenever
			working = working.substring(working.indexOf('>')+1);//cuts out the first option
			i++;
		}
	}
	public  int next(String tag,int index) //sets up the next progress for quotes
	{
		if(tag.startsWith("S"))//checks to see if this is story
			return index+1; // goes to the next line
		
		return 0;
	}
	public  int next(String tag,int index, int choice) //sets up the progress for options 
	{
		if(tag.startsWith("C"))//is it a path choice
		{
			return lookFor("A"+choice)+1;//goes to the spot in the path you want eg. "A1"
		}
		else //A normal points based option
		{
			String c1 = tag.substring(1);//used for parsing out the point values
			String c2 = c1.substring(c1.indexOf('P')+1);
			String c3 = c2.substring(c2.indexOf('P')+1);
	            switch(choice){//what option you used
	            	case 1:
	            	{
	            		points += Integer.parseInt(tag.substring(1,tag.indexOf('P',1)));//adds the points from option 1
	            		break;
	            	}
	            	case 2:
	            	{
	            		points += Integer.parseInt(c2.substring(0,c2.indexOf('P')));//adds the points from option 2
	            		break;
	            	}
	            	case 3:
	            	{
	            		points += Integer.parseInt(c3);//adds the points from option 3
	            		break;
	            	}
	            	default: points = 0;//this should never happen
	            	break;
	            }
			return lookFor("SP"+points,index);//next spot with the right amount of points eg "SP-1"
		}
	}

	public void choose(int x, int y)
	{//method for clicking the options
		int bW = 874, bH = 81; //size of the boxes
		int ox = 216, o1y = 128, o2y = 297, o3y = 466; //location of each box
		if(ox < x && x < ox+bW) //in the boxes x values
		{
			if(o1y < y && y < o1y + bH) //First option
			{
				index = next(tag,index,1); //add the points and set up next progress
				progress(); //continue
			}
			if(o2y < y && y < o2y + bH)//Second option
			{
				index = next(tag,index,2);//add the points and set up next progress
				progress();//continue
			}
			if(o3y < y && y < o3y + bH)//Third option
			{
				index = next(tag,index,3);//add the points and set up next progress
				progress();//continue
			}
		}
	}
	private  int lookFor(String string) {//find the next part of the story
		for(int i = index; i <p.Story.size(); i++)//starts where we are and goes to the end of the story
		{
			if(p.Story.get(i).contains(string)) //found the string
			{
				return i;//return where we are
			}
		}
		return 100;//this should never happen
	}
	private  int lookFor(String string,int current) {//find the next point value this method is redundant and can easily be taken out
		for(int i = current; i <p.Story.size(); i++) //starts where we are and goes to end
		{
			if(p.Story.get(i).startsWith(string))//found the points
			{
				return i;//return where we are
			}
		}
		return 100;//this should never happen
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(state == 1)//we are in a story
		{
			progress();//keep going
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
		if(state == 1)//we are in a story 
		{
			progress(); //continue
		}
		else if (state == 2)//it's an option
		{
			choose(e.getX(),e.getY());//check where we are to see if it's an option
		}
		
		if(fadeCounter < FADETIMELIMIT)
		{
			fadeCounter = FADETIMELIMIT;
			oldbg = bg;
		}
		
		fadeUpdate = true;
		fadeCounter = 0;
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

	@Override
	public void run() {
		while(true)
		{
			long temp = System.currentTimeMillis();
			if((temp - timer) >= 16L)
			{
				timer = temp;
				if(screenShakeUpdate)
				{
					if(screenShakeCounter % 2 == 1)
					{
						screenShakeX = r.nextInt(SCREENSHAKESTRENGTH) - SCREENSHAKESTRENGTH/2;
						screenShakeY = r.nextInt(SCREENSHAKESTRENGTH) - SCREENSHAKESTRENGTH/2;
					}
					
					else
					{
						screenShakeX = 0;
						screenShakeY = 0;
					}
					
					screenShakeCounter++;
					if(screenShakeCounter > SCREENSHAKETIMELIMIT)
					{
						screenShakeUpdate = false;
						screenShakeCounter = 0;
						screenShakeX = 0;
						screenShakeY = 0;
					}
				}
				if(fadeUpdate)
				{
					fadeCounter++;
					if(fadeCounter >= FADETIMELIMIT)
					{
						oldbg = bg;
						fadeUpdate = false;
					}
				}
				
				repaint();
			}
		}
	}
}
