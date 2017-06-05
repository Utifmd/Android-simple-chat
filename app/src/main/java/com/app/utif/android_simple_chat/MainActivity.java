package com.app.utif.android_simple_chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public DatabaseReference mRef(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public FirebaseAuth mAuth(){
        return FirebaseAuth.getInstance();
    }

    public FirebaseUser mUsr(){
        return mAuth().getCurrentUser();
    }
}
