package be.kdg.sa.orderservice.domain.restaurant;

public enum PriceCriteria {
    €("Cheap"),
    €€("Normal"),
    €€€("Expensive"),
    €€€€("Premium"),
    UNKNOWN("Unknown");

    private final String Description;

    PriceCriteria(String description) {
        Description = description;
    }
}
