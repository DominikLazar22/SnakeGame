package main;

import java.awt.Point;
import java.util.Random;

public class Snake {

	private int x, y;
	
	private Snake next;
	private int length = 0;
	
	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
		
		next = null;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Snake getNext() {
		return next;
	}
	
	private Point RandomPoint(int boardSize) {
		Random rd = new Random();
		int rdX = rd.nextInt(boardSize);
		int rdY = rd.nextInt(boardSize);
		return new Point(rdX, rdY);
	}
	public Point generateFood(int boardSize) {
		Point choice = RandomPoint(boardSize);
		if(choice.x == x && choice.y == y) {
			choice = generateFood(boardSize);
		}
		Snake tmp = next;
		while(tmp != null) {
			if(choice.x == tmp.x && choice.y == tmp.y) {
				choice = generateFood(boardSize);
			}
			tmp = tmp.next;
		}
		return choice;
	}
	
	private void move(int nextX, int nextY) {
		int tmpX = this.x;
		int tmpY = this.y;
		
		this.x = nextX;
		this.y = nextY;
		
		if(next != null) {
			next.move(tmpX, tmpY);
		}
	}
	
	public boolean isAlive(int boardSize) {
		
		int headX = this.x;
		int headY = this.y;
		
		//head outside of board
		if(headX < 0 || headY < 0 || headX >= boardSize || headY >= boardSize) {
			return false;
		}
		
		//For each tailSegment check if head collided 
		Snake tmp = next;
		while(tmp != null) {
			int tailX = tmp.x;
			int tailY = tmp.y;
			
			if(headX == tailX && headY == tailY) {
				return false;
			}
			tmp = tmp.next;
		}
		return true;
	}
	
	
	
	public void move(int direction) {
		//System.out.println("Direction:" + direction + "HeadX:" + x + "HeadY:" + y);
		int tmpX = x;
		int tmpY = y;
		// UP
		if(direction == 2) {
			y -= 1;
		//Left
		}else if(direction == 1) {
			x -= 1;
		//Right	
		}else if(direction == 3) {
			x += 1;
		}// Down
		else if(direction == 0) {
			y += 1;
		}
		
		if(next != null) {
			next.move(tmpX, tmpY);
		}
	}
	
	
	public void append(int direction) {
		int tmpX,tmpY;
		if(next == null) {
			tmpX = this.x;
			tmpY = this.y;
			next = new Snake(tmpX, tmpY);
			move(direction);
		}else {		
			Snake tmp = next;
			while(tmp.next != null) {
				tmp = tmp.next;
			}
			tmpX = tmp.x;
			tmpY = tmp.y;
			move(direction);
			tmp.next = new Snake(tmpX, tmpY);
		}
		length++;
	}
	
	public int getLength() {
		return length;
	}
	
	
}
