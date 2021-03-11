package br.com.sbrunettajr.schat.models;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.LinkedList;
import java.util.List;

import br.com.sbrunettajr.schat.models.Contact;

public class PhoneBook {

    private static final Uri URI = ContactsContract.Contacts.CONTENT_URI;
    private Context context;

    public PhoneBook(Context context) {
        this.context = context;
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new LinkedList();
        Cursor cursorContacts  = getCursorContacts();

        try {
            while (cursorContacts.moveToNext()) {
                long id = cursorContacts.getLong(cursorContacts.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                for (String phoneNumber : getPhoneNumbers(id)) {
                    contacts.add(new Contact(id, name, phoneNumber));
                }
            }
        } finally {
            cursorContacts.close();
        }
        return contacts;
    }

    private Cursor getCursorContacts() {
        return context.getContentResolver().query(
                URI,
                null,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1",
                null, ContactsContract.Contacts.DISPLAY_NAME);
    }

    private Cursor getCursorPhoneNumber(long id) {
        return context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null, null);
    }

    private List<String> getPhoneNumbers(long id) {
        List<String> phoneNumbers = new LinkedList();
        Cursor cursorPhoneNumber  = getCursorPhoneNumber(id);

        try {
            while (cursorPhoneNumber.moveToNext()) {
                String phoneNumber = cursorPhoneNumber.getString(cursorPhoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                phoneNumbers.add(phoneNumber.trim());
            }
        } finally {
            cursorPhoneNumber.close();
        }
        return phoneNumbers;
    }

    public String getContactName(long id) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        Cursor cursor = context.getContentResolver().query(
                uri,
                null,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1",
                null,
                null);

        if (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
        }
        return null;
    }

}
