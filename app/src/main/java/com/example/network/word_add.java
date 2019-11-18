package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class word_add extends AppCompatActivity implements View.OnClickListener {
    private Button b_enter;
    private EditText addintput_key;
    private  Button b_add;
    private TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_add);
        b_enter=(Button)findViewById(R.id.button_ok);
        b_add=(Button)findViewById(R.id.button_add);
        addintput_key=(EditText)findViewById(R.id.edit_e);
        show=(TextView)findViewById(R.id.show);
        b_enter.setOnClickListener(this);
        b_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_ok:
                String key=addintput_key.getText().toString();
                addword(key);
                show.setText("操作已完成，点击look按钮查看单词");
                break;
            case R.id.button_add:
                List<Word> wd1= DataSupport.select("input","sortAndMain","e","sentence",  "A_voice","A_voice_adress","E_voice","E_voice_adress").where("input=?",addintput_key.getText().toString()).find(Word.class);
                for (Word word:wd1) {
                    System.out.println("word:[" + word.getSortAndMain() + word.getE() + "]");
                    Log.d("word", word.getSentence());
                    show.setText(word.getInput()+"\n");
                    show.append(word.getSortAndMain()+"\n");
                    show.append(word.getE()+"\n");
                    show.append(word.getSentence()+"\n");
                    show.append(word.getA_voice()+"\n");
                    show.append(word.getA_voice_adress()+"\n");
                    show.append(word.getE_voice()+"\n");
                    show.append(word.getE_voice_adress());
                }
                break;
        }
    }
private  void addword(String key){
        HttpUtil.sendHttpRequest("https://dict-co.iciba.com/api/dictionary.php?w=" + key + "&key=3FC7FD013A135991236E712D101F0557", new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Word word=parseXMLWithSAX(response);
                word.save();
                System.out.println("执行saveWordMP3前");
                word.saveWordsMP3(word);
                System.out.println("执行savewordmp3后");
            }

            @Override
            public void onFinish(InputStream inputStream) throws IOException {

            }

            @Override
            public void onError(Exception e) {
                System.out.println(e);
            }
        });
}

    /*private void addword(final String key,Word wd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("xiancheng","new Thread");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL("https://dict-co.iciba.com/api/dictionary.php?w="+key+"&key=3FC7FD013A135991236E712D101F0557");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response =new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        response.append(line);
                        Log.d("line",line);
                    }
                    wd= parseXMLWithSAX(response.toString());
                    //showResponse(response.toString());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(reader != null)
                    {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null)
                    {
                        connection.disconnect();
                    }
                }
            }

        }).start();
    }*/
    private Word parseXMLWithSAX(String xml){
        Word word =new Word();
        try {
            SAXParserFactory factory =SAXParserFactory.newInstance();
            XMLReader xmlreader =factory.newSAXParser().getXMLReader();
            dealWhithxml handler= new dealWhithxml();
            xmlreader.setContentHandler(handler);
            xmlreader.parse(new InputSource(new StringReader(xml)));
            word= handler.getWord();
            System.out.println("parseXMLwithsax:"+word.getSentence()+"");
        }
        catch (EOFException e)
        {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return word;
    }
}
