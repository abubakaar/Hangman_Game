package com.example.qaiser.hangmanproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qaiser.hangmanproject.Database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayGame extends Activity {

    ImageView btnHint;

    GlobalVar gb=new GlobalVar();
    private List<String> words;
    private Random rand;
    private String currWord;
    private LinearLayout wordLayout;
    private TextView[] charViews;
    private GridView letters;
    private boolean isHint=false;
    TextView txtScore;
    int scr =0;
    private String hintletter;
    private LetterAdapter ltrAdapt;
    //body part images
    private ImageView[] bodyParts;
    //number of body parts
    private int numParts = 6;
    //current part - will increment when wrong answers are chosen
    private int currPart;
    //number of characters in current word
    private int numChars;
    //number correctly guessed
    private int numCorr;
    String level;
    private List<Word> wordsall;
    MediaPlayer mp;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        btnHint = (ImageView) findViewById(R.id.hint);
        txtScore = (TextView) findViewById(R.id.V23);
////        TextView txtHgh = (TextView) findViewById(R.id.V4);
//        int score = getIntent().getIntExtra("score", 0);
//        txtScore.setText(score + "");
//
//        SharedPreferences settings = getSharedPreferences("Game Data", Context.MODE_PRIVATE);
//        int HghScore = settings.getInt("high score", 0);
//
//        if (score > HghScore) {
//            txtHgh.setText("high score:" + score);
//
//
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putInt("high score", score);
//            editor.commit();
//        } else {
//            txtHgh.setText("high score" + HghScore);
//        }



        // getActionBar().setDisplayHomeAsUpEnabled(true);
        Resources res = getResources();
        Intent a = getIntent();
        level = a.getStringExtra("level");
        int b = Integer.parseInt(level);
        DataBaseHelper helper = new DataBaseHelper(this);
        wordsall = helper.getAll("level" + (b+1));
        int count = wordsall.size();


//        String s = wordsall.get(0).getWord();
        words = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            words.add(wordsall.get(i).getWord());
        }

        //  words = res.getStringArray(R.array.words);


        rand = new Random();
        currWord = "";
        wordLayout = (LinearLayout) findViewById(R.id.word);
        letters = (GridView) findViewById(R.id.letters);

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView) findViewById(R.id.head);
        bodyParts[1] = (ImageView) findViewById(R.id.body);
        bodyParts[2] = (ImageView) findViewById(R.id.arm1);
        bodyParts[3] = (ImageView) findViewById(R.id.arm2);
        bodyParts[4] = (ImageView) findViewById(R.id.leg1);
        bodyParts[5] = (ImageView) findViewById(R.id.leg2);

        playGame();





        // hint button code
        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               char word=currWord.charAt(new Random().nextInt(currWord.length()));
               makeHintDilaog(String.valueOf(word));
//                isHint = true;
//                hintletter=word+"";
             
               btnHint.setEnabled(true);


            }


        });
    }

    private void playGame() {

        btnHint.setEnabled(true);
        String newWord = words.get(rand.nextInt(words.size()));
        while (newWord.equals(currWord)) {
            newWord = words.get(rand.nextInt(words.size()));
        }
        currWord = newWord;

        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();

        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText("" + currWord.charAt(c));
            charViews[c].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.TRANSPARENT);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);
            //add to layout
            wordLayout.addView(charViews[c]);

        }
        ltrAdapt = new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        currPart = 0;
        numChars = currWord.length();
        numCorr = 0;

        for (int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

    }



    // hint dialog box
  public void makeHintDilaog(String word)
  {
      View view= LayoutInflater.from(getBaseContext()).inflate(R.layout.hint_dialog,null,false);
      TextView textView=(TextView) view.findViewById(R.id.tv_hint);
      textView.setText(word);
      new AlertDialog.Builder(PlayGame.this).setView(view).setNegativeButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
      }).create().show();

  }
    public void letterPressed(View view) {
        String ltr = ((TextView) view).getText().toString();
        /*if(isHint)
        {
            isHint=false;
           ltr=hintletter;
        }*/
        char letterChar = ltr.charAt(0);
        Log.i("character:",letterChar+"");
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);

        boolean correct = false;
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k) == letterChar) {
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);

                if (correct) {
                    if (numCorr == numChars)
                        gb.score += 50;
                    txtScore.setText(gb.score + "");
                } else {
                    if (numCorr != numChars)
                        scr = Integer.parseInt(txtScore.getText().toString());
                    gb.score -= 50;
                    txtScore.setText(gb.score + "");
                }
            }

        }
        if (correct) {


            if (numCorr == numChars) {
                mp=MediaPlayer.create(this,R.raw.cheering);
                mp.start();
                disableBtns();

                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
                winBuild.setTitle("Nice Game!");
                winBuild.setMessage("You won...!\n\nThe answer was:\n\n" + currWord);

                winBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PlayGame.this.playGame();
                                mp.stop();

                            }
                        });

                winBuild.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PlayGame.this.finish();
                                mp.stop();

                            }
                        });

                Dialog d = winBuild.create();
                d.setCancelable(false);
                d.setCanceledOnTouchOutside(false);
                d.show();
            }
        } else if (currPart < numParts) {

            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        } else {

            correct=false;
            gb.score -= 50;
            txtScore.setText(gb.score + "");
            mp=MediaPlayer.create(this,R.raw.fail);
            mp.start();
            disableBtns();
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("Ooops");

            loseBuild.setMessage("You lose...!\n\nThe answer was:\n\n" + currWord);
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            PlayGame.this.playGame();
                            mp.stop();

                        }
                    });

            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            PlayGame.this.finish();
                            mp.stop();

                        }
                    });

            Dialog d = loseBuild.create();
            d.setCancelable(false);
            d.setCanceledOnTouchOutside(false);
            d.show();

        }
    }

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }
}
