package br.com.sbrunettajr.schat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.models.Chat;

public class ChatAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<Chat> chats;

    public ChatAdapter(Context context, List<Chat> chats) {
        this.chats = chats;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return chats != null ? chats.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return chats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_chat, parent, false);
        TextView tv_chat_name = (TextView) view.findViewById(R.id.tv_chat_name);

        Chat chat = chats.get(position);

        tv_chat_name.setText(chat.friendName);
        return view;

    }
}
