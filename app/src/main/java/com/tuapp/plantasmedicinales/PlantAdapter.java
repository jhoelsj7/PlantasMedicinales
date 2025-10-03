package com.tuapp.plantasmedicinales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<Plant> plantList;
    private OnPlantClickListener listener;

    // Añadir también OnItemClickListener para compatibilidad
    public interface OnItemClickListener {
        void onItemClick(Plant plant);
    }

    public interface OnPlantClickListener {
        void onPlantClick(Plant plant);
    }

    public PlantAdapter(List<Plant> plantList, OnPlantClickListener listener) {
        this.plantList = plantList;
        this.listener = listener;
    }

    // Constructor adicional para OnItemClickListener
    public PlantAdapter(List<Plant> plantList, final OnItemClickListener itemListener) {
        this.plantList = plantList;
        this.listener = new OnPlantClickListener() {
            @Override
            public void onPlantClick(Plant plant) {
                itemListener.onItemClick(plant);
            }
        };
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.bind(plant);
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public void updateList(List<Plant> newList) {
        this.plantList = newList;
        notifyDataSetChanged();
    }

    class PlantViewHolder extends RecyclerView.ViewHolder {
        private TextView textPlantName;
        private TextView textScientificName;
        private TextView textUses;
        private ImageView plantIcon;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            textPlantName = itemView.findViewById(R.id.textPlantName);
            textScientificName = itemView.findViewById(R.id.textScientificName);
            textUses = itemView.findViewById(R.id.textUses);
            plantIcon = itemView.findViewById(R.id.plantIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onPlantClick(plantList.get(position));
                    }
                }
            });
        }

        public void bind(Plant plant) {
            // Manejo de nombres con compatibilidad
            String commonName = plant.getCommon_name();
            if (commonName == null || commonName.isEmpty()) {
                commonName = plant.getName();
            }
            textPlantName.setText(commonName != null ? commonName : "Sin nombre");

            String scientificName = plant.getScientific_name();
            if (scientificName == null || scientificName.isEmpty()) {
                scientificName = plant.getScientificName();
            }
            textScientificName.setText(scientificName != null ? scientificName : "");

            String uses = plant.getMedicinal_uses();
            if (uses == null || uses.isEmpty()) {
                uses = plant.getMedicinalUses();
            }
            if (uses != null && uses.length() > 100) {
                uses = uses.substring(0, 97) + "...";
            }
            textUses.setText(uses != null ? uses : "");

            // Por ahora usar el icono por defecto
            plantIcon.setImageResource(R.mipmap.ic_launcher_round);
        }
    }
}