package org.Chessmate

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Pawn extends Piece
{
	BufferedImage BPawn = null;
	BufferedImage WPawn = null;
	int color;
	Pawn(int x,int y, int type, int color)
	{
		super(x,y,type,"Pawn",color);
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
	public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)
	{
		return null;
	}
	public void draw(Graphics g, int i)
	{
		super.draw(g,i,BPawn,WPawn,color);
	}
}
