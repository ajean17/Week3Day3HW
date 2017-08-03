package com.example.alvin.w3_d3_ex02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectURL();
    }

    private void connectURL() {
        final String message = "From thread";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        URL url = new URL("https://www.google.com");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        Log.d(TAG, "onConnect: " + result.toString());
                    } catch (MalformedURLException mue) {
                        mue.printStackTrace();
                        Log.d(TAG, "onCreate: MUE");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        Log.d(TAG, "onCreate: IOE");
                    }
                }
            }
        });
        thread.start();
    }
}
