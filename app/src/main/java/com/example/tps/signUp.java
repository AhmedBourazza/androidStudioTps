package com.example.tps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {
    Button suBtn,lgbtn;
    EditText uidEdt,emailEdt,fnameEdt,passEdt;
    FirebaseDatabase database ;
    DatabaseReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        uidEdt = findViewById(R.id.UserNameEdit);
        emailEdt = findViewById(R.id.EmailEdt);
        fnameEdt = findViewById(R.id.FullNameEdit); // Replace with your actual EditText ID
        passEdt  =findViewById(R.id.passEdt);
        lgbtn=findViewById(R.id.lgbtn);
        suBtn = findViewById(R.id.suBtn);
        suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String uid = uidEdt.getText().toString();
                String fullName = fnameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String pass = passEdt.getText().toString();
                HelperClass helperClass = new HelperClass(uid,fullName,email,pass);
                reference.child(uid).setValue(helperClass);
                Toast.makeText(signUp.this, "You have SignUp successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signUp.this,MainActivity.class);
                startActivity(intent);
            }
        });
        lgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signUp.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}