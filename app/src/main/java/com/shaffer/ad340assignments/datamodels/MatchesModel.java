package com.shaffer.ad340assignments.datamodels;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shaffer.ad340assignments.models.MatchItem;

import java.util.HashMap;
import java.util.function.Consumer;

public class MatchesModel {

    private DatabaseReference mDatabase;
    private HashMap<DatabaseReference, ValueEventListener> listeners;

    public MatchesModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listeners = new HashMap<>();
    }

    public void addMatchItem(MatchItem item) {
        DatabaseReference matchItemsRef = mDatabase.child("matches");
        matchItemsRef.push().setValue(item);
    }

    public void getMatchItems(Consumer<DataSnapshot> dataChangedCallback, Consumer<DatabaseError> dataErrorCallback) {
        DatabaseReference matchItemsRef = mDatabase.child("matches");
        ValueEventListener matchItemsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataChangedCallback.accept(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataErrorCallback.accept(databaseError);
            }
        };
        matchItemsRef.addValueEventListener(matchItemsListener);
        listeners.put(matchItemsRef, matchItemsListener);
    }

    public void updateMatchItemById(MatchItem item) {
        DatabaseReference matchItemsRef = mDatabase.child("matches");  //"matchItems"?
        matchItemsRef.child(item.uid).setValue(item);
    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(Query::removeEventListener);
    }
}
