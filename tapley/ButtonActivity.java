package com.example.araic.tapley;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class ButtonActivity extends ActionBarActivity {

    int x, y;
    Tile[][] tiles = new Tile[8][8];//-----
    AI ai;
    Piece userPiece = null;

    private int[][] map =
        {{1, 1, 1, 0, 0, 1, 1, 1},
        {1, 1, 1, 0, 0, 1, 3, 1},
        {1, 1, 1, 0, 0, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 4, 1},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 0, 0, 1, 1, 1},
        {1, 9, 1, 0, 0, 1, 1, 1},
        {1, 1, 1, 0, 0, 1, 1, 1}};

    ImageAdapter img = new ImageAdapter(this, map);

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

                if(moveLoc.getType()!=0) {
                    Tile tempPiece = new Tile(userPiece);
                    Tile[][] temp = userPiece.move(moveLoc, tiles, ai);
                    if(moveLoc.equals(tempPiece)){
                        tiles = temp;

                        if(!ai.isEmpty()){
                            ai.updateUser(userPiece);
                            tiles = ai.decision(tiles);
                        }
                    }
                    for(int i = 0; i < tiles.length; i++){
                        for(int j = 0; j < tiles[0].length; j++){
                            map[i][j] = tiles[i][j].getType();
                        }
                    }
                    img.notifyDataSetChanged();//repaint
                }
            }
        });
    }
/* if(moveLoc.getType()!=0&&!(userPiece.getX()==moveLoc.getX()&&userPiece.getY()==moveLoc.getY())) {
                    Tile tempPiece = new Tile(userPiece);
                    Tile[][] temp = userPiece.move(moveLoc, tiles, ai);
                    if(moveLoc.equals(tempPiece)){
                        tiles = temp;
                        map[x][y] = userPiece.getType();
                        System.out.println("In if statement: " + map[x][y]);
                        map[userPiece.getX()][userPiece.getY()] = 1;
                        img.notifyDataSetChanged();
                        System.out.println("hereeeee");
                        if(!ai.isEmpty()){
                            ai.updateUser(userPiece);
                            //tiles = ai.decision(tiles);
                        }
                    }

    tiles[x][y] = userPiece;
    map[x][y] = userPiece.getType();//integer to repaint
    map[userPiece.getX()][userPiece.getY()] = 1;//if user clicks on same square
    //repaint
    img.notifyDataSetChanged();*/
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
