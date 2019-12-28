package com.example.qaiser.hangmanproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.qaiser.hangmanproject.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonAddWord, buttonOption,buttonPlayGame;
    public  static int languageId;

    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         db = new DataBaseHelper(this);
        words=new ArrayList<>();
        buttonOption=(Button)findViewById(R.id.btn_Option);
        buttonPlayGame=(Button)findViewById(R.id.btn_playGame);
        buttonAddWord=(Button)findViewById(R.id.btn_dictionary);


        buttonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Select_Level.class));
            }
        });
        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),AddWords.class));

                Addword();
            }
        });

        buttonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Option.class));

            }
        });
    }

    private ListView listView;
    private ImageButton addWordButton;
    private ArrayAdapter<Word> adapter;
    private List<Word> words;
    void Addword(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dictionary);
        LayoutInflater inflater = (LayoutInflater) MainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final  View v=inflater.inflate(R.layout.add_words, null, false);
        builder.setView(v);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final EditText wordInput = (EditText)v.findViewById(R.id.etAddWords);
                final Spinner class_= (Spinner)v.findViewById(R.id.spLevel);


               Word word1 = new Word(wordInput.getText().toString(),"level"+(class_.getSelectedItemPosition()+1)+"");
                if (db.addWord(word1)) {
                    words.add(word1);
                    Collections.sort(words, new Comparator<Word>() {
                        @Override
                        public int compare(Word w1, Word w2) {
                            return w1.getWord().compareToIgnoreCase(w2.getWord());
                        }
                    });
                    // adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "You added " + word1.getWord(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, word1.getWord() + " not added!", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
      //  alertDialogBuilder.setTitle("Exit Application?");

        alertDialogBuilder
                .setMessage("Are you want to Exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
