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
	public boolean move()
	{
		return true;
	}
	public void draw(Graphics g, int i)
	{
		super.draw(g,i);
		if(color==0)
			g.drawImage(BRook,x*width,y*width,null);
		else 
			g.drawImage(WRook,x*width,y*width,null);
	}
}
