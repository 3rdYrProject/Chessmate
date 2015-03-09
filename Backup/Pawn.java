import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
class Pawn extends Piece
{
	BufferedImage BPawn = null;
	BufferedImage WPawn = null;
	int color;
	Pawn(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BPawn= ImageIO.read(new File("images/BlackPawn.png"));
			else 
				WPawn= ImageIO.read(new File("images/WhitePawn.png"));
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
		super.draw(g,i,BPawn,WPawn,color);
	}
}
