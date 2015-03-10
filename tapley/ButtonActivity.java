package com.example.araic.tapley;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class ButtonActivity extends ActionBarActivity {

    int x, y;
    Tile[][] tiles = new Tile[8][8];
    AI ai;
    Piece userPiece = null;
    int[][] map;

    ImageAdapter img;

    int[][] mapForLevel(int level) {
        int[][] mapFromArray;
        if(level == 1) {
            mapFromArray = new int[][]
                {{1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 3, 1},
                {1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1},
                {1, 9, 1, 0, 0, 4, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1}};
        }
        else if(level == 2){
            mapFromArray =  new int[][]
                {{1, 1, 1, 0, 0, 1, 1, 1},
                {1, 4, 1, 0, 0, 1, 3, 1},
                {1, 4, 1, 0, 0, 1, 1, 1},
                {1, 4, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1},
                {1, 9, 1, 0, 0, 4, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1}};
        }
        else if(level == 3){
            mapFromArray =  new int[][]
                {{1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 3, 1},
                {1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1},
                {1, 9, 1, 0, 0, 4, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1}};
        }
        else{
            mapFromArray =  new int[][]
                {{1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 3, 1},
                {1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1},
                {1, 11, 1, 0, 0, 4, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1}};
        }
        return mapFromArray;
    }

    void print(Tile[][] tiles){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.println("x: " + tiles[i][j].getX() + "y: " + tiles[i][j].getY() + "type: " + tiles[i][j].getType());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        Intent myself = getIntent();
        int level= myself.getIntExtra("test",0);

        map = mapForLevel(level);

        img = new ImageAdapter(this, map);


        GridView gridview = (GridView) findViewById(R.id.grid_view);
        gridview.setAdapter(img);//paint


        tiles = img.getTiles();
        ai = img.getAi();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                x = position/8;
                y = position%8;

                //print(tiles);
                userPiece = img.getPiece();//current userTile

                Tile moveLoc = tiles[x][y];//new userTile

                if(moveLoc.getType()!=0&&!(userPiece.getX()==moveLoc.getX()&&userPiece.getY()==moveLoc.getY())) {
                    Tile tempPiece = new Tile(userPiece);
                    Tile[][] temp = userPiece.move(moveLoc, tiles, ai);
                    if(moveLoc.equals(tempPiece)){
                        tiles = temp;

                        if(!ai.isEmpty()){
                            ai.updateUser(userPiece);
                            //System.out.println("Going into decision");
                            tiles = ai.decision(tiles);
                            //System.out.println("Returned from decision");
                        }
                    }
                    for(int i = 0; i < tiles.length; i++){
                        for(int j = 0; j < tiles[0].length; j++){
                            map[i][j] = tiles[i][j].getType();
                        }
                    }//repaint
                    img.notifyDataSetChanged();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
