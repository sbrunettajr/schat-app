package br.com.sbrunettajr.schat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class FriendDb extends SQLiteOpenHelper {

    public static final String TAG = "sql";

    private static final String DB_NAME = "schat.sqlite";
    private static final int DB_VERSION = 6;

    private Context context;

    public FriendDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS friend(_id INTEGER, phoneNumber TEXT, userId TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE friend;");
        onCreate(db);
    }

    public void insertAll(List<Friend> friends) {
        SQLiteDatabase db = getWritableDatabase();

        deleteAll(db);
        try {
            for (Friend friend : friends) {
                insert(friend);
            }
        } finally {
            db.close();
        }
    }

    private long insert(Friend friend) {
        SQLiteDatabase db = getWritableDatabase();

        insert(db, friend);
        return -1;
    }

    private long insert(SQLiteDatabase db, Friend friend) {
        try {
            ContentValues values = new ContentValues();
            values.put("_id", friend.id);
            values.put("phoneNumber", friend.phoneNumber);
            values.put("userId", friend.userId);

            long _id = db.insert("friend", "", values);

            return _id;

        } finally {
            db.close();
        }
    }





    private void deleteAll(SQLiteDatabase db) {
        db.execSQL("DELETE FROM friend");
    }

    public int delete(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            int count = db.delete("friend", "_id = ?", new String[] { String.valueOf(contact.id) });

            return count;
        } finally {
            db.close();
        }
    }

    public List<Friend> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        List<Friend> friends = new LinkedList();

        try {
            Cursor cursorFriends = db.query("friend", null, null, null, null, null, null);
            PhoneBook phoneBook = new PhoneBook(context);

            while (cursorFriends.moveToNext()) {
                Friend friend = new Friend();

                friend.id = cursorFriends.getLong(cursorFriends.getColumnIndex("_id"));
                friend.name = phoneBook.getContactName(friend.id);
                friend.phoneNumber = cursorFriends.getString(cursorFriends.getColumnIndex("phoneNumber"));
                friend.userId = cursorFriends.getString(cursorFriends.getColumnIndex("userId"));

                friends.add(friend);
            }
            cursorFriends.close();
        } finally {
            db.close();
        }
        return friends;
    }

}
