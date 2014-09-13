package myPackageOne;

import static myPackageOne.Constant.SCREEN_HEIGHT;
import static myPackageOne.Constant.SCREEN_WIDTH;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class SwingOne extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2485423082916178583L;


	protected void init(){
		//this.setSize(SCREEN_WIDTH+25,SCREEN_HEIGHT+50);		
		PanelOne jp = new PanelOne();
		this.setSize(SCREEN_WIDTH+100,SCREEN_HEIGHT+100);
		//new Thread(jp).start();
		//this.setSize(, 900);
		Container cp = getContentPane();
		//this.getContentPane().setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setBackground(Color.RED);
		cp.add(jp);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	
}