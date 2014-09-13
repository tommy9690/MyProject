package testingSub;


import static testingSub.Constant.BLOCK_EDGE;
import static testingSub.Constant.BLOCK_NUMBER_X;
import static testingSub.Constant.BLOCK_NUMBER_Y;
import static testingSub.Constant.EASTPANEL_HEIGHT;
import static testingSub.Constant.ITEM_DROP_TIME_DELAY;
import static testingSub.Constant.ITEM_SCORE;
import static testingSub.Constant.MAX_ITERATION;
import static testingSub.Constant.UPDATE_PERIOD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import testingSub.Pieces.ItemType;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseInputListener, ActionListener{
	
	//Game Board Piece
	private Pieces[][] itemlists = new Pieces[BLOCK_NUMBER_X][2*BLOCK_NUMBER_Y] ;
	
	//Use by mouse clicked
	private int selectedIndexX;
	private int selectedIndexY;
	private Pieces selectedPiece;
	private Pieces tempPiece = new Pieces();
	private boolean isClicked = false;
	
	
	//private Vector<Integer,Integer> emptyLists = new Vector<Integer,Integer>();
	
	//Coordinate empty starting point (lowest point of a column)
	private ArrayList<int[]> emptyLists= new ArrayList<int[]>();
	
	//No of empty block per column
	private HashMap<Integer, Integer> emptyMap = new HashMap<Integer, Integer>();
	
	//First empty block (matches block)
	private ArrayList<int[]> markedLists= new ArrayList<int[]>();
	
	
	private Timer timer = new Timer(UPDATE_PERIOD, this);;
	private String action = "";
	//private int frame = 0;
	
	//Moving distance per frame, use for acceleration
	int movingPixel = 0;
	//private int frame = 0;
	
	//Use for checked matches
	private boolean allnotmatched = true;
	private boolean allouternotmatched = true;
	
	
	
	public Board(){
		this.setSize(EASTPANEL_HEIGHT, EASTPANEL_HEIGHT);
		this.addMouseListener(this);
		//this.setLayout(new GridLayout(BLOCK_NUMBER_X,BLOCK_NUMBER_Y));
		
		initGamePieces();

//		/this.setVisible(true);
	}
	
	public void initGamePieces() {
		
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = 0; block_y < 2*BLOCK_NUMBER_Y; block_y++){
				itemlists[block_x][block_y] = new Pieces(block_x,block_y);
				
				itemlists[block_x][block_y].setItemtypes((int)Math.floor(Math.random()*4));
				//this.add(itemlists[block_x][block_y]);
			}
		}
		itemlists[12][31].setItemtypes(ItemType.Circle);
		itemlists[13][31].setItemtypes(ItemType.Circle);
		itemlists[14][31].setItemtypes(ItemType.Circle);
		itemlists[15][31].setItemtypes(ItemType.Circle);
		checkMatches();
		//printOutsidePiece();
		generateNewPieces();
		System.out.println("allnotmatched after generateNewPieces: " + allnotmatched);
		//printOutsidePiece();
	}
	
	public void checkMatches() {
		System.out.println("!----------------checkMatches start--------------------!");
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = 0; block_y < 2 * BLOCK_NUMBER_Y; block_y++){
				if(!itemlists[block_x][block_y].isMatch()) {
					checkedRow(1,0);
					checkedRow(0,1);
				}
			}
		}
		System.out.println("!----------------checkMatches end--------------------!");
	}
	
	public void checkInnerMatches() {
		System.out.println("!----------------checkMatches start--------------------!");
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = 0; block_y < 2 * BLOCK_NUMBER_Y; block_y++){
				if(!itemlists[block_x][block_y].isMatch()) {
					checkedInnerRow(1,0);
					checkedInnerRow(0,1);
				}
			}
		}
		System.out.println("!----------------checkMatches end--------------------!");
	}
	
	public void generateNewPieces() {
		//int generateIterator =1;
		
		while (!allnotmatched) {
			
			for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
				for (int block_y = 0; block_y < 2 * BLOCK_NUMBER_Y; block_y++){
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
/*			System.out.println("$#$##$#$#$#$#$#$#$#4");
			printOutsidePiece();
			System.out.println("$#$##$#$#$#$#$#$#$#4");*/
			//System.out.println("iteration to get the board: "+ generateIterator);
			//generateIterator++;
		}
	}
	
	public void checkedRow(int checktop, int checkleft) {
		Vector<Integer> markedList = new Vector<Integer>();
		//int matchTime;
		Pieces currPiece;
		Pieces nextPiece;
		int block_y_new = 0;
		int block_x_new = 0;
		int checkMaximum = 0;
		int checkXMaximum = 0;
		
		for (int block_x = 0; block_x < 2*BLOCK_NUMBER_X; block_x++) {
			//for (int block_y = BLOCK_NUMBER_Y; block_y < 2*BLOCK_NUMBER_Y; block_y++){
			for (int block_y = 0; block_y < 2*BLOCK_NUMBER_Y; block_y++){
				if(checktop == 1){
					block_y_new = block_y; // 0 ~ 31
					block_x_new = block_x ;//0 ~31
					checkMaximum = 2 * BLOCK_NUMBER_Y;//32
					checkXMaximum = BLOCK_NUMBER_X; //16
				}
				if(checkleft == 1){
					block_x_new = block_x;//0~30
					block_y_new = block_y;//0~31
					checkMaximum = BLOCK_NUMBER_Y;//~16
					checkXMaximum = 2*BLOCK_NUMBER_X;
				}
				if(block_y_new + 1 < checkMaximum  
						&& block_x_new < checkXMaximum) {
					//System.out.println("x:"+block_x + " y:"+block_y);					
					currPiece = itemlists[block_x_new*checktop + (block_y_new)*checkleft]
										 [block_y_new*checktop + block_x_new*checkleft];
					nextPiece = itemlists[block_x_new*checktop + (block_y_new)*checkleft + checkleft]
										 [block_y_new*checktop + block_x_new*checkleft + checktop];
					//System.out.println("--------------");
					//currPiece.printLog();
					//nextPiece.printLog();
					//System.out.println("--------------");
					if (currPiece.getItemtypes() == nextPiece.getItemtypes()) {
						
						markedList.add(block_y_new);
						
						if (block_y_new + 1 == checkMaximum - 1){
							markedList.add(block_y_new + 1);
						}
					}
					//10-SEP-2014 - CZH BUG - BOUNDARY VALUE ARE NOT CHECKED FOR MARKED LIST AND INSERT -START
					if (currPiece.getItemtypes() != nextPiece.getItemtypes()
							||block_y_new + 1 == checkMaximum - 1) {
						
						if(markedList.size() > 0) {
							
							//don't add last slot in the list, already added when check equal
							if(block_y_new + 1 != checkMaximum - 1||
									currPiece.getItemtypes() != nextPiece.getItemtypes()) {
								markedList.add(block_y_new);
							}
	
							if(markedList.size() > MAX_ITERATION){
								
/*								if(block_y_new + 1 == checkMaximum - 1){
									System.out.println("Matched List"+markedList.toString());
								}*/
								setPieceMatches(block_x_new, markedList, checktop, checkleft);
								//System.out.println("allnotmatched after setPieceMatches: "+ allnotmatched);
								
								//System.out.println("allnotmatched after assigned false: "+ allnotmatched);
								//for(int::markedList
								//isMatched = true;
							}
							markedList.removeAllElements();
						}
						
					}
					//10-SEP-2014 - CZH BUG - BOUNDARY VALUE ARE NOT CHECKED FOR MARKED LIST AND INSERT -eND
				}
			}
			markedList.removeAllElements();
		}
		markedList.removeAllElements();
	}
	
	public void setPieceMatches(int row_index, Vector<Integer> markedList, int checktop, int checkleft){
		for (Integer index : markedList){
			if(checktop == 1){
				itemlists[row_index][index].setMatch(true);
				if(index < BLOCK_NUMBER_Y) {
					allouternotmatched = false;
				}
			}
			if(checkleft == 1){
				itemlists[index][row_index].setMatch(true);
				if(row_index < BLOCK_NUMBER_Y) {
					allouternotmatched = false;
				}
			}
			allnotmatched = false;

		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y =BLOCK_NUMBER_Y; block_y < 2*BLOCK_NUMBER_Y; block_y++){
				itemlists[block_x][block_y].paint(g);
			}
		}
		if (isClicked) {			
			g2d.setStroke(new BasicStroke(3));
//			g2d.setColor(Color.WHITE);
//			g2d.drawRect(selectedPiece.getX()*BLOCK_EDGE,selectedPiece.getY()* BLOCK_EDGE,
//					BLOCK_EDGE,BLOCK_EDGE);
			g2d.setColor(Color.ORANGE);
			System.out.println(selectedPiece.getX()*BLOCK_EDGE);
			System.out.println(selectedPiece.getY()*BLOCK_EDGE);
			g2d.drawRect(selectedPiece.getX()*BLOCK_EDGE,
						selectedPiece.getY()* BLOCK_EDGE,
						BLOCK_EDGE,
						BLOCK_EDGE);
			
		}
		//g2d.drawRect(0,	0,50,50);
		
	}
	
	public void runAnimation(){
		
		
		timer.setInitialDelay(ITEM_DROP_TIME_DELAY);
		if (!timer.isRunning()){
			timer.start();
		}
		
		System.out.println("Timer Started!");
		System.out.println("Timer is Running:"+timer.isRunning());
		this.removeMouseListener(this);
		//timer.add
		//timer.
/*		if(frame > BLOCK_EDGE) {
			timer.stop();
			frame = 0;
			movingPixel = 0;
			this.addMouseListener(this);
		}*/
		
	}
	
	public void moveDown() {
		//frame++;
		int indexX;
		int indexY;
		int hasMoved;
		//int emptyMapX;
		int noofEmptyBlock;
		int blockHeight;
		int size = emptyLists.size();
		movingPixel = movingPixel + 1;
		//System.out.println("emptyLists size: " + emptyLists.size());
		for (int list = 0; list < size; list ++) {
			indexX = emptyLists.get(list)[0];
			indexY = emptyLists.get(list)[1];
			hasMoved= emptyLists.get(list)[2]; //0 havent move, 1 have moved;
			//emptyMapX = indexX;
			noofEmptyBlock = emptyMap.get(indexX);
			if(hasMoved==0) {
/*				System.out.println("***************************************");
				System.out.println("indexX: " + indexX +" list: "+list);
				System.out.println("indexY: " + indexY +" list: "+list);
				System.out.println("noofEmptyBlock: " + noofEmptyBlock +" list: "+list);*/
				//System.out.println(emptyLists.size());
				blockHeight = noofEmptyBlock * BLOCK_EDGE;
				for(int counter = 0; counter < indexY; counter++) {
					if(itemlists[indexX][counter].getMoveDown() + movingPixel <= blockHeight) {
						itemlists[indexX][counter].setMoveDown(itemlists[indexX][counter].getMoveDown() + movingPixel);
					} else {
						itemlists[indexX][counter].setMoveDown(blockHeight);
					}
					
				} 
				//System.out.println("Frame: " + frame);
				//System.out.println("MOveDown: "+ itemlists[indexX][indexY-1].getMoveDown() + " list: "+ list);
				if( itemlists[indexX][indexY-1].getMoveDown() >= blockHeight) {
					System.out.println("HEllo");
					//frame = 0;
					
					
					moveItem(indexX, indexY, noofEmptyBlock);
					emptyLists.get(list)[2] = 1;
					populateEmptyItem(indexX);
					
					
				}
				//System.out.println("***************************************");
				//System.out.println();
			}

		}
		
		if(checkNoMoves()){
			System.out.println("Clear");
			movingPixel = 0;
			timer.stop();
			
			emptyMap.clear();
			emptyLists.clear();
			//action = "checkedMatches";
			//timer.start();
			//while(allnotmatched){
			printOutsidePiece();
			//allnotmatched = true;
			//System.out.println("allnotmatched before match: " + allnotmatched);
			checkMatches();	
			printOutsidePiece();
			//System.out.println("allnotmatched after match: " + allnotmatched);
			replaceOuterMatches();
			printOutsidePiece();
			//System.out.println("allnotmatched before getMarkedBlock: " + allnotmatched);
			getMarkedBlock();
			//System.out.println("allnotmatched after getMarkedBlock: " + allnotmatched);
			//}
			
			repaint();
			action = "moveDown";
			runAnimation();
			if(allnotmatched) {
				timer.stop();
			}
			//timer.stop();
			this.addMouseListener(this);
		}		
	}
	
	public void printOutsidePiece(){
		System.out.println();
		char results = 'E';
		char match = ' ';
		for (int block_y = 0; block_y < 2*BLOCK_NUMBER_X; block_y++) {
			
			System.out.print(block_y+" ");
			if (block_y < 10) {
				System.out.print(" ");
			}
			for (int block_x = 0; block_x <  BLOCK_NUMBER_Y; block_x++){
				switch (itemlists[block_x][block_y].getItemtypes()) {
				case Circle:
					results = 'C';
					break;
				case Diamond:
					results = 'D';
					break;
				case Triangle:
					results = 'T';
					break;
				case Square:
					results = 'S';
					break;
				case Empty:
					results = 'E';
					break;
				}
				if(itemlists[block_x][block_y].isMatch()){
					match = 'T';
				}
				if(!itemlists[block_x][block_y].isMatch()){
					match = 'F';
				}
				System.out.print(results+""+match+" ");
				
				
			}
			System.out.println();
		}
	}
	
	public void replaceOuterMatches() {
		while(!allouternotmatched) {
			for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
				for (int block_y = 0; block_y <  BLOCK_NUMBER_Y; block_y++){
					//if(block_y >= BLOCK_NUMBER_Y) {
						if(itemlists[block_x][block_y].isMatch()){
							itemlists[block_x][block_y].setItemtypes((int)Math.floor(Math.random()*4));
							itemlists[block_x][block_y].setMatch(false);
						}
					//}
				}
			}
			allouternotmatched = true;
			checkOuterMatches();
			System.out.println("$#$##$#$#$#$#$#$#$#4");
			printOutsidePiece();
			System.out.println("$#$##$#$#$#$#$#$#$#4");
		}
			
	}
	
	public void checkOuterMatches() {
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = 0; block_y < BLOCK_NUMBER_Y; block_y++){
				if(!itemlists[block_x][block_y].isMatch()) {
					checkedOuterRow(1,0);
					checkedOuterRow(0,1);
				}
			}
		}
	}
	
	public void checkedOuterRow(int checktop, int checkleft) {
		Vector<Integer> markedList = new Vector<Integer>();
		//int matchTime;
		Pieces currPiece;
		Pieces nextPiece;
		//int block_y_new = 0;
		//int block_x_new = 0;
		//int checkMaximum = 0;
		
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			//for (int block_y = BLOCK_NUMBER_Y; block_y < 2*BLOCK_NUMBER_Y; block_y++){
			for (int block_y = 0; block_y < BLOCK_NUMBER_Y; block_y++){

				if(block_y + 1 < BLOCK_NUMBER_Y && block_y >= 0) {
					//System.out.println("x:"+block_x + " y:"+block_y);					
					currPiece = itemlists[block_x*checktop + (block_y)*checkleft]
										 [block_y*checktop + block_x*checkleft];
					nextPiece = itemlists[block_x*checktop + (block_y)*checkleft + checkleft]
										 [block_y*checktop + block_x*checkleft + checktop];
/*					System.out.println("--------------");
					currPiece.printLog();
					nextPiece.printLog();
					System.out.println("--------------");*/
					if (currPiece.getItemtypes() == nextPiece.getItemtypes()) {
						
						markedList.add(block_y);
						if (block_y + 1 == BLOCK_NUMBER_Y - 1){
							markedList.add(block_y + 1);
						}
					}  
					if (currPiece.getItemtypes() != nextPiece.getItemtypes()
							||block_y + 1 == BLOCK_NUMBER_Y - 1) {
						
						if(markedList.size() > 0) {
							
							if(block_y + 1 != BLOCK_NUMBER_Y - 1 ||
									currPiece.getItemtypes() != nextPiece.getItemtypes()) {
								markedList.add(block_y);
							}
	
							if(markedList.size() > MAX_ITERATION){
								System.out.println("Inside checkoutermatches() allouternotmatched:" +allouternotmatched);
								setPieceMatches(block_x, markedList, checktop, checkleft);
								System.out.println("Inside 2nd checkoutermatches() allouternotmatched:" +allouternotmatched);
								//isMatched = true;
								//allouternotmatched = false;
							}
							markedList.removeAllElements();
						}
					}
				}
			}
			markedList.removeAllElements();
		}
	}
	
	public void checkedInnerRow(int checktop, int checkleft) {
		Vector<Integer> markedList = new Vector<Integer>();
		//int matchTime;
		Pieces currPiece;
		Pieces nextPiece;
		int block_y_new = 0;
		int block_x_new = 0;
		int checkMaximum = 0;
		int checkXMaximum = 0;
		
		for (int block_x = 0; block_x < 2*BLOCK_NUMBER_X; block_x++) {
			//for (int block_y = BLOCK_NUMBER_Y; block_y < 2*BLOCK_NUMBER_Y; block_y++){
			for (int block_y = BLOCK_NUMBER_Y; block_y < 2*BLOCK_NUMBER_Y; block_y++){
				if(checktop == 1){
					block_y_new = block_y; // 16 ~ 31	y
					block_x_new = block_x ;//0 ~31		x
					checkMaximum = 2 * BLOCK_NUMBER_Y;//32
					checkXMaximum = BLOCK_NUMBER_X; //16
				}
				if(checkleft == 1){
					block_x_new = block_x + BLOCK_NUMBER_X;//(0~31) + 16		 y
					block_y_new = block_y - BLOCK_NUMBER_Y;//0~30 - 16     x
					checkMaximum = BLOCK_NUMBER_Y;//~16
					checkXMaximum = 2*BLOCK_NUMBER_X;//32
				}
				if(block_y_new >= 0
						&& block_y_new + 1 < checkMaximum  
						&& block_x_new < checkXMaximum) {
					//System.out.println("x:"+block_x + " y:"+block_y);					
					currPiece = itemlists[block_x_new*checktop + (block_y_new)*checkleft]
										 [block_y_new*checktop + block_x_new*checkleft];
					nextPiece = itemlists[block_x_new*checktop + (block_y_new)*checkleft + checkleft]
										 [block_y_new*checktop + block_x_new*checkleft + checktop];
					//System.out.println("--------------");
					//currPiece.printLog();
					//nextPiece.printLog();
					//System.out.println("--------------");
					if (currPiece.getItemtypes() == nextPiece.getItemtypes()) {
						
						markedList.add(block_y_new);
						
						if (block_y_new + 1 == checkMaximum - 1){
							markedList.add(block_y_new + 1);
						}
					}
					//10-SEP-2014 - CZH BUG - BOUNDARY VALUE ARE NOT CHECKED FOR MARKED LIST AND INSERT -START
					if (currPiece.getItemtypes() != nextPiece.getItemtypes()
							||block_y_new + 1 == checkMaximum - 1) {
						
						if(markedList.size() > 0) {
							
							//don't add last slot in the list, already added when check equal
							if(block_y_new + 1 != checkMaximum - 1||
									currPiece.getItemtypes() != nextPiece.getItemtypes()) {
								markedList.add(block_y_new);
							}
	
							if(markedList.size() > MAX_ITERATION){
								
/*								if(block_y_new + 1 == checkMaximum - 1){
									System.out.println("Matched List"+markedList.toString());
								}*/
								setPieceMatches(block_x_new, markedList, checktop, checkleft);
								//System.out.println("allnotmatched after setPieceMatches: "+ allnotmatched);
								
								//System.out.println("allnotmatched after assigned false: "+ allnotmatched);
								//for(int::markedList
								//isMatched = true;
							}
							markedList.removeAllElements();
						}
						
					}
					//10-SEP-2014 - CZH BUG - BOUNDARY VALUE ARE NOT CHECKED FOR MARKED LIST AND INSERT -eND
				}
			}
			markedList.removeAllElements();
		}
		markedList.removeAllElements();
	}
	
	public void getMarkedBlock(){
		int runonce = 1;
		allnotmatched = true;
		for(int block_y = 2*BLOCK_NUMBER_Y - 1; block_y >= BLOCK_NUMBER_Y; block_y-- ){
			for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x ++) {			
				if(itemlists[block_x][block_y].isMatch()){
					//itemlists[block_x][block_y].setItemtypes(ItemType.Empty);
					//nomarked++;
					allnotmatched = false;
					if(runonce == 1) {
						markedLists.add(new int[]{block_x, block_y});
						recursiveChecked(1, block_x, block_y, 1, 0 );
						System.out.println("MarkedLists Size(): " + markedLists.size());
						int markedListsSize = markedLists.size();
						for (int markedCounter = 0; markedCounter < markedListsSize; markedCounter++){
							recursiveChecked(1, markedLists.get(markedCounter)[0], block_y, 0, 1 );
							recursiveChecked(1, markedLists.get(markedCounter)[0], block_y, 0, -1 );
						}
						for (int[] piece: markedLists){
							itemlists[piece[0]][piece[1]].setItemtypes(ItemType.Empty);
							itemlists[piece[0]][piece[1]].setMatch(false);
							

						}
						ComponentsLists list = new ComponentsLists();
						ScoreBoard scoreboard = (ScoreBoard) list.getComponent("ScoreBoard");
						int score = markedListsSize * ITEM_SCORE;
						scoreboard.addScore(score);
						scoreboard.updateScore();
						//updateScore();
						System.out.println("Score is: "+ score);
						processMarkedListToEmptyListMap(markedLists);
						//getEmptyMap(markedLists);
						markedLists.clear();
						runonce++;
						//break;
					} else {
						itemlists[block_x][block_y].setMatch(false);
						
					}
				}
			}
			//break;
		}
		
		System.out.println("MarkedLists Size(): " + markedLists.size());
	}
	
	public void processMarkedListToEmptyListMap(ArrayList<int[]> markedLists){
		//better to use one only
		System.out.println("------------processMarkedListToEmptyListMap Start --------");
		HashMap<Integer, ArrayList<Integer>> contingencyEmptyMap = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> ylist;
		for (int[] piece: markedLists){
			
			
			if(!contingencyEmptyMap.containsKey(piece[0])){
				ylist = new ArrayList<Integer>();
				ylist.add(piece[1]);
				contingencyEmptyMap.put(piece[0], ylist);
			} else {
				ylist = contingencyEmptyMap.get(piece[0]);
				ylist.add(piece[1]);
				contingencyEmptyMap.put(piece[0], ylist);
			}
		}
		Iterator<Entry<Integer, ArrayList<Integer>>> iter = contingencyEmptyMap.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Integer, ArrayList<Integer>> map = (Map.Entry<Integer, ArrayList<Integer>>)iter.next();
			int indexX = map.getKey();
			ArrayList<Integer> indexesY = map.getValue();
			Collections.sort(indexesY);
			System.out.println("emptylist X:" + indexX);
			System.out.println("emptylist Ylist:" + indexesY.toString());
			emptyLists.add(new int[]{indexX, indexesY.get(indexesY.size()-1), 0});
			emptyMap.put(indexX, indexesY.size());
			System.out.println("emptyLists" + Arrays.toString(emptyLists.get(0)));
			System.out.println("emptyMap" + emptyMap.toString());
		}
		System.out.println("------------processMarkedListToEmptyListMap End --------");
		
	}
	
	//-1 check left, 0 don't check, 1 chek right
	public boolean recursiveChecked(int iteration, int block_x, int block_y, int checkleft, int checktop){
		boolean isMatched = false;
		boolean secondMatched = false;
		System.out.println("Matched x: " +block_x);
		System.out.println("Matched y: " +block_y);
		System.out.println("Iteration:  " +iteration);
		System.out.println("Next Matched x: " +(block_x+ iteration*checkleft));
		System.out.println("Next Matched y: " +(block_y+iteration*checktop));
/*		if((block_x+iteration*checkleft >= 0) && 
		   (block_y+iteration*checktop >= 0) &&
		   (block_x+iteration*checkleft < BLOCK_NUMBER_X) &&
		   (block_y+iteration*checktop < BLOCK_NUMBER_Y) &&
		   (itemlists[block_x][block_y].getItemtypes() 
						== itemlists[block_x+ iteration*checkleft][block_y+iteration*checktop].getItemtypes())){
			isMatched = true;
		} */
		if((block_x+iteration*checkleft >= 0) && 
				   (block_y+iteration*checktop >= BLOCK_NUMBER_Y) &&
				   (block_x+iteration*checkleft < BLOCK_NUMBER_X) &&
				   (block_y+iteration*checktop < 2*BLOCK_NUMBER_Y) &&
				   (itemlists[block_x][block_y].getItemtypes() 
							== itemlists[block_x+ iteration*checkleft][block_y+iteration*checktop].getItemtypes())&&
				   (itemlists[block_x+ iteration*checkleft][block_y+iteration*checktop].isMatch())){
			markedLists.add(new int[]{block_x+ iteration*checkleft, block_y+iteration*checktop});
			isMatched = true;
		}
		
		if(isMatched) {
			secondMatched = recursiveChecked(iteration + 1, block_x, block_y, checkleft, checktop);
			System.out.println(iteration+"  " +secondMatched);
			if(secondMatched){
				//itemlists[block_x][block_y].setMatch(true);
				//TO DO
			}
		}

		return isMatched;
	}
	

	
	public boolean checkNoMoves() {
		boolean noMoves = true;
		
		for (int list = 0; list < emptyLists.size(); list ++) {
			for(int block_y = 0; block_y < 2 * BLOCK_NUMBER_Y; block_y++ ){
				if(itemlists[emptyLists.get(list)[0]][block_y].getMoveDown()!=0){
					noMoves = false;
				}
			}
		}
		return noMoves;
	}
	
	public void moveItem(int x, int y, int noofyblock) {
/*		for(int counter = y; counter >= 0; counter--) {
			//itemlists[x][counter + 1].clone(itemlists[x][counter]);
			itemlists[x][counter].printLog();
		}*/
		for(int counter = y - noofyblock; counter >= 0; counter--) {
			itemlists[x][counter+noofyblock].setItemtypes(itemlists[x][counter].getItemtypes());
			//itemlists[x][counter+1].clone(itemlists[x][counter]);
			itemlists[x][counter+noofyblock].setMoveDown(0);
			//itemlists[x][counter+1].printLog();
		} 
		for (int emptycounter = 0; emptycounter < noofyblock; emptycounter++) {
			itemlists[x][emptycounter].setItemtypes(ItemType.Empty);
			itemlists[x][emptycounter].setMoveDown(0);
		}
/*		for(int counter = y; counter >= 0; counter--) {
			//itemlists[x][counter + 1].clone(itemlists[x][counter]);
			itemlists[x][counter].printLog();
		}*/
	}		
	
	public void populateEmptyItem(int indexX) {
		//int indexX=0;
		int indexY=emptyMap.get(indexX);
		System.out.println("emptyMap>> indexX: "+indexX);
		System.out.println("emptyMap>> indexY: "+indexY);
		//Set<E> set = emptyMap.keySet();
/*		Iterator<Entry<Integer, Integer>> it = emptyMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> map = (Map.Entry<Integer, Integer>)it.next();
			indexX = map.getKey();
			indexY = map.getValue();
		}*/
		for (int counter = 0; counter < indexY; counter++){
			itemlists[indexX][counter].setItemtypes((int)Math.floor(Math.random()*4));
		}
	}
	
	//public void 
	
//Old working code	
/*	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Click Start!!");
		
		this.selecteditemX = arg0.getX()/BLOCK_EDGE;
		this.sleecteditemY = arg0.getY()/BLOCK_EDGE +BLOCK_NUMBER_Y;
		int emptyCounter = 0;
		
		
		itemlists[selecteditemX][sleecteditemY].setItemtypes(ItemType.Empty);
		itemlists[selecteditemX][sleecteditemY-1].setItemtypes(ItemType.Empty);
		emptyLists.add(new int[]{selecteditemX,sleecteditemY, 0});
		itemlists[selecteditemX+1][sleecteditemY].setItemtypes(ItemType.Empty);
		emptyLists.add(new int[]{selecteditemX+1,sleecteditemY, 0});
		////System.out.println("emptyLists size: " + emptyLists.size());
		int rowNum = arg0.getX()/BLOCK_EDGE;
		emptyCounter++;
		emptyMap.put(rowNum, 2);
		emptyMap.put(rowNum+1, emptyCounter);
		
		
		repaint();
		action = "moveDown";
		runAnimation();
		//populateEmptyItem();
		System.out.println("Hello!");
		//emptyMap.clear();
		//emptyLists.clear();
		//checkMatches();
		//repaint();
		//doMatches();
		//checkMatches();
		repaint();
		System.out.println("Click End!!");
	}*/
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("-------------Click Start ------------");
		selectedIndexX = arg0.getX()/BLOCK_EDGE;
		selectedIndexY = arg0.getY()/BLOCK_EDGE;
		printOutsidePiece();
		if(selectedPiece != null) {
			if((((selectedPiece.getX() - selectedIndexX) <= 1) 
							&& (selectedPiece.getX() - selectedIndexX) >= -1)
					&& (((selectedPiece.getY() - selectedIndexY) <= 1) 
							&& (selectedPiece.getY() - selectedIndexY) >= -1)){
				swapPieces(selectedPiece.getX(), selectedPiece.getY() + BLOCK_NUMBER_Y, 
						selectedIndexX, selectedIndexY + BLOCK_NUMBER_Y);
				System.out.println("Before clicked allnotmatches:" + allnotmatched);
				checkInnerMatches();
				printOutsidePiece();
				System.out.println("When clicked allnotmatches:" + allnotmatched);
				if(allnotmatched) {
					swapPieces(selectedPiece.getX(), selectedPiece.getY()+ BLOCK_NUMBER_Y
							, selectedIndexX, selectedIndexY+ BLOCK_NUMBER_Y);
				} else {
					//Play Animation
					//generateNewPieces();
					action = "moveDown";
					runAnimation();
					//getMarkedBlock();
				}
			}

			//isClicked = false;
			selectedPiece = null;
			isClicked = false;
			
		} else {
			selectedPiece = itemlists[selectedIndexX][selectedIndexY];
			isClicked = true;
			System.out.println(selectedPiece.getX()+" "+selectedPiece.getY());
		}
		
		repaint();
		//isClicked = false;
		System.out.println("-------------Click End ------------");
	}
	
	public void swapPieces(int indexX1, int indexY1, int indexX2, int indexY2) {
		tempPiece.setItemtypes(itemlists[indexX1][indexY1].getItemtypes()); 
		//long starttime = System.nanoTime();
		System.out.println("Get Move Down: " + itemlists[indexX1][indexY1].getMoveDown()); 
		System.out.println("Get Move Right: " + itemlists[indexX1][indexY1].getMoveRight()); 
		
		itemlists[indexX1][indexY1].setItemtypes(itemlists[indexX2][indexY2].getItemtypes());;
		itemlists[indexX2][indexY2].setItemtypes(tempPiece.getItemtypes());
		//tempPiece = null;
	}
	
	public void doMatches() {
		for (int block_x = 0; block_x < BLOCK_NUMBER_X; block_x++) {
			for (int block_y = BLOCK_NUMBER_Y; block_y < 2 * BLOCK_NUMBER_Y; block_y++){
				if(itemlists[block_x][block_y].isMatch()) {
					itemlists[block_x][block_y].setItemtypes(ItemType.Empty);
					action = "moveDown";
					runAnimation();
				}
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if("moveDown".equals(action)) {
			moveDown();			
		}
		repaint();
	}
}
