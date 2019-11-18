package com.example.network;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.crud.DataSupport.where;

public class contentprovider extends ContentProvider {
    public static final  int data_dir=0;
    public static final  int data_iten=1;
    public static final  String authority="com.example.network.word";
    private static UriMatcher uriMatcher;
    static
    {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authority,"data",data_dir);
        uriMatcher.addURI(authority,"data/#",data_iten);
    }
    @Override
    public boolean onCreate() {//在创建contentprovider时使用
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String input, @Nullable String[] strings1, @Nullable String s1) {//查询指定uri的conrentprovider，返回一个Cursor
       Cursor cursor=null;
       System.out.println("查询"+input);
       cursor= LitePal.findBySQL("select input,sortAndMain,sentence from Word where input=?",input);
       int i=cursor.getColumnCount();
       System.out.println("当前行数为"+i);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {//返回指定uri中的数据的mime类型
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
//        switch (uriMatcher.match(uri))
//        {
//            case user_dir:break;
//            case user_iten:Word word=new Word();
//            word.setInput(contentValues.getAsString("input"));
//            //word.setSortAndMain(contentValues.getAsString("sortAndMain"));
//            //word.setSentence(contentValues.getAsString("sentence"));
//            word.save();
//        }
        Word word=new Word();
        word.setInput(contentValues.getAsString("input"));
        word.setSortAndMain(contentValues.getAsString("sortandmain"));
        word.setSentence(contentValues.getAsString("sentence"));
        System.out.println("word内内容为："+word.getInput()+word.getSortAndMain()+word.getSentence());
        word.save();
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String input, @Nullable String[] selectionArgs) {
        int deleteint = 0;
        deleteint=DataSupport.deleteAll(Word.class,"input=?",input);
        return deleteint;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String input, @Nullable String[] strings) {
        Word word1=new Word();
        word1.setInput(contentValues.getAsString("input"));
        word1.setSortAndMain(contentValues.getAsString("sortAndMain"));
        word1.setSentence(contentValues.getAsString("sentence"));
        int res=word1.updateAll("input=?",input);
        return res;
    }
}
