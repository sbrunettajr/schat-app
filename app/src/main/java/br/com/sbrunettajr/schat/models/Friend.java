package br.com.sbrunettajr.schat.models;

public class Friend {

    public long id;
    public String name;
    public String phoneNumber;
    public String userId;

    public Friend() {

    }

    public Friend(long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userId = null;

    }

    public Friend(long id, String name, String phoneNumber, String userId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userId = userId;

    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
