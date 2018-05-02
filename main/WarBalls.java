package wb.main;
import wb.controller.GameController;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;


public class WarBalls extends Applet implements Runnable,  MouseListener, MouseMotionListener, KeyListener
{
	/**
	 * Author: Luis Serpas
	 * 201-772-8988
	 * Lms4@njit.edu
	 * web.njit.edu/~lms4
	 * 5/19/2015
	 */
	private static final long serialVersionUID = 31647466L;
	
	
	public static final int width = WarBallsConfig.width;
	public static final int height = WarBallsConfig.height;
	protected Thread mainThread;
	private GameController controller = new GameController();
	
	public void init()
	{
		setSize(width, height);
		setBackground(Color.BLACK);
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
	}

	public void start()
	{
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	public void stop()
	{
		mainThread = null;
	}
	
	public void run()
	{
		startGame();
	}
	
	public void startGame()
	{
		BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = screen.getGraphics();
		Graphics appletGraphics = getGraphics();

		Graphics g2 = screen.getGraphics();
		
		long nano = 1000000000L;  
		int frame = 60;
		double timePerFrame = nano/frame; //16 milliseconds
		long last = System.nanoTime();
		double delta = 0;
		
		while(Thread.currentThread() == mainThread)
		{
			long now = System.nanoTime();
			double elapsedTime = now - last;
			
			delta += elapsedTime;
			last = now;
			
			if(delta>= timePerFrame)
			{
				//Update Model
				controller.update((float)(delta/nano));
				//System.out.println("updating...");

				delta = 0;
			}
			//System.out.println("rendering...");
			
			//Clear Screen
			g.setColor(new Color(69, 45, 27));//Color.DARK_GRAY);//
			g.fillRect(0, 0, width, height);
			
			//Draw Model
			controller.render(g, g2);
			appletGraphics.drawImage(screen, 0, 0, null);
			
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		controller.keyPressed(e);
	}

	public void keyReleased(KeyEvent e)
	{
		controller.keyReleased(e);
	}

	public void keyTyped(KeyEvent e){}

	public void mouseClicked(MouseEvent e){}

	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e){}

	public void mousePressed(MouseEvent e)
	{
		controller.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		controller.mouseReleased(e);
	}

	public void mouseDragged(MouseEvent e)
	{
		controller.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e){}
}
