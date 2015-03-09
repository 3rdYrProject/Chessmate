package com.example.araic.tapley;

/**
 * Created by araic on 05/03/15.
 */

class Queen extends Piece
{
    int color;
    Queen(int x,int y, int type, int color)
    {
        super(x,y,type,"Queen",color);
        this.color= color;
    }
    public Tile[][] move(Tile t, Tile[][] tiles, AI ai)
    {
        if(t==null)
            return null;
        if(t.getType()==0)
            return null;
        int diag = checkDirQ(t);
        Tile temp;
        if(diag > 0){//if on a diagonal allow move
            temp= checkRouteQ(t,diag, tiles);
            if(temp!=null)
            {
                tiles = (super.move(t,tiles,ai));
            }
        }
        else
            return null;
        return tiles;
    }
    int checkDirQ(Tile goal){
        if(super.checkDiag(goal)==0)
            return super.checkOrth(goal);
        else return (super.checkDiag(goal));
    }

    Tile checkRouteQ(Tile goal, int direction, Tile[][] tiles){
        if(direction > 4)
            return super.checkRouteDiag(goal, direction, tiles);
        else
            return super.checkRoute(goal, direction, tiles);
    }
}