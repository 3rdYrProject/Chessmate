package com.example.araic.tapley;

/**
 * Created by araic on 05/03/15.
 */

class King extends Piece
{
    int color;
    King(int x,int y, int type, int color)
    {
        super(x,y,type,"King",color);
        this.color= color;
    }
    public Tile[][] move(Tile t, Tile[][] tiles)
    {
        return null;
    }
}