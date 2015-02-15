import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

abstract class Piece extends Tile{
		
		LinkedList<Tile> route = new LinkedList<>();
		String name;
		Piece(int x, int y, int type, String name)
		{
			super(x,y,type);
			occupied=true;
			this.name=name;
		}
		String getName()
		{
			return name;
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
