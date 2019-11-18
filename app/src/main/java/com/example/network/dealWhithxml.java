package com.example.network;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class dealWhithxml  extends DefaultHandler {
    private String nodeNmae;
    private Word word;
    private StringBuilder sortAndmain;
    private StringBuilder sentence;

    public Word getWord() {
        return word;
    }
    public void startDocument() throws SAXException{
        word=new Word();
        sortAndmain=new StringBuilder();
        sentence=new StringBuilder();
    }
    @Override
    public void endDocument()throws SAXException{
        word.setSortAndMain(sortAndmain.toString().trim());
        word.setSentence(sentence.toString().trim());
    }
    @Override
    public void startElement(String uri, String localname, String qName, Attributes attributes) throws SAXException
    {
        nodeNmae=localname;
    }
    @Override
    public void endElement(String uri,String localName,String qName) throws SAXException{
        if ("acceptation".equals(localName))
        {
            sortAndmain.append("\n");
        }
        else if ("orig".equals(localName))
        {
            sentence.append("\n");
        }
        else if ("trans".equals(localName))
        {
            sentence.append("\n");
            sentence.append("\n");
        }
    }
    @Override
    public void characters(char[] ch, int start,int length)throws SAXException{
        String a= new String(ch,start,length);
        for(int i=start;i<start+length;i++){
            if(ch[i]=='\n')//去掉换行
            {
                return;
            }
        }
            if("key".equals(nodeNmae)){
                word.setInput(a.trim());
            }
            else if("ps".equals(nodeNmae))
            {
                if(word.getE_voice().length()<=0)//金山api的接口，有两个ps，第一个为英音，第二个为美音
                {
                    word.setE_voice(a.trim());
                }
                else{
                    word.setA_voice(a.trim());
                }
            }
            else if("pron".equals(nodeNmae)){
                if(word.getE_voice_adress().length()<=0)//地址同理
                {
                    word.setE_voice_adress(a.trim());
                }
                else{
                    word.setA_voice_adress(a.trim());
                }
            }
            else if("pos".equals(nodeNmae)){
                sortAndmain.append(a);
            }
            else if("acceptation".equals(nodeNmae)){
                sortAndmain.append(a);
            }
            else if("orig".equals(nodeNmae))
            {
                sentence.append(a);
            }
            else if("trans".equals(nodeNmae))
            {
                sentence.append(a);
            }
            else if("fy".equals(nodeNmae))
            {
                word.setE(a);
                word.setEorC(true);
            }
        }
}
