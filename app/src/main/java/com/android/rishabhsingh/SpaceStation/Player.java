package com.something.rishabhsingh.SpaceStation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Rishabh Singh on 29-06-2016.
 */

public class Player {

    // Bitmap to get character from image
    private Bitmap bitmap;
    // Coordinates of the character
    private int x;
    private int y;
    // motion speed of the character
    private int speed;
    // Boolean variable to track the ship is boosting or not
    private boolean boosting;
    // Gravity Value to add gravity effect on the ship to make it move down
    private final int GRAVITY=-30;
    // Controlling y-coordinates so that ship won't go outside the screen
    private int maxY;
    private int minY;
    // Limiting the value of speed of the ship
    private final int MIN_SPEED=20;
    private final int MAX_SPEED=40;
    Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {

        x=75;
        y=50;
        speed=20;
        // Getting bitmap from drawable resource
        bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.player);
        boosting=false;
        // Top edge's y point is 0 so min y will always be zero
        minY=0;
        // Bottom edge's y point is the maximum y till player can go excluding the bitmap dimensions
        maxY=screenY-bitmap.getHeight();
        detectCollision=new Rect(x,y+5,bitmap.getWidth()-5,bitmap.getHeight()-10);
    }

    public void stopBoosting() {
        boosting=false;
    }
    public void setBoosting() {
        boosting=true;
    }

    public void update() {
        // Updating the x-coordinate of the character as the player moves
        if(boosting) {
            speed+=2;
        } else {
            speed-=5;
        }
        // Controlling the max speed of the ship
        if(speed>MAX_SPEED) {
            speed=MAX_SPEED;
        }
        // If the speed is less than min speed,controlling it so that it won't stop completely
        if(speed<MIN_SPEED) {
            speed=MIN_SPEED;
        }
        // Controlling the going down of the ship
        y-=speed+GRAVITY;
        // But controlling it also so that it won't go off the screen
        if(y<minY) {
            y=minY;
        }
        if(y>maxY) {
            y=maxY;
        }

        detectCollision.left=x;
        detectCollision.top=y+5;
        detectCollision.right=x+bitmap.getWidth()-5;
        detectCollision.bottom=y+bitmap.getHeight()-10;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
