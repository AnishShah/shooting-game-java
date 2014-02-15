import java.applet.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GamePanel extends JPanel{
	static final int CANON_RADIUS = 70;
	static final int CANON_WIDTH = 15;
	static final int CANON_HEIGHT = 8;
	public static int currentangle = 60, force_value = 25;
	public final BallShooter ballShooter;
	public Target target;
	public Rectangle targetRect;
	public Ellipse2D.Double ballCircle;
	private int hits = 0;
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener l){
		support.addPropertyChangeListener(l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l){
		support.removePropertyChangeListener(l);
	}
	
	public int getHits(){
		return hits;
	}
	
	GamePanel(int x, int y, int width, int height, BallShooter ballShooter){
		this.setBounds(x, y, width, height);
		this.setBackground(Color.WHITE);	
		this.ballShooter = ballShooter;
		this.target = new Target();
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
	    	ballCircle = new Ellipse2D.Double(x-1, y-10, 10, 10);
	    	g2d.fill(ballCircle);
	    }
    	if(target != null){
    		Point2D targetPos = target.getPosition();
    		int x = (int)targetPos.getX();
    		int y = (int)targetPos.getY();
    		g2d.setColor(Color.RED);
    		targetRect = new Rectangle(x, this.getHeight()-10-y, 20, 10);
    		g2d.fill(targetRect);
    	}
    	if(ballCircle != null && targetRect != null){
    		if(ballCircle.intersects(targetRect)){
    			target = new Target();
    			support.firePropertyChange("hits", hits, ++hits);
    		}
    	}
    	g2d.setColor(Color.YELLOW);
    	Rectangle wall = new Rectangle(145, this.getHeight()-140, 15, 140);
    	g2d.fill(wall);

	}	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);	
	}
}
