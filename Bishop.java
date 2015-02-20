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
		super(x,y,type,"Bishop");
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
	public Tile[][] move(Tile t, Tile[][] tiles)
	{
		if(t.getType()==0)
			return null;
		int diag = checkDiag(t);
		Tile temp= null;
		if(diag > 0){//if on a diagonal allow move
			temp= checkRouteDiag(t,diag,tiles);
			if(temp!=null)
			{
				this.x= temp.getX();
				this.y= temp.getY();
				return(super.move(t,tiles));
			}
		}
		return null;	
	}
	public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)
	{
		return null;
	}
	int checkDiag(Tile goal){
		return super.checkDiag(goal);
	}
	
	public Tile checkRouteDiag(Tile goal, int direction, Tile[][] tiles){
		return super.checkRouteDiag(goal, direction, tiles);
	}

	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BBishop,WBishop,color);
	}
}