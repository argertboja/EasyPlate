package com.the_notorious_five.easyplate;


public class Dish {

    // Variables
    private int image_id;
    private String dish_name, time;

    // Constructor
    public Dish(int image_id, String dish_name, String time) {
        this.setImage_id(image_id);
        this.setDish_name(dish_name);
        this.setTime(time);
    }

    // Getters and Setters methods
    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
