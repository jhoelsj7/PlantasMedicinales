package com.tuapp.plantasmedicinales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuapp.plantasmedicinales.database.DatabaseHelper;
import com.tuapp.plantasmedicinales.utils.ImageUtils;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<DatabaseHelper.HistoryItem> historyItems;
    private OnHistoryItemListener listener;

    public interface OnHistoryItemListener {
        void onItemClick(DatabaseHelper.HistoryItem item);
        void onDeleteClick(DatabaseHelper.HistoryItem item, int position);
    }

    public HistoryAdapter(List<DatabaseHelper.HistoryItem> historyItems, OnHistoryItemListener listener) {
        this.historyItems = historyItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        DatabaseHelper.HistoryItem item = historyItems.get(position);

        holder.tvPlantName.setText(item.plantName);
        holder.tvConfidence.setText(String.format("Confianza: %.1f%%", item.confidence * 100));
        holder.tvDate.setText(item.date);

        // Cargar imagen capturada si existe, sino mostrar imagen por defecto
        if (item.imagePath != null && !item.imagePath.isEmpty()) {
            ImageUtils.loadLocalImage(holder.itemView.getContext(), item.imagePath, holder.ivPlant);
        } else {
            holder.ivPlant.setImageResource(R.mipmap.ic_launcher);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public void removeItem(int position) {
        historyItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, historyItems.size());
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlant;
        TextView tvPlantName;
        TextView tvConfidence;
        TextView tvDate;
        ImageButton btnDelete;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPlant = itemView.findViewById(R.id.ivHistoryPlant);
            tvPlantName = itemView.findViewById(R.id.tvHistoryPlantName);
            tvConfidence = itemView.findViewById(R.id.tvHistoryConfidence);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
            btnDelete = itemView.findViewById(R.id.btnDeleteHistory);
        }
    }
}
