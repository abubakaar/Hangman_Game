package com.example.qaiser.hangmanproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


public class LetterAdapter extends BaseAdapter {
	public String[] letters;
	private LayoutInflater letterInf;



	String str[]={"پ","ہ","ی","ض","ے","ت","ر","ع","و","ق",
					"ل","ک","ج","ھ","گ","ف","د","س","ا","ٹ",
						"م","ن","ب","ط","چ","ش","چ"," ح","خ",
			" ث","ڈ","د","ذ","ظ","ھ","ژ","ص","ذ","ز"};


//	String str1[]={"ا","پ","ب","پ","ت","ٹ"," ث" ,"ج","چ" , " ح" ,"خ","د","ڈ" ,
//			"ذ" , "ر","ڑ","ز","ژ","س","ش","ص","ض","ط","ظ", "ع","غ",
//			"ف","ق","ک","گ","ل","م","ن","و","ہ","ھ","ء","ی","ے"};



	public LetterAdapter(Context c) {
		 letters= new  String[]{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"
                                  ,"A", "S", "D", "F", "G", "H", "J", "K", "L"
                                  ,"Z", "X", "C", "V", "B", "N", "M"};



//		letters=new String[26];
//		for (int a = 0; a < letters.length; a++) {
//		  letters[a] = "" + (char)(a+'A');

		letterInf = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {

	    if(MainActivity.languageId==0)
		return letters.length;
	    else
	        return  str.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//create a button for the letter at this position in the alphabet
		  Button letterBtn;
		  if (convertView == null) {
		    //inflate the button layout
		    letterBtn = (Button)letterInf.inflate(R.layout.letter, parent, false);
		  } else {
		    letterBtn = (Button) convertView;
		  }
		  //set the text to this letter
        if(MainActivity.languageId==0)
            letterBtn.setText(letters[position]);
            else
            letterBtn.setText(str[position]);
		  return letterBtn;
		  
	}

}
