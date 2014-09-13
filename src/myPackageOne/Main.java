package myPackageOne;

import javax.swing.SwingUtilities;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		
		
		SwingUtilities.invokeLater(new Thread(){
			public void run() {
				SwingOne so = new SwingOne();
				so.init();
				
			}
		});
		
	}



}

