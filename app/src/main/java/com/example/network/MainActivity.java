package com.example.network;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.LitePal;
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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView responseText;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        verifyStoragePermissions(MainActivity.this);
        Button sendRequest=(Button)findViewById(R.id.send_request);
        Button word_add=(Button)findViewById(R.id.b_word_add);
        Button word_find=(Button)findViewById(R.id.b_word_find);
        Button word_delete=(Button)findViewById(R.id.b_word_delete);
        Button word_update=(Button)findViewById(R.id.b_word_update);
        Button content=(Button)findViewById(R.id.content_provider);
        responseText=(TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
        word_add.setOnClickListener(this);
        word_find.setOnClickListener(this);
        word_delete.setOnClickListener(this);
        word_update.setOnClickListener(this);
        content.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       LitePal.getDatabase();
       switch (view.getId())
       {
           case R.id.b_word_add:
               Intent intent1=new Intent(MainActivity.this,word_add.class);
               startActivity(intent1);
               break;
           case R.id.send_request:
               Log.d("Main","button be lowed;");
               sendReqestWithHttpURLConnection();
               break;
           case R.id.b_word_find:
               Intent intent2=new Intent(MainActivity.this,Word_find.class);
               startActivity(intent2);break;
           case R.id.b_word_delete:
               Intent intent3=new Intent(MainActivity.this,Word_delete.class);
               startActivity(intent3);break;
           case R.id.b_word_update:
               Intent intent4=new Intent(MainActivity.this,Word_update.class);
               startActivity(intent4);
               break;
           case R.id.content_provider:
               Intent intent5= new Intent(MainActivity.this, CP.class);
               startActivity(intent5);
               default:break;
       }
        /*if(view.getId()==R.id.send_request)
        {
            Log.d("Main","button be lowed;");
            sendReqestWithHttpURLConnection();
        }*/
    }

    private void sendReqestWithHttpURLConnection() {
        Log.d("sendR","in sendRequesWithHttpURLConnection");
       /* if(Build.VERSION.SDK_INT>=23)
        {
            int REQUEST_CODE_CONTENT=101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for(String str:permissions)
            {
                if(this.checkSelfPermission(str)!= PackageManager.PERMISSION_GRANTED)
                {
                    this.requestPermissions(permissions,REQUEST_CODE_CONTENT);
                }
            }
        }*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("xiancheng","new Thread");
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL("https://dict-co.iciba.com/api/dictionary.php?w=go&key=3FC7FD013A135991236E712D101F0557");
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
                    parseXMLWithSAX(response.toString());
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
    }
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
    private void parseXMLWithSAX(String xml){
        try {
            SAXParserFactory factory =SAXParserFactory.newInstance();
            XMLReader xmlreader =factory.newSAXParser().getXMLReader();
            dealWhithxml handler= new dealWhithxml();
            xmlreader.setContentHandler(handler);
            xmlreader.parse(new InputSource(new StringReader(xml)));
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
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.help:
                AlertDialog.Builder dialog_help= new AlertDialog.Builder(MainActivity.this);
                dialog_help.setTitle("help");
                dialog_help.setMessage("");//-------------------------
                dialog_help.setCancelable(false);
                dialog_help.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog_help.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog_help.show();
            default:break;
        }
        return  true;
    }
}
