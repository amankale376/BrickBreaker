package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false; //initial state
	private int score = 0;	//initial score
	private int totalBricks = 21; //total bricks
	private Timer timer;  //time
	private int delay = 1; //delay or speed of time
	private int playerX = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -4;
	private int ballYdir =-4;
	private MapGenerator map;
	 
	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black); //background
		g.fillRect(1, 1, 692, 592);
		//border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 691, 3);
		g.fillRect(680, 0, 680, 592);
		//map
		map.draw((Graphics2D)g);
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD,25));
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		g.drawString(""+score, 590, 30);
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		if(totalBricks==0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.fillRect(playerX, 550, 100, 8);
			g.drawString("YOU WON!!!", 190, 300);
			g.fillRect(playerX, 550, 100, 8);
			g.drawString("Press Enter to Restart", 230, 350);
		}
		if(ballposY>570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.fillRect(playerX, 550, 100, 8);
			g.drawString("Game over, Scores: "+score, 190, 300);
			g.fillRect(playerX, 550, 100, 8);
			g.drawString("Press Enter to Restart", 230, 350);
			
		}
		g.dispose();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY,20,20).intersects(new Rectangle(playerX,550, 100, 8))){
				ballYdir = -ballYdir;
			}
			
			A:for(int i = 0; i< map.map.length; i++) {
				for(int j = 0; j<map.map[0].length; j++) {
				if(map.map[i][j]>0) {
					int brickX = j* map.brickWidth +80;
					int brickY = i* map.brickHeight +50;
					int brickWidth = map.brickWidth;
					int brickheight = map.brickHeight;
					
					Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickheight);
					Rectangle ballrect = new Rectangle(ballposX,ballposY,20,20);
					Rectangle brickRect = rect;
					if(ballrect.intersects(rect)) {
						map.setBrickValue(0, i, j);
						totalBricks--;
						score += 5;
						if(ballposX + 19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width) {
							ballXdir = -ballXdir;
						}else {
							ballYdir = -ballYdir;
						}
						break A;
					}
				}
				}}
			
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX<0) {
				ballXdir = -ballXdir;
				
			}
			if(ballposY<0) {
				ballYdir = -ballYdir;
				
			}
			if(ballposX>660) {
				ballXdir = -ballXdir;
				
			}
		}
		repaint();

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
		if(playerX >= 575) {
			playerX = 575;
		} else {
			moveRight();
		}
	}
	if(e.getKeyCode() == KeyEvent.VK_LEFT) {
		if(playerX <= 10) {
			playerX = 10;
		} else {
			moveLeft();
		}
	}
		
	if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		if(!play) {
			play = true;
			ballposX = 120;
			ballposY = 350;
			ballXdir = -4;
			ballYdir= -4;
			playerX = 310;
			score = 0;
			totalBricks = 21;
			map = new MapGenerator(3,7);
			repaint();
			
		}
	}
	}
	
	public void moveRight() {
		play = true;
		playerX+=20;
		
	}

	public void moveLeft() {
		play = true;
		playerX= playerX-20;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
