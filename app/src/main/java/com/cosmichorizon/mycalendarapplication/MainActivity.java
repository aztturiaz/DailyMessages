package com.cosmichorizon.mycalendarapplication;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private MediaPlayer mPlayer;
    private int currentSong = 0;
    private int calendarCode = 0;
    private TextView txtVw;
    private ImageView imgVw;
    private Date today;
    private int month = 0, day = 0;

    /*private Button btn;
    private int testCode = 700;*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        calendarCode = (month * 100) + day;

        setContentView(R.layout.activity_main);

        // Get the Views by ID
        imgVw = (ImageView) findViewById(R.id.imageView);
        txtVw = (TextView) findViewById(R.id.MainText);
        /*btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(testCode == 731)
                {
                    testCode = 800;
                }
                if(testCode == 826)
                {
                    testCode = 913;
                }

                testCode++;
                if(mPlayer != null && mPlayer.isPlaying())
                {
                    mPlayer.stop();
                    mPlayer = null;
                }
                imgVw.setImageResource(0);
                setContentByCode(testCode);
            }
        });
        */

        txtVw.setText(getString(R.string.Salutation));

        imgVw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setContentByCode(calendarCode);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPlayer != null && mPlayer.isPlaying())
        {
            mPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPlayer == null && currentSong != 0)
        {
            mPlayer = MediaPlayer.create(MainActivity.this, currentSong);
        }

        if(mPlayer != null)
        {
            mPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mPlayer != null && mPlayer.isPlaying())
        {
            mPlayer.stop();;
        }
    }

    private void setContentByCode(int code)
    {
        int stringResourceID = getResourceIDbyName(getApplicationContext(), "string", "txt" + String.valueOf(code));
        int imgResourceID = getResourceIDbyName(getApplicationContext(), "drawable", "img" + String.valueOf(code));
        int songResourceID = getResourceIDbyName(getApplicationContext(), "raw", "song" + String.valueOf(code));

        // Set the TextView text
        if(stringResourceID != 0)
        {
            txtVw.setText(getString(stringResourceID));
        }
        else
        {
            txtVw.setText(getString(R.string.Salutation2));
        }

        // Set the ImageView drawable resource
        imgVw.setImageResource(0); // Clear Image View
        if(imgResourceID != 0)
        {
            imgVw.setImageResource(imgResourceID);
        }

        // Play the Song
        if(songResourceID != 0)
        {
            playSongByResourceID(songResourceID);
        }

        return;
    }

    private void playSongByResourceID(int resourceValue)
    {
        if(mPlayer != null)
        {
            modifyMediaPlayer();
            return;
        }

        mPlayer = MediaPlayer.create(MainActivity.this, resourceValue);
        mPlayer.start();
        currentSong = resourceValue;
    }

    private void getImageDimensions(int resourceID)
    {
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), resourceID, dimensions);
        int height = dimensions.outHeight;
        int width =  dimensions.outWidth;
        imgVw.setMaxHeight(height);
        imgVw.setMaxWidth(width);
    }

    private void modifyMediaPlayer()
    {
        if(mPlayer == null)
            return;

        if(mPlayer.isPlaying())
        {
            mPlayer.pause();
        }
        else
        {
            mPlayer.start();
        }
    }

    private int getResourceIDbyName(Context ctx, String defType, String stringName) {
        //String flagString = "a" + teamName;
        int resId = ctx.getResources().getIdentifier(stringName, defType, "com.cosmichorizon.mycalendarapplication");
        if(resId != 0){
            return resId;
        }
        return 0;
    }
}
