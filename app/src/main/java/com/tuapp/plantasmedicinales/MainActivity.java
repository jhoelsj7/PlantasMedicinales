package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tuapp.plantasmedicinales.controller.SyncController;
import com.tuapp.plantasmedicinales.service.AuthService;

public class MainActivity extends AppCompatActivity {

    private Button btnCamera, btnPlantList, btnSearch, btnSync;
    private SyncController syncController;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        syncController = new SyncController(this);
        authService = new AuthService(this);

        btnCamera = findViewById(R.id.btnCamera);
        btnPlantList = findViewById(R.id.btnPlantList);
        btnSearch = findViewById(R.id.btnSearch);
        btnSync = findViewById(R.id.btnSync);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });

        btnPlantList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlantListActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronizeData();
            }
        });

        synchronizeData();
    }

    private void synchronizeData() {
        Toast.makeText(this, "Sincronizando datos...", Toast.LENGTH_SHORT).show();

        syncController.synchronizeData(new SyncController.SyncCallback() {
            @Override
            public void onSyncComplete(final boolean success, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            authService.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}