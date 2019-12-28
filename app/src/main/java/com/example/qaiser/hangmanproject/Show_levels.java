package com.example.qaiser.hangmanproject;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.qaiser.hangmanproject.Database.DataBaseHelper;

import java.util.List;

public class Show_levels extends AppCompatActivity {
    Spinner spLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_levels);
        spLevel=(Spinner)findViewById(R.id.spLevel);

        DataBaseHelper helper = new DataBaseHelper(this);
        words = helper.getAll(spLevel.getSelectedItemPosition()+"");
        int count=words.size();
        System.out.println(spLevel.getSelectedItemPosition()+"");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
        listView = (ListView) findViewById(R.id.ListView_level);
        //  addWordButton = (ImageButton) findViewById(R.id.addWordImageButton);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClicked);
        spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DataBaseHelper helper = new DataBaseHelper(Show_levels.this);
                words = helper.getAll(position+"");

                adapter = new ArrayAdapter<>(Show_levels.this, android.R.layout.simple_list_item_1, words);
                listView = (ListView) findViewById(R.id.ListView_level);
                //  addWordButton = (ImageButton) findViewById(R.id.addWordImageButton);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(onItemClicked);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private ListView listView;
    private ImageButton addWordButton;
    private ArrayAdapter<Word> adapter;
    private List<Word> words;

    AdapterView.OnItemClickListener onItemClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
            final Word word = words.get((int) l);
            AlertDialog.Builder builder = new AlertDialog.Builder(Show_levels.this);
            builder.setMessage("Do you really want to delete " + word.getWord()).setTitle("Delete word");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DataBaseHelper db = new DataBaseHelper(Show_levels.this);
                    db.deleteWord(word);
                    words.remove(word);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(Show_levels.this, "You deleted " + word.getWord(), Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            builder.create().show();
        }
    };

}
