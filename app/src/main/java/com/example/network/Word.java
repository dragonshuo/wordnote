
package com.example.network;

import android.content.Context;
import android.provider.UserDictionary;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Word extends DataSupport {
    private  boolean EorC;//判断是否为中文
    private String input;//输入的单词或者中文
    private  String e;//中文翻译
    private  String E_voice;//英音发音
    private  String E_voice_adress;//英音发音地址
    private  String A_voice;//美音发音
    private  String A_voice_adress;//美音发音地址
    private String sortAndMain;//词性和翻译
    private String sentence;//例句
    public Word()
    {
        this.EorC=false;
        this.input="";
        this.e="";
        this.E_voice="";
        this.E_voice_adress="";
        this.A_voice="";
        this.A_voice_adress="";
        this.sortAndMain="";
        this.sentence="";
    }
    public Word(boolean EorC,String input,String e,String E_voice,String E_voice_adress,String A_voice,String A_voice_adress,String sortAndMain,String sentence)
    {
        this.EorC=EorC;
        this.input=input;
        this.e=e;
        this.E_voice=E_voice;
        this.E_voice_adress=E_voice_adress;
        this.A_voice=A_voice;
        this.A_voice_adress=A_voice_adress;
        this.sortAndMain=sortAndMain;
        this.sentence=sentence;
    }
    public boolean getEorC()
    {
        return EorC;
    }
    public  String getInput()
    {
        return input;
    }
    public String getE()
    {
        return  e;
    }
    public  String getE_voice()
    {
        return E_voice;
    }
    public String getE_voice_adress()
    {
        return E_voice_adress;
    }
    public String getA_voice()
    {
        return A_voice;
    }
    public String getA_voice_adress()
    {
        return  A_voice_adress;
    }
    public String getSortAndMain()
    {
        return sortAndMain;
    }

    public String getSentence() {
        return sentence;
    }


    public void setE(String e) {
        this.e = e;
    }

    public void setEorC(boolean eorC) {
        EorC = eorC;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setA_voice(String a_voice) {
        A_voice = a_voice;
    }

    public void setA_voice_adress(String a_voice_adress) {
        A_voice_adress = a_voice_adress;
    }

    public void setE_voice(String e_voice) {
        E_voice = e_voice;
    }

    public void setE_voice_adress(String e_voice_adress) {
        E_voice_adress = e_voice_adress;
    }

    public void setSortAndMain(String sortAndMain) {
        this.sortAndMain = sortAndMain;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
    public void saveWordsMP3(Word word)
    {
        String addressE=word.getE_voice_adress();
        String addressA=word.getA_voice_adress();
        if(addressE!="")
        {
            //String filePathE=word.getInput();
           new way().execute(addressE,word.getInput()+"E.mp3");

        }
        if(addressA!="")
        {
            //String filePathA=word.getInput();
            new way().execute(addressA,word.getInput()+"A.mp3");
//            HttpUtil.sendHttpRequest(addressA, new HttpUtil.HttpCallbackListener() {
//                @Override
//                public void onFinish(String response) {
//
//                }
//
//                @Override
//                public void onFinish(InputStream inputStream) throws IOException {
//                    FileUtil.getInstance().writeToSD(filePathA,"A.mp3",inputStream);
//                }
//
//                @Override
//                public void onError(Exception e) {
//
//                }
//            });
        }
    }
}
