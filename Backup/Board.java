import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Board extends JPanel implements MouseListener
{
	Tile[][] tiles = new Tile[8][8];//the entire board.
	Piece userPiece= null;
	Board()
	{
		readLevel();
		addMouseListener(this);
	}	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
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
		//reads in a map for the current level. 
		Scanner sc= null;
		try{
			sc = new Scanner(new File("level.txt"));
		}catch(FileNotFoundException e){}
		
		int token= sc.nextInt();//player token.

		for(int i=0;sc.hasNextLine();i++)
		{
			for(int j=0;j<tiles[i].length;j++)
			{
				int temp = sc.nextInt();
				if(temp>3)//AI tile
				{
					tiles[j][i] = getPiece(j,i,temp,0);
				}
				else if(temp==2)//0 is immovable, 1 is regular
				{
					userPiece= getPiece(j,i,token,1);
					tiles[j][i]= userPiece;
				}
				else 
					tiles[j][i] = new Tile(j,i,temp);
			}
		}
		
	}
	
	//various listener methods.
	public Piece getPiece(int x, int y, int i, int color)
	{
		//i is greater than 3. 
		switch(i)
		{
			case(4):return(new Rook(x,y,1,color));
			case(5):return(new Knight(x,y,1,color));
			case(6):return(new Bishop(x,y,1,color));
			case(7):return(new Queen(x,y,1,color));
			case(8):return(new King(x,y,1,color));
		}
		return null;
	}
	public void mousePressed(MouseEvent e)
	{
		Tile moveLoc = null;
		outer: for(int i=0;i<tiles.length;i++)
		{
			for(int j=0;j<tiles[i].length;j++)
			{
				moveLoc = tiles[j][i].check(e);
				
				if(moveLoc!=null)
				{
					
					System.out.println("x:"+j+ " y:" + i);
					break outer;
				}
			}
		}
		Tile temp= new Tile(userPiece);
		LinkedList<Tile> route = new LinkedList<>(); 
		if(userPiece.move(moveLoc))
		{
			System.out.println(temp.getX()+ " "+temp.getY());
			tiles[temp.getX()][temp.getY()]= new Tile(temp.getX(),temp.getY(),temp.getType());
			tiles[moveLoc.getX()][moveLoc.getY()]= userPiece;
			repaint();
		}	
	}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	
	class Tile{
		
		BufferedImage dark = null;//image dark
		BufferedImage light = null;//image bright
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
	abstract class Piece extends Tile{
		
		private LinkedList<Tile> moves = new LinkedList<Tile>();
		Piece(int x, int y, int type)
		{
			super(x,y,type);
			occupied=true;
		}
		
		public void draw(Graphics g, int i, BufferedImage BPiece, BufferedImage WPiece, int color)
		{
			super.draw(g,i);
			if(color==0)
				g.drawImage(BPiece,x*width,y*width,null);
			else 
				g.drawImage(WPiece,x*width,y*width,null);
		}
		
		public abstract boolean move(LinkedList<Tile> route);
	}
}	