package com.example.araic.tapley;

import android.content.Context;
import android.content.res.Resources;
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
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    //Resources res = mContext.getResources();
    //int[] mapValues = res.getIntArray(R.array.bits);

    private Integer[] mThumbIds = new Integer[64];
    public void setThumbs(){
        for(int i = 0; i < 64; i=i+2){
            mThumbIds[i] = R.drawable.sample_20;
            mThumbIds[i+1] = R.drawable.sample_21;
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        setThumbs();
        imageView.setImageResource(mThumbIds[position]);
        System.out.println("test");
        return imageView;
    }



    // references to our images
   /* private Integer[] mThumbIds= {
            //Row 0
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            //Row 1
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20,
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20,
            //Row 2
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            //Row 3
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20,
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20,
            //Row 4
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            //Row 5
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20,
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20,
            //Row 6
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            R.drawable.sample_20, R.drawable.sample_21,R.drawable.sample_20, R.drawable.sample_21,
            //Row 7
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20,
            R.drawable.sample_21, R.drawable.sample_20,R.drawable.sample_21, R.drawable.sample_20
    };*/
}
