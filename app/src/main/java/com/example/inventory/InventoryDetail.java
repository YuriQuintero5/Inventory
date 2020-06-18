package com.example.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class InventoryDetail extends AppCompatActivity {
    private Inventory p;
    TextView code, description, quantity, weight, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ImageView foto;
        Intent intent;
        Bundle bundle;
        String _code, _description, _quantity, _weight, _size, id;
        int fot;
        StorageReference storageReference;

        foto = findViewById(R.id.imgFotoDetalle);
        code = findViewById(R.id.lblCode);
        description = findViewById(R.id.lblDescription);
        quantity = findViewById(R.id.lblQuantity);
        weight = findViewById(R.id.lblWeight);
        size = findViewById(R.id.lblSize);

        intent =getIntent();
        bundle = intent.getBundleExtra("datos");


        fot = bundle.getInt("foto");
        id = bundle.getString("id");
        _code = bundle.getString("code");
        _description = bundle.getString("description");
        _quantity = bundle.getString("quantity");
        _weight = bundle.getString("weight");
        _size = bundle.getString("size");


        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto);
                foto.setImageURI(null);
                foto.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                /// Uri uri = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/drawable/"+R.drawable.picture);
                Uri uri = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/drawable/picture");
                Picasso.get().load(uri).into(foto);
                foto.setImageURI(null);
                foto.setImageURI(uri);
            }
        });

        //foto.setImageResource(fot);
        code.setText(_code);
        description.setText(_description);
        quantity.setText(_quantity);
        weight.setText(_weight);
        size.setText(_size);

        p = new Inventory(_code, _description, _quantity, _weight, _code, fot, id);

    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(InventoryDetail.this, MainActivity.class);
        startActivity(i);
    }


    public void eliminar(View v){
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_inventory);
        builder.setMessage(R.string.delete_inventory_detail);
        positivo = getString(R.string.delete_inventory_confirm);
        negativo = getString(R.string.delete_inventory_cancel);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                p.eliminar();
                onBackPressed();
            }
        });
        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
