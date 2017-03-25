import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter{
	
	public static JOptionPane j = new JOptionPane();
	
	public void mousePressed(MouseEvent e){
		switch (e.getButton()){
		case 1:		//Left mouse button being pressed
			Component c = e.getComponent();
			while (!(c instanceof JFrame)){
				c = c.getParent();
				if (c == null){
					
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button being pressed
			Component d = e.getComponent();
			while (!(d instanceof JFrame)){
				d = d.getParent();
				if (d == null){
					
					return;
				}
			}
			JFrame myFrame2 = (JFrame) d;
			MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
			Insets myInsets2 = myFrame2.getInsets();
			int x2 = myInsets2.left;
			int y2 = myInsets2.top;
			e.translatePoint(-x2, -y2);
			int x3 = e.getX();
			int y3 = e.getY();
			myPanel2.x = x3;
			myPanel2.y = y3;
			myPanel2.mouseDownGridX = myPanel2.getGridX(x3, y3);
			myPanel2.mouseDownGridY = myPanel2.getGridY(x3, y3);
			myPanel2.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
		switch (e.getButton()) {
		case 1:		//Left mouse button being released
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX != -1) && (gridY != -1)) {
					if ((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)) {
						//Released the mouse button on the same cell where it was pressed
						//Here is where the game mechanics go
						Color newColor = null;
						if (!myPanel.checkForMines(myPanel.mouseDownGridX,myPanel.mouseDownGridY)){ // checks if mine is clicked																					
							myPanel.checkAdjacent(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							newColor = Color.GRAY;			
						}

						if(myPanel.mineArray[gridX][gridY] == 1){
							newColor = Color.BLACK;
							String s = new String("LOSER");
							JOptionPane.showMessageDialog(c, s, "GAME OVER", 1);
						}
						
						//A mine is clicked
						//cell is colored Black
						//Game over message is shown, and application is terminated
						
						myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
						myPanel.repaint();
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button being released
			Component d = e.getComponent();
			while (!(d instanceof JFrame)) {
				d = d.getParent();
				if (d == null) {
					return;
				}
			}
			JFrame myFrame2 = (JFrame)d;
			MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets2 = myFrame2.getInsets();
			int x2 = myInsets2.left;
			int y2 = myInsets2.top;
			e.translatePoint(-x2, -y2);
			int x3 = e.getX();
			int y3 = e.getY();
			myPanel2.x = x3;
			myPanel2.y = y3;
			int gridX2 = myPanel2.getGridX(x3, y3);
			int gridY2 = myPanel2.getGridY(x3, y3);
			if ((myPanel2.mouseDownGridX == gridX2) && (myPanel2.mouseDownGridY == gridY2)){
				if (gridX2 != -1 && gridY2 != -1){				
						 if (myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] == Color.WHITE){  //Changes color of a white cell to Red when right-clicked
							myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.RED;
							myPanel2.repaint();
							
						 }
						 else if(myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] == Color.RED){  //Changes color of a red cell back to white if right-clicked again
							 myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.WHITE;
							 myPanel2.repaint();
					}					
				}
			}
			
			
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}
