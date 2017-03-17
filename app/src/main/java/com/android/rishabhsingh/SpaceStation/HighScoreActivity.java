package com.something.rishabhsingh.SpaceStation;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Rishabh Singh on 02-07-2016.
 */

public class HighScoreActivity extends AppCompatActivity {

    TextView score1,score2,score3,score4;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        score1=(TextView)findViewById(R.id.score1);
        score2=(TextView)findViewById(R.id.score2);
        score3=(TextView)findViewById(R.id.score3);
        score4=(TextView)findViewById(R.id.score4);
        sharedPreferences=getSharedPreferences("HIGH_SCORES",MODE_PRIVATE);
        score1.setText("1-"+sharedPreferences.getInt("score1",0));
        score2.setText("2-"+sharedPreferences.getInt("score2",0));
        score3.setText("3-"+sharedPreferences.getInt("score3",0));
        score4.setText("4-"+sharedPreferences.getInt("score4",0));
    }
}
