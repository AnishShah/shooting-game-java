import java.applet.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class GamePanel extends JPanel{
	static final int CANON_RADIUS = 70;
	static final int CANON_WIDTH = 15;
	static final int CANON_HEIGHT = 8;
	public static int currentangle = 60;
	
	GamePanel(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
		this.setBackground(Color.WHITE);
	}
	
	private void doDrawing(Graphics g){
		g.setColor(Color.BLUE);
	    g.fillArc(-CANON_RADIUS/2, this.getHeight()-CANON_RADIUS/2, CANON_RADIUS, CANON_RADIUS, 0, 90);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.BLUE);
	    Rectangle rect = new Rectangle(CANON_RADIUS/2, this.getHeight()-4, CANON_WIDTH, CANON_HEIGHT);
	    AffineTransform transform = new AffineTransform();
	    transform.rotate(Math.toRadians(-currentangle), 0, this.getHeight());
	    Shape transformed = transform.createTransformedShape(rect);
	    g2d.fill(transformed);

	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);	
	}
}
