import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Knight extends Piece
{
	BufferedImage BKnight = null;
	BufferedImage WKnight = null;
	int color;
	Knight(int x,int y, int type, int color)
	{
		super(x,y,type,"Knight",color);
		this.color= color;
		try
		{
			if(color==0)
			
				BKnight= ImageIO.read(new File("images/BlackKnight.png"));
			else 
				WKnight= ImageIO.read(new File("images/WhiteKnight.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	public Tile[][] move(Tile t, Tile[][] tiles,AI ai)//once the destination tile is valid the knight can move there regardless.
	{
		if(t==null)
			return null;
		int tempX = t.getX();
		int tempY = t.getY();
		
		if(t.getType()==0)
			return null;
		if(checkRouteK(tempX,tempY))
		{	
			tiles =(super.move(t,tiles,ai));
		}
		return tiles;
	}
	public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)
	{
		return null;
	}
	public boolean checkRouteK(int tempX, int tempY)
	{
		System.out.println("test");
		int xNum= 2, yNum=1;
		for(int i=0; i<2; i++)//8 possible positions for a knight to move.
		{
			if(x-xNum==tempX&&y-yNum==tempY)
				return true;
			else if(x-xNum==tempX&&y+yNum==tempY)
				return true;
			else if(x+xNum==tempX&&y-yNum==tempY)
				return true;
			else if(x+xNum==tempX&&y+yNum==tempY)
				return true;
			
			xNum=1;
			yNum=2;
		}
		return false;
	}
	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BKnight,WKnight,color);
	}
}
