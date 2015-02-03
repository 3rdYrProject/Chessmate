import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
abstract class Piece extends Tile{
	
	Piece(int x, int y, int type)
	{
		super(x,y,type);
	}
	private LinkedList<Tile> moves = new LinkedList<Tile>();
	
	public void draw(Graphics g, int i)
	{
		super.draw(g,i);
	}
	
	public abstract boolean move();
}
/*nothing done, black mark.

retrievemoves
--sets valid moves into a linked list

move
--knows which moves each piece can take

displaymove
--change the colours of the squares found from retrievemove method

*/
