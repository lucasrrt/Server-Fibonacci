package com.example.ricarte.serverfibonacci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    public TextView receiver;
    public EditText sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = (TextView) findViewById(R.id.receiver);
        sender = (EditText) findViewById(R.id.sender);
    }

    public void startConnection(View view){
        AsyncTaskActivity task = new AsyncTaskActivity(sender, receiver);
        task.execute();
    }
}
