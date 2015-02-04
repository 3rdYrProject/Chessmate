import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
class Rook extends Piece
{
	BufferedImage BRook = null;
	BufferedImage WRook = null;
	int color; 
	Rook(int x, int y, int type, int color)
	{
		super(x,y,type);
		this.color=color;
		try
		{
			if(color==0)
			
				BRook= ImageIO.read(new File("images/BlackRook.png"));
			else 
				WRook= ImageIO.read(new File("images/WhiteRook.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
		
	}
	public boolean move(Tile t)//checks move is valid
	{
		this.x= t.getX();
		this.y= t.getY();
		return true;
	}
	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BRook,WRook,color);
	}
}
