package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Word_find extends AppCompatActivity implements View.OnClickListener {
    private String key=new String();
    private Button findb_enter,voiceA_player,voiceE_player;
    private MediaPlayer player_A=new MediaPlayer();
    private MediaPlayer player_E=new MediaPlayer();
    private AutoCompleteTextView finde_input;
    private TextView input,voiceA,voiceE,sortAndMian,sentence;
    private ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_find);
        findb_enter=(Button)findViewById(R.id.button_ok_f);
        voiceA_player=(Button)findViewById(R.id.voiceA_player);
        voiceE_player=(Button)findViewById(R.id.voiceE_player);
        finde_input=(AutoCompleteTextView)findViewById(R.id.edit_input);
        input=(TextView)findViewById(R.id.find_input);
        voiceA=(TextView)findViewById(R.id.find_voiceA);
        voiceE=(TextView)findViewById(R.id.find_voiceE);
        sortAndMian=(TextView)findViewById(R.id.find_sortAndMain);
        sentence=(TextView)findViewById(R.id.find_sentence);
        findb_enter.setOnClickListener(this);
        voiceA_player.setOnClickListener(this);
        voiceE_player.setOnClickListener(this);
        List<Word> wd= DataSupport.select("input").find(Word.class);
        for(Word wrd:wd)
        {
            list.add(wrd.getInput());
            System.out.println("list内数据"+wrd.getInput());
        }
        String[] strings= (String[]) list.toArray(new String[list.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,strings);//this是这个活动的context，也可以写成Word_find.this
        finde_input.setAdapter(adapter);
//        finde_input.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
//            //text 输入框中改变前的字符串信息
//                //start输入框中改变前的字符串的起始位置
//                //count输入框中改变前后的字符串改变数量一般为0
//                //after输入框中改变后的字符串与起始位置的偏移量
//            }
//
//            @Override
//            public void onTextChanged(CharSequence text, int start, int before, int count) {
//            //text 输入框中改变后的字符串信息
//                //start输入框中改变后的字符串的起始位置
//                //before输入框中改变前的字符串位置默认为0
//                //count输入框中改变后的一共输入的字符串数量
//                System.out.println("正在调用onTextchanged");
//        int i=0;
//        String in=finde_input.getText().toString();
//        System.out.println("in内的内容"+in);
//        List<Word> wd= DataSupport.select("input").where("input like ?","%"+in+"%").find(Word.class);
//        for (Word wrd: wd)
//        {
//            i++;
//            if(i<=5) {
//                list[i] = wrd.getInput();
//                System.out.println("list里的word"+wrd.getInput());
//            }
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Word_find.this,android.R.layout.simple_dropdown_item_1line,list);
//        System.out.println("完成文本适配器的填充");
//        finde_input.setAdapter(adapter);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            //editable 输入结束呈现在输入框中的信息
//            }
//        });
//        finde_input.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_ok_f:
                key=finde_input.getText().toString();
                List<Word> wd= DataSupport.select("input","A_voice","E_voice","sortAndMain","sentence").where("input=?",key).find(Word.class);
                for (Word wrd:wd)
                {
                    input.setText(wrd.getInput());
                    voiceA.setText(wrd.getA_voice());
                    voiceE.setText(wrd.getE_voice());
                    sortAndMian.setText(wrd.getSortAndMain());
                    sentence.setText(wrd.getSentence());
                    initMediaPlayer(player_A,wrd.getInput(),"A.mp3");
                    initMediaPlayer(player_E,wrd.getInput(),"E.mp3");
                }
                break;
            case R.id.voiceA_player:playMp3(player_A);break;
            case R.id.voiceE_player:playMp3(player_E);break;
            default:break;
        }
    }
    public void initMediaPlayer(MediaPlayer mediaPlayer,String key,String sort)
    {
        String fileName=key+sort;
        //String fileName=key+"_"+sort+".mp3";
        FileUtil fileUtil=new FileUtil();
        System.out.println("文件："+fileUtil.getPATH()+key+sort);
        File file=new File(fileUtil.getPATH()+key+sort);
        if (file.exists())
        {
            System.out.println("存在"+file.getPath());

        }
        else
        {
            System.out.println("不存在");
        }
        System.out.println("文件路径为"+fileUtil.getPATH()+key+sort);
        try {
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playMp3(MediaPlayer mediaPlayer)
    {
        if (!mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
        }
//        String fileNmae=wordskey+"/"+ps+".mp3";
//        String adrs= FileUtil.getInstance().getPathinSD(fileNmae);
//        if(player!=null)
//        {
//            if(player.isPlaying())
//            {
//                player.stop();
//            }
//            player.release();
//            player=null;
//        }
//        if(adrs!="") {
//            player = MediaPlayer.create(context, Uri.parse(adrs));
//            Log.d("播放", "成功");
//            player.start();
//        }
//        else{
//            List<Word> wd= DataSupport.select("sortAndMain").where("input=?",wordskey).find(Word.class);
//            for (Word wrd:wd)
//            {
//                wrd.saveWordsMP3(wrd);
//            }
//        }
    }

//    @Override
//    public void onFocusChange(View view, boolean b) {
//        if(b)
//        {
//            showListPopulWindow();
//        }
//    }
//    public  void showListPopulWindow()
//    {
//        final  String[] list={"1","2","3","4","5"};
//        int i=0;
//        String in=finde_input.getText().toString();
//        List<Word> wd= DataSupport.select("input").where("input=?",key+"+").find(Word.class);
//        for (Word wrd: wd)
//        {
//            i++;
//            if(i<=5) {
//                list[i] = wrd.getInput();
//            }
//        }
//        final ListPopupWindow listPopupWindow;
//        listPopupWindow=new ListPopupWindow(this);
//        listPopupWindow.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
//        listPopupWindow.setAnchorView(finde_input);
//        listPopupWindow.setModal(true);
//        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener( ){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                finde_input.setText(list[i]);
//                listPopupWindow.dismiss();
//            }
//        });
//        listPopupWindow.show();
//    }

}
