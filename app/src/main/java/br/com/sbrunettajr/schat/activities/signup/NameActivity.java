package br.com.sbrunettajr.schat.activities.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import br.com.sbrunettajr.schat.R;
import br.com.sbrunettajr.schat.activities.MainActivity;
import br.com.sbrunettajr.schat.models.User;
import br.com.sbrunettajr.schat.services.SignUpService;
import br.com.sbrunettajr.schat.utils.Preference;

public class NameActivity extends AppCompatActivity {

    EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        getSupportActionBar().hide();

        et_name = (EditText) findViewById(R.id.et_name);

        Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        User user = new User();
        user.name = et_name.getText().toString();
        user.phoneNumber = getIntent().getStringExtra("phoneNumber");

        try {
            user = new SignUpService(user).execute().get();

            if (user.id != null) {
                Preference.setString(NameActivity.this, Preference.PreferenceName.PREF_USER_ID, user.id);
                signIn();
            } else {
                Toast.makeText(NameActivity.this, "An error has occured", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException |  ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void signIn() {
        Intent intent = new Intent(NameActivity.this, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}