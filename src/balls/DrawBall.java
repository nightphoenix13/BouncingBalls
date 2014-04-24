package balls;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.*; //Used to get the 2D shape methods
import java.util.*;

public class DrawBall extends JPanel
{
	//Panel dimensions
	private static final int DEFAULT_WIDTH = 450,
							 DEFAULT_HEIGHT = 350;
	//Collection for all ball objects that can be added
	private ArrayList<Ball> balls = new ArrayList<Ball>();

	//Method for adding a ball to the screen
	public void add(Ball b)
	{
		balls.add(b);
	}
	
	// castrate method removes the balls from the JFrame
	public void castrate() // castrate method start
	{
		Graphics x = getGraphics();
		x.clearRect(0, 0, 550, 450);
		balls.clear();
	} // castrate method end
	
	//paints the balls in the array list.  Called automatically
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (Ball b : balls)
		{
			g2.fill(b.getShape());
		}
	}
	
	//An easy way to store the dimensions of the given panel
	public Dimension getPreferredSize()
	{
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}

//This class helps to create the ball to be drawn in the above class
class Ball
{
	//ball width and height. Clearly a circle
	private static final int XSIZE = 75,
							 YSIZE = 75;
	//starting position of each ball and how to increment/move the ball
	private double x = 0,
				   y = 0,
				  dx = 1,
				  dy = 1;
	
	//method that actually moves the ball within the bounds of the panel
	public void move(Rectangle2D bounds)
	{
		//Increase each dimension by 1
		x += dx; 
		y += dy;
		
		//if the left side if the panel is hit, start increasing in the other direction
		if (x < bounds.getMinX())
		{
			x = bounds.getMinX();
			dx = -dx;
		}
		//if the right side of the panel is hit, start going in the other direction
		if (x + XSIZE >= bounds.getMaxX())
		{
			x = bounds.getMaxX() - XSIZE;
			dx = -dx;
		}
		
		//if the top of the panel is hit, start going in the other direction
		if(y < bounds.getMinY())
		{
			y = bounds.getMinY();
			dy = -dy;
		}
		//if the bottom of the panel is hit, start going in the other direction
		if(y + YSIZE >= bounds.getMaxY())
		{
			y = bounds.getMaxY() - YSIZE;
			dy = -dy;
		}		
	}
	
	//make a new circle
	public Ellipse2D getShape()
	{
		return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	}
}

