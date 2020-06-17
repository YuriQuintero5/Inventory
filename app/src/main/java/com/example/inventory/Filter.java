package com.example.inventory;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Filter {

    private static String db = "Inventory";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public static Inventory filterByCode(ArrayList<Inventory> list, String code) {
        Inventory result = null;
        for (Inventory item: list) {

            if (item.getCode().toLowerCase().equals(code.toLowerCase())) {
                result = item;
            }
        }
        return result;
    }
    public static void filterByCodeExist(String code, final IBasicCallback callback) {
        Query q = databaseReference.child(db).orderByChild("code").equalTo(code).limitToFirst(1);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    callback.onSuccess();
                }else{
                    callback.onError("registered");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}