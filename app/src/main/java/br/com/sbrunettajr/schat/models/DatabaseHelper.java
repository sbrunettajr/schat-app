package br.com.sbrunettajr.schat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import br.com.sbrunettajr.schat.utils.Preference;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String LOG = "DatabaseHelper";

    private static final String  DATABASE_NAME = "schatDb";

    private static final int DATABASE_VERSION = 2;

    // TABLES

    private static final String TABLE_FRIEND = "friend";
    private static final String TABLE_MESSAGE = "message";

    // COLUMN

    private static final String KEY_DATETIME = "datetime";
    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_PHONENUMBER = "phone_number";
    private static final String KEY_SENT = "sent";
    private static final String KEY_USERID = "user_id";

    private static final String KEY_FROMUSERID = "from_user_id";
    private static final String KEY_TOUSERID = "to_user_id";

    // CREATE TABLE

    private static final String CREATE_TABLE_FRIEND =
            "CREATE TABLE " + TABLE_FRIEND + "(" +
                    KEY_ID + " INTEGER NOT NULL, " +
                    KEY_PHONENUMBER + " TEXT NOT NULL, " +
                    KEY_USERID + " TEXT NOT NULL" +
            ");";

    //    private static final String CREATE_TABLE_MESSAGE =
    //            "CREATE TABLE " + TABLE_MESSAGE + "(" +
    //                    KEY_ID + " INTEGER NOT NULL, " +
    //                    KEY_MESSAGE + " TEXT NOT NULL," +
    //                    KEY_DATETIME + " TEXT NOT NULL, " +
    //                    KEY_USERID + " TEXT NOT NULL, " +
    //                    KEY_SENT + " INTEGER NOT NULL" +
    //            ");";

    private static final String CREATE_TABLE_MESSAGE =
            "CREATE TABLE " + TABLE_MESSAGE + "(" +
                    KEY_ID + " INTEGER NOT NULL, " +
                    KEY_MESSAGE + " TEXT NOT NULL," +
                    KEY_DATETIME + " TEXT NOT NULL, " +
                    KEY_FROMUSERID + " TEXT NOT NULL, " +
                    KEY_TOUSERID + " TEXT NOT NULL" +
                    ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FRIEND);
        db.execSQL(CREATE_TABLE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }

    // FRIEND

    //    public long insertFriend(Friend friend) {
    //        ContentValues values = new ContentValues();
    //        values.put(KEY_ID, friend.id);
    //        values.put(KEY_PHONENUMBER, friend.phoneNumber);
    //        values.put(KEY_USERID, friend.userId);
    //
    //        return insert(TABLE_FRIEND, values);
    //    }

    // FRIENDS

    public void insertFriends(List<Friend> friends) {
        deleteFriends();
        for (Friend friend : friends) {
            ContentValues values = new ContentValues();

            values.put(KEY_ID, friend.id);
            values.put(KEY_PHONENUMBER, friend.phoneNumber);
            values.put(KEY_USERID, friend.userId);
            insert(TABLE_FRIEND, values);
        }
    }

    private void deleteFriends() {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            db.execSQL("DELETE FROM " + TABLE_FRIEND);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Friend> selectFriends() {
        SQLiteDatabase db = getWritableDatabase();
        List<Friend> friends = new LinkedList();

        try {
            Cursor cursorFriends = db.query(TABLE_FRIEND, null, null, null, null, null, null);
            PhoneBook phoneBook = new PhoneBook(context);

            while (cursorFriends.moveToNext()) {
                Friend friend = new Friend();
                friend.id = cursorFriends.getLong(cursorFriends.getColumnIndex(KEY_ID));
                friend.name = phoneBook.getContactName(friend.id);
                friend.phoneNumber = cursorFriends.getString(cursorFriends.getColumnIndex(KEY_PHONENUMBER));
                friend.userId = cursorFriends.getString(cursorFriends.getColumnIndex(KEY_USERID));

                friends.add(friend);
            }
            cursorFriends.close();
        } finally {
            db.close();
        }
        return friends;
    }

    // MESSAGE

    public long insertMessage(Message message) {
        ContentValues values = new ContentValues();

        values.put(KEY_ID, message.id);
        values.put(KEY_MESSAGE, message.message);
        values.put(KEY_DATETIME, message.datetime.toString());
        values.put(KEY_FROMUSERID, message.fromUserId);
        values.put(KEY_TOUSERID, message.toUserId);
        return insert(TABLE_MESSAGE, values);
    }

    public List<Chat> selectChats() {
        SQLiteDatabase db = getWritableDatabase();
        List<Chat> chats = new LinkedList();

        String id = Preference.getString(context, Preference.PreferenceName.PREF_USER_ID);

        String selectQuery =
                "SELECT DISTINCT f.id " +
                "     , f.user_id " +
                "  FROM friend f " +
                "     , message m " +
                " WHERE f.user_id  = m.from_user_id" +
                "   AND f.user_id != '" + id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        PhoneBook phoneBook = new PhoneBook(context);

        while (cursor.moveToNext()) {
            String userId = cursor.getString(cursor.getColumnIndex(KEY_USERID));
            String friendName = phoneBook.getContactName(cursor.getLong(cursor.getColumnIndex(KEY_ID)));

            chats.add(new Chat(userId, friendName));
        }
        return chats;
    }



    public void insertMessages(List<Message> messages) {
        for (Message message : messages) {
            ContentValues values = new ContentValues();

            values.put(KEY_ID, message.id);
            values.put(KEY_MESSAGE, message.message);
            values.put(KEY_DATETIME, message.datetime.toString());
            values.put(KEY_FROMUSERID, message.fromUserId);
            values.put(KEY_TOUSERID, message.toUserId);
            insert(TABLE_MESSAGE, values);
        }
    }

    public List<Message> selectMessages() {
        SQLiteDatabase db = getWritableDatabase();
        List<Message> messages = new LinkedList();

        try {
            Cursor cursorMessages = db.query(TABLE_MESSAGE, null, null, null, null, null, null);
            // PhoneBook phoneBook = new PhoneBook(context);

            while (cursorMessages.moveToNext()) {
                Message message = new Message();
                message.id = cursorMessages.getLong(cursorMessages.getColumnIndex(KEY_ID));
                message.message = cursorMessages.getString(cursorMessages.getColumnIndex(KEY_MESSAGE));
                message.datetime = LocalDateTime.parse(cursorMessages.getString(cursorMessages.getColumnIndex(KEY_DATETIME)));
                message.fromUserId = cursorMessages.getString(cursorMessages.getColumnIndex(KEY_FROMUSERID));
                message.toUserId = cursorMessages.getString(cursorMessages.getColumnIndex(KEY_TOUSERID));

                messages.add(message);
            }
            cursorMessages.close();
        } finally {
            db.close();
        }
        return messages;
    }

    // GENERIC

    private long insert(String table, ContentValues values) {
        SQLiteDatabase db = null;
        long id = 0;

        try {
            db = this.getWritableDatabase();
            id = db.insert(table, null, values);

        } finally {
            if (db != null) {
                db.close();
            }
        }
        return id;
    }

}
