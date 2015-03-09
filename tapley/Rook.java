package com.example.araic.tapley;

/**
 * Created by araic on 05/03/15.
 */

class Rook extends Piece
{
    Rook(int x, int y, int type, int color)
    {
        super(x,y,type,"Rook",color);
        this.color=color;
    }

    public Tile[][] move(Tile t, Tile[][] tiles,AI ai)
    {
        if(t==null)
            return tiles;
        if(t.getType()==0)
            return tiles;
        int dir = checkOrth(t);
        Tile temp;
        //p("dir: " + dir);
        if(dir > 0){//if on a direction allow move
            //method to create a list of tiles between current and goal

            //need to check each square in route to see if AI piece or obstacle
            temp = checkRoute(t,dir,tiles);
            //System.out.println("in rook move " + temp);

            if(temp!=null)
            {
                tiles=(super.move(t,tiles,ai));
            }
        }
        return tiles;
    }
    void p(String s) {
        System.out.println(s);
    }

    public int checkOrth(Tile goal){
        //System.out.println("in orth: " + goal);
        return super.checkOrth(goal);
    }

    public Tile checkRoute(Tile goal, int direction, Tile[][] tiles){
        return super.checkRoute(goal, direction, tiles);
    }
}
