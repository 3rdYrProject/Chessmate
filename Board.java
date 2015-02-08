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

		for(int i=0;sc.hasNextInt();i++)
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
		
		moveLoc = userPiece.move(moveLoc);
		if(moveLoc!=null)
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
				g.drawImage(dark,x*width,y*width,null);//g.drawImage(x,y,dark);
			}
			else 
			{
				g.drawImage(light,x*width,y*width,null);//g.drawImage(x,y,bright);
			}
			if(type==0)//not moveable
			{
				g.drawImage(obstacle,x*width,y*width,null);
			}
		}
	}
	abstract class Piece extends Tile{
		
		LinkedList<Tile> route = new LinkedList<>();
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
		public abstract Tile move(Tile t);
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
		public Tile move(Tile t)
		{
			
			int dir = checkDir(t);
			Tile temp = null;
			if(dir > 0){//if on a direction allow move
				//method to create a list of tiles between current and goal
				
				//need to check each square in route to see if AI piece or obstacle
				temp = checkRoute(t,dir);
				if(temp.equals(t)){						
					this.x= t.getX();
					this.y= t.getY();
				}
				else if(temp!=null)
				{
					this.x= temp.getX();
					this.y= temp.getY();
				}
			}
			return temp;	
		}
		int checkDir(Tile goal){//checks to see if move is on a vertice
		
			//1 1 1
			//1 2 1
			//1 1 1
			int direction = 0;//down is 1, up is 2, right is 3, left is 4
			
			if(goal.getX() == (this.x) && goal.getY()>(this.y)){//down 
				System.out.println("down");	
				direction = 1;
			}
			else if(goal.getX() == (this.x) && goal.getY()<(this.y)){//up
				System.out.println("up");	
				direction = 2;
			}
			else if(goal.getX()>(this.x) && goal.getY() == (this.y)){//right
				System.out.println("right");	
				direction = 3;
			}
			else if(goal.getX()<(this.x) && goal.getY() == (this.y)){//left
				System.out.println("left");	
				direction = 4;
			}
			
			System.out.println("Direction " + direction);
			return direction;
		}
		public Tile checkRoute(Tile goal, int direction){//needs to be generic
			int distance = 0;
			route.clear();
			distance = Math.abs(this.x - goal.getX())+ Math.abs(this.y - goal.getY());
			int inc = 0;
			//down up right left
			System.out.println(direction+ " " +distance);
			while(distance >0){
				if(direction == 1){
					if(tiles[this.x][this.y + distance].getType()==0)
						return null;
					else if(tiles[this.x][this.y + distance].getOccupied())
						return tiles[this.x][this.y + distance];
				}
				else if(direction == 2){
					if(tiles[this.x][this.y - distance].getType()==0)
						return null;
					else if(tiles[this.x][this.y - distance].getOccupied())
						return tiles[this.x][this.y - distance];
				}
				else if(direction == 3){
					if(tiles[this.x + distance][this.y].getType()==0)
						return null;
					else if(tiles[this.x + distance][this.y].getOccupied())
						return tiles[this.x + distance][this.y];
				}
				else if(direction == 4){
					if(tiles[this.x - distance][this.y].getType()==0)
						return null;
					else if(tiles[this.x - distance][this.y].getOccupied())
						return tiles[this.x - distance][this.y];
				}
				distance--;
				inc++;
			}
			return goal;
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
		public Tile move(Tile t)
		{
			return null;
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
		public Tile move(Tile t)//once the destination tile is valid the knight can move there regardless.
		{
			int tempX = t.getX();
			int tempY = t.getY();
			
			if(t.getType()!=1)
				return null;
			if(checkRoute(tempX,tempY))
			{	
				this.x= t.getX();
				this.y= t.getY();
				return t;
			}
			return null;
		}
		public boolean checkRoute(int tempX, int tempY)
		{
			int xNum= 2, yNum=1;
			for(int i=0; i<2; i++)//8 possible positions for a knight to move.
			{
				if(x-xNum==tempX&&y-yNum==tempY)
					return true;
				else if(x-xNum==tempX&&y+yNum==tempY)
					return true;
				else if(x+xNum==tempX&&y-yNum==tempY)
					return true;
				else if(x+xNum==tempX&&y+yNum==tempY)
					return true;
				
				xNum=1;
				yNum=2;
			}
			return false;
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
		
		int checkDiag(Tile goal){//checks to see if move is diagonal
			int direction = 0;//down left is 1, up left is 2, up right is 3, down right is 4
			for(int n = 1; n < 9; n++){
				if(goal.getX() == (this.x - n) && goal.getY() == (this.y + n)){//down left
					System.out.println("down left");	
					direction = 1;
				}
				else if(goal.getX() == (this.x - n) && goal.getY() == (this.y - n)){//up left
					System.out.println("up left");	
					direction = 2;
				}
				else if(goal.getX() == (this.x + n) && goal.getY() == (this.y - n)){//up right
					System.out.println("up right");	
					direction = 3;
				}
				else if(goal.getX() == (this.x + n) && goal.getY() == (this.y + n)){//down right
					System.out.println("down right");	
					direction = 4;
				}
			}
			System.out.println("Direction " + direction);
			return direction;
		}

		public Tile move(Tile t)
		{
			
			int diag = checkDiag(t);
			
			if(diag > 0){//if on a diagonal allow move
				//method to create a list of tiles between current and goal
				//need to check each square in route to see if AI piece or obstacle
				
				if(checkRoute(t,diag)==t){						
					this.x= t.getX();
					this.y= t.getY();
					return t;
				}
			}
			return null;	
		}
		
		public Tile checkRoute(Tile goal, int direction){
			int distance = 0;
			route.clear();
			distance = Math.abs(this.x - goal.getX());
			int inc = 0;
			
			while(distance >0){
				if(direction == 1){
					if(tiles[this.x - distance][this.y + distance].getType()==0)
						return null;
				}
				else if(direction == 2){
					if(tiles[this.x - distance][this.y - distance].getType()==0)
						return null;
				}
				else if(direction == 3){
					if(tiles[this.x + distance][this.y - distance].getType()==0)
						return null;
				}
				else if(direction == 4){
					if(tiles[this.x + distance][this.y + distance].getType()==0)
						return null;
				}
				distance--;
				inc++;
			}
			return goal;
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
		public Tile move(Tile t)
		{
			return null;
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
		public Tile move(Tile t)
		{
			return null;
		}
		public void draw(Graphics g, int i)
		{
			super.draw(g,i,BKing,WKing,color);
		}
	}
}	