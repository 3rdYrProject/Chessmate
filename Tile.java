import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Tile implements Comparable{
	
	BufferedImage dark = null;
	BufferedImage light = null;
	BufferedImage obstacle = null;
	BufferedImage goal = null;
	
	boolean occupied=false;
	int x,y;
	int width=75;
	int g_score=0,h_score=0,f_score=0;
	int type;//0 unmovable, 1 is normal, 2 is goal and 3 is start.
	
	Tile(int x, int y, int type)
	{
		initImages();
		this.x=x;
		this.y=y;
		this.type=type;
	}
	Tile(Tile t)
	{
		this.x=t.x;
		this.y=t.y;
		this.type=t.type;
	}
	void setG(int g)
	{
		this.g_score= g;
	}
	void setH(int h)
	{
		this.h_score= h;
	}
	void setF(int f)
	{
		this.f_score= f;
	}	
	int getG()
	{
		return g_score;
	}
	int getH()
	{
		return h_score;
	}
	int getF()
	{
		return f_score;
	}
	int getX()
	{
		return x;
	}
	int getY()
	{
		return y;
	}
	int getType()
	{
		return type;
	}
	boolean getOccupied()
	{
		return occupied;
	}
	@Override
	public boolean equals(Object obj)
	{
		Tile t = (Tile)obj;
		return(this.x==t.x && this.y==t.y);
	}
	@Override
	public int compareTo(Object obj)
	{
		Tile t = (Tile)obj;
		return((this.x-t.x)+(this.y-t.y));
	}
	
	public void initImages(){
		try {
			dark = ImageIO.read(new File("images/Dark.png"));
			light = ImageIO.read(new File("images/Light.png"));
			obstacle = ImageIO.read(new File("images/tree.png"));
			goal = ImageIO.read(new File("images/goal.png"));
		} catch (IOException e) {
		}
	}
	
	Tile check(MouseEvent e)
	{
		int tempX=e.getX(), tempY=e.getY();
		if((tempX>=x*width&&tempX<=x*width+width)&&(tempY>=y*width&&tempY<=y*width+width))
		{
			return(this);
		}
		return null;
	}
	
	@Override
	public String toString()
	{
		return(x+ " " +y);
	}
	
	void draw(Graphics g, int i)//i is 0 or 1 depending on black or white
	{	
		if(i==0)
		{
			g.drawImage(dark,x*width,y*width,null);
		}
		else 
		{
			g.drawImage(light,x*width,y*width,null);
		}
		if(type==0)//not moveable
		{
			g.drawImage(obstacle,x*width,y*width,null);
		}
		else if(type==3)
		{
			g.drawImage(goal,x*width,y*width,null);
		}
	}
}

