package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tuapp.plantasmedicinales.database.DatabaseHelper;
import com.tuapp.plantasmedicinales.service.AuthService;
import java.util.List;

public class HistoryActivity extends BaseActivity implements HistoryAdapter.OnHistoryItemListener {

    private ImageButton btnBack;
    private RecyclerView recyclerViewHistory;
    private TextView tvEmptyHistory;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;
    private AuthService authService;
    private HistoryAdapter adapter;
    private List<DatabaseHelper.HistoryItem> historyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DatabaseHelper(this);
        authService = new AuthService(this);

        // Inicializar vistas
        btnBack = findViewById(R.id.btnBack);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);
        progressBar = findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));

        loadHistory();
    }

    private void loadHistory() {
        progressBar.setVisibility(View.VISIBLE);

        String username = authService.getUsername();
        historyItems = dbHelper.getHistory(username);

        progressBar.setVisibility(View.GONE);

        if (historyItems.isEmpty()) {
            tvEmptyHistory.setVisibility(View.VISIBLE);
            recyclerViewHistory.setVisibility(View.GONE);
        } else {
            tvEmptyHistory.setVisibility(View.GONE);
            recyclerViewHistory.setVisibility(View.VISIBLE);

            adapter = new HistoryAdapter(historyItems, this);
            recyclerViewHistory.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(DatabaseHelper.HistoryItem item) {
        // Abrir detalles de la planta
        Intent intent = new Intent(this, PlantDetailActivity.class);
        intent.putExtra("plant_name", item.plantName);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(DatabaseHelper.HistoryItem item, int position) {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Eliminar del historial")
            .setMessage("¿Deseas eliminar esta identificación del historial?")
            .setPositiveButton("Eliminar", (dialog, which) -> {
                boolean deleted = dbHelper.deleteIdentification(item.id);
                if (deleted) {
                    adapter.removeItem(position);
                    Toast.makeText(this, "Eliminado del historial", Toast.LENGTH_SHORT).show();

                    // Si ya no hay items, mostrar mensaje vacío
                    if (adapter.getItemCount() == 0) {
                        tvEmptyHistory.setVisibility(View.VISIBLE);
                        recyclerViewHistory.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
