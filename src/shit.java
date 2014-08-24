import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class shit extends JApplet implements KeyListener, MouseListener
{
    int state; //
	public  int SCREENWIDTH = 1280;
	public  int SCREENHEIGHT = 720;
	int index = 0;
	ArrayList<String> options = new ArrayList<String>();
	String working;
	parser p;
	Image textBox;
	int points;
	String tag;
	String quoteC = "", nameC = "Tornado Max";
	public void init()
	{
		options.add("");
		options.add("");
		options.add("");
		state=1;
		try {
			textBox =ImageIO.read(new File("Text Boxes.png"));
			p = new parser("TestRoute.txt");
			progress();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resize(SCREENWIDTH,SCREENHEIGHT);
		addMouseListener(this);
		addKeyListener(this);
	}
	public void paint(Graphics g)
	{
		Graphics2D scene = (Graphics2D)g;
		//use scene to draw the background
		
		Font q = new Font("Arial",Font.PLAIN,40);
		g.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
		g.setFont(q);
		if(state == 1)
		{

			g.drawImage(textBox, 0,0,null);
			Font n = new Font("Arial",Font.PLAIN,48);
			g.setFont(n);
			scene.drawString(nameC,53,430);
			g.setFont(q);
			scene.drawString(quoteC, 40, 520);
		}
		else if(state == 2)
		{
			scene.drawString(options.get(0), 50, 50);
			scene.drawString(options.get(1), 50, 250);
			scene.drawString(options.get(2), 50, 450);
		}
		else
		{

			scene.drawString("The end", 40, 520);
		}
		
	}
	public  void run()
	{
		
	}
	public  void progress() 
	{
		String name;
		String quote;
		if(index<p.Story.size()-1){
			working = p.Story.get(index);
			if(working.equals("ending"))
			{
				state = 3;
				System.out.println("End");
				return;
			}
			tag = working.substring(0,working.indexOf(']'));
			if(working.contains(":"))
			{
				name = working.substring(working.indexOf(']')+1,working.indexOf(':'));
				quote = working.substring(working.indexOf(':')+1);
				display(name,quote);
				index = next(tag,index);
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
		int bW = 500, bH = 100;
		int ox = 200, o1y = 150, o2y = 250, o3y = 350;
		if(ox < x && x < ox+bW)
		{
			if(o1y < y && y < o1y + bH)
			{
				index = next(tag,index,1);
				progress();
			}
			if(o1y < y && y < o1y + bH)
			{
				index = next(tag,index,2);
				progress();
			}
			if(o2y < y && y < o2y + bH)
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
