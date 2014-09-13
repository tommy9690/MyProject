package myPackageOne;

import static myPackageOne.Constant.BLOCK_EDGE;
import static myPackageOne.Constant.BLOCK_NUMBER_X;
import static myPackageOne.Constant.BLOCK_NUMBER_Y;
import static myPackageOne.Constant.EASTPANEL_HEIGHT;
import static myPackageOne.Constant.EASTPANEL_WIDTH;
import static myPackageOne.Constant.MAX_ITERATION;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class PanelOne extends JPanel implements MouseInputListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5421799860491572735L;

	private int x = 25;
	private int text_x = EASTPANEL_HEIGHT/2;
	private int text_y = EASTPANEL_WIDTH/2;
	//private boolean isMatched = false;
	private Graphics g;
	private boolean isPressed = false;
	private boolean isClicked = false;
	private Pieces[][] itemlists;
	private boolean allnotmatched = true;
	private Pieces selectedPiece;
	private Pieces tempPiece = new Pieces();
	private int selectedIndexX;
	private int selectedIndexY;
	private Thread threadmove;
	private Animation moveanime;
	private int testingx = 0;
	//private boolean somePieceSelected = false;

	public PanelOne () {
		//this.setBackground(new Color(255,0,0));
		//BorderLayout bl = new BorderLayout();
		//JPanel eastPanel = new JPanel();
		//eastPanel.setSize(EASTPANEL_WIDTH, EASTPANEL_HEIGHT);
		//eastPanel.setBackground(Color.GRAY);
		//this.setLayout(new BorderLayout());
		//this.add(eastPanel,BorderLayout.EAST);
		//this.setSize(EASTPANEL_WIDTH,EASTPANEL_HEIGHT);
		itemlists = new Pieces[BLOCK_NUMBER_X][BLOCK_NUMBER_Y];
		addMouseListener(this);
		addMouseMotionListener(this);
		initGamePieces();

	}
	
	public void initGamePieces() {
		//genPieces();
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = 0; block_y < BLOCK_NUMBER_Y; block_y++){
				itemlists[block_x][block_y] = new Pieces(block_x,block_y);
				itemlists[block_x][block_y].setItemtypes((int)Math.floor(Math.random()*4));
			}
		}
		checkMatches();
		generateNewPieces();
	
	}
	
	
	public void generateNewPieces() {
		int generateIterator =1;
		while (!allnotmatched) {
			
			for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
				for (int block_y = 0; block_y < BLOCK_NUMBER_Y; block_y++){
					//itemlists[block_x][block_y] = new Pieces();
					if(itemlists[block_x][block_y].isMatch()) {
						//System.out.println(block_x + " " + block_y);
						itemlists[block_x][block_y].setItemtypes((int)Math.floor(Math.random()*4));
						itemlists[block_x][block_y].setMatch(false);
					}
					
				}
			}
			allnotmatched = true;
			checkMatches();
			//System.out.println("iteration to get the board: "+ generateIterator);
			generateIterator++;
		}
	}
//	public void genPieces() {
//		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
//			for (int block_y = 0; block_y < BLOCK_NUMBER_Y; block_y++){
//				itemlists[block_x][block_y] = new Pieces();
//				itemlists[block_x][block_y].setItemtypes((int)Math.floor(Math.random()*4));
//			}
//		}
//	}
	
/*	public void drawItems (Pieces[][] itemtype,Graphics g) {
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y =0; block_y < BLOCK_NUMBER_Y; block_y++){
				ItemType item= itemtype[block_x][block_y].getItemtypes();
				g.setColor(Color.BLACK);
				switch(item) {
					case Circle:
						g.fillOval(5 + block_x * BLOCK_EDGE, 5 + block_y * BLOCK_EDGE, BLOCK_EDGE-10, BLOCK_EDGE-10);
						break;
					case Diamond: 
						 //Graphics2D g2d = (Graphics2D)g;
						 //g2d.rotate(Math.toRadians(45));
						 //g2d.drawRect(5 + block_x * BLOCK_EDGE, 5 + block_y * BLOCK_EDGE, BLOCK_EDGE-10, BLOCK_EDGE-10);
						 //g2d.rotate(Math.toRadians(-45));
						 int[] xRPoints = {(block_x + 1) * BLOCK_EDGE - BLOCK_EDGE/2,
								 			(block_x + 1) * BLOCK_EDGE - 5,
								 			(block_x + 1) * BLOCK_EDGE - BLOCK_EDGE/2,
								 			5 + block_x * BLOCK_EDGE};
						 int[] yRPoints = { 5 + block_y * BLOCK_EDGE,
								 			(block_y + 1) * BLOCK_EDGE - BLOCK_EDGE/2,
								 			(block_y + 1) * BLOCK_EDGE - 5,
								 			(block_y + 1) * BLOCK_EDGE - BLOCK_EDGE/2};
						 g.fillPolygon(xRPoints, yRPoints, 4);
						 break;
					case Square: 
						g.fillRect(5 + block_x * BLOCK_EDGE, 5 + block_y * BLOCK_EDGE, BLOCK_EDGE-10, BLOCK_EDGE-10);
						break;
					case Triangle:
						Point basePoint1 = new Point( 5 + block_x * BLOCK_EDGE,
								                      (block_y + 1) * BLOCK_EDGE - 5);
						Point basePoint2 = new Point( (block_x + 1) * BLOCK_EDGE - 5,
													  (block_y + 1) * BLOCK_EDGE - 5);
						Point topPoint = new Point( block_x * BLOCK_EDGE/2, 
													5 + block_x * BLOCK_EDGE);
						int[] xPoints = {5 + block_x * BLOCK_EDGE, 
						                 (block_x + 1) * BLOCK_EDGE - 5,
						                 (block_x + 1) * BLOCK_EDGE - BLOCK_EDGE/2};
						int[] yPoints = {(block_y + 1) * BLOCK_EDGE - 5,
										 (block_y + 1) * BLOCK_EDGE - 5,
										 5 + block_y * BLOCK_EDGE};
						g.fillPolygon(xPoints, yPoints, 3);
						break;							  
				}
			}
		}

	}*/
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		//this.g = getGraphics();
		super.paintComponent(g);
		
/*		g.drawLine(0, 0, EASTPANEL_WIDTH, EASTPANEL_HEIGHT);
		g.setColor(Color.blue);
		g.draw3DRect(50, 50, 50, 50, true);
		
	    g.setColor (Color.gray);
	    g.draw3DRect (x, 10, 50, 75, true);
	    g.draw3DRect (x, 110, 50, 75, false);
	    g.fill3DRect (100, 10, 50, 75, true);
	    g.fill3DRect (100, 110, 50, 75, false);*/
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y =0; block_y < BLOCK_NUMBER_Y; block_y++){
				if(itemlists[block_x][block_y].isMatch()){
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.RED);
				}
				g.fillRect(block_x * BLOCK_EDGE, block_y * BLOCK_EDGE, BLOCK_EDGE, BLOCK_EDGE);					
				
			}
		}
		g.setColor(Color.BLACK);
		
		for (int width = 0; width <= EASTPANEL_WIDTH; width += BLOCK_EDGE){
			g.drawLine(width, 0, width, EASTPANEL_HEIGHT);
			g.drawLine(0, width, EASTPANEL_WIDTH, width);
		}		
		
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y =0; block_y < BLOCK_NUMBER_Y; block_y++){
				itemlists[block_x][block_y].paint(g);
			}
		}
		//drawItems(itemlists, g);
		

		
		if (isPressed) {
			g.drawString("x:" + text_x + " y:" + text_y, text_x, text_y);
			g.drawRect(text_x,text_y,50,50);
			
		}
		
		if (isClicked) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
//			g2d.setColor(Color.WHITE);
//			g2d.drawRect(selectedPiece.getX()*BLOCK_EDGE,selectedPiece.getY()* BLOCK_EDGE,
//					BLOCK_EDGE,BLOCK_EDGE);
			g2d.setColor(Color.ORANGE);
			g2d.drawRect(selectedPiece.getX()*BLOCK_EDGE,selectedPiece.getY()* BLOCK_EDGE,
					BLOCK_EDGE,BLOCK_EDGE);
			
		}
		g.setColor(Color.CYAN);
		g.drawRect(testingx,0,20,20);
	}
	
	
	public void checkMatches() {
//		boolean leftMatched;
//		boolean rightMatched;
//		boolean topMatched;
//		boolean bottomMatched;
//		boolean totalMatched;
//		
//		if (iteration < MAX_ITERATION) {
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = 0; block_y < BLOCK_NUMBER_Y; block_y++){
				if(!itemlists[block_x][block_y].isMatch()) {
					//check left
					/*					if((block_x-iteration >= 0) && 
								(itemlists[block_x][block_y].getItemtypes() == itemlists[block_x-iteration][block_y].getItemtypes())){
							itemlists[block_x][block_y].setMatch(true);
						}*/
					//leftMatched = recursiveChecked(1, block_x, block_y, -1, 0);

					//check top
					/*					if((block_y-iteration >= 0) && 
								(itemlists[block_x][block_y].getItemtypes() == itemlists[block_x][block_y-iteration].getItemtypes())){
							itemlists[block_x][block_y].setMatch(true);
						}*/
					//topMatched = recursiveChecked(1, block_x, block_y, 0, -1);

					//check right
					/*					if((block_x+iteration < BLOCK_NUMBER_X) && 
								(itemlists[block_x][block_y].getItemtypes() == itemlists[block_x+iteration][block_y].getItemtypes())){
							itemlists[block_x][block_y].setMatch(true);
						}*/
					//rightMatched = recursiveChecked(1, block_x, block_y, 1, 0);
					checkedRow(1,0);

					//check bottom
					/*					if((block_y+iteration < BLOCK_NUMBER_Y) && 
								(itemlists[block_x][block_y].getItemtypes() == itemlists[block_x][block_y+iteration].getItemtypes())){
							itemlists[block_x][block_y].setMatch(true);
						}*/
					//bottomMatched = recursiveChecked(1, block_x, block_y, 0, 1);
					checkedRow(0,1);

					/*					if(itemlists[block_x][block_y].isMatch()){
							int iterationCopy = iteration + 1;
							checkMatches(iterationCopy);
						}*/

					//totalMatched = leftMatched||topMatched||rightMatched||bottomMatched;
					//itemlists[block_x][block_y].setMatch(totalMatched);
				}
			}
		}
		//}
	}
	
	//-1 check left, 0 don't check, 1 chek right
/*	public boolean recursiveChecked(int iteration, int block_x, int block_y, int checkleft, int checktop){
		boolean isMatched = false;
		boolean secondMatched = false;
		if((block_x+iteration*checkleft >= 0) && 
		   (block_y+iteration*checktop >= 0) &&
		   (block_x+iteration*checkleft < BLOCK_NUMBER_X) &&
		   (block_y+iteration*checktop < BLOCK_NUMBER_Y) &&
		   (itemlists[block_x][block_y].getItemtypes() 
						== itemlists[block_x+ iteration*checkleft][block_y+iteration*checktop].getItemtypes())){
			isMatched = true;
		} 
		if(isMatched) {
			secondMatched = recursiveChecked(iteration + 1, block_x, block_y, checkleft, checktop);
			System.out.println(iteration+"  " +secondMatched);
			if(secondMatched){
				itemlists[block_x][block_y].setMatch(true);
			}
		}

		return isMatched;
	}
	*/
	
	public void checkedRow(int checkleft, int checktop) {
		Vector<Integer> markedList = new Vector<Integer>();
		//int matchTime;
		Pieces currPiece;
		Pieces nextPiece;
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = 0; block_y < BLOCK_NUMBER_Y; block_y++){
				if(block_y + 1 < BLOCK_NUMBER_Y) {
					currPiece = itemlists[block_x*checkleft + block_y*checktop]
										 [block_y*checkleft + block_x*checktop];
					nextPiece = itemlists[block_x*checkleft + block_y*checktop + checktop]
										 [block_y*checkleft + block_x*checktop + checkleft];
					if (currPiece.getItemtypes() == nextPiece.getItemtypes()) {
						markedList.add(block_y);
						if (block_y + 1 == BLOCK_NUMBER_Y - 1){
							markedList.add(block_y + 1);
						}
					} else if(markedList.size() > 0) {
						markedList.add(block_y);

						if(markedList.size() > MAX_ITERATION){
							setPieceMatches(block_x, markedList, checkleft, checktop);
							//isMatched = true;
						}
						markedList.removeAllElements();
					}
				}
			}
			markedList.removeAllElements();
		}
	}
	
	public void setPieceMatches(int row_index, Vector<Integer> markedList, int checkleft, int checktop){
		for (Integer index : markedList){
			if(checkleft == 1){
				itemlists[row_index][index].setMatch(true);
			}
			if(checktop == 1){
				itemlists[index][row_index].setMatch(true);
			}
			allnotmatched = false;
		}
	}
	
	public void swapPieces(int indexX1, int indexY1, int indexX2, int indexY2) {
		tempPiece.setItemtypes(itemlists[indexX1][indexY1].getItemtypes()); 
		//Play Animation
		//moveanime = new Animation("MOVE", itemlists[indexX1][indexY1], itemlists[indexX1][indexY2]);
//		threadmove = new Thread(moveanime);
//		threadmove.start();
		long starttime = System.nanoTime();
		
/*		while(System.nanoTime()-starttime < 3000000000L) {
		//for(int steps = 0; steps <= BLOCK_EDGE; steps++){
			if(((int)(System.nanoTime()-starttime)/100000000)%100000000 == 0 ){
				itemlists[indexX1][indexY1].setMoveRight(itemlists[indexX1][indexY1].getMoveRight() + 
						(itemlists[indexX1][indexY2].getX()-itemlists[indexX1][indexY1].getX())*1);
			   itemlists[indexX1][indexY1].setMoveDown(itemlists[indexX1][indexY1].getMoveDown() + 
						(itemlists[indexX1][indexY2].getY()-itemlists[indexX1][indexY1].getY())*1);
				repaint();
				
			}
			System.out.println((int)(System.nanoTime()-starttime)/100000000);
			//repaint();
		}*/
		System.out.println("Get Move Down: " + itemlists[indexX1][indexY1].getMoveDown()); 
		System.out.println("Get Move Right: " + itemlists[indexX1][indexY1].getMoveRight()); 
		//moveanime = null;
		//threadmove.
		
		itemlists[indexX1][indexY1].setItemtypes(itemlists[indexX2][indexY2].getItemtypes());;
		itemlists[indexX2][indexY2].setItemtypes(tempPiece.getItemtypes());
		//tempPiece = null;
	}
	
/*	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//int i = 0;
		while (true) {
			testingx += 20;
			if(moveanime != null) {
				moveanime.move();
				//System.out.println(moveanime);
			}
			if (testingx > EASTPANEL_HEIGHT) {
				testingx = 0;
			}
			//System.out.println(i);
			//this.re
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//i++;
		}
	}*/

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		selectedIndexX = arg0.getX()/BLOCK_EDGE;
		selectedIndexY = arg0.getY()/BLOCK_EDGE;
		
		if(selectedPiece != null) {
			if((((selectedPiece.getX() - selectedIndexX) <= 1) 
							&& (selectedPiece.getX() - selectedIndexX) >= -1)
					&& (((selectedPiece.getY() - selectedIndexY) <= 1) 
							&& (selectedPiece.getY() - selectedIndexY) >= -1)){
				swapPieces(selectedPiece.getX(), selectedPiece.getY(), selectedIndexX, selectedIndexY);
				checkMatches();
				if(allnotmatched) {
					swapPieces(selectedPiece.getX(), selectedPiece.getY(), selectedIndexX, selectedIndexY);
				} else {
					//Play Animation
					//generateNewPieces();
				}
			}

			//isClicked = false;
			selectedPiece = null;
			isClicked = false;
			
		} else {
			selectedPiece = itemlists[selectedIndexX][selectedIndexY];
			isClicked = true;
			//System.out.println(selectedPiece.getX()+" "+selectedPiece.getY());
		}
		
		repaint();
		//isClicked = false;
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {

		//this.repaint();
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
/*		isPressed = true;
		//g = this.getGraphics();
		text_x = arg0.getX();
		text_y = arg0.getY();
		//g.drawString("x:" + text_x + " y:" + text_y, text_x, text_y);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		this.repaint();
		//g.dispose();
*/	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
/*		isPressed = false;
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		repaint();*/
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		text_x = arg0.getX();
		text_y = arg0.getY();
		//g = this.getGraphics();
		//g.drawString("x:" + arg0.getX() + " y:" + arg0.getY(), arg0.getX(), arg0.getY());
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
