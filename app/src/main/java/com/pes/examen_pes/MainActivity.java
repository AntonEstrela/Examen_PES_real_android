package com.pes.examen_pes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("edat", editText.getText().toString());
        startActivity(intent);
    }

    //get profe:
    public void sayHelloThreads(View view) {

        new Thread(new Runnable() {
            InputStream stream = null;
            String str = "";
            String result = null;
            Handler handler = new Handler();
            public void run() {

                try {

                    //String query = String.format("http://192.168.1.123:9000/Application/Hello");
                    String query = String.format("http://10.192.171.29:9000/Application/hello");
                    URL url = new URL(query);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 );
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    stream = conn.getInputStream();

                    BufferedReader reader = null;

                    StringBuilder sb = new StringBuilder();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();

                    // Mostrar resultat en el quadre de text.
                    // Codi incorrecte
                    // EditText n = (EditText) findViewById (R.id.edit_message);
                    //n.setText(result);

                    //Codi correcte
                    Log.i("lolaforms1", result);
                    handler.post(new Runnable() {
                        public void run() {
                            EditText n = (EditText) findViewById (R.id.editText);
                            n.setText("Threads: "+result);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void sayHelloAsynktask(View view) {
        //new myFirstMessage(this).execute("http://192.168.1.123:9000/Application/Hello" );
        new HelloMessage(this).execute("http://10.192.171.29:9000/Application/hello" );
    }

    private class HelloMessage extends AsyncTask<String, Void, String> {
        Context context;
        InputStream stream = null;
        String str = "";
        String result = null;

        private HelloMessage(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... urls) {

            try {
                String query = String.format(urls[0]);
                URL url = new URL(query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();

                BufferedReader reader = null;

                StringBuilder sb = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(stream));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();


                Log.i("lolaforms1", result);


                return result;

            } catch(IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //EditText n = (EditText) findViewById (R.id.editText);
            //n.setText("AsynkTask: " +result);

        }
    }
    //fin get profe

    //post

    public void LoginClick(View view) {

        new Thread(new Runnable() {
            InputStream stream = null;
            String str = "";
            String result = null;
            Handler handler = new Handler();
            public void run() {

                try {

                    String query = String.format("http://192.168.0.13:9000/Application/loginandroid");
                    URL url = new URL(query);

                    Map<String,Object> params = new LinkedHashMap<>();
                    //params.put("nom", EditName.getText());
                    //params.put("password", EditPass.getText());

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setReadTimeout(10000 );
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);


                    stream = conn.getInputStream();

                    BufferedReader reader = null;

                    StringBuilder sb = new StringBuilder();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();
                    // Mostrar resultat en el quadre de text.
                    // Codi incorrecte
                    // EditText n = (EditText) findViewById (R.id.edit_message);
                    //n.setText(result);

                    //Codi correcte

                    handler.post(new Runnable() {
                        public void run() {
                            //TextView n = (TextView) findViewById (R.id.textView);
                            //n.setText("Result: "+result);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
