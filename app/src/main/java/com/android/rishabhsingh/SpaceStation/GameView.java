package com.something.rishabhsingh.SpaceStation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Rishabh Singh on 28-06-2016.
 */

public class GameView extends SurfaceView implements Runnable {

    // boolean variable to track if the game is playing or not,visible across multiple threads

    volatile boolean playing;
    private Thread gameThread=null;
    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    ArrayList<Star> stars=new ArrayList<Star>();
    private Enemy enemy;
    private Boom boom;
    // A screenX holder
    int screenX;
    // To count the number of misses
    int countMisses;
    // An indicator that the enemy has just entered the game screen
    boolean flag;
    // An indicator if the game is Over
    private boolean isGameOver;
    static MediaPlayer gameOnSound;
    final MediaPlayer killedEnemySound;
    final MediaPlayer gameOverSound;
    Context context;
    int score;
    int highScore[]=new int[4];
    SharedPreferences sharedPreferences;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        player=new Player(context,screenX,screenY);
        paint=new Paint();
        surfaceHolder=getHolder();
        this.screenX=screenX;
        countMisses=0;
        isGameOver=false;
        // Adding the stars to the list
        int numOfStars=200;
        for(int i=0;i<numOfStars;i++) {
            Star star=new Star(screenX,screenY);
            stars.add(star);
        }
        // Adding the enemy
        enemy=new Enemy(context,screenX,screenY);
        boom=new Boom(context);
        this.context=context;
        gameOnSound=MediaPlayer.create(context,R.raw.gameonsound);
        killedEnemySound=MediaPlayer.create(context,R.raw.killedenemysound);
        gameOverSound=MediaPlayer.create(context,R.raw.gameoversound);
        gameOnSound.start();
        score=0;
        sharedPreferences=context.getSharedPreferences("HIGH_SCORES",Context.MODE_PRIVATE);
        highScore[0]=sharedPreferences.getInt("score1",0);
        highScore[1]=sharedPreferences.getInt("score1",0);
        highScore[2]=sharedPreferences.getInt("score1",0);
        highScore[3]=sharedPreferences.getInt("score1",0);

    }

    @Override
    public void run() {
        while(playing) {

            // to update the frame so as to update the coordinates of the characters
            update();
            //to draw the frame so as to place the characters on the canvas
            draw();
            // to control the frames per seconds drawn on the screen
            control();
        }

    }

    private void draw() {
        // Checking if the surface is valid to start the drawing
        if(surfaceHolder.getSurface().isValid()) {
            // Locking the canvas of the surfaceview
            canvas=surfaceHolder.lockCanvas();
            // Setting the black colour on the canvas
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            // To add the Star Objects on the canvas
            for(Star star:stars) {
                paint.setStrokeWidth(star.getStarWidth());
                canvas.drawPoint(star.getX(),star.getY(),paint);
            }

            paint.setTextSize(40);
            canvas.drawText("Score:"+score,100,50,paint);
            // To draw the player Bitmap Object on the canvas
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);
            // To draw the enemy Bitmap Object on the canvas
                canvas.drawBitmap(
                        enemy.getBitmap(),
                        enemy.getX(),
                        enemy.getY(),
                        paint);

            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint);

            if(isGameOver) {
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos=(int)((canvas.getHeight()/2)-((paint.descent()+paint.ascent())/2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
            }
            // Unlocking the canvas after the whole drawing is done
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {

        score++;
        player.update();
        boom.setX(-250);
        boom.setY(-250);
        // Updating the stars with player speed so as to create the animation
        for (Star star : stars) {
            star.update(player.getSpeed());
        }
        // Setting the flag true when the enemy just enters the screen
        if (enemy.getX() == screenX) {
            flag = true;
        }
        // Updating the enemy to move towards left during the course of time
        enemy.update(player.getSpeed());
        // If collision occurs with player
        if (Rect.intersects(player.getDetectCollision(), enemy.getDetectCollision())) {
            // Displaying boom at that location
            boom.setX(enemy.getX());
            boom.setY(enemy.getY());
            killedEnemySound.start();
            enemy.setX(-200);
        }
        // The condition where player misses the enemy
        else {
            // If the enemy has just entered
            if (flag) {
                // If the player's x-coordinate is more than the enemy's x-coordinate which means enemy has just passed across the player
                if (player.getDetectCollision().exactCenterX()-100>enemy.getDetectCollision().exactCenterX()) {
                    countMisses++;
                    // Setting the flag false so that the else part is executed only when new enemy enters the screen
                    flag = false;
                    if (countMisses == 10) {
                        // Setting playing false to stop the game
                        playing = false;
                        isGameOver = true;

                        for(int i=0;i<4;i++) {
                            if(highScore[i]<score) {
                                // ighScore[i+1]=highScore[i];
                                final int finalI=i;
                                highScore[finalI]=score;
                                break;
                            }
                        }

                        SharedPreferences.Editor edit=sharedPreferences.edit();
                        for(int i=0;i<4;i++) {
                            edit.putInt("score"+(i+1),highScore[i]);
                        }
                        edit.apply();
                        gameOnSound.stop();
                        gameOverSound.start();
                    }
                }
            }
        }
    }

    public void pause() {

        // when the game is paused,the thread will be stopped
        playing=false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        // when the game is resumed,the thread will be resumed
        playing=true;
        gameThread=new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // When the user presses on the screen,we will do something here
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
            // When the user releases the screen,we will do something here
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
        }
        if(isGameOver) {
            stopMusic();
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                context.startActivity(new Intent(context,MainActivity.class));
            }
        }
        return true;
    }

    public static void stopMusic () {
        gameOnSound.stop();
    }
}