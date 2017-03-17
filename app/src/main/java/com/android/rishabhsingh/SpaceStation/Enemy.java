package com.something.rishabhsingh.SpaceStation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Rishabh Singh on 30-06-2016.
 */

public class Enemy {

    // Bitmap for the enemy
    private Bitmap bitmap;
    // x and y coordinates
    private int x;
    private int y;
    // Enemy speed
    private int speed;
    // Minimum and maximum coordinates to keep the enemy inside the screen
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY) {

        bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy);
        maxX=screenX;
        maxY=screenY;
        minX=0;
        minY=0;
        // Generating the random coordinates to add enemy
        Random generator=new Random();
        speed=generator.nextInt(6)+10;
        x=screenX;
        y=100+generator.nextInt(screenY-100)-bitmap.getHeight();
        detectCollision=new Rect(x+10,y,bitmap.getWidth()-10,bitmap.getHeight());

    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void setX(int x) {
        this.x = x;
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

    public void update(int playerSpeed) {

        // Decreasing x-coordinate so that enemy will move from right to left
        x-=playerSpeed;
        x-=speed;
        if(x<minX-bitmap.getWidth()) {

            // If the enemy reaches the left edge,adding the enemy again to the right edge
            x=maxX;
            Random generator=new Random();
            y=100+generator.nextInt(maxY-100)-bitmap.getHeight();
        }

        detectCollision.left=x+10;
        detectCollision.top=y;
        detectCollision.right=x+bitmap.getWidth()-10;
        detectCollision.bottom=y+bitmap.getHeight();
    }
}