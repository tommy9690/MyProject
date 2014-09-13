package testingSub;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Is it a lot of static good?
@SuppressWarnings("serial")
public class ScoreBoard extends JPanel {
	
	private int score = 0;

	private String scorestr = " SCORE: ";
	private JTextField textf;
	private static final int NUMBER_LENGTH = 8;
	private static final int SCORE_LENGTH = 8;

	
	//public void
	
	public ScoreBoard() {
		textf = new JTextField(scorestr+leftPad(this.score)+" ");
		textf.setFont(new Font("Monospaced",Font.BOLD,30));
		System.out.println("No of columns:" + textf.getColumns());
		
		textf.setColumns(SCORE_LENGTH + NUMBER_LENGTH + 1);
		textf.setEditable(false);
		textf.setBorder(BorderFactory.createLineBorder(Color.GRAY,5));
		this.add(textf);
		
	}
	public void updateScore(){
		String score = leftPad(this.score);
		this.textf.setText(scorestr + score+" ");
		System.out.println("Score String:"+ scorestr+score+" ");
		repaint();
	}
	
	private String leftPad(int score){
		String scoreString = String.valueOf(score);
		int scoreStringLength = scoreString.length();
		for (int counter = 1; counter <= NUMBER_LENGTH - scoreStringLength; counter++){
			scoreString = " " + scoreString;
		}
		
		return scoreString;
	}
	
	public int getScore() {
		return score;
	}
	public void addScore(int score) {
		this.score += score;
	}

}
