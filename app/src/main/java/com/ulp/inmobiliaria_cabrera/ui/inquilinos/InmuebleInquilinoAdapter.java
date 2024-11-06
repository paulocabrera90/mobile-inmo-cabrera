package com.ulp.inmobiliaria_cabrera.ui.inquilinos;

import android.content.Context;
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

public class InmuebleInquilinoAdapter extends RecyclerView.Adapter<InmuebleInquilinoAdapter.ViewHolder>{

    private Context context;
    private List<Inmueble> listaInmueble ;
    private LayoutInflater layoutInflater;

    public InmuebleInquilinoAdapter(Context context, List<Inmueble> listaInmueble, LayoutInflater layoutInflater) {
        this.context = context;
        this.listaInmueble = listaInmueble;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public InmuebleInquilinoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInmuebleBinding binding = ItemInmuebleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InmuebleInquilinoAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleInquilinoAdapter.ViewHolder holder, int position) {
        final Inmueble inmueble = listaInmueble.get(position);
        holder.binding.textDireccion
                .setText("Dirección: " + String.valueOf(listaInmueble.get(position).getDireccion()));

        holder.binding.textPrecio
                .setText("Nombres : " + String.valueOf(listaInmueble.get(position).getNombreInmueble()));

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
                        .navigate(R.id.action_nav_inmuebles_contrato_to_nav_inquilino_detalle, bundle);
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
