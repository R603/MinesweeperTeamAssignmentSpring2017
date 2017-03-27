import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter{

	public void mousePressed(MouseEvent e){
		switch (e.getButton()){
		case 1:		//Left mouse button
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
			
		case 3:		//Right mouse button
			Component c2 = e.getComponent();
			while (!(c2 instanceof JFrame)){
				c2 = c2.getParent();
				if (c2 == null){
					
					return;
				}
			}
			JFrame myFrame2 = (JFrame) c2;
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
			
		default:
			break;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
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
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) { } 
			else {
				if ((gridX != -1) && (gridY != -1)) {
					if ((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)) {
						Color newColor = null;
						if (!myPanel.checkForMines(myPanel.mouseDownGridX,myPanel.mouseDownGridY)){																					
							myPanel.checkAdjacent(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							newColor = Color.GRAY;
							if (myPanel.ifGameIsWon()){ 
								myPanel.repaint();
								myFrame.dispose();		
							}
						}

						if(myPanel.mineArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == 1){ // ifGameIsWon's counterpart
							if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == Color.WHITE){
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLACK;
								myPanel.repaint();
								for (int i=0; i<myPanel.adjacentMineArray.length; i++){
									for(int j=0; j<myPanel.adjacentMineArray.length; j++){
										if(myPanel.mineArray[i][j] == 1)
										myPanel.colorArray[i][j] = Color.BLACK;
									}
								}
							}
							JOptionPane.showMessageDialog(c, "YOU LOST", "GAME OVER", 1);
							myFrame.dispose();
						}
						
						myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
						myPanel.repaint();
					}
				}
			}
			break;
			
		case 3:		//Right mouse button
			Component d = e.getComponent();
			while (!(d instanceof JFrame)) {
				d = d.getParent();
				if (d == null) {
					return;
				}
			}
			JFrame myFrame2 = (JFrame)d;
			MyPanel myPanel2 = (MyPanel) myFrame2.getContentPane().getComponent(0);
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
					if (myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] == Color.WHITE){
					myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.RED;
					myPanel2.repaint();
						 }
					else if(myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] == Color.RED){
						myPanel2.colorArray[myPanel2.mouseDownGridX][myPanel2.mouseDownGridY] = Color.WHITE;
						myPanel2.repaint();
					}					
				}
			}
			break;
			
		default:
			break;
		}
	}
}
