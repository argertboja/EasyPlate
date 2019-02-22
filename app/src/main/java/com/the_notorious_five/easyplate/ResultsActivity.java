package com.the_notorious_five.easyplate;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ResultsActivity extends Activity {

    private Fragment fragment;
    private int[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        fragment = new FavouritesFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_results, menu);
        if(((FavouritesFragment)fragment).getArray().length == 0){
            TextView warning = (TextView) findViewById(R.id.warning_text);
            warning.setText("There are no results found according to your selection.");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        array = ((FavouritesFragment)fragment).getArray();
        if(array.length > 0) {
            if (array[0] == 0) {
                Intent things = new Intent(this, ResultsActivity.class);
                things.putExtra("indexes", ((FavouritesFragment)fragment).getOff(array));
                finish();
                startActivity(things);

            } else {
                Intent things = new Intent(this, ResultsActivity.class);
                things.putExtra("indexes", ((FavouritesFragment)fragment).putIn(array));
                finish();
                startActivity(things);
            }
        }
        return true;
    }



}