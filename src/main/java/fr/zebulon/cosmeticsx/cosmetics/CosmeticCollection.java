package fr.zebulon.cosmeticsx.cosmetics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;
import java.util.Set;

public enum CosmeticCollection {
    DEV(Component.text("Dev").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true));

    private final Component tag;
    private final List<Component> lore;
    private static final Set<CosmeticCollection> ACTIVE_COLLECTIONS = Set.of(DEV);

    CosmeticCollection(Component tag) {
        this(tag, List.of());
    }

    CosmeticCollection(Component tag, List<Component> lore) {
        this.tag = tag;
        this.lore = lore;
    }

    public Component getTag() {
        return tag;
    }

    public List<Component> getLore() {
        return lore;
    }

    public static boolean isActive(CosmeticCollection collection) {
        return ACTIVE_COLLECTIONS.contains(collection);
    }
}
