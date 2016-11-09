package com.example.ricarte.serverfibonacci;

import android.app.Activity;
import android.content.Context;
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

public class AsyncTaskActivity extends AsyncTask<Void, Void, Void>{

    private Socket CLISOCK;
    public String sentMessage;
    public String receivedMessage;
    public TextView receiver;
    public EditText sender;


    public AsyncTaskActivity(EditText sender, TextView receiver){
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    protected void onPreExecute() {
        sentMessage = sender.getText().toString() + "\n";
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            CLISOCK = new Socket("192.168.25.126", 3334);

            //Enviando mensagem ao servidor
            OutputStream os = CLISOCK.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(sentMessage);
            bw.flush();

            System.out.println("Mensagem enviada: "+sentMessage);

            //Recebendo a mensagem do servidor
            InputStream is = CLISOCK.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            receivedMessage = br.readLine();
            System.out.println("Mensagem recebida: "+receivedMessage);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Erro 3: "+e.toString());
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... params) {

    }

    @Override
    protected void onPostExecute(Void params) {
        receiver.setText(receivedMessage);

    }

}