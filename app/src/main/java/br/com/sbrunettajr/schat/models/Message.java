package br.com.sbrunettajr.schat.models;

import java.time.LocalDateTime;

public class Message {

    //    public long id;
    //    public String message;
    //    public LocalDateTime datetime;
    //    public String userId;
    //    public int sent;

    public long id;
    public String message;
    public LocalDateTime datetime;
    public String fromUserId;
    public String toUserId;

    public Message() {

    }

    //    public Message(long id, String message, LocalDateTime datetime, String userId, int sent) {
    //        this.id = id;
    //        this.message = message;
    //        this.datetime = datetime;
    //        this.userId = userId;
    //        this.sent = sent;
    //    }

    public Message(long id, String message, LocalDateTime datetime, String fromUserId, String toUserId) {
        this.id = id;
        this.message = message;
        this.datetime = datetime;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", datetime=" + datetime +
                ", fromUserId='" + fromUserId + '\'' +
                ", toUserId=" + toUserId +
                '}';
    }
}
