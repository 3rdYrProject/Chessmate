import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Tile{
	
	BufferedImage dark = null;
	BufferedImage light = null;
	BufferedImage obstacle = null;
	BufferedImage goal = null;
	boolean occupied=false;
	int x,y;
	int width=75;
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
	public boolean equals(Tile t)
	{
		return(this.x==t.x && this.y==t.y);
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