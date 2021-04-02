package brickGame;

import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

	private int map [][];
	private final int brickWidth;
	private final int brickHeight;
	private final int row;
	private final int column;
	
	public MapGenerator(int row, int column, int WIDTH, int HEIGHT) {
		this.row = row;
		this.column= column;
		
		map = new int [row][column];
		
		for(int i = 0; i < row; ++i)
			for(int j = 0; j < column; ++j)
				map [i][j] = 1;
		
		brickWidth = WIDTH / (column + 3);
		brickHeight = HEIGHT / (row + 6);
		
	}
	
	public void draw(Graphics2D g) {
		int spacer = 15;
		
		for(int i = 0; i < row; ++i) {
			for(int j = 0; j < column; ++j) {
				if(map [i][j] >= 1) {
					g.setColor(Color.WHITE);
					g.fillRect(60 + (j*(brickWidth + spacer)), 50 + (i*(brickHeight + spacer)), brickWidth, brickHeight);
				}
				
			}
		}
			
	}
	
	public int getValue(int row, int column) {
		return map [row][column];
	}
	
	public void setValue(int row, int column, int value) {
		map [row][column] = value;
	}
	
	public int row() { return row; }
	
	public int column() { return column; }
	
	public int getBrickWidth() { return brickWidth; }
	
	public int getBrickHeight() { return brickHeight; }
}
