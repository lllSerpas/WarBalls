package wb.menu;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;


public class WarBallsMenu extends Applet implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame f = new JFrame("War Balls Menu");
	JMenuBar menuBar = new JMenuBar();
	JPanel bottom;
	InputPanel dialog;
	
	
	public WarBallsMenu()
	{
		setLayout(new BorderLayout());
		
		add(new Button("Start"), BorderLayout.SOUTH);
		
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}
