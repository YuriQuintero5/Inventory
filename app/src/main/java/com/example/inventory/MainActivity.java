package com.example.inventory;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdaptadorPersona.OnPersonaClickListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab;
        RecyclerView lstPersonas;
        final ArrayList<Inventory> inventories;
        LinearLayoutManager llm;
        final AdaptadorPersona adapter;

        DatabaseReference databaseReference;
        String db = "Personas";
        lstPersonas = findViewById(R.id.lstPersonas);

        inventories = new ArrayList<>();
        llm = new LinearLayoutManager(this);
        adapter = new AdaptadorPersona(inventories, this);
        llm.setOrientation(RecyclerView.VERTICAL);
        lstPersonas.setLayoutManager(llm);
        lstPersonas.setAdapter(adapter);
        fab = findViewById(R.id.btnAgregar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    inventories.clear();
                    if (dataSnapshot.exists()){
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            Inventory p = snapshot.getValue(Inventory.class);
                            inventories.add(p);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    Datos.setInventories(inventories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void agregar(View v) {
       Intent intent;
       intent = new Intent(MainActivity.this, AddInventory.class);
       startActivity(intent);
       //finish();


    }

    @Override
    public void onPersonaClick(Inventory p) {
        Intent  intent;
        Bundle  bundle;

        bundle = new Bundle();

        bundle.putString("id", p.getId());
        bundle.putString("code", p.getCode());
        bundle.putString("description", p.getDescription());
        bundle.putString("quantity", p.getQuantity());
        bundle.putString("weight", p.getWeight());
        bundle.putString("size", p.getSize());
        bundle.putInt("foto", p.getFoto());

        intent = new Intent(MainActivity.this, DetallePersona.class);
        intent.putExtra("datos",bundle);
        startActivity(intent);
        //finish();
    }
}
