package br.com.sbrunettajr.schat.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private static final String PREF_ID = "SCHAT";

    public static void setString(Context context, PreferenceName key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key.toString(), value);
        editor.commit();
    }

    public static String getString(Context context, PreferenceName key) {
        return context.getSharedPreferences(PREF_ID, 0).getString(key.toString(), null);
    }

    public enum PreferenceName {
        PREF_USER_NAME,
        PREF_USER_ID
    };

}
