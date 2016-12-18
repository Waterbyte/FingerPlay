package sagar.mehar.furiousfingo;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FastestFinger extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private long best_fastest_finger_time;
    private ImageButton startTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fastestfinger);
        //this is free version

        final SharedPreferences sharedpreferences = getSharedPreferences(FastestFinger.MyPREFERENCES, Context.MODE_PRIVATE);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int value = sharedpreferences.getInt(date, 0);
        sharedpreferences.edit().putInt(date, (value + 1)).apply();

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.hide();

        startTimer = (ImageButton) findViewById(R.id.timerbutton1);

        if (value + 1 <15) {

            startTimer.setOnClickListener(new View.OnClickListener() {
                long start = 0l, end = 0l, microseconds;
                int step = 0;
                final TextView fastest1 = (TextView) findViewById(R.id.fastest1);


                @Override
                public void onClick(View v) {

                    if (step == 1) {
                        end = System.nanoTime();
                        microseconds = (end - start) / 1000;
                        fastest1.setText(String.valueOf(microseconds) + " MicroSeconds");
                        if (best_fastest_finger_time > microseconds) {
                            sharedpreferences.edit().putString("FastestFinger", encode(microseconds)).apply();
                        }
                        showDial(microseconds);
                        step = 2;
                        startTimer.setImageResource(R.drawable.start1);
                    }

                    if (step == 0) {
                        start = System.nanoTime();
                        step = 1;
                        microseconds = 0;
                        best_fastest_finger_time = decode(sharedpreferences.getString("FastestFinger", encode(999999999)));


                        startTimer.setImageResource(R.drawable.stop1);
                    }
                    if (step == 2) {
                        step = 0;
                        start = 0l;
                        end = 0l;
                        microseconds = 0l;
                    }
                }


            });
        } else
        {
            Toast.makeText(this,"Buy The Pro Version for Unlimited Tries.",Toast.LENGTH_SHORT).show();

        }


    }

    private void showDial(long microseconds) {
        String shareText,s;
        if(microseconds<best_fastest_finger_time) {
            shareText = "My best time in THE FASTEST FINGER is " + microseconds + " Microseconds.\nDo you Dare to Beat it?.\nDownload now at google playstore - Furious Fingo.";
            s = ("Your BestTime is :\n" + microseconds + " Microseconds.\n\n" + "Your CurrentTime is :\n" + microseconds + " Microseconds");
        }
        else
        {
            shareText = "My best time in THE FASTEST FINGER is " + best_fastest_finger_time + " Microseconds.\nDo you Dare to Beat it?.\nDownload now at google playstore - Furious Fingo.";
            s = ("Your BestTime is :\n" + best_fastest_finger_time + " Microseconds.\n\n" + "Your CurrentTime is :\n" + microseconds + " Microseconds");
        }


        FragmentManager fm = getFragmentManager();
        HighScores highScoresfrag=new HighScores();
        Bundle bus=new Bundle();
        bus.putString("message", s);
        bus.putString("ShareText",shareText);
        highScoresfrag.setArguments(bus);
        highScoresfrag.show(fm, "Dialog Fragment");




    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private String encode(long value)  {
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
    private long decode(String value)
    {
        value=value.substring(0,value.length()-3);
        long convertdata;
        String decodedata="";
        byte[] data=Base64.decode(value,Base64.DEFAULT);
        try {
             decodedata=new String(data,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        convertdata=Long.parseLong(decodedata);
        return convertdata;
    }

}


