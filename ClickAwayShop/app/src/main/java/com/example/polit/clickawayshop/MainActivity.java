package com.example.polit.clickawayshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button mapButton;

    FirebaseFirestore mDb;
    List<Store> mStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapButton = findViewById(R.id.map_button);

        setListeners();

        mStores = new ArrayList<>();
        //populateStores();

        mDb = FirebaseFirestore.getInstance();
        //populateDatabaseWithStores();
    }

    private void setListeners() {
        mapButton.setOnClickListener(v -> goToMap());
    }

    private void goToMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void populateStores() {
        Log.d("TGM", "Entered populateStores()");
        mStores.add(new Store("TGM Athens", 38.000950, 23.752333));
        mStores.add(new Store("TGM Zografou", 37.976251, 23.770611));
        mStores.add(new Store("TGM Kallitheas", 37.953633, 23.697144));
        mStores.add(new Store("TGM Agias Paraskevis", 38.011229, 23.826037));
        mStores.add(new Store("TGM Chalandriou", 38.024263, 23.798835));
    }

    public void populateDatabaseWithStores() {
        Log.d("TGM", "Entered populateDatabaseWithStores()");
        Iterator<Store> iterator = mStores.iterator();

        while (iterator.hasNext()) {
            Store store = iterator.next();
            mDb.collection("stores").add(store)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TGM", "Store was successfully written to the database.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TGM", "Something went wrong: " + e);
                }
            });
        }
    }
}