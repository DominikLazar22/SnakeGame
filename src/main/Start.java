package main;

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game g = new Game();
		try {
			g.gameLoop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
