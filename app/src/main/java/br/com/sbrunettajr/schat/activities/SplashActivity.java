package br.com.sbrunettajr.schat.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.activities.signup.PhoneActivity;
import br.com.sbrunettajr.schat.utils.Preference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (isLogged()) {
            signIn();
        } else {
            signUp();
        }
    }

    private boolean isLogged() {
        return Preference.getString(SplashActivity.this, Preference.PreferenceName.PREF_USER_ID) != null;
    }

    private void signIn() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
    }

    private void signUp() {
        Intent intent = new Intent(SplashActivity.this, PhoneActivity.class);

        startActivity(intent);
        finish();
    }

    // private void grantPermission() {
    //    String[] permissions = new String[] {
    //            Manifest.permission.READ_CONTACTS
    //    };
    //    boolean OK = Permission.validate(this, 0, permissions);
    //
    //    if (OK) {
    //        startActivity(new Intent(this, MainActivity.class));
    //        finish();
    //    }
    // }

    // @Override
    // public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //     super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //
    //    for (int result : grantResults) {
    //        if (result == PackageManager.PERMISSION_DENIED) {
    //            grantPermission();
    //        }
    //    }
    // }
}