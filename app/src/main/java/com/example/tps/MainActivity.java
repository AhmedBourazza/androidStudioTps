package com.example.tps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button loginBtn,signUpBtn,googleSignInBtn,signUptn,fbloginBtn;
    EditText uidEdit,passEdit;
    GoogleSignInClient client;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.loginBtn);
        uidEdit = findViewById(R.id.uidEdit);
        passEdit = findViewById(R.id.PassEdit);
        googleSignInBtn = findViewById(R.id.googleBtn);
        signUpBtn = findViewById(R.id.signupBtn);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,options);
        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); // Sign out the Firebase user
                client.signOut();
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail()||!validatePass())
                {

                }
                else {
                    checkUser();
                }
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,signUp.class);
                startActivity(intent);
            }
        });

        //facebook auth
        callbackManager = CallbackManager.Factory.create();
        fbloginBtn=findViewById(R.id.login_button2);
        fbloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    public  Boolean validateEmail()
    {
        String email = uidEdit.getText().toString();
        if (email.isEmpty())
        {
            uidEdit.setError("Email can not be empty");
            return false;
        }
        else {
            uidEdit.setError(null);
    return true;
        }
    }
    public  Boolean validatePass()
    {
        String pass = passEdit.getText().toString();
        if (pass.isEmpty())
        {
            passEdit.setError("Email can not be empty");
            return false;
        }
        else {
            passEdit.setError(null);
            return true;
        }
    }
    public  void checkUser()
    {
        String uid = uidEdit.getText().toString().trim();
        String pass = passEdit.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkEmaildb = reference.orderByChild("username").equalTo(uid);
        checkEmaildb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    uidEdit.setError(null);
                    String passDb = snapshot.child(uid).child("password").getValue(String.class);

                  //  String passDb = snapshot.child(email).child("password").getValue(String.class);
                    if(pass.equals(passDb)) {
                        // Passwords match, log in successful
                        uidEdit.setError(null);
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                    }
                    else {
                        passEdit.setError("Invalid information");
                    }
                }
                else {
                    uidEdit.setError("User does not exist !");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234)
        {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent intent = new Intent(getApplicationContext(),Home.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            catch (ApiException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }

    }
}