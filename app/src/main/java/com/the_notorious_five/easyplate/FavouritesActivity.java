package com.the_notorious_five.easyplate;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouritesActivity extends Activity {

    // Variables
    private SQLiteDatabase db;
    private Cursor cursor; // add the database and the cursor
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    ArrayList<Dish> list = new ArrayList<Dish>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        try {
            SQLiteOpenHelper foodDatabase = new FoodDatabase(this);
            db = foodDatabase.getReadableDatabase();
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database is Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        cursor = db.query("DISHES", new String[]{"_id", "NAME", "TIME", "IMAGE_RESOURCE_ID"}, "FAVOURITE = ?", new String[]{"1"}, null, null, "TIME ASC");
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish(cursor.getInt(3), cursor.getString(1), "Time : " + cursor.getInt(2) + " minutes");
                list.add(dish);
            } while (cursor.moveToNext());
        }
        else{
            TextView warning = (TextView) findViewById(R.id.warning_text);
            warning.setText("There are no favourites for the moment.");
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        // Improve performance of recycler view
        recyclerView.setHasFixedSize(true);

        adapter = new DishAdapter(list, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        list = new ArrayList<Dish>();
        cursor = db.query("DISHES", new String[]{"_id", "NAME", "TIME", "IMAGE_RESOURCE_ID"}, "FAVOURITE = ?", new String[]{"1"}, null, null, "TIME ASC");
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish(cursor.getInt(3), cursor.getString(1), "Time : " + cursor.getInt(2) + " minutes");
                list.add(dish);
            } while (cursor.moveToNext());
        }
        adapter = new DishAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }
}