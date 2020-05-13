package com.example.signs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class record_screen extends AppCompatActivity {
    VideoView videoView;
    String sign_name;
    private Uri fileUri,pathtovideo;


    //Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_screen);

        Intent intent = getIntent();
        sign_name = intent.getStringExtra("sign_name");
        System.out.println("in third activity " + sign_name);

        TextView textView = (TextView) findViewById(R.id.sign_text);
        textView.setText("Record your sign for   " + sign_name);
        videoView = (VideoView) findViewById(R.id.viewrecordvideo);

        requestAppPermissions();



    }

    final int REQUEST_VIDEO_CAPTURE = 1;

  //String GESTURE;
  int PRACTICE_NUM=0;

    //@android.support.annotation.RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void Startrecord(View view) {

        File mediaFile = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            mediaFile = new
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        }
        System.out.println();

        PRACTICE_NUM++;
        System.out.println(PRACTICE_NUM+" NUM");

        StrictMode.VmPolicy.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            builder = new StrictMode.VmPolicy.Builder();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setVmPolicy(builder.build());
        }
        Intent record_video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        record_video.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);

     fileUri = Uri.fromFile(new File(mediaFile.getPath() + "//" + sign_name + PRACTICE_NUM +"_THOTA" + ".mp4"));

     ////
       pathtovideo  =Uri.parse(mediaFile.getPath()+"//"+sign_name + PRACTICE_NUM +"_THOTA"+ ".mp4");
        System.out.println("path is ...."+mediaFile.getPath()+ sign_name + PRACTICE_NUM +"_THOTA" + ".mp4");

        System.out.println("in 3 record  file uri is" + fileUri);

       record_video.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        record_video.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);




        if (record_video.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(record_video, REQUEST_VIDEO_CAPTURE);
        }

    }

    ///////
    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(record_screen.this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 3); // your request code

        //}, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
    ////////////////////////////


    ////
    Uri videoUri;
    //String videopath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {


            videoUri = intent.getData();


            System.out.println("in 3 record  file  videouri is" + videoUri);

            Toast.makeText(this, "Video has been saved to:\n" +
                    intent.getData(), Toast.LENGTH_LONG).show();

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Video recording cancelled.",
                    Toast.LENGTH_LONG).show();
        }
    }



    ////


    public void Playrecord(View view) {


        if (videoUri != null) {
            System.out.println("uri is" + videoUri);
            videoView.setVideoURI(videoUri);
            videoView.start();
            System.out.println("in 3 record " + videoUri);
        } else {
            Toast.makeText(this, "RECORD YOUR SIGN AND VIEW", Toast.LENGTH_SHORT).show();
        }

    }


    public void upload(View view) {
        Toast.makeText(getApplicationContext(),pathtovideo.toString()+"UPLOADED Successfully",Toast.LENGTH_LONG);



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
             //String link ="/storage/emulated/0/Snapchat/Snapchat-1806129112.mp4";
            // String link = "/storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20200301-WA0037.mp4";
                String link = pathtovideo.toString();
               //String link="/storage/emulated/0/DCIM/Camera/BUY1_THOTA.mp4";

                File f  = new File(link);

                String content_type  = getMimeType(f.getPath());

                String file_path = f.getAbsolutePath();
                OkHttpClient client = new OkHttpClient();

                RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type",content_type)
                        .addFormDataPart("fileToUpload",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                        .build();

                Request request = new Request.Builder()
                        .url("http://192.168.0.21:80/test/upload.php")
                        .post(request_body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    System.out.println("res is"+response);
                    if(!response.isSuccessful()){
                        System.out.println("FAILED____");
                        throw new IOException("Error : "+response);
                    }
                    else    System.out.println("NOT---FAILED____");



                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });

        t.start();
        //Intent moveto1st = new Intent(getApplicationContext(),Show_sign_second.class);
        //startActivity(moveto1st);




    }


    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

}




