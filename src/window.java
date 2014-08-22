import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class window extends JFrame implements KeyListener, MouseListener
{
	//Boolean RUN determines if game is running
	private int tits;
	private boolean RUN;
	//Determines the screen size
	private static int SCREENWIDTH = 600;
	private static int SCREENHEIGHT = 600;
	//Screen
	private Rectangle Background;
	
	public window(){
		RUN = true;
		Background = new Rectangle(0, SCREENHEIGHT, SCREENWIDTH, SCREENHEIGHT);
	}
		
	
	private void createFrame()
	{
		//Add the level here.
        setTitle("Katana Shoujo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(SCREENWIDTH - 10, SCREENHEIGHT - 10));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setFocusable(true);
        addNotify();
        requestFocus();
        addKeyListener(this);
	}
	
}









