import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class window extends JFrame implements KeyListener, MouseListener
{
	//Boolean RUN determines if game is running
	private boolean RUN;
	//Determines the screen size
	private static int SCREENWIDTH = 600;
	private static int SCREENHEIGHT = 600;
	
	public window(){
		RUN = true;
		
	}
}
