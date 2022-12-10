package mx.admino.models;

public enum Clasificacion {
    PUBLICA("Todas las personas, incluso aquellas externas al condominio lo pueden ver."),
    INTERNO("Todas las personas que viven o son dueños de unidades habitacionales pueden verlo."),
    CONFIDENCIAL("Solo los dueños de las unidades habitacionales pueden verlo."),
    PRIVADA("Solo la mesa directiva y los administradores del condominio pueden verlo.");

    private String texto;


    private Clasificacion(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return this.texto;
    }
}
