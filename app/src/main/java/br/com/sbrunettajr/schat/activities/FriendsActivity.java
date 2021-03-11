package br.com.sbrunettajr.schat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.adapters.FriendsAdapter;
import br.com.sbrunettajr.schat.models.Contact;
import br.com.sbrunettajr.schat.models.DatabaseHelper;
import br.com.sbrunettajr.schat.models.Friend;
import br.com.sbrunettajr.schat.models.PhoneBook;
import br.com.sbrunettajr.schat.services.FriendsService;

public class FriendsActivity extends AppCompatActivity {

    private ListView lv_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        lv_friends = (ListView) findViewById(R.id.lv_friends);

        lv_friends.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Friend friend = (Friend) parent.getAdapter().getItem(position);

                        Intent intent = new Intent(FriendsActivity.this, ConversaActivity.class);
                        intent.putExtra("userId", friend.userId);
                        intent.putExtra("friendName", friend.name);
                        startActivity(intent);
                        finish();
                    }
                }
        );

    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Friend> friends = new DatabaseHelper(FriendsActivity.this).selectFriends();

        if (friends.isEmpty()) {
            friends = refreshFriends();

        }
        refreshListView(friends);
    }

    private List<Friend> refreshFriends()  {
        Context context        = FriendsActivity.this;
        List<Contact> contacts = new PhoneBook(FriendsActivity.this).getContacts();

        try {
            return new FriendsService(context, contacts)
                    .execute()
                    .get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void refreshListView(List<Friend> friends) {
        lv_friends.setAdapter(new FriendsAdapter(FriendsActivity.this, friends));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            List<Friend> friends = refreshFriends();

            refreshListView(friends);
        }
        return super.onOptionsItemSelected(item);
    }


}