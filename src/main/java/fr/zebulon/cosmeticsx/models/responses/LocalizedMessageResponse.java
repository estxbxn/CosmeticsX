package fr.zebulon.cosmeticsx.models.responses;

import java.util.List;

public record LocalizedMessageResponse(String message, List<String> inserts) {
}
