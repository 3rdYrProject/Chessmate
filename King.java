import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class King extends Piece
{
	BufferedImage BKing = null;
	BufferedImage WKing = null;
	int color;
	King(int x,int y, int type, int color)
	{
		super(x,y,type,"King");
		this.color= color;
		try
		{
			if(color==0)
			
				BKing= ImageIO.read(new File("images/BlackKing.png"));
			else 
				WKing= ImageIO.read(new File("images/WhiteKing.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)
	{
		return null;
	}
	public Tile move(Tile t, Tile[][] tiles)
	{
		return null;
	}
	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BKing,WKing,color);
	}
}