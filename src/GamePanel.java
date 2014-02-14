import java.applet.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GamePanel extends JPanel{
	static final int CANON_RADIUS = 70;
	static final int CANON_WIDTH = 15;
	static final int CANON_HEIGHT = 8;
	public static int currentangle = 60, force_value = 25;
	public final BallShooter ballShooter;
	
	GamePanel(int x, int y, int width, int height, BallShooter ballShooter){
		this.setBounds(x, y, width, height);
		this.setBackground(Color.WHITE);	
		this.ballShooter = ballShooter;
	}
	
	private void doDrawing(Graphics g){
		g.setColor(Color.BLUE);
	    g.fillArc(-CANON_RADIUS/2, this.getHeight()-CANON_RADIUS/2, CANON_RADIUS, CANON_RADIUS, 0, 90);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.BLUE);
	    Rectangle rect = new Rectangle(CANON_RADIUS/2-2, this.getHeight(), CANON_WIDTH, CANON_HEIGHT);
	    AffineTransform transform = new AffineTransform();
	    transform.rotate(Math.toRadians(-currentangle), 0, this.getHeight());
	    Shape transformed = transform.createTransformedShape(rect);
	    g2d.fill(transformed);
	    
	    Ball ball = ballShooter.getBall();
	    if(ball != null){
	    	g.setColor(Color.BLACK);
	    	Point2D position = ball.getPosition();
	    	int x = (int)position.getX();
	    	int y = this.getHeight() - (int)position.getY();
	    	g.fillOval(x-1, y-10, 10, 10);
	    }
	}		

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);	
	}
}
