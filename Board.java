import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
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
		
		BufferedImage dark = null;
		BufferedImage light = null;
		BufferedImage obstacle = null;
		
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
				obstacle = ImageIO.read(new File("images/ground.png"));
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
				g.drawImage(obstacle,x*width,y*width,null);
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
		
		public abstract boolean move(Tile t);
	}
	
	class Rook extends Piece
	{
		BufferedImage BRook = null;
		BufferedImage WRook = null;
		int color; 
		Rook(int x, int y, int type, int color)
		{
			super(x,y,type);
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
		public boolean move(Tile t)//checks move is valid
		{
			
			//valid.
			this.x= t.getX();
			this.y= t.getY();
			return true;
		}
		public void draw(Graphics g, int i)
		{
			super.draw(g,i,BRook,WRook,color);
		}
	}
	
	class Pawn extends Piece
	{
		BufferedImage BPawn = null;
		BufferedImage WPawn = null;
		int color;
		Pawn(int x,int y, int type, int color)
		{
			super(x,y,type);
			this.color= color;
			try
			{
				if(color==0)
				
					BPawn= ImageIO.read(new File("images/BlackPawn.png"));
				else 
					WPawn= ImageIO.read(new File("images/WhitePawn.png"));
			}
			catch(FileNotFoundException e){}
			catch(IOException e){}
		}
		public boolean move(Tile t)
		{
			return true;
		}
		public void draw(Graphics g, int i)
		{
			super.draw(g,i,BPawn,WPawn,color);
		}
	}
		
	class Knight extends Piece
	{
		BufferedImage BKnight = null;
		BufferedImage WKnight = null;
		int color;
		Knight(int x,int y, int type, int color)
		{
			super(x,y,type);
			this.color= color;
			try
			{
				if(color==0)
				
					BKnight= ImageIO.read(new File("images/BlackKnight.png"));
				else 
					WKnight= ImageIO.read(new File("images/WhiteKnight.png"));
			}
			catch(FileNotFoundException e){}
			catch(IOException e){}
		}
		public boolean move(Tile t)
		{
			return true;
		}
		public void draw(Graphics g, int i)
		{
			super.draw(g,i,BKnight,WKnight,color);
		}
	}	
	class Bishop extends Piece
	{
		BufferedImage BBishop = null;
		BufferedImage WBishop = null;
		int color;
		Bishop(int x,int y, int type, int color)
		{
			super(x,y,type);
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
		public boolean move(Tile t)
		{
			return true;
		}
		public void draw(Graphics g, int i)
		{
			super.draw(g,i,BBishop,WBishop,color);
		}
	}
	class Queen extends Piece
	{
		BufferedImage BQueen = null;
		BufferedImage WQueen = null;
		int color;
		Queen(int x,int y, int type, int color)
		{
			super(x,y,type);
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
		public boolean move(Tile t)
		{
			return true;
		}
		public void draw(Graphics g, int i)
		{
			super.draw(g,i,BQueen,WQueen,color);
		}
	}
	class King extends Piece
	{
		BufferedImage BKing = null;
		BufferedImage WKing = null;
		int color;
		King(int x,int y, int type, int color)
		{
			super(x,y,type);
			this.color= color;
			try
			{
				if(color==0)
				
					BKing= ImageIO.read(new File("images/BlackKing.png"));
				else 
					WKing= ImageIO.read(new File("images/WhiteKing.png"));
			}
			catch(FileNotFoundException e){}
			catch(IOException e){}
		}
		public boolean move(Tile t)
		{
			return true;
		}
		public void draw(Graphics g, int i)
		{
			super.draw(g,i,BKing,WKing,color);
		}
	}

}	
