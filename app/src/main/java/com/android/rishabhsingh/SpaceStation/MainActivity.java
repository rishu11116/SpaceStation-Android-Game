package com.something.rishabhsingh.SpaceStation;

/**
 * Created by Rishabh Singh on 28-06-2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class

MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton buttonPlay;
    private ImageButton buttonHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setting the orientation to Landscape Mode to have a better game experience
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        buttonPlay=(ImageButton)findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
        buttonHighScore=(ImageButton)findViewById(R.id.buttonScore);
        buttonHighScore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view==buttonPlay) {
            startActivity(new Intent(this,GameActivity.class));
        }
        if(view==buttonHighScore) {
            startActivity(new Intent(this,HighScoreActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        GameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert=builder.create();
        alert.show();
    }
}
