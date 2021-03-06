import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Queen extends Piece
{
	BufferedImage BQueen = null;
	BufferedImage WQueen = null;
	int color;
	Queen(int x,int y, int type, int color)
	{
		super(x,y,type,"Queen",color);
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
	public Tile[][] move(Tile t, Tile[][] tiles, AI ai)
	{
		if(t==null)
			return null;
		if(t.getType()==0)
			return null;
		int diag = checkDirQ(t);
		Tile temp= null;
		if(diag > 0){//if on a diagonal allow move
			temp= checkRouteQ(t,diag, tiles);
			if(temp!=null)
			{
				tiles = (super.move(t,tiles,ai));
			}
		}
		else
			return null;
		return tiles;
	}
	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BQueen,WQueen,color);
	}
	
	int checkDirQ(Tile goal){
		if(super.checkDiag(goal)==0)
			return super.checkOrth(goal);
		else return (super.checkDiag(goal));
	}
	
	Tile checkRouteQ(Tile goal, int direction, Tile[][] tiles){
		if(direction > 4)
			return super.checkRouteDiag(goal, direction, tiles);
		else
			return super.checkRoute(goal, direction, tiles);
	}
}
