package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game implements KeyListener{
	
	private MyCanvas canvas = new MyCanvas();
	private int boxesCount = 20;
	private int size = 600;
	private int direction;
	private Snake head;
	private int foodX, foodY;
	private boolean alive;
	
	public Game() {
		initialize();
	
		JFrame frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		c.setPreferredSize(new Dimension(size,size));
		canvas.setFocusable(true);
		canvas.addKeyListener(this);
		c.add(canvas);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public void gameLoop() throws InterruptedException {
		System.out.println("StartLoop");
		while(alive) {
			//sleep
			Thread.sleep(400);
			if(!checkFood()) {
				head.move(direction);
			}
			canvas.update();
			alive = head.isAlive(boxesCount);
		}
		System.out.println("End Loop");
	}
	
	private boolean checkFood() {
		if(head.getX() == foodX && head.getY() == foodY) {
			getFood();
			head.append(direction);
			return true;
		}
		return false;
	}
	
	
	private void initialize() {
		alive = true;
		direction = 1;
		Random rd = new Random();
		
		int headX = rd.nextInt(boxesCount-1);
		int headY = rd.nextInt(boxesCount-1);
		
		head = new Snake(headX, headY);
		System.out.println("Generate Head" + head.getX() +","+ head.getY() + "REAL: " + headX + "," + headY);
		getFood();
	}
	
	private void getFood() {
		Point food = head.generateFood(boxesCount);
		foodX = food.x;
		foodY = food.y;
	}
		
	
	@SuppressWarnings("serial")
	class MyCanvas extends JPanel{
		
		public void update() {
			repaint();
		}
		
		public MyCanvas() {
			setBackground(Color.BLACK);
		}
		
		public void paintComponent(Graphics g) {
			int boxSize = size/boxesCount;
			
			super.paintComponent(g);
			for(int i = 0; i < boxesCount; i++) {
				for(int j = 0; j < boxesCount; j ++) {
					g.setColor(Color.WHITE);
					g.drawRect(i*boxSize, j*boxSize, boxSize, boxSize);
				}
			}
			
			//Render Food
			g.setColor(Color.YELLOW);
			g.fillOval(foodX*boxSize, foodY*boxSize, boxSize, boxSize);
			//Render Snake Head
			g.setColor(Color.GRAY);
			g.fillRect(head.getX()*boxSize,head.getY()*boxSize, boxSize, boxSize);
			//Render Body
			g.setColor(Color.WHITE);
			Snake tmp = head.getNext();
			while(tmp != null) {
				int tmpX = tmp.getX() * boxSize; 
				int tmpY = tmp.getY() * boxSize;
				g.fillRect(tmpX, tmpY, boxSize, boxSize);
				tmp = tmp.getNext();
			}
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = (direction +3) % 4;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = (direction + 1) % 4;
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER && !alive) {
			restartGame();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void restartGame() {
		System.out.println("Restart Game");
		try {
			initialize();
			gameLoop();
			canvas.update();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
