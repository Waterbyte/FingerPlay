package sagar.mehar.furiousfingo;


import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class QuickestReaction extends AppCompatActivity {


    private TextView timeCount;
    private CountDownTimer countDownTimer;
    private boolean blink;
    private final long timeBlinkInMilliseconds= 10 * 1000;
    private boolean end_game=false;
    private SharedPreferences sharedpreferences;
    private int score =0;
    private int score2=0;
    private int score3=0;
    private int finalscore=0;
    private int Bestscore=0;
    private MediaPlayer tick;
    private MediaPlayer fasttick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickestreaction);
        ActionBar actionBar=this.getSupportActionBar();
        actionBar.hide();
        sharedpreferences = getSharedPreferences(FastestFinger.MyPREFERENCES, Context.MODE_PRIVATE);//Saving highscores
         tick = MediaPlayer.create(this, R.raw.ticksound);
         fasttick=MediaPlayer.create(this,R.raw.fasttick);

        final RelativeLayout playground2 = (RelativeLayout) findViewById(R.id.playground2);
        final ImageButton startQuickestReaction=(ImageButton)findViewById(R.id.startQuickestReactionbutton);
        final TextView quickReactionMsg=(TextView)findViewById(R.id.QuickestReactionmsg);

        final TextView finalScoreView=(TextView)findViewById(R.id.finalScoreView);
        finalScoreView.setVisibility(View.GONE);

        timeCount = (TextView) findViewById(R.id.timeCount);
        final Button ball = new Button(QuickestReaction.this);
        ball.setText("0");
        final Button ball2=new Button(QuickestReaction.this);
        ball2.setText("0");
        final Button ball3=new Button(QuickestReaction.this);
        ball3.setText("0");
        ball.setBackgroundResource(R.drawable.bluesplash);
        ball2.setBackgroundResource(R.drawable.bluesplash);
        ball3.setBackgroundResource(R.drawable.bluesplash);
        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!end_game) {
                    Random r = new Random();
                    score += 1;

                    playground2.removeView(ball);
                    ball.setText(String.valueOf(score));
                    int x = playground2.getWidth() - 100;
                    int y = playground2.getHeight() - 100;
                    ball.setTranslationX(r.nextInt(x));
                    ball.setTranslationY(r.nextInt(y));

                    playground2.addView(ball, 100, 100);
                    finalScoreView.setText(""+(score+score2+score3));
                } else {


                }
            }
        });
        ball2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!end_game) {



                    Random r = new Random();
                    score2+=1;
                    playground2.removeView(ball2);
                    ball2.setText(String.valueOf(score2));
                    int x = playground2.getWidth() - 100;
                    int y = playground2.getHeight() - 100;
                    ball2.setTranslationX(r.nextInt(x));
                    ball2.setTranslationY(r.nextInt(y));
                    playground2.addView(ball2, 100, 100);
                    finalScoreView.setText(""+(score+score2+score3));
                } else {


                }
            }});
        ball3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!end_game) {


                    Random r = new Random();
                    score3+=1;
                    playground2.removeView(ball3);
                    ball3.setText(String.valueOf(score3));
                    int x = playground2.getWidth() - 100;
                    int y = playground2.getHeight() - 100;
                    ball3.setTranslationX(r.nextInt(x));
                    ball3.setTranslationY(r.nextInt(y));
                    playground2.addView(ball3, 100, 100);
                    finalScoreView.setText(""+(score+score2+score3));
                } else {


                }
            }});
        startQuickestReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickReactionMsg.setVisibility(View.INVISIBLE);
                startQuickestReaction.setVisibility(View.INVISIBLE);
                finalScoreView.setVisibility(View.VISIBLE);
                finalScoreView.setTextColor(Color.GRAY);

                timeCount.setVisibility(View.VISIBLE);

                ball2.setTranslationX(200);
                ball3.setTranslationY(200);

                playground2.addView(ball, 100, 100);
                playground2.addView(ball2, 100, 100);
                playground2.addView(ball3, 100, 100);
                playground2.setBackgroundResource(R.drawable.page);
                finalScoreView.setText(""+finalscore);

                startTimer();
                Bestscore= decode(sharedpreferences.getString("QuickestReaction", encode(0)));
                //hacker check
                if(Bestscore>450)
                    sharedpreferences.edit().putString("QuickestReaction", encode(0)).apply();

            }
        });

    }



    private void startTimer() {
        long totalTimeCountInMilliseconds = 60 * 1000;
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds,1000 ) {
            // 500 means, onTick function will be called at every 500
            // milliseconds


            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                if(leftTimeInMilliseconds<4000)                 //sound section
                {
                    fasttick.start();
                }
                else {
                    if (tick != null) {
                        tick.release();
                    }
                    tick = MediaPlayer.create(QuickestReaction.this, R.raw.ticksound);
                    tick.start();
                }

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    //   timeCount.setTextAppearance(getApplicationContext(),
                    //        R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink) {
                        timeCount.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        timeCount.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                timeCount.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }


            @Override
            public void onFinish() {
                timeCount.setText("Time up!");
                timeCount.setVisibility(View.VISIBLE);
                end_game=true;
                finalscore=score+score2+score3;
                if(finalscore>Bestscore) {

                    sharedpreferences.edit().putString("QuickestReaction", encode(finalscore)).apply();
                }
                String s,shareText;

                if(finalscore>Bestscore) {
                    s = ("BestScore : " + finalscore + " Taps\n\n" + "CurrentScore : " + finalscore + " Taps");
                    shareText="My Best Score in QUICKEST REACTION is "+finalscore+" Taps.\nGuess What!, You can't beat it. Dare to Download \"Furious Fingo\" from Google PlayStore.";
                }
                else
                {
                    s = ("BestScore : " + Bestscore + " Taps\n\n" + "CurrentScore : " + finalscore + " Taps");
                    shareText="My Best Score in QUICKEST REACTION is "+Bestscore+" Taps.\nGuess What!, You can't beat it. Dare to Download \"Furious Fingo\" from Google PlayStore.";
                }

                final FragmentManager fm = getFragmentManager();
                final HighScores highScoresfrag=new HighScores();
                Bundle bus=new Bundle();
                bus.putString("message",s);
                bus.putString("ShareText",shareText);
                highScoresfrag.setArguments(bus);
                CountDownTimer countDownTimer=new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        try{
                        highScoresfrag.show(fm, "Dialog Fragment");
                    }catch (Exception e)
                        {

                        }
                    }
                }.start();


            }

        }.start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    @Override
    protected void onPause() {
        tick.release();
        fasttick.release();
        if(countDownTimer!=null)
        countDownTimer.cancel();
        super.onPause();
    }


    private String encode(int value)  {
        String convertdata=String.valueOf(value);
        byte[] data=null;
        try {
            data = convertdata.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64= Base64.encodeToString(data,Base64.DEFAULT);
        base64 += (int) (Math.random() * 9)+""+(int) (Math.random() * 9)+""+(int) (Math.random() * 9);
        return base64;
    }
    private int decode(String value)
    {
        value=value.substring(0,value.length()-3);
        int convertdata;
        String decodedata="";
        byte[] data=Base64.decode(value,Base64.DEFAULT);
        try {
            decodedata=new String(data,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        convertdata=Integer.parseInt(decodedata);
        return convertdata;
    }

}
