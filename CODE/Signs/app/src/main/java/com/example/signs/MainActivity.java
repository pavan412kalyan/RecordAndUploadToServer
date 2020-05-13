package com.example.signs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    Spinner Drop;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Drop = (Spinner)findViewById(R.id.screen1_spinner);
        ArrayAdapter<String> sign_adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.signs));
        sign_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Drop.setAdapter(sign_adapter);
        System.out.println("Signs name is "+getResources().getStringArray(R.array.signs)[0]);
        System.out.println("Sign link is "+getResources().getStringArray(R.array.Links)[0]);


        Button submit = (Button)findViewById(R.id.submit_screen1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Text = Drop.getSelectedItem().toString();
                System.out.println("Selected is "+ Text + Drop.getSelectedItemId());
                int x = (int)Drop.getSelectedItemId();
                final String link = getResources().getStringArray(R.array.Links)[x];
                final String sign_name = (String)Drop.getSelectedItem();
                System.out.println(link);
                Intent intent = new Intent(getApplicationContext(),Show_sign_second.class);
                intent.putExtra("link",link);
                intent.putExtra("sign_name",sign_name);
                startActivity(intent);

            }
        });

    }
}
