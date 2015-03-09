package com.example.araic.tapley;

/**
 * Created by araic on 05/03/15.
 */

import java.util.*;

class Knight extends Piece
{
    int color;
    Knight(int x,int y, int type, int color)
    {
        super(x,y,type,"Knight",color);
        this.color= color;
    }
    public Tile[][] move(Tile t, Tile[][] tiles,AI ai)//once the destination tile is valid the knight can move there regardless.
    {
        if(t==null)
            return tiles;
        int tempX = t.getX();
        int tempY = t.getY();

        if(t.getType()==0)
            return tiles;
        if(checkRouteK(tempX,tempY))
        {
            tiles =(super.move(t,tiles,ai));
        }
        return tiles;
    }
    @Override
    public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)//direction is here for generics sake
    {
        LinkedList<Tile> moves = new LinkedList<>();
        int[] knightX = { -1, 1, -2, 2, -2, 2, -1, 1 };
        int[] knightY = { -2, -2, -1, -1, 1, 1, 2, 2 };
        for(int i=0; i<knightX.length; i++)//8 possible positions for a knight to move.
        {
            int newX = x+ knightX[i];
            int newY = y+ knightY[i];
            if(newX<0||newX>=tiles[0].length||newY<0||newY>=tiles.length)
                continue;
            moves.add(tiles[newX][newY]);
        }
        System.out.println("KNIGHT: ");
        for(Tile t:moves)
        {
            System.out.println("\t"+t);
        }
        return moves;
    }
    public boolean checkRoute(int tempX, int tempY)
    {
        System.out.println("test");
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
}