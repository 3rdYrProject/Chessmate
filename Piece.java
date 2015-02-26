import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

abstract class Piece extends Tile{
		
		String name;
		
		Piece(int x, int y, int type, String name, int color)
		{
			super(x,y,type);
			occupied=true;
			this.color=color;
			this.name=name;
		}
		String getName()
		{
			return name;
		}
		public void changePos(Tile t)
		{
			x=t.x;
			y=t.y;
		}
		public Tile[][] move(Tile t, Tile[][] tiles,AI ai)
		{
			//need to check if we reached the goal or took a piece
			//System.out.println("TYPE: "+t.getType());
			if(t.getType()==3)
			{
				JOptionPane.showMessageDialog(null,"You completed the level.");
				tiles[t.x][t.y]= tiles[this.x][this.y];
				tiles[this.x][this.y] = new Tile(this.x,this.y,1);
			}
			else if(t.getOccupied())
			{
				if(t.color==1)
				{
					JOptionPane.showMessageDialog(null,"Hard luck.");
				}
				else 
				{
					ai.removePiece(t);
				}
				tiles[t.x][t.y]= tiles[this.x][this.y];
				tiles[this.x][this.y] = new Tile(this.x,this.y,1);
			}
			else
			{
				Tile temp = tiles[this.x][this.y];
				tiles[this.x][this.y] = tiles[t.x][t.y];
				tiles[t.x][t.y]= temp;
			}
			int tempX=this.x, tempY=this.y;
			this.x=t.x;
			t.x=tempX;
			this.y=t.y;
			t.y=tempY;
			
			return tiles;
		}
		//can probably use checkRoute to check how far we 
		
		public void draw(Graphics g, int i, BufferedImage BPiece, BufferedImage WPiece, int color)
		{
			super.draw(g,i);
			if(color==0)
				g.drawImage(BPiece,x*width,y*width,null);
			else 
				g.drawImage(WPiece,x*width,y*width,null);
		}
		public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)//rook
		{
			if(direction==0||direction>4)
				return(new LinkedList<Tile>());
			int tempX=x;
			int tempY=y;
			LinkedList<Tile> moves = new LinkedList<>();
			boolean occupied= false;
			boolean outOfBounds=false;
			while(true)
			{				
						//down left, up left, up right, down right
				if(direction==1)//down,up,right,left
				{
					if(this.getName().equals("Rook"))
						tempY+=1;
					else if(this.getName().equals("Bishop")){
						tempX-=1;
						tempY+=1; 
					}
				}
				else if(direction==2)
				{
					if(this.getName().equals("Rook"))
						tempY-=1;
					else if(this.getName().equals("Bishop")){
						tempX-=1;
						tempY-=1; 
					}
				}
				else if(direction==3)
				{
					if(this.getName().equals("Rook"))
						tempX+=1;
					else if(this.getName().equals("Bishop")){
						tempX+=1;
						tempY-=1; 
					}
				}
				else if(direction==4)
				{
					if(this.getName().equals("Rook"))
						tempX-=1;
					else if(this.getName().equals("Bishop")){
						tempX+=1;
						tempY+=1; 
					}
				}
				outOfBounds=tempY>=tiles.length||tempX>=tiles[0].length||tempY<0||tempX<0;
				if(!outOfBounds)
					occupied= tiles[tempX][tempY].getOccupied();
				
				if(outOfBounds||occupied||(tiles[tempX][tempY].getType()==0)||tiles[tempX][tempY].getType()==3)
				{
					//System.out.println(tempX+ " "+tempY );//tiles[tempX][tempY].getOccupied());
					if(occupied)
					{
						moves.add(tiles[tempX][tempY]);
					}
					moves.addAll(getMoves(tiles,(direction+1)));
					return moves;
				}
				
				moves.add(tiles[tempX][tempY]);
			}
		}
		
	}
