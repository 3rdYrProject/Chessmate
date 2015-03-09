package com.example.araic.tapley;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by araic on 02/03/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int[][] array;
    Piece userPiece = null;
    Tile tiles[][] = new Tile[8][8];
    AI ai = new AI();
    Tile goal;

    Piece getPiece(){
        return userPiece;
    }

    Tile[][] getTiles(){
        return tiles;
    }

    AI getAi(){
        return ai;
    }

    public ImageAdapter(Context c,int[][] array) {
        mContext = c;
        this.array = array;
    }

    public int getCount() {
        return squares.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    private Integer[] squares = new Integer[64];
    int count = 0;

    public void setMap(){//sets tiles for imageView

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                count %= 2;
                squares[(i*8)+j] = getImage(i,j,count);
                int temp = array[i][j];
                if(temp>8) {//userPiece
                    userPiece = getPiece(i, j, temp, 0);
                    //System.out.println("setting userPiece");
                    tiles[i][j] = userPiece;
                    ai.addUser(userPiece);
                }
                else if(temp>3){
                    tiles[i][j] = getPiece(i,j,temp,1);
                    ai.addPiece((Piece)tiles[i][j]);
                }
                else{
                    if(temp==3){
                        goal = new Tile(i,j,temp);
                        ai.addGoal(goal);
                    }
                    tiles[i][j] = new Tile(i, j, temp);
                }
                //System.out.println("x: " + tiles[i][j].getX() + "y: " + tiles[i][j].getY() + "type: " + tiles[i][j].getType());
                count++;
            }
            count++;
        }
    }

    public Piece getPiece(int x, int y, int i, int color)
    {
        //i is greater than 3.
        switch(i)
        {
            case(4):return(new Rook(x,y,4,color));
            case(5):return(new Knight(x,y,5,color));
            case(6):return(new Bishop(x,y,6,color));
            case(7):return(new Queen(x,y,7,color));
            case(8):return(new King(x,y,8,color));
            case(9):return(new Rook(x,y,9,color));
            case(10):return(new Knight(x,y,10,color));
            case(11):return(new Bishop(x,y,11,color));
            case(12):return(new Queen(x,y,12,color));
            case(13):return(new King(x,y,13,color));
        }
        return null;
    }

    public Integer getImage(int x, int y, int count){

        //trying to read array from xml file because i think it's better practice??
        //Resources r = mContext.getResources();
        //int[] array = r.getIntArray(R.array.bits);
        if(count == 0) {
            if (array[x][y] == 1) {
                return R.drawable.darktile;
            }
            else if(array[x][y] == 3){
                return R.drawable.darkgoal;
            }
            else if(array[x][y] == 4) {//only ai tiles9
                return R.drawable.darkrook;
            }
            else if(array[x][y] == 5) {//only ai tiles
                return R.drawable.darkknight;
            }
            else if(array[x][y] == 6) {//only ai tiles
                return R.drawable.darkbishop;
            }
            else if(array[x][y] == 7) {//only ai tiles
                return R.drawable.darkqueen;
            }
            else if(array[x][y] == 8) {//only ai tiles
                return R.drawable.darkking;
            }
            else if(array[x][y] == 9) {
                //userPiece = new Rook(x,y,9,1);
                return R.drawable.usdarkrook;
            }
            else if(array[x][y] == 10) {
                return R.drawable.usdarkknight;
            }
            else if(array[x][y] == 11) {
                return R.drawable.usdarkbishop;
            }
            else if(array[x][y] == 12) {
                return R.drawable.usdarkqueen;
            }
            else if(array[x][y] == 13) {
                return R.drawable.usdarkking;
            }
            else {
                return R.drawable.darktree;
            }
        }
        else{
            if(array[x][y] == 1) {
                return R.drawable.lighttile;
            }
            else if(array[x][y] == 3){
                return R.drawable.lightgoal;
            }
            else if(array[x][y]==4){//only ai tiles
                return R.drawable.lightrook;
            }
            else if(array[x][y]==5){//only ai tiles
                return R.drawable.lightknight;
            }
            else if(array[x][y]==6){//only ai tiles
                return R.drawable.lightbishop;
            }
            else if(array[x][y]==7){//only ai tiles
                return R.drawable.lightqueen;
            }
            else if(array[x][y]==8){//only ai tiles
                return R.drawable.lightking;
            }
            else if(array[x][y]==9){
                //userPiece = new Rook(x,y,9,1);
                return R.drawable.uslightrook;
            }
            else if(array[x][y]==10){
                return R.drawable.uslightknight;
            }
            else if(array[x][y]==11){
                return R.drawable.uslightbishop;
            }
            else if(array[x][y]==12){
                return R.drawable.uslightqueen;
            }
            else if(array[x][y]==13){
                return R.drawable.uslightking;
            }
            else {

                return R.drawable.lighttree;
            }
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(140, 140));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        setMap();

        imageView.setImageResource(squares[position]);

        return imageView;
    }
}
