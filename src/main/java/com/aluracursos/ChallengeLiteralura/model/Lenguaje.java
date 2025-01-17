package com.aluracursos.ChallengeLiteralura.model;

public enum Lenguaje {
    EN("en"),
    ES("es"),
    FR("fr"),
    DE("de"),
    IT("it"),
    DESCONOCIDO("Desconocido");

    private String len;

    Lenguaje (String lenguaje){
        this.len = lenguaje;
    }

    public String getLen() {
        return len;
    }


    public static Lenguaje fromString(String text) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.len.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Lenguaje no encontrado: " +text);
    }
}
