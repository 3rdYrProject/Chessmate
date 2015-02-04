import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
class Queen extends Piece
{
	BufferedImage BQueen = null;
	BufferedImage WQueen = null;
	int color;
	Queen(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BQueen= ImageIO.read(new File("images/BlackQueen.png"));
			else 
				WQueen= ImageIO.read(new File("images/WhiteQueen.png"));
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
			g.drawImage(BQueen,x*width,y*width,null);
		else 
			g.drawImage(WQueen,x*width,y*width,null);
	}
}
