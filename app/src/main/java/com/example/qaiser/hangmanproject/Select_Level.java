package com.example.qaiser.hangmanproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class Select_Level extends AppCompatActivity {
    Button btnStart;
    private ListView listView;
    Spinner sp;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__level);
        btnStart=(Button)findViewById(R.id.btn_strt);

        sp=(Spinner)findViewById(R.id.spLevel_lvl);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = sp.getSelectedItemPosition();
                System.out.println(a);
                Intent i=new Intent(getApplicationContext(),PlayGame.class);

                i.putExtra("level",a+"");
                startActivity(i);
            }
        });
    }
}
