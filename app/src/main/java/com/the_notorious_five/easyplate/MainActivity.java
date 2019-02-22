package com.the_notorious_five.easyplate;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private int[] choosen = new int[100];
    private Cursor cursor;
    private SQLiteDatabase database;
    private int[] nrIngredients = new int[choosen.length];


    /*String[] ingredients = {
            "Bread", "Cheese", "Beef", "Chicken", "Fish", "Rice", "Tomato", "Potato", "Milk", "Onion"
    };*/

    int[] images = {
            R.drawable.bacon, R.drawable.bakingsoda,R.drawable.banana, R.drawable. basil, R.drawable.meat,
            R.drawable.blackolives, R.drawable.bread,R.drawable.broccoli, R.drawable.butter, R.drawable.cheese ,
            R.drawable.chickenbreast, R.drawable.chocolatechips, R.drawable.cidervinegar ,R.drawable.cinnamon ,
            R.drawable.cocoapowder, R.drawable.coconutmilk, R.drawable.coconutoil, R.drawable.coriander,
            R.drawable.cumin, R.drawable.fish, R.drawable.flour, R.drawable.garlic, R.drawable.greenolives,
            R.drawable.groundlamb, R.drawable.honey ,R.drawable.lemon, R.drawable.maplesyrup, R.drawable.marinara,
            R.drawable.milk2, R.drawable.mozzarellasticks, R.drawable.oliveoil, R.drawable.onion3, R.drawable.orecchiette,
            R.drawable.oregano, R.drawable.paprika, R.drawable.parsley, R.drawable.passata, R.drawable.pea,
            R.drawable.peppercorns, R.drawable.pork, R.drawable.potato5, R.drawable.raisins, R.drawable.rice,
            R.drawable.soysauce, R.drawable.spaghetti, R.drawable.sugar, R.drawable.thyme, R.drawable.tomato3,
            R.drawable.tomatosauce, R.drawable.tortilla, R.drawable.vanilla,R.drawable.yoghurt, R.drawable.water};

    private String[] ingredients;
    //int[] images;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingredients = getResources().getStringArray(R.array.names);
        //images = getResources().getIntArray(R.array.ids);
        setListAdapter(new MyAdapter());


        //create cursor
        try {
            SQLiteOpenHelper foodDatabase = new FoodDatabase(this);
            database = foodDatabase.getReadableDatabase();

        }
        catch (SQLiteException e){
            Toast.makeText(MainActivity.this, "Database is unavailable", Toast.LENGTH_SHORT).show();
        }

    }



    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ingredients.length;
        }

        @Override
        public String getItem(int position) {
            return ingredients[position];
        }
        public int getItemye(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return ingredients[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position));

            ((ImageView) convertView.findViewById(R.id.img1))
                    .setImageResource(getItemye(position));

            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create_order:
                //Code to run when the Create Order item is clicked
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("indexes",relevanceSort());
                startActivity(intent);
                return true;
            case R.id.favourites_folder:
                Intent intent1 = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onListItemClick(ListView listview, View itemView , int position , long id){
        new Thread(new Task(listview, position)).start();

    }



    public int[] relevanceSort(){
        int[] index = new int[choosen.length];
        int[] thePercentage = new int[choosen.length];
        int[] temp = copy(choosen);
        for (int i = 1 ; i < choosen.length; i++){
            index[i] = i;
            if (nrIngredients[i] == 0){
                nrIngredients[i]++;
            }
            thePercentage[i] = ((choosen[i] * (-100)) / nrIngredients[i]);
            if(thePercentage[i] == 0){
                index[i] = 0;
            }
        }



        return push(quickSort(temp, thePercentage , index , 1 , choosen.length - 1 ));
    }

    //this is just used to push choosen one value to the left for it has the number of elemnts
    //clicked at the index 0
    public int[] push(int[] a){
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int[] array;
        for (int i = 1; i < a.length ; i++){
            if(a[i] > 0 ){
                temp.add(a[i]);
            }
        }
        array = new  int[temp.size()];
        for(int i = 0; i < temp.size(); i++){
            array[i] = temp.get(i);
        }
        return array;
    }

    public int[] quickSort(int[] theChoosen, int[] percentage, int[] indexes,int low, int high ){
        int partition;

        if (low < high){
            partition = partition( theChoosen, percentage, indexes, low, high);
            quickSort(theChoosen, percentage , indexes , low, partition - 1 );
            quickSort(theChoosen , percentage , indexes , partition + 1, high);
        }
        return indexes;
    }

    public int partition (int[] theChoosen, int[] percentage, int[] indexes,int low, int high){
        int i = low;
        int pivot = percentage[high];
        int k;

        for (int j = low; j <= high; j++){
            //if they are the same compare them with the number of elements each of them has
            //multiple switches have to be made because there are 3 arrays
            if (percentage[j] <= pivot){
                if (percentage[j] == pivot){
                    if (theChoosen[j] <= theChoosen[high]){
                        k = percentage[j];
                        percentage[j] = percentage[i];
                        percentage[i] = k;
                        k = theChoosen[j];
                        theChoosen[j] = theChoosen[i];
                        theChoosen[i] = k;
                        k = indexes[j];
                        indexes[j] = indexes[i];
                        indexes[i] = k;
                        i++;
                    }
                }
                else {
                    k = percentage[j];
                    percentage[j] = percentage[i];
                    percentage[i] = k;
                    k = theChoosen[j];
                    theChoosen[j] = theChoosen[i];
                    theChoosen[i] = k;
                    k = indexes[j];
                    indexes[j] = indexes[i];
                    indexes[i] = k;
                    i++;
                }
            }
        }
        return i - 1;
    }



    private class Task implements Runnable {
        ListView listView;
        int position;

        public Task(ListView listView, int position){
            this.listView = listView;
            this.position = position;
        }

        @Override
        public void run() {
            cursor =  database.query("DISHES" , new String[]{"_id" , "INGREDIENTS"}, "INGREDIENTS LIKE ?" ,
                    new String[]{"%" + ingredients[position]+"%"} ,null, null, null);
            if (listView.isItemChecked( position)) {
                while(cursor.moveToNext()) {
                    choosen[cursor.getInt(0)]  = choosen[ cursor.getInt(0)] + 1;
                    nrIngredients[ cursor.getInt(0)] = extract(cursor.getString(1));
                }
            }
            else {
                while(cursor.moveToNext()) {
                    choosen[cursor.getInt(0)]  = choosen[cursor.getInt(0)] - 1;
                }
            }
        }
    }

    public int extract(String str){
        int number = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '\n'){
                number++;
            }
        }
        return number + 1;
    }

    public  int[] copy(int[] a){
        int[] copied = new int[a.length];
        for (int i = 0; i  < a.length; i++){
            copied[i] = a[i];
        }
        return copied;
    }



}
