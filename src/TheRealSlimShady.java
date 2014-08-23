import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
/* FrameDemo.java requires no other files. */
public class TheRealSlimShady {
	//The size of the window
	public static int SCREENWIDTH = 1280;
	public static int SCREENHEIGHT = 720;

	
    private static void createFrame() {
    	
        //Create and set up the window.
        JFrame frame = new JFrame("Katana Shoujo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
 
    }
}