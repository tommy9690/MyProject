package testingSub;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class PieceAnimation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1476382830688320584L;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//JFrame jr = new JFrame
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame jr = new JFrame("Candy Crush");
				
				Board board= new Board();
				ScoreBoard scoreboard = new ScoreBoard();
				ComponentsLists list = new ComponentsLists();
				list.addComponent("Board", board);
				list.addComponent("ScoreBoard", scoreboard);
				Container cp = jr.getContentPane();
				cp.setLayout(new BorderLayout());
				cp.add(board,BorderLayout.CENTER);
				
				cp.add(scoreboard, BorderLayout.SOUTH);
				//jr.setContentPane(board);
				jr.setSize(900,900);				
				jr.setVisible(true);
				jr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
		});
		
		
	}

}
