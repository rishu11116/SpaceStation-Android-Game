package com.something.rishabhsingh.SpaceStation;

import java.util.Random;

/**
 * Created by Rishabh Singh on 29-06-2016.
 */

public class Star {

    private int x;
    private int y;
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public Star(int screenX,int screenY) {

        minX=0;
        minY=0;
        maxX=screenX;
        maxY=screenY;
        // Generating a random coordinate,but keeping the coordinate inside the screen size
        Random generator=new Random();
        x=generator.nextInt(maxX);
        y=generator.nextInt(maxY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public float getStarWidth() {

        // Making the star width random so that it will give a real look
        float minWidth=1.0f;
        float maxWidth=4.0f;
        Random generator=new Random();
        float finalWidth=generator.nextFloat()*(maxWidth-minWidth)+minWidth;
        return finalWidth;
    }

    public void update(int playerSpeed) {

        // Animating the star horizontally to left side by decreasing x coordinate with player speed
        x-=playerSpeed;
        if(x<0) {
            /**  If the star reached the left edge of the screen,again starting the star from right edge
             which will give an infinite scrolling background effect  **/
            x=maxX;
            Random generator=new Random();
            y=generator.nextInt(maxY);
        }
    }
}
