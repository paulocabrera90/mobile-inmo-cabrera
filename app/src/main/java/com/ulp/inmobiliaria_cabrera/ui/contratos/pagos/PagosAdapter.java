package com.ulp.inmobiliaria_cabrera.ui.contratos.pagos;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.ItemPagoBinding;
import com.ulp.inmobiliaria_cabrera.models.Pago;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolder> {
    private Context context;
    private List<Pago> pagosList ;
    private LayoutInflater layoutInflater;

    public PagosAdapter(List<Pago> pagosList, Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.pagosList = pagosList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public PagosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPagoBinding binding = ItemPagoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PagosAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PagosAdapter.ViewHolder holder, int position) {
        final Pago pago = pagosList.get(position);
        holder.binding.textNroPago.setText("Nro de Pago: "
                + pago.getNumeroPago());
        holder.binding.textContrato.setText("Nro de Contrato: 00000" + pago.getContrato().getId());
        holder.binding.textEstado.setText("Estado: "
                + pago.getEstado().getDisplayName());

        int backgroundColor = pago.isAnulado() ? Color.parseColor("#ff7a5d") : Color.GRAY;
        holder.binding.cardViewPago.setCardBackgroundColor(backgroundColor);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idPago", pago.getId());
                Navigation.findNavController(view)
                        .navigate(R.id.action_nav_pagos_to_nav_pagos_detalle, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pagosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemPagoBinding binding;

        public ViewHolder(@NonNull ItemPagoBinding binding) {

            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
