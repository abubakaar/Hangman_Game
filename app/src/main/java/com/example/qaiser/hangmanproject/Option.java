package com.example.qaiser.hangmanproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;


public class Option extends AppCompatActivity {


    AlertDialog alertDialog1;
    Button buttonLanguage, buttonShowLevel;


    CharSequence[] values = {" English "," اردو "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);

        buttonShowLevel=(Button)findViewById(R.id.btn_show_level);
        buttonLanguage=(Button)findViewById(R.id.btn_language);
        buttonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAlertDialogWithRadioButtonGroup();
               // onClickSound.start();

            }
        });

        buttonShowLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(getApplicationContext(),Show_levels.class));

            }
        });
    }

    // code for alertBox
    public void CreateAlertDialogWithRadioButtonGroup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(Option.this);

        builder.setTitle(R.string.select_the_language);
        builder.setIcon(R.drawable.language_icon);

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        String languageToLoad  = "en"; // your language
                        Locale locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                 getBaseContext().getResources().getDisplayMetrics());
                        MainActivity.languageId=0;
                        startActivity(new Intent(getApplication(),MainActivity.class));
                        finish();

                        Toast.makeText(Option.this, "You Selected English", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        languageToLoad = "ur";
                        locale = new Locale(languageToLoad);
                        Locale.setDefault(locale);
                        config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());
                        MainActivity.languageId=1;
                        startActivity(new Intent(getApplication(),MainActivity.class));
                        finish();

                        Toast.makeText(Option.this, "You selected اردو", Toast.LENGTH_LONG).show();
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }
}
