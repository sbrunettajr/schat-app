package br.com.sbrunettajr.schat.activities.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.activities.MainActivity;
import br.com.sbrunettajr.schat.models.User;
import br.com.sbrunettajr.schat.services.SignInService;
import br.com.sbrunettajr.schat.utils.Preference;

public class PhoneActivity extends AppCompatActivity {

    EditText et_phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        getSupportActionBar().hide();

        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);

        Button btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

    }

    private void onSubmit() {
        User user = isRegisteredUser();

        if (user != null) {
            Preference.setString(PhoneActivity.this, Preference.PreferenceName.PREF_USER_ID, user.id);
            signIn();
        } else {
            nextStep();
        }
    }

    private User isRegisteredUser() {
        try {
            return new SignInService(et_phoneNumber.getText().toString()).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void signIn() {
        Intent intent = new Intent(PhoneActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
    }

    private void nextStep() {
        Intent intent = new Intent(PhoneActivity.this, NameActivity.class);

        intent.putExtra("phoneNumber", et_phoneNumber.getText().toString());
        startActivity(intent);
    }

    //    private void signIn() {
    //        User user = new User();
    //        user.name = getIntent().getStringExtra("name");
    //        user.phoneNumber = et_phoneNumber.getText().toString();
    //
    //        try {
    //            user = new SignInService(user).execute().get();
    //
    //            if (user.id != null) {
    //
    //                Preference.setString(PhoneActivity.this, Preference.PreferenceName.PREF_USER_ID, user.id);
    //
    //                Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
    //
    //                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //                startActivity(intent);
    //            } else {
    //                Toast.makeText(PhoneActivity.this, "An error has occured", Toast.LENGTH_SHORT).show();
    //            }
    //        } catch (InterruptedException | ExecutionException e) {
    //            e.printStackTrace();
    //        }
    //
    //    }

}