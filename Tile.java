import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

class Tile{
	
	BufferedImage dark = null;//image dark
	BufferedImage light = null;//image bright
	
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
	public void initImages(){
		try {
			dark = ImageIO.read(new File("images/Dark.png"));
			light = ImageIO.read(new File("images/Light.png"));
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
		if(type==0)//not moveable
		{
			g.setColor(new Color(238,238,238));
			g.fillRect(x*width+1,y*width+1,width,width);
		}
		else if(type==1)//regular tiles
		{
			if(i==0)
			{
				g.drawImage(dark,x*width,y*width,null);//g.drawImage(x,y,dark);
			}
			else 
			{
				g.drawImage(light,x*width,y*width,null);//g.drawImage(x,y,bright);
			}
		}
	}
}
