package br.com.sbrunettajr.schat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.models.Friend;

public class FriendsAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<Friend> friends;

    public FriendsAdapter(Context context, List<Friend> friends) {
        this.friends = friends;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return friends != null ? friends.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        Friend c = friends.get(position);
        return c.id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_friend, parent, false);

        TextView tNome = (TextView) view.findViewById(R.id.tNome);
        TextView tFone = (TextView) view.findViewById(R.id.tFone);

        Friend f = friends.get(position);
        tNome.setText(f.name);
        if (!f.phoneNumber.isEmpty()) {
            tFone.setText(f.phoneNumber);
        } else {
            tFone.setText("SEM NÚMERO");
        }
        return view;

    }
}
