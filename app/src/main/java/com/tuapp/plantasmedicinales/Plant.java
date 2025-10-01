package com.tuapp.plantasmedicinales;

public class Plant {
    private int id;
    private String scientific_name;
    private String common_name;
    private String family;
    private String description;
    private String medicinal_uses;
    private String created_at;
    private String updated_at;

    // Getters
    public int getId() { return id; }
    public String getScientific_name() { return scientific_name; }
    public String getCommon_name() { return common_name; }
    public String getFamily() { return family; }
    public String getDescription() { return description; }
    public String getMedicinal_uses() { return medicinal_uses; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
}