import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*; 
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
abstract class Piece extends Tile{
	
	private LinkedList<Tile> moves = new LinkedList<Tile>();
	Piece(int x, int y, int type)
	{
		super(x,y,type);
	}
	
	public void draw(Graphics g, int i, BufferedImage BPiece, BufferedImage WPiece, int color)
	{
		super.draw(g,i);
		if(color==0)
			g.drawImage(BPiece,x*width,y*width,null);
		else 
			g.drawImage(WPiece,x*width,y*width,null);
	}
	
	public abstract boolean move(Tile t);
}
/*nothing done, black mark.

retrievemoves
--sets valid moves into a linked list

move
--knows which moves each piece can take

displaymove
--change the colours of the squares found from retrievemove method

*/
