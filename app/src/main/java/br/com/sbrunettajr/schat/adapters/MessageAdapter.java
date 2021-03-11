package br.com.sbrunettajr.schat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.models.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View messageView = inflater.inflate(R.layout.message_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(messageView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Message contact = messages.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.tv_message;
        textView.setText(contact.message);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_message;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }

}
