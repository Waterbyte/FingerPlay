package sagar.mehar.furiousfingo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;

import java.io.UnsupportedEncodingException;

import sagar.mehar.furiousfingo.BaseGame.BaseGameActivity;


public class GoogleScores extends BaseGameActivity {


    private SharedPreferences sharedPreferences;
     private long best_fastest_finger_time=99999999;
     private int best_quickest_reaction_score=0;
    private TextView fastestfingerhighscore;
    private TextView quickestreactionhighscore;
    private Button GlobalFastestBut;
    private Button GlobalQuickestBut;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.highscore);
         sharedPreferences = getSharedPreferences(FastestFinger.MyPREFERENCES, Context.MODE_PRIVATE);
         best_fastest_finger_time = decode(sharedPreferences.getString("FastestFinger", encode(999999999)));
         best_quickest_reaction_score= decode2(sharedPreferences.getString("QuickestReaction", encode2(0)));
         fastestfingerhighscore=(TextView)findViewById(R.id.fastestfingerhighscore);
         quickestreactionhighscore=(TextView)findViewById(R.id.quickestreactionhighscore);
         GlobalFastestBut=(Button)findViewById(R.id.globalfhs);
         GlobalQuickestBut=(Button)findViewById(R.id.globalqhs);
         fastestfingerhighscore.setText("Fastest Finger Score:\n" + best_fastest_finger_time+" MicroSeconds");
         quickestreactionhighscore.setText("Quickest Reaction Score:\n" + best_quickest_reaction_score+" Taps");

         if (isSignedIn()) {
             onSignInSucceeded();
         }else{
             onSignInFailed();
         }

         GlobalFastestBut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),getString(R.string.leaderboard_the_fastest_finger_in_the_world)),100);
             }
         });
         GlobalQuickestBut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),getString(R.string.leaderboard_the_quickest_reaction)),100);
             }
         });

     }

     @Override
    public void onSignInFailed() {
         GlobalFastestBut.setText("Offline!");
         GlobalQuickestBut.setText("Offline!");
    }

    @Override
    public void onSignInSucceeded() {

        Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_the_fastest_finger_in_the_world), best_fastest_finger_time);
        Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_the_quickest_reaction), best_quickest_reaction_score);
        GlobalFastestBut.setText("Online Score");
        GlobalQuickestBut.setText("Online Score");
        loadScoreOfLeaderBoardfastest();
        loadScoreOfLeaderBoardquickest();
    }

    private void loadScoreOfLeaderBoardfastest() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(), getString(R.string.leaderboard_the_fastest_finger_in_the_world), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                if (isScoreResultValid(scoreResult)) {
                    // here you can get the score like this
                    long fastestfingervalue = scoreResult.getScore().getRawScore();
                    if (fastestfingervalue < best_fastest_finger_time) {
                        sharedPreferences.edit().putString("FastestFinger", encode(fastestfingervalue)).apply();
                        fastestfingerhighscore.setText("Fastest Finger Score:\n" + fastestfingervalue+" MicroSeconds");
                    }
                }
            }
        });
    }
    private void loadScoreOfLeaderBoardquickest() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(), getString(R.string.leaderboard_the_quickest_reaction), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                if (isScoreResultValid(scoreResult)) {
                    // here you can get the score like this
                    int quickestreactionvalue =(int)scoreResult.getScore().getRawScore();
                    if(quickestreactionvalue>best_quickest_reaction_score)
                    {
                        sharedPreferences.edit().putString("QuickestReaction", encode2(quickestreactionvalue)).apply();
                        quickestreactionhighscore.setText("Quickest Reaction Score:\n" + quickestreactionvalue+" Taps");
                    }
                }
            }
        });
    }

    private boolean isScoreResultValid(final Leaderboards.LoadPlayerScoreResult scoreResult) {
        return scoreResult != null && GamesStatusCodes.STATUS_OK == scoreResult.getStatus().getStatusCode() && scoreResult.getScore() != null;
    }



    private String encode(long value)  {
        String convertdata=String.valueOf(value);
        byte[] data=null;
        try {
            data = convertdata.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64= Base64.encodeToString(data, Base64.DEFAULT);
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

    //2nd encoder-decoder pair
    private String encode2(int value)  {
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
    private int decode2(String value)
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
