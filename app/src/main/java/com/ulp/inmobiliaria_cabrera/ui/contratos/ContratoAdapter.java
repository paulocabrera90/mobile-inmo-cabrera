package com.ulp.inmobiliaria_cabrera.ui.contratos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.ItemContratoBinding;
import com.ulp.inmobiliaria_cabrera.models.Contrato;
import com.ulp.inmobiliaria_cabrera.models.enumMod.EstadoContrato;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private Context context;
    private List<Contrato> contratoList ;
    private LayoutInflater layoutInflater;

    public ContratoAdapter(List<Contrato> contratoList, Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.contratoList = contratoList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ContratoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContratoBinding binding = ItemContratoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContratoAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolder holder, int position) {
        final Contrato contrato = contratoList.get(position);
        holder.binding.textNombre.setText("Nombre del Inquilino: "
                + contrato.getInquilino().getNombreCompleto());
        holder.binding.textNroContrato.setText(String.format("Nro de Contrato: %06d", + contrato.getId()));
        holder.binding.textHabilitado.setText("Estado: "
                + (EstadoContrato.VIGENTE.getDisplayName()));
        holder.binding.textPrecio.setText("Monto alquiler: "
                + String.format("$%.2f", contrato.getMontoAlquiler()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idContrato", contrato.getId());
                Navigation.findNavController(view)
                        .navigate(R.id.action_nav_contrato_to_nav_contratos_detalle, bundle);
               }
        });
    }

    @Override
    public int getItemCount() {
        return contratoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemContratoBinding binding;

        public ViewHolder(@NonNull ItemContratoBinding binding) {

            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
