package com.tuapp.plantasmedicinales.model;

import com.tuapp.plantasmedicinales.Plant;
import java.util.List;

/**
 * Respuesta del endpoint GET /plants.php
 * Compatible con el formato del dashboard
 */
public class PlantsResponse {
    private boolean success;
    private String message;
    private Data data;

    // Clase interna para manejar el objeto "data"
    public static class Data {
        private List<Plant> plants;
        private Pagination pagination;

        public List<Plant> getPlants() {
            return plants;
        }

        public void setPlants(List<Plant> plants) {
            this.plants = plants;
        }

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }
    }

    // Clase interna para paginación
    public static class Pagination {
        private int page;
        private int per_page;
        private int total_items;
        private int total_pages;
        private int offset;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getTotal_items() {
            return total_items;
        }

        public void setTotal_items(int total_items) {
            this.total_items = total_items;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
    }

    // Getters y setters principales
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // Método helper para obtener las plantas directamente
    public List<Plant> getPlants() {
        return data != null ? data.getPlants() : null;
    }
}
