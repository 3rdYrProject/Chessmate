package com.example.araic.tapley;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return tiles.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    private int[][] array =
            {{1, 1, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 0, 0, 1, 3, 1},
            {1, 1, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 4, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 1, 1, 1},
            {1, 2, 1, 0, 0, 1, 1, 1},
            {1, 1, 1, 0, 0, 1, 1, 1}};
    void print(){
        System.out.println("array length: " + array.length);
    }

    private Integer[] tiles = new Integer[64];
    int count = 0;
    public void setMap(){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                count %= 2;
                tiles[(i*8)+j] = getImage(i,j,count);
                //tiles[i] = R.drawable.sample_20;
                count++;
            }
            count++;
        }
    }

    public Integer getImage(int x, int y, int count){
        //Resources r = mContext.getResources();
        //int[] array = r.getIntArray(R.array.bits);
        if(count == 0) {
            if (array[x][y] == 1) {
                return R.drawable.darktile;
            }
            else if(array[x][y] == 4) {//only ai tiles
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
            else {//if (array[x][y] == 0)
                return R.drawable.darktree;
            }
        }
        else {
            if(array[x][y] == 1) {
                return R.drawable.lighttile;
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
            else { // if (array[x][y] == 0)
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
        //print();
        imageView.setImageResource(tiles[position]);
        return imageView;
    }
}
