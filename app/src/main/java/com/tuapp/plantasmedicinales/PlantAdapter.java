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

    public interface OnPlantClickListener {
        void onPlantClick(Plant plant);
    }

    public PlantAdapter(List<Plant> plantList, OnPlantClickListener listener) {
        this.plantList = plantList;
        this.listener = listener;
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
            textPlantName.setText(plant.getCommon_name());
            textScientificName.setText(plant.getScientific_name());

            String uses = plant.getMedicinal_uses();
            if (uses != null && uses.length() > 100) {
                uses = uses.substring(0, 97) + "...";
            }
            textUses.setText(uses);

            // Por ahora usar el icono por defecto
            plantIcon.setImageResource(R.mipmap.ic_launcher_round);
        }
    }
}