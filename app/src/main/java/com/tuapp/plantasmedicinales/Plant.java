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

    // Constructor vacío
    public Plant() {}

    // Constructor completo
    public Plant(int id, String scientific_name, String common_name,
                 String family, String description, String medicinal_uses) {
        this.id = id;
        this.scientific_name = scientific_name;
        this.common_name = common_name;
        this.family = family;
        this.description = description;
        this.medicinal_uses = medicinal_uses;
    }

    // Getters
    public int getId() { return id; }
    public String getScientific_name() { return scientific_name; }
    public String getCommon_name() { return common_name; }
    public String getFamily() { return family; }
    public String getDescription() { return description; }
    public String getMedicinal_uses() { return medicinal_uses; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }

    // SETTERS - ESTOS SON LOS QUE FALTAN
    public void setId(int id) {
        this.id = id;
    }

    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMedicinal_uses(String medicinal_uses) {
        this.medicinal_uses = medicinal_uses;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}