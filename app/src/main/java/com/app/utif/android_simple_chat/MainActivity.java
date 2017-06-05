package com.app.utif.android_simple_chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.utif.android_simple_chat.adapter.AdapterUsr;
import com.app.utif.android_simple_chat.auth.AuthAct;
import com.app.utif.android_simple_chat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AuthAct {
    private ProgressBar mPbar;
    private RecyclerView mRecylerUsr;

    private List<String> mUsrKeyList;
    private String mUsrId;

    private AdapterUsr mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPbar = (ProgressBar) findViewById(R.id.mProgressBar);
        mRecylerUsr = (RecyclerView) findViewById(R.id.mRecyclerUsr);

        showProgressBar();

        //set instance
        mRefUsr(); mAuth();

        //set supporting method
        setRecyclerUsr();
        setUsrKeyList();
        setAuthListener();
    }

    public DatabaseReference mRefUsr(){
        return FirebaseDatabase.getInstance().getReference("users");
    }

    public FirebaseAuth mAuth(){
        return FirebaseAuth.getInstance();
    }

    public FirebaseUser mUsr(){
        return mAuth().getCurrentUser();
    }

    private void setRecyclerUsr() {
        mAdapter = new AdapterUsr(this, new ArrayList<User>());

        mRecylerUsr.setLayoutManager(new LinearLayoutManager(this));
        mRecylerUsr.setHasFixedSize(true);
        mRecylerUsr.setAdapter(mAdapter);
    }

    private void setUsrKeyList() {
        mUsrKeyList = new ArrayList<String>();
    }

    private void setAuthListener() {

    }

    private FirebaseAuth.AuthStateListener mAuthListener(){
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                hideProgressBar();
                if (mUsr() != null){
                    mUsr().getUid();
                    queryingUsr();
                }
            }
        };
    }

    private ChildEventListener mChildListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    String userId = dataSnapshot.getKey();
                    if (userId.equals(mUsrId)){
                        User modelSender = dataSnapshot.getValue(User.class);

                        mAdapter.setCurrentUsr(userId, modelSender.getTime());
                    }else {
                        User modelRecipient = dataSnapshot.getValue(User.class);
                        modelRecipient.setRecipient(userId);

                        mUsrKeyList.add(userId);
                        mAdapter.refill(modelRecipient);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    String userId = dataSnapshot.getKey();

                    if (userId.equals(mUsrId)){
                        User model = dataSnapshot.getValue(User.class);

                        int index = mUsrKeyList.indexOf(userId);
                        if (index > -1){
                            mAdapter.changeUsr(index, model);
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void queryingUsr() {
        mRefUsr().limitToFirst(100).addChildEventListener(mChildListener());
    }

    private void clearCurrentUsr(){
        mAdapter.cleanUp();
        mUsrKeyList.clear();
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth().addAuthStateListener(mAuthListener());
    }

    @Override
    protected void onStop() {
        super.onStop();

        clearCurrentUsr();

        mRefUsr().removeEventListener(mChildListener());
        mAuth().removeAuthStateListener(mAuthListener());
        /*if (mChildListener() != null){
        }
        if (mAuthListener() != null){
        }*/
    }

    private void showProgressBar(){
        mPbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        if(mPbar.getVisibility()==View.VISIBLE) {
            mPbar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_login:
                signIn();
                break;
            case R.id.item_logout:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
