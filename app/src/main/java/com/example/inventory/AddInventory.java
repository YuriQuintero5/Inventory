package com.example.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;

public class AddInventory extends AppCompatActivity {
    private ArrayList<Integer> fotos;
    private EditText code, description, quantity, weight, size;
    private StorageReference storageReference;
    private Uri uri;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        code = findViewById(R.id.txtCode);
        description = findViewById(R.id.txtDescription);
        quantity = findViewById(R.id.txtQuantity);
        weight = findViewById(R.id.txtWeight);
        size = findViewById(R.id.txtSize);
        foto = findViewById(R.id.imgFotoSeleccionada);
        fotos = new ArrayList<>();
        fotos.add(R.drawable.images);
        fotos.add(R.drawable.images2);
        fotos.add(R.drawable.images3);

        storageReference = FirebaseStorage.getInstance().getReference();


    }

    public void add(View v){
        String _code, _description, _quantity, _weight, _size, id;
        int foto;
        Inventory inventory;
        InputMethodManager imp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//para qu el teclado se baje
        _code = code.getText().toString();
        _description = description.getText().toString();
        _quantity = quantity.getText().toString();
        _weight = weight.getText().toString();
        _size = size.getText().toString();
        foto = foto_aleatoria();
        id = Datos.getId();
        inventory = new Inventory(_code, _description, _quantity, _weight, _size, foto, id);
        inventory.guardar(new ViewSimpleCallback(v) {
            @Override
            public void execute(boolean ejecutado) {
                if(ejecutado){
                    subir_fotos(Datos.getId());
                    limpiar();
                    InputMethodManager imp =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imp.hideSoftInputFromWindow(code.getWindowToken(), 0);//para qu el teclado se baje
                    Snackbar.make(view, getString(R.string.mensaje_guardado_correcto), Snackbar.LENGTH_LONG).show();
                }
                else{
                    Snackbar.make(view, "The record is stored in the database", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void subir_fotos(String id){
        StorageReference child = storageReference.child(id);
        //Uri uri = Uri.parse("android.resourse://"+R.class.getPackage().getName()+"/"+foto);
        UploadTask uploadTask = child.putFile(uri);
    }



    public void clear(View V){
        limpiar();
    }

    public int foto_aleatoria(){
        int foto_seleccionada;
        Random r = new Random();
        foto_seleccionada = r. nextInt(this.fotos.size());
        return fotos.get(foto_seleccionada);
    }

    public void limpiar(){
        code.setText("");
        description.setText("");
        quantity.setText("");
        weight.setText("");
        size.setText("");
        code.requestFocus();
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AddInventory.this, MainActivity.class);
        startActivity(i);
    }

    public void seleccionar_foto(View v){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Photo"),1);
    }

    protected void onActivityResult(int requesCode, int resultCode, Intent data){
        super.onActivityResult(requesCode, resultCode, data);
        if (requesCode == 1){
            uri = data.getData();
            if (uri != null){
                foto.setImageURI(uri);
            }
        }
    }
}
