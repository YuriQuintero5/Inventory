package com.example.inventory;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Datos {
    private static Inventory _inventory;
    private static String message = "";

    private static String db = "Inventory";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private static ArrayList<Inventory> inventories = new ArrayList();


    public static String getId() {
        return databaseReference.push().getKey();
    }

    public static void guardar(Inventory p, final SimplaCallback callback) {
        _inventory = p;

        filterByCodeExist(p.getCode(), new IBasicCallback() {
            @Override
            public void onSuccess() {
                databaseReference.child(db).child(_inventory.getId()).setValue(_inventory);
                callback.execute(true);
            }
            @Override
            public void onError(String err) {
                callback.execute(false);
            }
        });
        //message = "The record is stored in the database. Code: "+p.getCode()+", Description: "+p.getDescription();
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
    public static String GetMensaje(){
        return message;
    }


    public static ArrayList<Inventory> obtener() {
        return inventories;
    }


    public static void setInventories(ArrayList<Inventory> inventories) {
        inventories = inventories;
    }

    public static void eliminar(Inventory p) {
       databaseReference.child(db).child(p.getId()).removeValue();
       storageReference.child(p.getId()).delete();
    }
}
