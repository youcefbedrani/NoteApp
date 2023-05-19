package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class singup extends AppCompatActivity {
    private EditText mSingUpemail , mSingUppassword;
    private RelativeLayout msingup;
    private TextView mgotologin;
    private FirebaseAuth firebaseAth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        getSupportActionBar().hide();

        mSingUpemail = findViewById(R.id.SingUpemail);
        mSingUppassword = findViewById(R.id.SingUppassword);
        msingup = findViewById(R.id.singup);
        mgotologin = findViewById(R.id.gotologin);

        firebaseAth = FirebaseAuth.getInstance();

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(singup.this , MainActivity.class);
                startActivity(intent);
            }
        });

        msingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mSingUpemail.getText().toString().trim();
                String password = mSingUppassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Your Email or Password", Toast.LENGTH_SHORT).show();
                }else if(password.length()<8){
                    Toast.makeText(getApplicationContext(), "Your Password Should Grater Then 7 Dijits", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerfication();
                            }else{
                                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    //Send Email Vefication
    private void sendEmailVerfication(){
        FirebaseUser firebaseUser = firebaseAth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification Email is Sent ,Verify and Login Again", Toast.LENGTH_SHORT).show();
                    firebaseAth.signOut();
                    finish();
                    startActivity(new Intent(singup.this,MainActivity.class));
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Failed To Send Email Verification", Toast.LENGTH_SHORT).show();
        }
    }
}