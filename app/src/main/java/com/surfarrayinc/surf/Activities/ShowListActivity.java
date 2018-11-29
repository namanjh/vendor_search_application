package com.surfarrayinc.surf.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.surfarrayinc.surf.R;
import com.surfarrayinc.surf.data.ListRecyclerAdapter;
import com.surfarrayinc.surf.model.vendor;


import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    private List<vendor> vendorlist;
    private RecyclerView recyclerView;
    private ListRecyclerAdapter listRecyclerAdapter;

    //search
    private FirebaseDatabase getmDatabase;
    private DatabaseReference getmDatabaseReference;
    private List<vendor> vendors;
    private ListRecyclerAdapter searchRecyclerAdapter;

    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        //authentication
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        //initialize the database and the recyclerview adapter
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Vendor");
        mDatabaseReference.keepSynced(true);

        vendorlist = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerShowListID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //for search
        //reference initialization
        vendors = new ArrayList<>();
        getmDatabase = FirebaseDatabase.getInstance();


        //searchbar initialization
        materialSearchBar =  findViewById(R.id.search_here);
        materialSearchBar.setSpeechMode(false);
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser = firebaseAuth.getCurrentUser();
                if(mUser != null){
                   /* Toast.makeText(ShowListActivity.this,"Signed in",Toast.LENGTH_LONG).show();*/
                }else if(mUser==null){
                    Toast.makeText(ShowListActivity.this,"Not signed in",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ShowListActivity.this,MainActivity.class));
                }
            }
        };


        //for suggestion only
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               //changing the suggestion list
                List <String> suggest = new ArrayList<String>();
                for(String Search: suggestList){
                    if(Search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(Search);
                    }
                    materialSearchBar.setLastSuggestions(suggest);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            

        });


        //when search action is done
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when searchbar is closed
                //restore original adapter
                if(!enabled){
                    recyclerView.setAdapter(listRecyclerAdapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search finish
                //show result of searchADAPTER
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        vendors.clear();
        getmDatabaseReference = getmDatabase.getReference().child("Vendor");
        getmDatabaseReference.keepSynced(true);
        getmDatabaseReference.orderByChild("vname").equalTo(text.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnap: dataSnapshot.getChildren()){
                     vendor vend = postSnap.getValue(vendor.class);
                     vendors.add(vend);
                     searchRecyclerAdapter = new ListRecyclerAdapter(ShowListActivity.this,vendors);
                     recyclerView.setAdapter(searchRecyclerAdapter);
                     searchRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void loadSuggest() {
        mDatabaseReference.orderByChild("vname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnap: dataSnapshot.getChildren()){
                    vendor vend = postSnap.getValue(vendor.class);
                    suggestList.add(vend.getVname());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_signout:
                if(mUser != null && mAuth != null) {
                    mAuth.signOut();
                    startActivity(new Intent(ShowListActivity.this,MainActivity.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        vendorlist.clear();
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                vendor vend = dataSnapshot.getValue(vendor.class);
                vendorlist.add(vend);

                listRecyclerAdapter = new ListRecyclerAdapter(ShowListActivity.this,vendorlist);
                recyclerView.setAdapter(listRecyclerAdapter);
                listRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
