package br.com.sbrunettajr.schat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.adapters.MessageAdapter;
import br.com.sbrunettajr.schat.models.DatabaseHelper;
import br.com.sbrunettajr.schat.models.Message;
import br.com.sbrunettajr.schat.services.MessageService;
import br.com.sbrunettajr.schat.utils.Preference;

public class ConversaActivity extends AppCompatActivity {

    private RecyclerView rv_messages;

    private RecyclerView.Adapter messageAdapter;

    private List<Message> messages;

    private EditText et_message;

    private ImageButton btn_send;


    private String title = null;
    private String toUserId = null;


    private BroadcastReceiver newMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = new Message();
            message.fromUserId = intent.getStringExtra("fromUserId");
            message.toUserId = intent.getStringExtra("toUserId");
            message.message = intent.getStringExtra("message");
            message.datetime = LocalDateTime.now();

            new DatabaseHelper(ConversaActivity.this).insertMessage(message);

            messages.add(message);

            messageAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        Intent intent = getIntent();
        title = intent.getStringExtra("friendName");
        toUserId = intent.getStringExtra("userId");

        getSupportActionBar().setTitle(title);

        messages = new DatabaseHelper(this).selectMessages();

        messageAdapter = new MessageAdapter(messages);

        rv_messages = (RecyclerView) findViewById(R.id.rv_messages);
        rv_messages.setLayoutManager(new LinearLayoutManager(this));
        rv_messages.setAdapter(messageAdapter);

        et_message = (EditText) findViewById(R.id.et_message);

        btn_send = (ImageButton) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMessage();
                        clearInputMessage();
                    }
                }
        );
        registerReceiver(newMessageReceiver, new IntentFilter("NEW-MESSAGE"));

    }

    private void sendMessage() {
        Message message = new Message();
        message.fromUserId = Preference.getString(this, Preference.PreferenceName.PREF_USER_ID);
        message.toUserId = toUserId;
        message.message = et_message.getText().toString();
        // message.datetime = LocalDateTime.now();

        new MessageService(message).execute();
    }

    private void clearInputMessage() {
        et_message.setText("");
    }

    private void scrollToBottom() {
        rv_messages.scrollToPosition(messageAdapter.getItemCount() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newMessageReceiver);
    }
}