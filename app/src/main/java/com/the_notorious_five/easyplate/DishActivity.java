package com.the_notorious_five.easyplate;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DishActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private ImageView img;
    private TextView ingredients;
    private TextView time;
    private TextView description;
    private Menu menuu;
    private String theTitle;
    private int keep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        Bundle stuff = getIntent().getExtras();
        theTitle = stuff.getString("dish_name");
        try {
            SQLiteOpenHelper foodDatabase = new FoodDatabase(this);
            db = foodDatabase.getWritableDatabase();

            cursor = db.query("DISHES" , new String[]{"_id" ,"INGREDIENTS", "NAME","DESCRIPTION","TIME","FAVOURITE" ,"IMAGE_RESOURCE_ID"},"Name = ? ",new String[]{theTitle} ,null,null,null);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database is Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        setEverything();
    }

    public void setEverything(){
        if( cursor.moveToFirst())
        {
            keep = cursor.getInt(5);
            img = (ImageView) findViewById(R.id.imageView);
            img.setImageResource(cursor.getInt(6));
            ingredients = (TextView)findViewById(R.id.ingredients);
            ingredients.setText(cursor.getString(1));
            time = (TextView)findViewById(R.id.time);
            time.setText(cursor.getInt(4) + " minutes");
            description = (TextView)findViewById(R.id.description);
            description.setText( cursor.getString(3));
            getActionBar().setTitle(cursor.getString(2));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_dish, menu);
        this.menuu = menu;
        return super.onCreateOptionsMenu(menu);

    }

   @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem menuItem = menu.findItem(R.id.favourite);
        if (keep == 0) {
            menuItem.setIcon(R.drawable.star_empty);
        } else {
            menuItem.setIcon(R.drawable.star_full);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem menuItem = menuu.findItem(R.id.favourite);
        if (keep == 0) {
            keep = 1;
            menuItem.setIcon(R.drawable.star_full);

        }
        else{
            keep = 0;
            menuItem.setIcon(R.drawable.star_empty);

        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ContentValues foodValues = new ContentValues();
        foodValues.put("FAVOURITE" , keep);
        db.update("DISHES" , foodValues, "NAME = ?" , new String [] {theTitle});
    }
}
