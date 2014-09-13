package myPackageOne;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Animation implements ActionListener{
	
	private String action;
	private Pieces piecebegin;
	private Pieces pieceend;
	
	
	public Animation(String action, Pieces piecebegin, Pieces pieceend)  {
//		piecebegin.clone(swapPieceBegin);
//		pieceend.clone(sawpPieceEnd);
//		thread1.start();
		this.action = action;
		this.piecebegin = piecebegin;
		this.pieceend = pieceend;
	}
	
	public void move (Pieces swapPieceBegin, Pieces sawpPieceEnd) {
		swapPieceBegin.setMoveRight(swapPieceBegin.getMoveRight() + 
				(sawpPieceEnd.getX()-swapPieceBegin.getX())*1);
		swapPieceBegin.setMoveDown(swapPieceBegin.getMoveDown() + 
				(sawpPieceEnd.getY()-swapPieceBegin.getY())*1);
		//System.out.println("m" + swapPieceBegin.getMoveRight()+ " "+ swapPieceBegin.getMoveDown());
	}
	
	public void move () {
		piecebegin.setMoveRight(piecebegin.getMoveRight() + 
				(pieceend.getX()-piecebegin.getX())*1);
		piecebegin.setMoveDown(piecebegin.getMoveDown() + 
				(pieceend.getY()-piecebegin.getY())*1);
		//System.out.println("m" + piecebegin.getMoveRight()+ " "+ piecebegin.getMoveDown());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

/*	@Override
	public void run() {
		// TODO Auto-generated method stub
		int counter = 1;
		while (counter <= BLOCK_EDGE) {
			if(action != null && "MOVE".equals(action)){
				move(piecebegin,pieceend);
				System.out.println("a" + piecebegin.getMoveRight()+ " "+ pieceend.getMoveDown());
			}
			System.out.println("a" + piecebegin.getMoveRight()+ " "+ pieceend.getMoveDown());
			try {
				Thread.sleep(100); 
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
		}
		
	}*/
	

}
