package enums;

public enum Endpoints {
    AUTHENTICATE("?endpoint=authenticate"),
    FetchCars("?endpoint=fetch_cars");

    private String value;

    Endpoints(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
