import java.applet.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Shooting extends JApplet{
	
	private static final int ANGLE_MAX = 70, FORCE_MAX = 50;
	private static final int ANGLE_MIN = 5, FORCE_MIN = 10;
	private static final int ANGLE_INIT = 60, FORCE_INIT = 25;
	private static int shots = 15;
	public boolean hitTarget = false;
	private JButton quitbtn, shoot, new_game;
	private JSlider angleSlider, forceSlider;
	private JLabel angleLabel, forceLabel, scoreLabel, shotLabel;
	private JPanel anglePanel, forcePanel;
	private GamePanel game;
	BallShooter ballShooter;
	
	public void init(){
		try{
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					makeGUI();
				}
			});
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	private void makeGUI(){
		setLayout(null);
		setSize(770, 500);
		
		
		setUpAngleSlider();
		setUpForceSlider();		
		setUpGame();	
		setUpScore();
		setUpShots();
		setUpButtons();
	}
	
	private void setUpGame(){
		//game Panel
		ballShooter = new BallShooter();
		game = new GamePanel(250, 100, 500, 350, ballShooter);
		ballShooter.setPaintingComponent(game);
		game.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent e){
				scoreLabel.setText(String.format("%02d", e.getNewValue()));
				hitTarget = true;
			}
		});
		game.setBorder(BorderFactory.createLineBorder(Color.black));
		add(game);
		ballShooter.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent e){
				shoot.setEnabled(true);
				if(!hitTarget){
					shots--;
					shotLabel.setText(String.format("%02d", shots));
					if(shots == 0){
						JOptionPane.showMessageDialog(null, "Game Over! Click on New Game!");
						shoot.setEnabled(false);
					}
				}
				hitTarget = false;
			}
		});
	}
	
	private void setUpForceSlider(){
		//Force Panel
		forceSlider = new JSlider(JSlider.HORIZONTAL, FORCE_MIN, FORCE_MAX, FORCE_INIT);
		forceLabel = new JLabel(Integer.toString(forceSlider.getValue()));
		forceLabel.setFont(new Font("Monospaced", Font.PLAIN, 32));
	    JLabel forceText = new JLabel("FORCE");
		forceSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				forceLabel.setText(Integer.toString(forceSlider.getValue()));
			}
		});
		forcePanel =  new JPanel();
		forcePanel.add(forceLabel);
		forcePanel.add(forceSlider);
		forcePanel.add(forceText);
		forcePanel.setBounds(20, 310, 210, 100);
		forcePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(forcePanel);
	}
	
	private void setUpAngleSlider(){
		//Angle Panel
		angleSlider = new JSlider(JSlider.HORIZONTAL, ANGLE_MIN, ANGLE_MAX, ANGLE_INIT);
		angleLabel = new JLabel(Integer.toString(angleSlider.getValue()));
		angleLabel.setFont(new Font("Monospaced", Font.PLAIN, 32));
		JLabel angleText = new JLabel("ANGLE");
		angleSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				angleLabel.setText(Integer.toString(angleSlider.getValue()));
				game.currentangle = angleSlider.getValue();
				game.repaint();
			}
		});
		anglePanel = new JPanel();
		anglePanel.add(angleLabel);
		anglePanel.add(angleSlider);
		anglePanel.add(angleText);
		anglePanel.setBounds(20, 100, 210, 100);
		anglePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(anglePanel);
	}
	
	private void setUpButtons(){
		//Quit button
		quitbtn = new JButton("Quit");
		quitbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		quitbtn.setBounds(20, 20, 90, 50);
		quitbtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		
		//Shoot button
		shoot = new JButton("Shoot!");
		shoot.setBounds(130, 20, 90, 50);
		shoot.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		shoot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				shoot.setEnabled(false);
				if(shots != 0){
					ballShooter.setAngle(Math.toRadians(angleSlider.getValue()));
					ballShooter.setPower(forceSlider.getValue());
					ballShooter.shoot();
				}else{
					JOptionPane.showMessageDialog(null, "Game Over! Click on New Game.");
				}
			}
		});

		//New Game button
		new_game = new JButton("New Game");
		new_game.setBounds(getWidth()-150, 20, 130, 50);
		new_game.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		new_game.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				shoot.setEnabled(true);
				game.resetGame();
				shots = 15;
				shotLabel.setText(String.format("%02d", shots));
				scoreLabel.setText(String.format("%02d", game.getHits()));
				angleSlider.setValue(ANGLE_INIT);
				forceSlider.setValue(FORCE_INIT);
			}
		});
		
		add(quitbtn);
		add(shoot);
		add(new_game);
	}

	private void setUpScore(){
		scoreLabel = new JLabel(String.format("%02d", game.getHits()));
		scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 32));
		JLabel hitsLabel = new JLabel("HITS");
		hitsLabel.setBounds(290, 20, 90, 50);
		scoreLabel.setBounds(240, 20, 50, 50);
		add(scoreLabel);
		add(hitsLabel);
	}

	private void setUpShots(){
		shotLabel = new JLabel(String.format("%02d", shots));
		shotLabel.setFont(new Font("Monospaced", Font.BOLD, 32));
		JLabel shotsLeftLabel = new JLabel("Shots Left");
		shotLabel.setBounds(400, 20, 50, 50);
		shotsLeftLabel.setBounds(450, 20, 90, 50);
		add(shotLabel);
		add(shotsLeftLabel);
	}
}