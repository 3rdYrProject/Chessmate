import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ChessMate 
{
	public static void main(String[] args)
	{
		JFrame f= new JFrame();
		f.add(new Board());
		f.setVisible(true);
		f.setSize(450,450);
	}
}
class Board extends JPanel implements MouseListener
{
	Tile[][] tiles = new Tile[8][8];//the entire board.
	
	Board()
	{
		readLevel();
	}	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		//g.drawRect(0,0,400,400);//----------------------hardcode
		int count=0;
		for(int i=0;i<tiles.length;i++)
		{
			for(int j=0;j<tiles[i].length;j++)
			{
				count%=2;
				tiles[i][j].draw(g,count);
				count++;
			}
			count++;
		}
	}
	void readLevel()
	{
		Scanner sc= null;
		try{
		sc = new Scanner(new File("level.txt"));
		}catch(FileNotFoundException e){}
		for(int i=0;sc.hasNextLine();i++)
		{
			for(int j=0;j<tiles[i].length;j++)
			{
				tiles[j][i] = new Tile(j,i,sc.nextInt());
			}
		}
		//reads in a map for the current level. 
	}
	
	//various listener methods.
	public void mousePressed(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
}	
class Tile{
	
	//image dark
	//image bright
	int x,y;
	int width;
	int type;//0 unmovable, 1 is normal, 2 is goal and 3 is start.
	Tile(int x, int y, int type)
	{
		this.x=x;
		this.y=y;
		this.type=type;
		width=50;
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
				g.setColor(new Color(0,128,0));
				g.fillRect(x*width,y*width,width,width);
				//g.drawImage(x,y,dark);
			}
			else 
			{
				g.setColor(new Color(181,230,30));
				g.fillRect(x*width,y*width,width,width);
				//g.drawImage(x,y,bright);
			}
		}
	}
}