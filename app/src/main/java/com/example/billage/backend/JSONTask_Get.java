package com.example.billage.backend;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONTask_Get extends AsyncTask<String,String,String> {
    String response="";
    @Override
    protected String doInBackground(String... urls){
        try{
            HttpURLConnection con =null;
            BufferedReader reader=null;
            try{
                URL url=new URL(urls[0]);
                con=(HttpURLConnection)url.openConnection();
                con.connect();

                InputStream stream=con.getInputStream();

                reader=new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer=new StringBuffer();
                String line="";

                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }


                return buffer.toString();
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {

                if(con!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        //서버로부터 get한
    }
}
