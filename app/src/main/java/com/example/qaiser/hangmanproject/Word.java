package com.example.qaiser.hangmanproject;

public class Word {
    private int id;
    public String word;
    public String Class_name;
    public  String Lang_urdu;

    public Word()
    {}
    public Word(String word, String class_name) {
        if(MainActivity.languageId==0) {
            this.word = word;
            this.Class_name = class_name;

        }
        else
        {
            this.Class_name = class_name;
            this.Lang_urdu = word;
        }
    }

    public Word(int id, String word, String class_name,String lang_urdu) {
        this.id = id;
        this.word = word;
        this.Class_name=class_name;
        this.Lang_urdu=lang_urdu;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getWordUrdu() {
        return Lang_urdu;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getClass_name() {
        return this.Class_name;
    }

    public void setClass_name(String Class_name) {
        this.Class_name= Class_name;
    }



    public String getLang_urdu() {
        return this.Lang_urdu;
    }

    public void setLang_urdu(String lang_urdu) {
        this.Lang_urdu = lang_urdu;
    }
    @Override
    public String toString() {
        return word;
    }
}
