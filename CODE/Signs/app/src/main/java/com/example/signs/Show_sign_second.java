package com.example.signs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Show_sign_second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sign_second);

        VideoView videoview =(VideoView) findViewById(R.id.Video_viewer);
        Intent intent = getIntent();
        String link =intent.getStringExtra("link");
        final String sign_name =(String)intent.getStringExtra("sign_name");
        System.out.println("in activity 2   " +link);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoview);
        //Uri uri = new Uri.parse();

        videoview.setMediaController(mediaController);
        videoview.start();
        videoview.setVideoURI(Uri.parse(link));
        videoview.start();


        Button record = (Button)findViewById(R.id.record) ;
        record.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent record_intent =new Intent(getApplicationContext(),record_screen.class);
                record_intent.putExtra("sign_name",sign_name);
                startActivity(record_intent);
            }
        });



    /* Button download = (Button)findViewById(R.id.download_button)   ;
        download.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });*/




    }




    }

