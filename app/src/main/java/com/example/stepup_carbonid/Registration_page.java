package com.example.stepup_carbonid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration_page extends AppCompatActivity {

    private static final String TAG = "RegistrationPage";

    FirebaseAuth mAuth;
    EditText email, password, repassword, username;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        username = findViewById(R.id.username);
        registerButton = findViewById(R.id.registration);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userRePassword = repassword.getText().toString().trim();

                if (userPassword.equals(userRePassword)) {
                    mAuth.createUserWithEmailAndPassword(userMail, userPassword)
                            .addOnCompleteListener(Registration_page.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Registration_page.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Registration_page.this, "Passwords do not match.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Redirect to another activity or update UI
            Intent intent = new Intent(Registration_page.this, login_page.class);
            startActivity(intent);
            finish();
        }
    }
}
