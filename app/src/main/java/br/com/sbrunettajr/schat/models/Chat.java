package br.com.sbrunettajr.schat.models;

public class Chat {

    public String userId;
    public String friendName;

    public Chat(String userId, String friendName) {
        this.userId = userId;
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "userId='" + userId + '\'' +
                ", friendName='" + friendName + '\'' +
                '}';
    }
}
