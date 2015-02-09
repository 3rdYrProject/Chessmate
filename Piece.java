import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

abstract class Piece extends Tile{
		
		LinkedList<Tile> route = new LinkedList<>();
		Piece(int x, int y, int type)
		{
			super(x,y,type);
			occupied=true;
		}
		public abstract Tile move(Tile t, Tile[][] tiles);
		
		public void draw(Graphics g, int i, BufferedImage BPiece, BufferedImage WPiece, int color)
		{
			super.draw(g,i);
			if(color==0)
				g.drawImage(BPiece,x*width,y*width,null);
			else 
				g.drawImage(WPiece,x*width,y*width,null);
		}
		
		int checkDiag(Tile goal){//checks to see if move is diagonal
			int direction = 0;//down left is 1, up left is 2, up right is 3, down right is 4
						
			int xPos = Math.abs(this.x - goal.getX());
			int yPos = Math.abs(this.y - goal.getY());
			if(xPos == yPos){
				if(goal.getX() < this.x && goal.getY() > this.y){//down left
					direction = 1;
				}
				else if(goal.getX() < this.x && goal.getY() < this.y){//up left
					direction = 2;
				}
				else if(goal.getX() > this.x && goal.getY() < this.y){//up right
					direction = 3;
				}
				else if(goal.getX() > this.x && goal.getY() > this.y){//down right
					direction = 4;
				}
				System.out.println("Direction " + direction);
			}
			return direction;
		}
		public int checkOrth(Tile goal){//checks to see if move is on a vertice
		
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
		public Tile checkRoute(Tile goal, int direction, Tile[][] tiles){//needs to be generic
			int distance = 0;
			route.clear();
			distance = Math.abs(this.x - goal.getX())+ Math.abs(this.y - goal.getY());
			int inc = 1;
			//down up right left
			System.out.println(direction+ " " +distance);
			while(inc <= distance){
				if(direction == 1){
					if(tiles[this.x][this.y + inc].getType()==0)
						return null;
					else if(tiles[this.x][this.y + inc].getOccupied())
						return tiles[this.x][this.y + inc];
				}
				else if(direction == 2){
					if(tiles[this.x][this.y - inc].getType()==0)
						return null;
					else if(tiles[this.x][this.y - inc].getOccupied())
						return tiles[this.x][this.y - inc];
				}
				else if(direction == 3){
					if(tiles[this.x + inc][this.y].getType()==0)
						return null;
					else if(tiles[this.x + inc][this.y].getOccupied())
						return tiles[this.x + inc][this.y];
				}
				else if(direction == 4)
				{
					if(tiles[this.x - inc][this.y].getType()==0)
						return null;
					else if(tiles[this.x - inc][this.y].getOccupied())
						return tiles[this.x - inc][this.y];
				}
				inc++;
			}
			return goal;
		}
		public Tile checkRouteDiag(Tile goal, int direction, Tile[][] tiles){
			int distance = 0;
			distance = Math.abs(this.x - goal.getX());
			int inc = 1;
			while(inc <= distance){
				if(direction == 1){
					if(tiles[this.x - inc][this.y + inc].getType()==0)
						return null;
					else if(tiles[this.x - inc][this.y + inc].getOccupied())
						return tiles[this.x - inc][this.y + inc];
				}
				else if(direction == 2){
					if(tiles[this.x - inc][this.y - inc].getType()==0)
						return null;
					else if(tiles[this.x - inc][this.y - inc].getOccupied())
						return tiles[this.x - inc][this.y - inc];
				}
				else if(direction == 3){
					if(tiles[this.x + inc][this.y - inc].getType()==0)
						return null;
					else if(tiles[this.x + inc][this.y - inc].getOccupied())
						return tiles[this.x + inc][this.y - inc];
				}
				else if(direction == 4){
					if(tiles[this.x + inc][this.y + inc].getType()==0)
						return null;
					else if(tiles[this.x + inc][this.y + inc].getOccupied())
						return tiles[this.x + inc][this.y + inc];
				}
				inc++;
			}
			return goal;
		}
	}
