package com.example.projectpark;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter <MainModel, MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
        holder.nama.setText(model.getNama());
        holder.merk.setText(model.getMerk());
        holder.pemilik.setText(model.getPemilik());
        holder.plat.setText(model.getPlat());

        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1650)
                        .create();


                View view = dialogPlus.getHolderView();

                EditText pemilik = view.findViewById(R.id.txtOwnerName);
                EditText merk = view.findViewById(R.id.txtMerk);
                EditText nama = view.findViewById(R.id.txtVName);
                EditText plat = view.findViewById(R.id.txtPlat);
                EditText turl = view.findViewById(R.id.txtPhotoUrl);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                pemilik.setText(model.getPemilik());
                merk.setText(model.getMerk());
                nama.setText(model.getNama());
                plat.setText(model.getPlat());
                turl.setText(model.getTurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("pemilik",pemilik.getText().toString());
                        map.put("merk",merk.getText().toString());
                        map.put("nama",nama.getText().toString());
                        map.put("plat",plat.getText().toString());
                        map.put("turl",turl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("parking")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nama.getContext(),"Data Updated Syccessfully.",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nama.getContext(),"Data Update Failed.",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });
        Objects.requireNonNull(holder).btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.pemilik.getContext());
                builder.setTitle("Are You Sure Want to Delete This Data?");
                builder.setMessage("Data Deleted cant be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("parking")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.pemilik.getContext(),"Cancelled.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView merk, nama, pemilik, plat;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            nama = (TextView) itemView.findViewById(R.id.carnametext);
            merk = (TextView) itemView.findViewById(R.id.merktext);
            pemilik = (TextView) itemView.findViewById(R.id.nametext);
            plat = (TextView) itemView.findViewById(R.id.plattext);

            btnEdit =(Button) itemView.findViewById(R.id.btnEdit);
            btnDelete =(Button) itemView.findViewById(R.id.btnDelete);
            }
    }
}