package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;

import java.io.Serializable;
import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder>{

    private Context context;
    private List<Inmueble> listaInmueble ;
    private LayoutInflater layoutInflater;

    public InmuebleAdapter(List<Inmueble> listaInmueble, Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.listaInmueble = listaInmueble;
        this.layoutInflater = layoutInflater;
    }
    @NonNull
    @Override
    public InmuebleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = layoutInflater.inflate(R.layout.item_mueble, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.ViewHolder holder, int position) {
        final Inmueble inmueble = listaInmueble.get(position);
       // holder.tvPrecio.setText("$ " + String.valueOf(listaInmueble.get(position).getPrecio()));
        holder.tvDireccion.setText(listaInmueble.get(position).getNombreInmueble());
//        Glide.with(context)
//                .load(listaInmueble.get(position).getImagen())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.ivInmueble);
//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble",(Serializable) inmueble);
               // Navigation.findNavController(view).navigate(R.id.nav_inmueble_detalle, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmueble.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion, tvPrecio;
        private ImageView ivInmueble;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvDireccion = itemView.findViewById(R.id.editTextDireccion);
//            tvPrecio = itemView.findViewById(R.id.texc);
//            ivInmueble = itemView.findViewById(R.id.imagenViewInmueble);
        }
    }
}
