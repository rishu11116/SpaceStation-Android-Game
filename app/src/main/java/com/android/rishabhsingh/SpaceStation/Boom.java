package com.something.rishabhsingh.SpaceStation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Rishabh Singh on 30-06-2016.
 */

public class Boom {

    private int x;
    private int y;
    private Bitmap bitmap;

    public Boom(Context context) {

        bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.boom);
        x=-250;
        y=-250;

    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {

        return x;
    }

    public int getY() {
        return y;
    }

    public Bitmap getBitmap() {

        return bitmap;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}