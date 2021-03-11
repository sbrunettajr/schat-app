package br.com.sbrunettajr.schat.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.time.LocalDateTime;

import br.com.sbrunettajr.schat.models.DatabaseHelper;
import br.com.sbrunettajr.schat.models.Message;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatService extends IntentService {

    private Socket mSocket;

    public ChatService() {
        super("Schat-Socket.IO");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            mSocket = IO.socket("http://192.168.0.5:3000");

            mSocket.on("NEW-MESSAGE", onNewMessage);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {

            Gson gson = new Gson();

            Message message = gson.fromJson(args[0].toString(), Message.class);
            message.datetime = LocalDateTime.now();

            Intent intent = new Intent("NEW-MESSAGE");

            intent.putExtra("fromUserId", message.fromUserId);
            intent.putExtra("toUserId", message.toUserId);
            intent.putExtra("message", message.message);
            intent.putExtra("datetime", message.datetime);

            sendBroadcast(intent);
        }

    };

}
