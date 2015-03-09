package com.example.araic.tapley;

/**
 * Created by araic on 05/03/15.
 */
class Bishop extends Piece
{
    int color;
    Bishop(int x,int y, int type, int color)
    {
        super(x,y,type,"Bishop",color);
        this.color= color;
    }
    public Tile[][] move(Tile t, Tile[][] tiles, AI ai)
    {
        if(t.getType()==0)
            return tiles;
        int diag = checkDiag(t);
        Tile temp= null;
        if(diag > 0){//if on a diagonal allow move
            temp= checkRouteDiag(t,diag,tiles);
            if(temp!=null)
            {
                return(super.move(t,tiles,ai));
            }
        }
        return tiles;
    }
    int checkDiag(Tile goal){
        return super.checkDiag(goal);
    }

    public Tile checkRouteDiag(Tile goal, int direction, Tile[][] tiles){
        return super.checkRouteDiag(goal, direction, tiles);
    }
}