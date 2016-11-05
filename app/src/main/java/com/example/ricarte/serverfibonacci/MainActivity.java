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

    private Socket CLISOCK;
    public String receivedMessage;

    public Thread tthread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = (TextView) findViewById(R.id.receiver);
        sender = (EditText) findViewById(R.id.sender);

        tthread = new Thread(new Runnable() {
            @Override
            public void run() {
                connection();
            }
        });

    }

    public void connection(){
        try {
            CLISOCK = new Socket("192.168.0.23", 3334);

            //Enviando mensagem ao servidor
            OutputStream os = CLISOCK.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

//                    Toast.makeText(MainActivity.this, "Mensagem Enviada!!", Toast.LENGTH_SHORT).show();

            String number = sender.getText().toString();

            String MESSAGE = number + "\n";

            bw.write(MESSAGE);
            bw.flush();


            //Recebendo a mensagem do servidor
//            InputStream is = CLISOCK.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);

            Scanner br = new Scanner(CLISOCK.getInputStream());


            receivedMessage = br.toString();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            //Closing the socket
            try
            {
                CLISOCK.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void startConnection(View view){
        tthread.start();
        Toast.makeText(this, receivedMessage, Toast.LENGTH_LONG).show();
        receiver.setText(receivedMessage);

    }
}
