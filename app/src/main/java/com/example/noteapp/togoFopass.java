package com.example.noteapp;

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
import com.google.firebase.auth.FirebaseAuth;

public class togoFopass extends AppCompatActivity {

    private EditText mforgotPass;
    private Button mgetpass;
    private TextView mgotologinscreen;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_togo_fopass);

        getSupportActionBar().hide();

        mforgotPass = findViewById(R.id.forgotPass);
        mgetpass = findViewById(R.id.getpass);
        mgotologinscreen = findViewById(R.id.gotologinscreen);

        firebaseAuth = FirebaseAuth.getInstance();

        mgotologinscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(togoFopass.this , MainActivity.class);
                startActivity(intent);
            }
        });

        mgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mforgotPass.getText().toString().trim();
                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Your Email First", Toast.LENGTH_SHORT).show();
                }else{
                    //we have to send email
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Mail sent , Verify Your Email",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(togoFopass.this , MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Email is Wrong or Does't Exist",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}