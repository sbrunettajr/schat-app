package br.com.sbrunettajr.schat.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    public static boolean validate(Activity activity, int requestCode, String... permissions) {
        List<String> list = new ArrayList();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            return true;
        }



        String[] newPermissions = new String[list.size()];

        list.toArray(newPermissions);
        ActivityCompat.requestPermissions(activity, newPermissions, 1);
        return false;



    }

}
