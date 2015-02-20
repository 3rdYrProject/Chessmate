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
	AI ai= null;
	Piece userPiece= null;
	Tile goal= null;
	Board()
	{
		ai= new AI();
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
					ai.addPiece((Piece)tiles[j][i]);
					System.out.println(tiles[j][i]);
				}
				else if(temp==2)//0 is immovable, 1 is regular
				{
					userPiece= getPiece(j,i,token,1);
					tiles[j][i]= userPiece;
					ai.addUser(userPiece);
					System.out.println(userPiece);
				}
				else 
				{
					if(temp==3)
					{
						goal= new Tile(j,i,temp);
						ai.addGoal(goal);
						System.out.println(goal);
					}
					tiles[j][i] = new Tile(j,i,temp);
				}
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
					break outer;
				}
			}
		}
		System.out.println("HEY");
		Tile previousLoc= new Tile(userPiece);
		ai.moveUser();
		Tile temp = userPiece.move(moveLoc,tiles);
		if(temp==null)
			return;
		if(temp.getOccupied())
		{
			ai.removePiece(userPiece);
		}
		ai.decision(tiles);
		//ai.evaluatePaths(tiles);
		tiles[previousLoc.getX()][previousLoc.getY()]= new Tile(previousLoc.getX(),previousLoc.getY(),previousLoc.getType());
		tiles[moveLoc.getX()][moveLoc.getY()]= userPiece;
		System.out.println(userPiece);
		LinkedList<Tile> moves = userPiece.getMoves(tiles,1);
		System.out.println("I can move to: ");
		for(Tile t: moves)
			System.out.println("\t"+ t);
		repaint();
	}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
}	
