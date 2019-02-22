package com.the_notorious_five.easyplate;

import android.content.Intent;
import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment{
    private ArrayList<Dish> list = new ArrayList<Dish>();
    private int[] array;
    private SQLiteDatabase db;
    private Cursor cursor;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.activity_results, container, false);
        recyclerView = (RecyclerView)layout.findViewById(R.id.recycler_view);

        Bundle stuff = getActivity().getIntent().getExtras();
        array = stuff.getIntArray("indexes");
        try {
            SQLiteOpenHelper foodDatabase = new FoodDatabase(getActivity());
            db = foodDatabase.getReadableDatabase();
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(getActivity(),"Database is Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (array.length == 0){
        }
        else if (array[0] == 0 && array.length > 1){
            int[] arraytemp = getOff(array);
            cursor = db.query("DISHES", new String[]{"_id", "NAME", "TIME","IMAGE_RESOURCE_ID"} , "_id IN(" + questionMark(arraytemp) + ")", generateString(arraytemp), null,null,"TIME ASC");
            if (cursor.moveToFirst()){
                do{
                    Dish dish = new Dish(cursor.getInt(3), cursor.getString(1), "Time : " + cursor.getInt(2) + " minutes");
                    list.add(dish);
                }while(cursor.moveToNext());
            }
        }
        else if (array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                cursor = db.query("DISHES", new String[]{"_id", "NAME", "TIME", "IMAGE_RESOURCE_ID"}, "_id = ?", new String[]{array[i] + ""}, null, null, null);
                if (cursor.moveToFirst()) {
                    Dish dish = new Dish(cursor.getInt(3), cursor.getString(1), "Time : " + cursor.getInt(2) + " minutes");
                    list.add(dish);
                }
            }
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        DishAdapter adapter = new DishAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);

        return layout;
    }

    public int[] getOff(int[] a){
        int[] b = new int[a.length - 1];
        for(int i = 0; i < b.length; i++){
            b[i] = a[i + 1];
        }
        return b;
    }
    public String questionMark(int[] a){
        String str = "";

        for (int i = 0; i < a.length - 1; i++){
            str += "?,";
        }
        str += "?";
        return str;
    }

    public int[] getArray(){
        return array;
    }
    public int[] putIn(int[] a){
        int[] b = new int[a.length + 1];
        b[0] = 0;
        for(int i = 0; i < a.length; i++){
            b[i + 1] = a[i];
        }
        return b;
    }
    public String[] generateString(int[] a){
        String[] str = new String[a.length];

        for (int i = 0; i < a.length; i++){
            str[i] = a[i] + "";
        }
        return str;
    }
}