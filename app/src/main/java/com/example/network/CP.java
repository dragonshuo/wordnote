package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

public class CP extends AppCompatActivity implements View.OnClickListener {
    private Button add,delete,update,find,cg_input,cg_sortandmain,cg_sentence;
    private int state_add=0,state_update=0;
    private EditText et,allinput,allsortandmain,allsentence;
    private String biginput=new String();
    //uri=/data/data/com.example.network/databases/Data.db
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp);
        add=(Button)findViewById(R.id.add);
        delete=(Button)findViewById(R.id.delete);
        update=(Button)findViewById(R.id.update);
        find=(Button)findViewById(R.id.find);
        et=(EditText)findViewById(R.id.et);
        cg_input=(Button)findViewById(R.id.change_allkey);
        cg_sortandmain=(Button)findViewById(R.id.change_allsortAndMain);
        cg_sentence=(Button)findViewById(R.id.change_allsentence);
        allinput=(EditText)findViewById(R.id.all_key);
        allsortandmain=(EditText)findViewById(R.id.all_sortAndMain);
        allsentence=(EditText)findViewById(R.id.all_sentence);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        find.setOnClickListener(this);
        cg_input.setOnClickListener(this);
        cg_sortandmain.setOnClickListener(this);
        cg_sentence.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add:
                if(state_add==0)
                {
                    state_add=1;
                    allinput.setEnabled(true);
                    allsortandmain.setEnabled(true);
                    allsentence.setEnabled(true);
                }
                else if(state_add==1)
                {
                    state_add=0;
                    String input=allinput.getText().toString();
                    String soranmain=allsortandmain.getText().toString();
                    String sentence=allsentence.getText().toString();
                    allinput.setEnabled(false);
                    allsortandmain.setEnabled(false);
                    allsentence.setEnabled(false);
                    Uri uri1=Uri.parse("content://com.example.network.database");
                    ContentValues values=new ContentValues();
                    values.put("input",input);
                    values.put("sortandmain",soranmain);
                    values.put("sentence",sentence);
                    getContentResolver().insert(uri1,values);
                }
                break;
            case R.id.delete:
                String input2=et.getText().toString();
                Uri uri2=Uri.parse("content://com.example.network.database");
                int answer=getContentResolver().delete(uri2,input2,new String[]{"input"});
                System.out.println("已删除"+answer);
                break;
            case R.id.update:
                if(state_update==0)
                {
                    state_update=1;
                    biginput=et.getText().toString();
                    Uri uri3=Uri.parse("content://com.example.network.database");
                    Cursor cursor=getContentResolver().query(uri3,null,biginput,null,null);
                    System.out.println("query完成");
                    cursor.moveToFirst();
                    do{
                        String ip=cursor.getString(cursor.getColumnIndex("input"));
                        String sortAndMain=cursor.getString(cursor.getColumnIndex("sortandmain"));//列名均小写
                        String sentence=cursor.getString(cursor.getColumnIndex("sentence"));
                        System.out.println("数据为"+ip+"||"+sortAndMain+"||"+sentence);
                        allinput.setText(ip);
                        allsortandmain.setText(sortAndMain);
                        allsentence.setText(sentence);
                    }while(cursor.moveToNext());
                    cg_input.setEnabled(true);
                    cg_sortandmain.setEnabled(true);
                    cg_sentence.setEnabled(true);
                }
                else if(state_update==1)
                {
                    state_update=0;
                    Uri uri3=Uri.parse("content://com.example.network.database");
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("input",allinput.getText().toString());
                    contentValues.put("sortAndMain",allsortandmain.getText().toString());
                    contentValues.put("sentence",allsentence.getText().toString());
                    allinput.setEnabled(false);
                    allsortandmain.setEnabled(false);
                    allsentence.setEnabled(false);
                    cg_input.setEnabled(false);
                    cg_sortandmain.setEnabled(false);
                    cg_sentence.setEnabled(false);
                    int res=getContentResolver().update(uri3,contentValues,biginput,new String[]{""});
                    System.out.println("更新结果为"+res);
                }
//                String input3=et.getText().toString();
//                Uri uri3=Uri.parse("content://com.example.network.database");
//                ContentValues values1=new ContentValues();
//                values1.put("input",input3);
//                getContentResolver().update(uri3,values1,"input",new String[]{""});
                break;
            case R.id.find:
                String input4=et.getText().toString();
                Uri uri4 =Uri.parse("content://com.example.network.database");
                Cursor cursor=getContentResolver().query(uri4,null,input4,null,null);
                if(cursor==null)
                {
                    allinput.setText("没有数据");
                    allsortandmain.setText("没有数据");
                    allsentence.setText("没有数据");
                }
                else {
                    cursor.moveToFirst();
                    System.out.println("query完成");
                    do {
                        String ip = cursor.getString(cursor.getColumnIndex("input"));
                        String sortAndMain = cursor.getString(cursor.getColumnIndex("sortandmain"));//列名均小写
                        String sentence = cursor.getString(cursor.getColumnIndex("sentence"));
                        allinput.setText(ip);
                        allsortandmain.setText(sortAndMain);
                        allsentence.setText(sentence);
                        System.out.println("数据为" + ip + "||" + sortAndMain + "||" + sentence);
                    } while (cursor.moveToNext());
                }
                break;
            case R.id.change_allkey:
                allinput.setEnabled(true);
                break;
            case R.id.change_allsortAndMain:
                allsortandmain.setEnabled(true);
                break;
            case R.id.change_allsentence:
                allsentence.setEnabled(true);
                break;
            default:break;
        }
    }
}
