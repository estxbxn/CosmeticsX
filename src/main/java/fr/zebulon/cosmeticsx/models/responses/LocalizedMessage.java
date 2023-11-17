package fr.zebulon.cosmeticsx.models.responses;

import java.util.List;

public record LocalizedMessage(String message, List<String> inserts) {
}
