package com.guillaume.cours3api;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CallAPI extends AppCompatActivity {
    ImageView imgView1;
    ProgressBar progressBar;
    String BingURL = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_api);
        imgView1 = findViewById(R.id.imgView1);
        progressBar = findViewById(R.id.progressBar2);
        setListener();
    }

    private void setListener(){
        findViewById(R.id.btnDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                downloadBingImageOfTheDay();

            }
        });

        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToConnectActivity();
            }
        });
    }

    private void moveToConnectActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void downloadImage(String URL){
        ImageDownloader imageDownloader = new ImageDownloader();
        try{
            imageDownloader.execute(URL);
        }
        catch(Exception e){e.printStackTrace();}
    }

    private void downloadBingImageOfTheDay(){
        BingImageLinkDownloader bingImageLinkDownloader = new BingImageLinkDownloader();
        try{

            bingImageLinkDownloader.execute(BingURL);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... URLs) {
            try{
                URL url = new URL(URLs[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                return imageBitmap;
            }
            catch (MalformedURLException e){e.printStackTrace();}
            catch ( IOException e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgView1.setImageBitmap(bitmap);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public class BingImageLinkDownloader extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try{
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0){
                    return  null;
                }
                return buffer.toString();
            }
            catch (MalformedURLException e){e.printStackTrace();}
            catch ( IOException e){e.printStackTrace();}
            finally {
                if (connection != null)
                {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null){
                JSONObject jsonRoot = null;
                try{
                    jsonRoot = new JSONObject(response);
                    JSONArray jsonArrayImage = jsonRoot.getJSONArray("images");
                    String urlImage = jsonArrayImage.getJSONObject(0).getString("url");
                    downloadImage("http://bing.com" + urlImage);
                }
                catch (JSONException e){e.printStackTrace();}
            }
        }
    }
}
