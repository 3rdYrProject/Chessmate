import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
class King extends Piece
{
	BufferedImage BKing = null;
	BufferedImage WKing = null;
	int color;
	King(int x,int y, int type, int color)
	{
		super(x,y,type);
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
	public boolean move()
	{
		return true;
	}
	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BKing,WKing,color);
	}
}
