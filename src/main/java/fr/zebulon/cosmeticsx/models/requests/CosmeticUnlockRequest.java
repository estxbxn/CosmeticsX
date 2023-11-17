package fr.zebulon.cosmeticsx.models.requests;

import fr.zebulon.cosmeticsx.models.CosmeticData;

import java.util.UUID;

public record CosmeticUnlockRequest(UUID uuid, CosmeticData cosmeticData) {
}
