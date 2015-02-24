import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Rook extends Piece
{
	BufferedImage BRook = null;
	BufferedImage WRook = null;
	
	Rook(int x, int y, int type, int color)
	{
		super(x,y,type,"Rook",color);
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
	
	public Tile[][] move(Tile t, Tile[][] tiles,AI ai)
	{
		if(t==null)
			return null;
		if(t.getType()==0)
			return null;
		int dir = checkOrth(t);
		Tile temp = null;
		if(dir > 0){//if on a direction allow move
			//method to create a list of tiles between current and goal
			
			//need to check each square in route to see if AI piece or obstacle
			temp = checkRoute(t,dir,tiles);
			
			if(temp!=null)
			{
				tiles=(super.move(t,tiles,ai));
			}
		}
		else 
			return null;
		return tiles;	
	}
	
	public int checkOrth(Tile goal){
		return super.checkOrth(goal);
	}
	
	public Tile checkRoute(Tile goal, int direction, Tile[][] tiles){
		return super.checkRoute(goal, direction, tiles);
	}
	
	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BRook,WRook,color);
	}
}
	