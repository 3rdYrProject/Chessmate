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
	
	Rook(int x, int y, int type)
	{
		super(x,y,type);
		try
		{
			BRook= ImageIO.read(new File("BlackRook.png"));
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
		g.drawImage(BRook,x*width,y*width,null);
	}
}