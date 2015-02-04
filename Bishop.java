import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
class Bishop extends Piece
{
	BufferedImage BBishop = null;
	BufferedImage WBishop = null;
	int color;
	Bishop(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BBishop= ImageIO.read(new File("images/BlackBishop.png"));
			else 
				WBishop= ImageIO.read(new File("images/WhiteBishop.png"));
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
		super.draw(g,i,BBishop,WBishop,color);
	}
}
