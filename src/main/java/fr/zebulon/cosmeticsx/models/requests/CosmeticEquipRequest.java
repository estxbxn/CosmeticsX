package fr.zebulon.cosmeticsx.models.requests;

import java.util.UUID;

public record CosmeticEquipRequest(UUID uuid, String slot, String id) {
}
