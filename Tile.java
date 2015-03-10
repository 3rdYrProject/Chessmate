
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

class Tile implements Comparable{
	
	BufferedImage dark = null;
	BufferedImage light = null;
	BufferedImage obstacle = null;
	BufferedImage goal = null;
	
	boolean occupied=false;
	int x,y;
	int color; 
	int width=75;
	int g_score=0,f_score=0;
	int type;//0 unmovable, 1 is normal, 3 is goal and 2 is start.
	
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
	int getColor()
	{
		return color;
	}
	void setG(int g)
	{
		this.g_score= g;
	}
	void setF(int f)
	{
		this.f_score= f;
	}	
	int getG()
	{
		return g_score;
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
            ClassLoader classLoader = getClass().getClassLoader();
            File darkF = new File(classLoader.getResource("dark.png").getFile());
            File lightF = new File(classLoader.getResource("light.png").getFile());
            File obstacleF = new File(classLoader.getResource("tree.png").getFile());
            File goalF = new File(classLoader.getResource("goal.png").getFile());
			dark = ImageIO.read(darkF);
			light = ImageIO.read(lightF);
			obstacle = ImageIO.read(obstacleF);
			goal = ImageIO.read(goalF);
		} catch (IOException e) {
		}
	}
	int checkDiag(Tile goal){//checks to see if move is diagonal
		int direction = 0;//down left is 1, up left is 2, up right is 3, down right is 4
					
		int xPos = Math.abs(this.x - goal.getX());
		int yPos = Math.abs(this.y - goal.getY());
		if(xPos == yPos){
			if(goal.getX() < this.x && goal.getY() > this.y){//down left
				direction = 5;//direction = 1;
			}
			else if(goal.getX() < this.x && goal.getY() < this.y){//up left
				direction = 6;//direction = 2;
			}
			else if(goal.getX() > this.x && goal.getY() < this.y){//up right
				direction = 7;//direction = 3;
			}
			else if(goal.getX() > this.x && goal.getY() > this.y){//down right
				direction = 8;//direction = 4;
			}
		}
		return direction;
	}
	public int checkOrth(Tile goal){//checks to see if move is on a vertice
	
		int direction = 0;//down is 1, up is 2, right is 3, left is 4]
		if(goal.getX() == (this.x) && goal.getY()>(this.y)){//down 
			direction = 1;
		}
		else if(goal.getX() == (this.x) && goal.getY()<(this.y)){//up
			direction = 2;
		}
		else if(goal.getX()>(this.x) && goal.getY() == (this.y)){//right
			direction = 3;
		}
		else if(goal.getX()<(this.x) && goal.getY() == (this.y)){//left
			direction = 4;
		}
		
		return direction;
	}
	public Tile checkRoute(Tile goal, int direction, Tile[][] tiles){//needs to be generic
			if(direction==0)
				return tiles[x][y];
			int distance = 0;
			//System.out.println(direction);
			distance = Math.abs(this.x - goal.getX())+ Math.abs(this.y - goal.getY());
			int inc = 1;
			//down up right left
			//System.out.println(direction+ " " +distance);
			while(inc <= distance){
				if(direction == 1){
					if(tiles[this.x][this.y + inc].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x][this.y + inc].getOccupied())
						return tiles[this.x][this.y + inc];
				}
				else if(direction == 2){
					if(tiles[this.x][this.y - inc].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x][this.y - inc].getOccupied())
						return tiles[this.x][this.y - inc];
				}
				else if(direction == 3){
					if(tiles[this.x + inc][this.y].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x + inc][this.y].getOccupied())
						return tiles[this.x + inc][this.y];
				}
				else if(direction == 4)
				{
					if(tiles[this.x - inc][this.y].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x - inc][this.y].getOccupied())
						return tiles[this.x - inc][this.y];
				}
				inc++;
			}
			return goal;
		}
		
		public Tile checkRouteDiag(Tile goal, int direction, Tile[][] tiles){
			if(direction==0)
				return tiles[x][y];
			int distance = 0;
			distance = Math.abs(this.x - goal.getX());
			int inc = 1;
			while(inc <= distance){
				if(direction == 5){
					if(tiles[this.x - inc][this.y + inc].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x - inc][this.y + inc].getOccupied())
						return tiles[this.x - inc][this.y + inc];
				}
				else if(direction == 6){
					if(tiles[this.x - inc][this.y - inc].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x - inc][this.y - inc].getOccupied())
						return tiles[this.x - inc][this.y - inc];
				}
				else if(direction == 7){
					if(tiles[this.x + inc][this.y - inc].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x + inc][this.y - inc].getOccupied())
						return tiles[this.x + inc][this.y - inc];
				}
				else if(direction == 8){
					if(tiles[this.x + inc][this.y + inc].getType()==0)
						return tiles[x][y];
					else if(tiles[this.x + inc][this.y + inc].getOccupied())
						return tiles[this.x + inc][this.y + inc];
				}
				inc++;
			}
			return goal;
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
		return("("+x+ "," +y+")");
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
		g.drawString(""+f_score,x*width,(y*width)+10);
	}
}

