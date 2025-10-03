package main.model;

public enum Role {
    AFFITTUARIO, LOCATORE, INVALID;

    public static Role fromString(String s) {
        if(s == null) return null;
        return switch (s.toLowerCase()) {
            case "affittuario" -> AFFITTUARIO;
            case "locatore" -> LOCATORE;
            default -> INVALID;
        };
    }
}
