import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

public class BallShooter {
	 private Ball ball;
	 private JComponent paintingComponent;
	 private double angleRad = Math.toRadians(60);
	 private double power = 25;
	 
	 void setPaintingComponent(JComponent paintingComponent){
		 this.paintingComponent = paintingComponent;
	 }
	 
	 void setAngle(double angleRad){
		 this.angleRad = angleRad;
	 }
	 
	 void setPower(double power){
		 this.power = power;
	 }
	 
	 void shoot(){
		 Thread t = new Thread(new Runnable(){
			 @Override
			 public void run(){
				 executeShot();
			 }
		 });
		 t.setDaemon(true);
		 t.start();
	 }
	 
	 private void executeShot(){
		 if(ball != null){
			 return;
		 }
		 ball = new Ball();
		 Point2D velocity =  AffineTransform.getRotateInstance(angleRad).
		                transform(new Point2D.Double(1,0), null);
		 velocity.setLocation(velocity.getX() * power * 0.5, velocity.getY() * power * 0.5);
		 ball.setVelocity(velocity);
		 
		 long prevTime = System.nanoTime();
	     while (ball.getPosition().getY() >= 0){
	    	 long currentTime = System.nanoTime();
	         double dt = 3 * (currentTime - prevTime) / 1e8;
	         ball.performTimeStep(dt);
	         prevTime = currentTime;
	         paintingComponent.repaint();
	         try{
	        	 Thread.sleep(10);
	         }
	         catch (InterruptedException e){
	        	 Thread.currentThread().interrupt();
	        	 return;
	         }
	     }
	     
	     ball = null;
	     paintingComponent.repaint();
	 }
	 
	 Ball getBall(){
		 return ball;
	 }
}

class Ball{
	 private final Point2D ACCELERATION = new Point2D.Double(0, -7.0 * 0.1);
	 private final Point2D position = new Point2D.Double();
	 private final Point2D velocity = new Point2D.Double();
	 
	 public Point2D getPosition(){
		 return new Point2D.Double(position.getX(), position.getY());
	 }
	 
	 public void setPosition(Point2D point){
		 position.setLocation(point);
	 }
	 
	 public void setVelocity(Point2D point){
	 	 velocity.setLocation(point);
	 }
	 
	 private static void scaleAddAssign(Point2D result, double factor, Point2D addend){
	 	 double x = result.getX() + factor * addend.getX();
	 	 double y = result.getY() + factor * addend.getY();		 
	 	 result.setLocation(x, y);
	 }
	 
	 void performTimeStep(double dt){
	        scaleAddAssign(velocity, dt, ACCELERATION);
	        scaleAddAssign(position, dt, velocity);
	 }
}
