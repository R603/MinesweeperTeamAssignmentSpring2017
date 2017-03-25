import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

//import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class MyPanel extends JPanel{
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;
	private Random totalMines = new Random();
	private final int TOTAL_MINES = 12 + totalMines.nextInt(4);
	private Random randomMine = new Random();
	private int numMines = 0;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int[][] mineArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public int[][] adjacentMineArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public MyPanel(){
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1){
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2){
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3){
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		for (int x = 0; x < TOTAL_COLUMNS; x ++){
			for (int y = 0; y < TOTAL_ROWS; y ++){
				colorArray[x][y] = Color.WHITE;
			}
		}
		//Populate grid with random mines
		while (numMines < TOTAL_MINES){
			int newMine = randomMine.nextInt(2);
			int X = randomMine.nextInt(TOTAL_COLUMNS);
			int Y = randomMine.nextInt(TOTAL_ROWS);
			if (newMine == 1 && mineArray[X][Y] != 1){
				numMines ++;
				mineArray[X][Y] = newMine;		
			}		
		}
		//Populate adjacentMineArray
		for (int i = 0; i < TOTAL_COLUMNS; i ++){
			for (int j = 0; j < TOTAL_ROWS; j ++){
				if (!checkForMines(i,j)){
					adjacentMineArray[i][j] = adjacentMines(i,j);
				}
				else {
					adjacentMineArray[i][j] = -1;
				}
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;
		
		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);
		
		//Draw the grid
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Paint cell colors and cell numbers when clicked
		for (int x = 0; x < TOTAL_COLUMNS; x ++){
			for (int y = 0; y < TOTAL_ROWS; y ++){
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
					if(colorArray[x][y] == Color.GRAY && getMineCount(x, y) > 0){
						g.setColor(Color.RED);
						g.drawString(getMineCount(x,y) + "",x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 20);
					}
			}
		}

		//Draw a mine counter on the bottom of the screen
		g.setColor(Color.BLUE);
		g.drawString("There Are  "+ TOTAL_MINES + " Total Mines", 100, height - 25);

	}
	
	public int getGridX(int x, int y){
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	
	public int getGridY(int x, int y){
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
	public boolean checkForMines(int x, int y){ //Checks if a mine is present in a cell
		return mineArray[x][y] == 1;		
	}
	
	public int getMineCount(int x,int y){ //Counts the total amount of Mines generated
		return adjacentMineArray[x][y];
	}
	
	
					
	public int adjacentMines(int x, int y){ //Counts the number of Mines adjacent to a cell
		int adjacentMines = 0;
		for (int i = 0; i < 3; i ++){
			for(int j = 0; j < 3; j ++){
				if (((x-1) + i < mineArray.length && (x-1)+i >= 0) && (y-1)+j < mineArray[(x-1)+i].length && (y-1)+j >= 0){
					if (checkForMines((x-1)+i,(y-1)+j)){
						adjacentMines ++;
					}
				}
			}
		}
		return adjacentMines;
	}

	public void checkAdjacent(int x, int y){ //Checks adjacent cells and paints them if no mine is present
		if((x >= mineArray.length) || (y >= mineArray[0].length) || (x < 0) || (y < 0)){ return; }

				
		if (checkForMines(x,y)){
			return;
		}else {
			if (adjacentMines(x,y) != 0){
				colorArray[x][y] = Color.GRAY;
				return;
			}
			if (colorArray[x][y] == Color.GRAY || colorArray[x][y] == Color.RED){
				
			return;
			}
					
			// Recursion
			colorArray[x][y] = Color.GRAY;
			checkAdjacent(x + 1, y);
			checkAdjacent(x, y + 1);
			checkAdjacent(x - 1, y);
			checkAdjacent(x, y - 1);
			
			checkAdjacent(x + 1, y - 1);
			checkAdjacent(x - 1, y - 1);
			checkAdjacent(x + 1, y + 1);
			checkAdjacent(x - 1, y + 1);
				
		}
	}
}