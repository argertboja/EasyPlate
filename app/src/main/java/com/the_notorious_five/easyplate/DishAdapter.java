package com.the_notorious_five.easyplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    // Variables
   private  ArrayList<Dish> dishes = new ArrayList<Dish>();
   private  Context ctx;

    // Constructor
    public DishAdapter(ArrayList<Dish> dishes, Context ctx) {
        this.dishes = dishes;
        this.ctx = ctx;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.card_view_layout, parent, false);
        DishViewHolder dishViewHolder = new DishViewHolder( view, ctx, dishes);
        return dishViewHolder;
    }

    // Assign resources for the view component
    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        // Get each object from dishes
        Dish dish = dishes.get(position);
        holder.dish_img.setImageResource(dish.getImage_id());
        holder.dish_nm.setText(dish.getDish_name());
        holder.cooking_time.setText(dish.getTime());
    }

    // Returns number of elements in the list
    @Override
    public int getItemCount() {
        return dishes.size();
    }

    // Private inner class
    public static class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Variables
        ImageView dish_img;
        TextView dish_nm, cooking_time;
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        Context ctx;

        // Constructor
        public DishViewHolder (View view, Context ctx, ArrayList<Dish> dishes) {
            super(view);
            this.dishes = dishes;
            this.ctx = ctx;
            view.setOnClickListener(this);
            dish_img = (ImageView) view.findViewById(R.id.dish);
            dish_nm = (TextView) view.findViewById(R.id.dish_name);
            cooking_time = (TextView) view.findViewById(R.id.time_txt);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Dish dish = this.dishes.get(position);
            Intent intent = new Intent(this.ctx, DishActivity.class);
            intent.putExtra("dish_name", dish.getDish_name());
            this.ctx.startActivity(intent);
        }
    }
}
