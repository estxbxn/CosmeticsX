package fr.zebulon.cosmeticsx.cosmetics;

import java.util.Optional;
import java.util.function.Function;

public enum CosmeticCategory {

    // TODO: category
    ;

    private final String categoryName;
    private final Function<String, Optional<AbstractCosmetic>> cosmetic;
    private final boolean saveLocal;

    CosmeticCategory(String categoryName, Function<String, Optional<AbstractCosmetic>> cosmetic, boolean saveLocal) {
        this.categoryName = categoryName;
        this.cosmetic = cosmetic;
        this.saveLocal = saveLocal;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Optional<AbstractCosmetic> getCosmetic(String query) {
        return cosmetic.apply(query);
    }

    public boolean isSaveLocal() {
        return saveLocal;
    }
}
