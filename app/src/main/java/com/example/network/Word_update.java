package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Word_update extends AppCompatActivity implements View.OnClickListener {
    private EditText update_input;
    private EditText update_key;
    private EditText update_e;
    private EditText update_sortAndMain;
    private EditText update_sentence;
    private Button update_ok,change_key,change_key_ok,change_e,change_e_ok,change_sortAndMain,change_sortAndMain_ok,change_sentence,change_sentence_ok;
    private String temp=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_update);
        update_input=(EditText) findViewById(R.id.update_input);
        update_key=(EditText) findViewById(R.id.update_key);
        update_e=(EditText) findViewById(R.id.update_e);
        update_sortAndMain=(EditText) findViewById(R.id.update_sortAndMain);
        update_sentence=(EditText) findViewById(R.id.update_sentence);
        update_ok=(Button) findViewById(R.id.update_ok);
        change_key=(Button) findViewById(R.id.change_key);
        change_key_ok=(Button) findViewById(R.id.change_key_ok);
        change_e=(Button) findViewById(R.id.change_e);
        change_e_ok=(Button) findViewById(R.id.change_e_ok);
        change_sortAndMain=(Button) findViewById(R.id.change_sortAndMain);
        change_sortAndMain_ok=(Button) findViewById(R.id.change_sortAndMain_ok);
        change_sentence=(Button) findViewById(R.id.change_sentence);
        change_sentence_ok=(Button) findViewById(R.id.change_sentence_ok);
        update_ok.setOnClickListener(this);
        change_key.setOnClickListener(this);
        change_key_ok.setOnClickListener(this);
        change_e.setOnClickListener(this);
        change_e_ok.setOnClickListener(this);
        change_sortAndMain.setOnClickListener(this);
        change_sortAndMain_ok.setOnClickListener(this);
        change_sentence.setOnClickListener(this);
        change_sentence_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.update_ok:
                String input=update_input.getText().toString();
                temp=input;
                List<Word> wd= DataSupport.select("input","e","sortAndMain","sentence").where("input=?",input).find(Word.class);
                for (Word wrd:wd)
                {
                    update_key.setText(wrd.getInput());
                    update_e.setText(wrd.getE());
                    update_sortAndMain.setText(wrd.getSortAndMain());
                    update_sentence.setText(wrd.getSentence());
                }
                change_key.setEnabled(true);
                change_e.setEnabled(true);
                change_sortAndMain.setEnabled(true);
                change_sentence.setEnabled(true);
                break;
            case R.id.change_key:
                update_key.setInputType(InputType.TYPE_CLASS_TEXT);
                update_key.setSelection(update_key.getText().length());
                update_key.setEnabled(true);
                change_key_ok.setEnabled(true);break;
            case R.id.change_key_ok:
                Word word=new Word();
                word.setInput(update_key.getText().toString());
                word.updateAll("input=?",temp);
                temp=update_key.getText().toString();
                update_key.setEnabled(false);
                change_key_ok.setEnabled(false);
                break;
            case R.id.change_e:
                update_e.setInputType(InputType.TYPE_CLASS_TEXT);
                update_e.setSelection(update_e.getText().length());
                change_e_ok.setEnabled(true);
                update_e.setEnabled(true);
                break;
            case R.id.change_e_ok:
                Word word1=new Word();
                word1.setE(update_e.getText().toString());
                word1.updateAll("input=?",temp);
                update_e.setEnabled(false);
                change_e_ok.setEnabled(false);
                break;
            case R.id.change_sortAndMain:
                 update_sortAndMain.setInputType(InputType.TYPE_CLASS_TEXT);
                update_sortAndMain.setSelection(update_sortAndMain.getText().length());
                change_sortAndMain_ok.setEnabled(true);
                update_sortAndMain.setEnabled(true);
                break;
            case R.id.change_sortAndMain_ok:
                Word word2=new Word();
                word2.setSortAndMain(update_sortAndMain.getText().toString());
                word2.updateAll("input=?",temp);
                update_sortAndMain.setEnabled(false);
                change_sortAndMain_ok.setEnabled(false);
                break;
            case R.id.change_sentence:
                 update_sentence.setInputType(InputType.TYPE_CLASS_TEXT);
                update_sentence.setSelection(update_sentence.getText().length());
                change_sentence_ok.setEnabled(true);
                update_sentence.setEnabled(true);
                break;
            case R.id.change_sentence_ok:
                Word word3=new Word();
                word3.setSentence(update_sentence.getText().toString());
                word3.updateAll("input=?",temp);
                update_sentence.setEnabled(false);
                change_sentence_ok.setEnabled(false);
                break;

                default:break;
        }
    }
}
