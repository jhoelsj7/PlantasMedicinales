package com.tuapp.plantasmedicinales;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plants")
public class Plant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String scientificName;
    private String common_name;
    private String scientific_name;
    private String family;
    private String description;
    private String medicinalUses;
    private String medicinal_uses;
    private String preparation;
    private String precautions;
    private String imageUrl;
    private String localImagePath;
    private boolean isSynced;

    // Getters y setters para ambos formatos
    public String getName() { return name; }
    public void setName(String name) { this.name = name; this.common_name = name; }

    public String getCommon_name() { return common_name != null ? common_name : name; }
    public void setCommon_name(String common_name) { this.common_name = common_name; this.name = common_name; }

    public String getScientificName() { return scientificName; }
    public void setScientificName(String scientificName) { this.scientificName = scientificName; this.scientific_name = scientificName; }

    public String getScientific_name() { return scientific_name != null ? scientific_name : scientificName; }
    public void setScientific_name(String scientific_name) { this.scientific_name = scientific_name; this.scientificName = scientific_name; }

    public String getFamily() { return family; }
    public void setFamily(String family) { this.family = family; }

    public String getMedicinalUses() { return medicinalUses; }
    public void setMedicinalUses(String medicinalUses) { this.medicinalUses = medicinalUses; this.medicinal_uses = medicinalUses; }

    public String getMedicinal_uses() { return medicinal_uses != null ? medicinal_uses : medicinalUses; }
    public void setMedicinal_uses(String medicinal_uses) { this.medicinal_uses = medicinal_uses; this.medicinalUses = medicinal_uses; }

    // Resto de getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPreparation() { return preparation; }
    public void setPreparation(String preparation) { this.preparation = preparation; }

    public String getPrecautions() { return precautions; }
    public void setPrecautions(String precautions) { this.precautions = precautions; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getLocalImagePath() { return localImagePath; }
    public void setLocalImagePath(String localImagePath) { this.localImagePath = localImagePath; }

    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }
}