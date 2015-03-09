package com.example.araic.tapley;

/**
 * Created by araic on 04/03/15.
 */
import java.util.*;

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
        //System.out.println("In here");
        //System.out.println("TYPE: "+t.getType()+" Color: " + t.getColor());
        if(t.getType()==3)
        {
            System.out.println("Got to goal");
            //--------JOptionPane.showMessageDialog(null,"You completed the level.");
            tiles[t.x][t.y]= tiles[this.x][this.y];
            tiles[this.x][this.y] = new Tile(this.x,this.y,1);
        }
        else if(t.getOccupied())
        {
            if(t.color==1)
            {
                System.out.println("caught user");
                //-------JOptionPane.showMessageDialog(null,"Hard luck.");
            }
            else if(t.color!=this.color)
            {
                ai.removePiece(t);
            }
            tiles[t.x][t.y]= tiles[this.x][this.y];
            tiles[this.x][this.y] = new Tile(this.x,this.y,1);
        }
        else
        {
            //System.out.println("changing tiles");
            Tile temp = tiles[this.x][this.y];
            tiles[this.x][this.y] = tiles[t.x][t.y];
            tiles[t.x][t.y]= temp;
        }
        int tempX=this.x, tempY=this.y;
        this.x=t.x;
        t.x=tempX;
        this.y=t.y;
        t.y=tempY;
        //System.out.println("This.x: " + this.x + " This.y: " + this.y + " t.x: " + t.x + " t.y " + t.y);
        return tiles;
    }
    //can probably use checkRoute to check how far we

    public boolean checkRouteK(int x, int y){
        return false;
    }
    public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)
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
                tempY+=1;
                if(name.equals("Bishop"))
                {
                    tempX-=1;
                }
            }
            else if(direction==2)
            {
                tempY-=1;
                if(name.equals("Bishop"))
                {
                    tempX-=1;
                }
            }
            else if(direction==3)
            {
                tempX+=1;
                if(name.equals("Bishop"))
                {
                    tempY-=1;
                }
            }
            else if(direction==4)
            {
                tempX-=1;
                if(name.equals("Bishop"))
                {
                    tempX+=2;
                    tempY+=1;
                }
            }
            outOfBounds=tempY>=tiles.length||tempX>=tiles[0].length||tempY<0||tempX<0;
            if(!outOfBounds)
                occupied= tiles[tempX][tempY].getOccupied();

            if(outOfBounds||occupied||(tiles[tempX][tempY].getType()==0)||tiles[tempX][tempY].getType()==3)
            {

                moves.addAll(getMoves(tiles,(direction+1)));
                return moves;
            }

            moves.add(tiles[tempX][tempY]);
        }
    }
}