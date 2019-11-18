package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.tablemanager.Connector.getWritableDatabase;

public class Word_delete extends AppCompatActivity implements View.OnClickListener {
    private EditText input;
    private Button ok;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_delete);
        input=findViewById(R.id.delete_input);
        ok=findViewById(R.id.delete_ok);
        text=findViewById(R.id.delete_text);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String temo=input.getText().toString();
        int num=DataSupport.deleteAll(Word.class,"input=?",temo);
        text.setText("已删除"+num+"行");
    }
}
