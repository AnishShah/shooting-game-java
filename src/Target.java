import java.awt.geom.Point2D;


public class Target {
	private final Point2D position = new Point2D.Double();
	
	Target(){
		this.position.setLocation(200+Math.random()*260, 30+Math.random()*340);
	}
	
	public Point2D getPosition(){
		return new Point2D.Double(position.getX(), position.getY());
	}
	
}
