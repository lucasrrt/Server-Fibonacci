package com.example.ricarte.serverfibonacci;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by ricarte on 04/11/16.
 */

public class AsyncTaskActivity extends Activity{

    public EditText sender;
    public TextView receiver;
    private Socket CLISOCK;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // because we implement OnClickListener we only have to pass "this"
        // (much easier)
        receiver = (TextView) findViewById(R.id.receiver);

        sender = (EditText) findViewById(R.id.sender);


    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            connection();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.receiver);
            txt.setText("Executed");
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

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
                InputStream is = CLISOCK.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String receivedMessage = br.readLine();
                receiver.setText(receivedMessage);
            }catch(Exception e){
                e.printStackTrace();
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

    }
}