package home.anastasia.prog.guess;

public enum CheckResult {
    EQUALS("равно"),
    LESS("меньше"),
    GREATER("больше");

    private final String description;

    CheckResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
