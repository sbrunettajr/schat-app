package br.com.sbrunettajr.schat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.adapters.ChatAdapter;
import br.com.sbrunettajr.schat.models.DatabaseHelper;
import br.com.sbrunettajr.schat.models.Chat;
import br.com.sbrunettajr.schat.services.ChatService;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private ListView lv_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriends();
            }
        });

        databaseHelper = new DatabaseHelper(this);

        lv_chat = (ListView) findViewById(R.id.lv_chat);
        lv_chat.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Chat chat = (Chat) parent.getAdapter().getItem(position);

                        Intent intent = new Intent(MainActivity.this, ConversaActivity.class);
                        intent.putExtra("userId", chat.userId);
                        intent.putExtra("friendName", chat.friendName);
                        startActivity(intent);
                    }
                }
        );
        startService(new Intent(this, ChatService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Chat> chats = databaseHelper.selectChats();

        lv_chat.setAdapter(new ChatAdapter(this, chats));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, ChatService.class));
    }

    private void openFriends() {
        Intent intent = new Intent(MainActivity.this, FriendsActivity.class);

        startActivity(intent);
    }
}