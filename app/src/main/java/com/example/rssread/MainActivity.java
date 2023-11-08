package com.example.rssread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Customadapter customadapter;
    ArrayList<DocBao> mangdocbao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
        mangdocbao = new ArrayList<DocBao>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Readdata().execute("https://vnexpress.net/rss/suc-khoe.rss");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("link", mangdocbao.get(position).link);
                startActivity(intent);
            }
        });
    }
    class Readdata extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDungTu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String hinhanh = "";
            for (int i = 0;i<nodeList.getLength(); i++){
                String cdata = nodeListdescription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if(matcher.find()){
                    hinhanh = matcher.group(1);
                    Log.d("hinhanh", hinhanh + "........." + i);
                }
                Element element = (Element) nodeList.item(i);
                title += parser.getValue(element, "title");
                link  = parser.getValue(element, "link");
                mangdocbao.add(new DocBao(title,link,hinhanh));

            }
            customadapter = new Customadapter(MainActivity.this, android.R.layout.simple_list_item_1,mangdocbao);
            listView.setAdapter(customadapter);
            super.onPostExecute(s);
        }
    }
    private static String docNoiDungTu_URL(String theURL){
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theURL);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return content.toString();
    }
}