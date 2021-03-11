package br.com.sbrunettajr.schat.services;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
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
import java.util.LinkedList;
import java.util.List;

import br.com.sbrunettajr.schat.models.Contact;
import br.com.sbrunettajr.schat.models.DatabaseHelper;
import br.com.sbrunettajr.schat.models.Friend;

public class FriendsService extends AsyncTask<Void, Void, List<Friend>> {

    private Context context;



    private List<Contact> contacts;

    public FriendsService(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;

    }



    @Override
    protected List<Friend> doInBackground(Void... voids) {


        List<Friend> friends = new LinkedList();

        try {
            URL url = new URL("http://192.168.0.5:3000/user/friends");
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
            JSONArray array = new JSONArray(sb.toString());
            Gson gson = new Gson();

            for (int i = 0; i < array.length(); i++) {
                Friend friend = gson.fromJson(array.get(i).toString(), Friend.class);

                friends.add(friend);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return friends;


    }



    private String getRequestBody() {
        try {
            JSONArray array = new JSONArray();

            for (Contact contact : contacts) {
                JSONObject object = new JSONObject();

                object.put("id", contact.id);
                object.put("name", contact.name);
                object.put("phoneNumber", contact.phoneNumber);
                array.put(object);
            }
            return array.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Friend> friends) {
        super.onPostExecute(friends);

        new DatabaseHelper(context).insertFriends(friends);
    }
}
