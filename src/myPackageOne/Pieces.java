package myPackageOne;

import static myPackageOne.Constant.BLOCK_EDGE;

import java.awt.Graphics;


public class Pieces {
	public enum ItemType {
		Circle, Diamond, Square, Triangle
	}
	private int x;
	private int y;
	private int moveRight = 0;
	private int moveDown = 0;
	private ItemType itemtypes;
	private boolean match;	
	
	public Pieces() {
		this.x = 0;
		this.y = 0;
		this.match = false;
		this.itemtypes = ItemType.Circle;
	}
	public Pieces(int x, int y) {
		this.match = false;
		this.x = x;
		this.y = y;
	}
	public Pieces(int x, int y, ItemType itemtypes, boolean match){
		this.x = x;
		this.y = y;
		this.match = match;
		this.itemtypes = itemtypes;
	}
	public Pieces clone(Pieces piece) {
		return new Pieces(piece.getX(), piece.getY(), piece.getItemtypes(), piece.isMatch());
	}
	
	public Pieces(ItemType itemtypes) {
		this.itemtypes = itemtypes;	
	}
	
	public ItemType getItemtypes() {
		return itemtypes;
	}
	public void setItemtypes(int itemtypes) {
		this.itemtypes = ItemType.values()[itemtypes] ;
	}
	public void setItemtypes(ItemType itemtypes) {
		// TODO Auto-generated method stub
		this.itemtypes = itemtypes;
	}
		
	public boolean isMatch() {
		return match;
	}
	public void setMatch(boolean match) {
		this.match = match;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getMoveRight() {
		return moveRight;
	}
	public void setMoveRight(int moveRight) {
		this.moveRight = moveRight;
	}
	public int getMoveDown() {
		return moveDown;
	}
	public void setMoveDown(int moveDown) {
		this.moveDown = moveDown;
	}
	
	
	public void paint(Graphics g) {
		//ItemType item= itemtype[x][y].getItemtypes();


		switch(this.itemtypes) {
		case Circle:
			g.fillOval(5 + x * BLOCK_EDGE + moveRight, 5 + y * BLOCK_EDGE + moveDown, BLOCK_EDGE-10, BLOCK_EDGE-10);
			break;
		case Diamond: 
			//Graphics2D g2d = (Graphics2D)g;
			//g2d.rotate(Math.toRadians(45));
			//g2d.drawRect(5 + x * BLOCK_EDGE, 5 + y * BLOCK_EDGE, BLOCK_EDGE-10, BLOCK_EDGE-10);
			//g2d.rotate(Math.toRadians(-45));
			int[] xRPoints = {(x + 1) * BLOCK_EDGE - BLOCK_EDGE/2 + moveRight,
					(x + 1) * BLOCK_EDGE - 5 + moveRight,
					(x + 1) * BLOCK_EDGE - BLOCK_EDGE/2 + moveRight,
					5 + x * BLOCK_EDGE + moveRight};
			int[] yRPoints = { 5 + y * BLOCK_EDGE + moveDown,
					(y + 1) * BLOCK_EDGE - BLOCK_EDGE/2 + moveDown,
					(y + 1) * BLOCK_EDGE - 5 + moveDown,
					(y + 1) * BLOCK_EDGE - BLOCK_EDGE/2 + moveDown};
			g.fillPolygon(xRPoints, yRPoints, 4);
			break;
		case Square: 
			g.fillRect(5 + x * BLOCK_EDGE + moveRight, 5 + y * BLOCK_EDGE + moveDown, BLOCK_EDGE-10, BLOCK_EDGE-10);
			break;
		case Triangle:
			/*						Point basePoint1 = new Point( 5 + x * BLOCK_EDGE,
								                      (y + 1) * BLOCK_EDGE - 5);
						Point basePoint2 = new Point( (x + 1) * BLOCK_EDGE - 5,
													  (y + 1) * BLOCK_EDGE - 5);
						Point topPoint = new Point( x * BLOCK_EDGE/2, 
													5 + x * BLOCK_EDGE);*/
			int[] xPoints = {5 + x * BLOCK_EDGE + moveRight, 
					(x + 1) * BLOCK_EDGE - 5 + moveRight,
					(x + 1) * BLOCK_EDGE - BLOCK_EDGE/2 + moveRight};
			int[] yPoints = {(y + 1) * BLOCK_EDGE - 5 + moveDown,
					(y + 1) * BLOCK_EDGE - 5 + moveDown,
					5 + y * BLOCK_EDGE + moveDown};
			g.fillPolygon(xPoints, yPoints, 3);
			break;							  

		}
	}

	
}
