package brickGame;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame obj = new JFrame("Brick Game");
		Gameplay game = new Gameplay();
		
		obj.setBounds(500, 350, game.WIDTH, game.HEIGHT);
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(game);

	}

}
