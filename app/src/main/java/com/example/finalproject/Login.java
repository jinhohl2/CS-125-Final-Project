package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = (Button) findViewById(R.id.signUp);
        tvSignIn = findViewById(R.id.goLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                String nick = email.split("@")[0];

                if (email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (pwd.length() < 6) {
                    password.setError("Password should be more than 5 digits");
                    password.requestFocus();
                }
                else if(!(email.isEmpty() && pwd.isEmpty() && nick.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(Login.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                validateDetails(email, "green", nick, "");
                                Intent i = new Intent(Login.this, MainActivity.class);
                                i.putExtra("nick", nick);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Login.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignIn.setOnClickListener(v -> {
            Intent i = new Intent(Login.this, LoginActivity.class);
            startActivity(i);
        });

    }
    private void validateDetails(final String email, final String status, final String nick, final String location) {
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference("users");
        User user = new User(email, status, nick, location);
        rootref.child(nick).setValue(user);
    }
}
