package br.com.sbrunettajr.schat.services;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.sbrunettajr.schat.models.Message;

public class MessageService extends AsyncTask<Void, Void, Void> {

    private Message message;

    public MessageService(Message message) {
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.0.5:3000/message");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            out.write(getRequestBody().getBytes());
            out.flush();

             InputStream in = new BufferedInputStream(conn.getInputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(in));

             StringBuilder sb = new StringBuilder();
             String line = null;

             while((line = br.readLine()) != null) {
                  sb.append(line);
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRequestBody() {
        Gson gson = new Gson();

        return gson.toJson(message);
    }

}
