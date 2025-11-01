package be.kdg.sa.orderservice.domain.restaurant;

import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Getter
public enum PriceCriteria {
    CHEAP("€"),
    NORMAL("€€"),
    EXPENSIVE("€€€"),
    PREMIUM("€€€€"),
    UNKNOWN("Unknown");

    private final String Description;

    PriceCriteria(String description) {
        Description = description;
    }

    public static PriceCriteria fromDescription(String description) {
        return switch (description) {
            case "€" -> CHEAP;
            case "€€" -> NORMAL;
            case "€€€" -> EXPENSIVE;
            case "€€€€" -> PREMIUM;
            default -> UNKNOWN;
        };
    }
}
