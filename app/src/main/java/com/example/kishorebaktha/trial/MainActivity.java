package com.example.kishorebaktha.trial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    String display_url = "http://192.168.43.44/view.php";
    String view_url = "http://192.168.43.44/sel.php";
    AlertDialog alertDialog;
    TextView tv;
    EditText et;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void view(View v) {
        Thread t = new Thread() {
            public void run() {
                try {
                     result="";
                    tv = (TextView) findViewById(R.id.et);
                    URL url = new URL(display_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                        result += line;
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    tv.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(result);
                            result="";
                        }
                    });
                } catch (
                        MalformedURLException e)

                {
                    e.printStackTrace();
                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
    public void select(View v) {

        Thread t = new Thread() {
            public void run() {
                try {
                     result="";
                    tv = (TextView) findViewById(R.id.et);
                    et = (EditText) findViewById(R.id.et2);
                    String name = et.getText().toString();
                    URL url = new URL(view_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                        result += line;
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    result=result.trim().toString();
                          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                    startActivity(browserIntent);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

}
