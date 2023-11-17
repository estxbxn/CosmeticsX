package fr.zebulon.cosmeticsx.cosmetics.events;

import fr.zebulon.cosmeticsx.cosmetics.AbstractCosmetic;
import fr.zebulon.cosmeticsx.cosmetics.CosmeticSlot;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CosmeticEquipEvent extends Event {

    private final UUID playerUUID;
    private final CosmeticSlot slot;
    private final AbstractCosmetic cosmetic;

    public CosmeticEquipEvent(UUID playerUUID, CosmeticSlot slot, AbstractCosmetic cosmetic) {
        this.playerUUID = playerUUID;
        this.slot = slot;
        this.cosmetic = cosmetic;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public CosmeticSlot getSlot() {
        return slot;
    }

    public AbstractCosmetic getCosmetic() {
        return cosmetic;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}