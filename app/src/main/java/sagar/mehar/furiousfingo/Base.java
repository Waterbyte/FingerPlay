package sagar.mehar.furiousfingo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class Base extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        AppRater.app_launched(this);
        Button upgrade=(Button)findViewById(R.id.upgrade);
        Button level1_button=(Button)findViewById(R.id.level1);
        Button level2_button=(Button)findViewById(R.id.level2);
        Button highscore_button=(Button)findViewById(R.id.highScores);
        Button exit_button=(Button)findViewById(R.id.exit_Button);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

       upgrade.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse("market://details?id=mehar.sagar.furiousfingopro"));
               startActivity(intent);
           }
       });


        level1_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                      Intent i = new Intent(Base.this, FastestFinger.class);
                      Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Base.this, R.anim.anim1, R.anim.anim2).toBundle();
                      startActivity(i, bndlanimation);
              }
          });
        level2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Base.this,QuickestReaction.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Base.this,R.anim.anim1,R.anim.anim2).toBundle();
                startActivity(i, bndlanimation);
            }
        });
        highscore_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Base.this,GoogleScores.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Base.this,R.anim.anim1,R.anim.anim2).toBundle();
                startActivity(i, bndlanimation);
            }
        });
         exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           finishAffinity();
            }
        });


    }
}
