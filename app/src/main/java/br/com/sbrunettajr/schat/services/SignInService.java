package br.com.sbrunettajr.schat.services;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.sbrunettajr.schat.models.User;

public class SignInService extends AsyncTask<Void, Void, User> {

    private String phoneNumber;

    public SignInService(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }

    @Override
    protected User doInBackground(Void... voids) {

        try {
            URL url = new URL("http://192.168.0.5:3000/user/signin");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            out.write(getRequestBody().getBytes());
            out.flush();

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                Gson gson = new Gson();

                return gson.fromJson(sb.toString(), User.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRequestBody() {
        JSONObject object = new JSONObject();

        try {
            object.put("phoneNumber", phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
