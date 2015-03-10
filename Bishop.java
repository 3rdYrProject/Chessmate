import java.awt.*;
import java.io.*;
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
		super(x,y,type,"Bishop",color);
		this.color= color;
		try
		{
            ClassLoader classLoader = getClass().getClassLoader();
			if(color==0) {
                File file = new File(classLoader.getResource("drawable/blackbishop.png").getFile());
                BBishop = ImageIO.read(file);
            }
			else {
                File file = new File(classLoader.getResource("drawable/whitebishop.png").getFile());
                WBishop = ImageIO.read(file);
            }
		}
		catch(Exception e){
            e.printStackTrace();
        }
	}
	public Tile[][] move(Tile t, Tile[][] tiles, AI ai)
	{
		if(t.getType()==0)
			return tiles;
		int diag = checkDiag(t);
		Tile temp= null;
		if(diag > 0){//if on a diagonal allow move
			temp= checkRouteDiag(t,diag,tiles);
			if(temp!=null)
			{
				return(super.move(t,tiles,ai));
			}
		}
		return tiles;	
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
