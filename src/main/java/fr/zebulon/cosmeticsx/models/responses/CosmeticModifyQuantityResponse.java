package fr.zebulon.cosmeticsx.models.responses;

import java.util.UUID;

public record CosmeticModifyQuantityResponse(UUID uuid, String id, String category, int quantity) {
}
