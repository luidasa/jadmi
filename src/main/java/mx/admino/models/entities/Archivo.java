package mx.admino.models.entities;

import jakarta.persistence.*;
import mx.admino.models.Clasificacion;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "archivos")
public class Archivo {

    public Archivo() {
        this.clasificacion = Clasificacion.CONFIDENCIAL;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreOriginal;

    private String contentType;

    private Casa casa;

    private Condominio condominio;

    private String ruta;

    @NotNull
    private Clasificacion clasificacion;

    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Archivo{" +
                "id='" + id + '\'' +
                ", nombreOriginal='" + nombreOriginal + '\'' +
                ", casa=" + casa +
                ", condominio=" + condominio +
                ", ruta='" + ruta + '\'' +
                ", clasificacion=" + clasificacion +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
