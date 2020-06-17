package com.example.inventory;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Datos {
    private static String db = "Personas";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private static ArrayList<Inventory> inventories = new ArrayList();


    public static String getId() {
        return databaseReference.push().getKey();
    }

    public static void guardar(Inventory p) {
        databaseReference.child(db).child(p.getId()).setValue(p);
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
