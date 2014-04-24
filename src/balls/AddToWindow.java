// Modified by Zack Rosales
// Original code produced a JFrame with MouseListener that would draw a bouncing ball when the 
// mouse is clicked. This was not threadsafe.
// Modified to use SwingWorker in order to create multiple bouncing balls. Also added 'stop' and
// 'clear' buttons.

package balls;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AddToWindow extends JFrame // AddToWindow class start
{
	
	private DrawBall ball;  //Object for the ball image
	private static JPanel buttonPanel; // South panel for stop and clear buttons
	private static JButton stop, // clear button to remove all bouncing balls
						   clear;
	private static boolean keepMoving;
	public static final int DELAY = 3;
	
	public AddToWindow()  //Constructor
	{
		buildButtonPanel(); // build button panel
		this.add(buttonPanel, BorderLayout.SOUTH);
		ball = new DrawBall();  //Create the ball object
		
		ball.addMouseListener(new MouseAdapter()   //Add a mouselistener to add a ball
		{										   //to the screen on click
			public void mouseClicked(MouseEvent e) // mouseClicked method start
			{
				keepMoving = true;
				SwingWorker<Void, Object> worker = new SwingWorker<Void, Object>()
				{ // start anonymous inner class
					@Override
					protected Void doInBackground() throws Exception // doInBackground method start
					{
						addBall();	//defined below		
						return null;
					} // doInBackground method end
				}; // end anonymous inner class
				worker.execute();
			} // mouseClicked method end
		}); // end anonymous inner class
		add(ball, BorderLayout.CENTER);
		pack();
	} // constructor end
	
	// addBall defines how a ball is added
	public void addBall() // addBall method start
	{
		try
		{
			Ball x = new Ball();  //make a ball drawing
			ball.add(x); //add that drawing to the component
		
			while (keepMoving)
			{
				x.move(ball.getBounds());  //move the ball
				ball.paint(getGraphics());  //keep repainting the ball
				Thread.sleep(DELAY); //take a small break
			} // end for
		} // end try
		catch (Exception e)
		{
			System.out.print("Problem");
		} // end catch
	} // addBall method end
	
	public static void main(String[] args) // main method start
	{
				AddToWindow a = new AddToWindow();
				a.setTitle("Bouncing Ball");
				a.setSize(550, 400);
				a.setDefaultCloseOperation(EXIT_ON_CLOSE);
				a.setVisible(true);		
	} // main method end
	
	// buildButtonPanel method builds the button panel
	public void buildButtonPanel() // buildButtonPanel method start
	{
		// creating components
		buttonPanel = new JPanel();
		stop = new JButton("Stop");
		clear = new JButton("Clear");
		
		// component properties
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		// adding components
		buttonPanel.add(stop);
		buttonPanel.add(clear);
		
		// adding event handlers
		stop.addActionListener(new StopHandler());
		clear.addActionListener(new ClearHandler());
	} // buildButtonPanel method end
	
	class StopHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			keepMoving = false;
		}
	}
	
	class ClearHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			ball.castrate();
		}
		
	}
}
