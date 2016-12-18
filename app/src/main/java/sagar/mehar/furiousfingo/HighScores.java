package sagar.mehar.furiousfingo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


public class HighScores extends DialogFragment {
    private String sharemessage="";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        sharemessage=getArguments().getString("ShareText");
        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                     getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        if(getActivity().getClass().getName().equals("sagar.mehar.furiousfingo.FastestFinger")) {
                            Intent i = new Intent(getActivity(), FastestFinger.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                        }
                        else
                        {
                            Intent i = new Intent(getActivity(), QuickestReaction.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                        }
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                       getActivity().finish();
                    }
                }).setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent2 = new Intent();
                        intent2.setAction(Intent.ACTION_SEND);
                        intent2.setType("text/plain");
                        intent2.putExtra(Intent.EXTRA_TEXT,sharemessage);
                        startActivity(Intent.createChooser(intent2,"Share Results Via..."));

                    }
                }).create();
    }
}