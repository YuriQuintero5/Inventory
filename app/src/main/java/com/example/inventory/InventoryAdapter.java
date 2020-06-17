package com.example.inventory;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.PersonaViewHolder>{
    private ArrayList<Inventory> inventories;
    private OnPersonaClickListener clickListener;


    public InventoryAdapter(ArrayList<Inventory> inventories, OnPersonaClickListener clickListener){
        this.inventories = inventories;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PersonaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
       return new PersonaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonaViewHolder holder, int position) {
        final Inventory p = inventories.get(position);
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(p.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.foto);
            }
        });

        //holder.foto.setImageResource(p.getFoto());
        holder.code.setText(p.getCode());
        holder.description.setText(p.getDescription());
        holder.quantity.setText(p.getQuantity());
        holder.weight.setText(p.getWeight());
        holder.size.setText(p.getSize());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onPersonaClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }


    public static class PersonaViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView code;
        private TextView description;
        private TextView quantity;
        private TextView weight;
        private TextView size;
        private View v;

        public PersonaViewHolder(View itemView){
            super(itemView);
            v = itemView;
            foto = v.findViewById(R.id.imgFoto);
            code = v.findViewById(R.id.lblCode);
            description = v.findViewById(R.id.lblDescription);
            quantity = v.findViewById(R.id.lblQuantity);
            weight = v.findViewById(R.id.lblWeight);
            size = v.findViewById(R.id.lblSize);
        }

      }
    public interface OnPersonaClickListener{
        void onPersonaClick(Inventory p);
    }
}
