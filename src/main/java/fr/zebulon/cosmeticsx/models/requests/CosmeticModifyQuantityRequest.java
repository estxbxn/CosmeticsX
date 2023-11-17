package fr.zebulon.cosmeticsx.models.requests;

import java.util.UUID;

public record CosmeticModifyQuantityRequest(UUID uuid, String id, String category, int quantity) {
}
