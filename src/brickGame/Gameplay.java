package brickGame;

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

	public int HEIGHT = 800;
	public int WIDTH = 800;
	
	private final int platFormLength = 100;
	private final int ballDimY = 20;
	private final int ballDimX = 20;
	private final int borderPx = 3;
	
	
	private boolean play = false;
	private int score = 0;
	private int totalBrick;
	
	private Timer time;
	private Timer repaintTimer;
	private int delay = 6;
	
	private int playerX = WIDTH / 2 - platFormLength / 2 - 10;
	
	private int ballPosX = playerX + 40;
	private int ballPosY = HEIGHT - 200;
	
	private int ballDirX = -1;
	private int ballDirY = -2;
	
	private MapGenerator brickMap;
	
	
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		brickMap = new MapGenerator(3, 8, WIDTH, HEIGHT);
		totalBrick = 3*8;
		
		time = new Timer(delay, this);
		
		repaintTimer = new Timer(1, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				repaintTimer.start();
				repaint();
			}
		});
		
		time.start();
		repaintTimer.start();
	}
	
	private void resetCoordinates() {
		
		playerX = WIDTH / 2 - platFormLength / 2 - 10;
		
		ballPosX = playerX + 40;
		ballPosY = HEIGHT - 200;
		
		ballDirX = -1;
		ballDirY = -2;
		
		brickMap = new MapGenerator(3, 8, WIDTH, HEIGHT);
		totalBrick = 3*8;
		score = 0;
	}
	
	@Override
	public void paint(Graphics g) {
		
		Font font = new Font("Arial", Font.BOLD, 20);
		//background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, WIDTH, HEIGHT);
		
		brickMap.draw((Graphics2D) g);
		
		//border
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, borderPx, HEIGHT);
		g.fillRect(0, 0, WIDTH, borderPx);
		g.fillRect(WIDTH - 5, 0, borderPx, HEIGHT);
		
		//score
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(Integer.toString(score), WIDTH - 100, 30);
		
		//platform
		g.setColor(Color.green);
		g.fillRect(playerX, WIDTH - 50, platFormLength, 8);
		
		//ball
		g.setColor(Color.RED);
		g.fillOval(ballPosX, ballPosY, ballDimX, ballDimY);
		
		//bottom border
		if(ballPosY >= WIDTH - 20 || totalBrick == 0) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("San Francisco", Font.BOLD, 20));
			g.drawString("GAME OVER!", 100, 30);
			g.drawString("Press ENTER to play again", WIDTH / 2 - 160, HEIGHT - 250);
			
			play = false;
			repaint();
		}
			
		
		g.dispose();
	}
	
	private void checkCollisionWall() {
		
		if(new Rectangle(ballPosX, ballPosY, ballDimX, ballDimY).intersects(new Rectangle(playerX, WIDTH - 50, platFormLength, 8)))
			ballDirY *= -1;
		
		ballPosX += ballDirX;
		ballPosY += ballDirY;
		
		//left border
		if(ballPosX <= borderPx)
			ballDirX *= -1;
		
		//right border
		if(ballPosX >= WIDTH - 20)
			ballDirX *= -1;
		
		//top border
		if(ballPosY <= borderPx)
			ballDirY *= -1;
	}
	
	private void checkCollisionBrick() {
		
		int row = brickMap.row();
		int column = brickMap.column();
		int spacer = 15;
		int brickWidth = brickMap.getBrickWidth();
		int brickHeight = brickMap.getBrickHeight();
		
		for(int i = 0; i < row; ++i) {
			for(int j = 0; j < column; ++j) {
				
				if(brickMap.getValue(i, j) > 0) {
					int brickX = 60 + (j*(brickWidth + spacer));
					int brickY = 50 + (i*(brickHeight + spacer));
					
					Rectangle brick = new Rectangle(brickX, brickY, brickWidth, brickHeight);
					Rectangle ball = new Rectangle(ballPosX, ballPosY, ballDimX, ballDimY);
					
					if(ball.intersects(brick)) {
						brickMap.setValue(i, j, 0);
						score += 10;
						totalBrick--;
						
						if(ballPosX == brickX || ballPosX == (brickX + brickWidth))
							ballDirX *= -1;
						
						else
							ballDirY *= -1;
						
						return;
					}			
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		time.start();
		
		if(play) {
			
			checkCollisionWall();
			checkCollisionBrick();
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= WIDTH - platFormLength - 10)
				playerX = WIDTH - platFormLength - 10;
			else 
				moveRight();
		}
				
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 10)
				playerX = 10;
			else 
				moveLeft();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !play)
			resetCoordinates();
		
	}
	
	private void moveRight() {
		play = true;
		playerX += 15;
	}
	
	private void moveLeft() {
		play = true;
		playerX -= 15;
	}
	
}
