package fr.zebulon.cosmeticsx.models;

public class CosmeticData {

    private String id;
    private String category;
    private final CosmeticMeta meta;

    public CosmeticData(String id, String category, CosmeticMeta meta) {
        this.id = id;
        this.category = category;
        this.meta = meta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CosmeticMeta getMeta() {
        return meta;
    }
}
