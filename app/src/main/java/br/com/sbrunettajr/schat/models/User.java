package br.com.sbrunettajr.schat.models;

public class User {

    public String id;
    public String name;
    public String phoneNumber;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
