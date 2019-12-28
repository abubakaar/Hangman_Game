package com.example.qaiser.hangmanproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.qaiser.hangmanproject.MainActivity;
import com.example.qaiser.hangmanproject.Word;

import junit.framework.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.UserDictionary.Words.WORD;

public class DataBaseHelper extends  SQLiteOpenHelper  {

        public SQLiteDatabase db;


    private static final String DB_NAME = "hangman_dictionary.db";

    private Context context;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME , null, 1);
        this.context = context;
    }



    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }








    public  String TABLE_WORDS="WORDS" ,WORD_ID="_id",WORD_class="class_name",Urdu="urdu",Word="word";



        public Word getRandom() {
          Cursor cursor = null;

        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
           cursor = db.query(TABLE_WORDS, new String[]{WORD_ID, Word}, null, null, null, null, "RANDOM()", "1");
         if (cursor.moveToNext()) {
                return getWord(cursor);
            }
            return null;
        } catch (SQLException e) {
            logException(e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Word> getAll(String classname) {
        SQLiteDatabase db = null;

        Cursor cursor = null;
        try {
            db = openDatabase();
            List<Word> words = new ArrayList<>();
           // db.query()
            //cursor = db.rawQuery("select * from "+TABLE_WORDS+" where "+WORD_class+" = "+classname,new String[]{WORD_ID, WORD_class,WORD});
            if(MainActivity.languageId==0)
               // cursor = db.query(TABLE_WORDS, new String[]{WORD_ID, WORD_class,WORD }, null, null, null, null, WORD);
                cursor = db.rawQuery("select * from "+TABLE_WORDS+" where "+WORD_class+" = "+"'"+classname+"'",null);

               //     cursor = db.rawQuery("select * from "+TABLE_WORDS+" where "+WORD_class+" = "+"'"+classname+"'",new String[]{WORD_ID, WORD_class,WORD});
            else
               // cursor = db.query(TABLE_WORDS, new String[]{WORD_ID, WORD_class,Urdu }, null, null, null, null, WORD);
                cursor = db.rawQuery("select * from "+ TABLE_WORDS+" where "+WORD_class+" = "+"'"+classname+"'",new String[]{WORD_ID, WORD_class,Urdu});

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Word w=getWord(cursor);
                if((w.getClass_name()+"").equalsIgnoreCase(classname    )) {
                    words.add(getWord(cursor));
                }
                cursor.moveToNext();
            }

            return words;
        } catch (SQLException e) {
            logException(e);
            return new ArrayList<>();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean addWord(Word word) {

        try {
            if(MainActivity.languageId==0) {
                if (word.getWord() == null || word.getWord().trim().isEmpty()) return false;
                word.setWord(word.getWord().trim().toUpperCase());
                db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("word", word.getWord());
                values.put("class_name", word.getClass_name());

                long newRowId = db.insert("Words", null, values);

                return true;
            }
            else {
                if (word.getWordUrdu() == null || word.getWordUrdu().trim().isEmpty()) return false;
                word.setWord(word.getWordUrdu().trim().toUpperCase());
                db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("urdu", word.getWordUrdu());
                values.put("class_name", word.getClass_name());

                long newRowId = db.insert("Words", null, values);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = null;
        try {
      //      db = getWritableDatabase();
            db.delete(TABLE_WORDS, "_id = ?", new String[]{Integer.toString(word.getId())});
        } catch (SQLException e) {
            logException(e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    @NonNull
    private Word getWord(Cursor cursor) {
        return new Word(
                cursor.getInt(cursor.getColumnIndex(WORD_ID)),
                cursor.getString(cursor.getColumnIndex(WORD)),
                cursor.getString(cursor.getColumnIndex(WORD_class)),
                cursor.getString(cursor.getColumnIndex(Urdu))

        );
    }

    private void logException(SQLException e) {
        Log.e("SQLError", e.getMessage());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
