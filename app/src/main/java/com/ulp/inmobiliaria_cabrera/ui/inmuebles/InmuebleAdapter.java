package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.ItemInmuebleBinding;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
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
        ItemInmuebleBinding binding = ItemInmuebleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleAdapter.ViewHolder holder, int position) {
        //defino lo q va adentro del card
        final Inmueble inmueble = listaInmueble.get(position);
        holder.binding.textDireccion
                .setText("Dirección: " + String.valueOf(inmueble.getDireccion()));
        holder.binding.textHabilitado
                .setText("Estado: " + String.valueOf(inmueble.isActivo()?"Activo":"Inactivo"));
        holder.binding.textPrecio
                .setText("Precio: $" + String.format("$%.2f", inmueble.getPrecio()));

        int backgroundColor = !inmueble.isActivo() ? Color.parseColor("#ff7a5d") : Color.GRAY;
        holder.binding.cardViewInmueble.setCardBackgroundColor(backgroundColor);

        String imagenBase64 = inmueble.getImageBlob();

        Glide.with(context)
                .load(imagenBase64 != null ? "data:image/jpeg;base64," + imagenBase64 : null)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.not_found)
                .error(R.drawable.not_found)
                .into(holder.binding.imageInmueble);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", inmueble.getId());
                bundle.putInt("idPropietario", inmueble.getIdPropietario());
                Navigation.findNavController(view)
                        .navigate(R.id.action_nav_inmuebles_to_nav_inmueble_detalle, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmueble.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemInmuebleBinding binding;

        public ViewHolder(@NonNull ItemInmuebleBinding binding) {

            super(binding.getRoot());
            this.binding = binding;

        }
    }

}
