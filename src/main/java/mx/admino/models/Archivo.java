package mx.admino.models;

import mx.admino.models.entities.Casa;
import mx.admino.models.entities.Condominio;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "archivos")
public class Archivo {

    public Archivo() {
        this.clasificacion = Clasificacion.CONFIDENCIAL;
    }

    @Id
    private String id;

    private String nombreOriginal;

    private String contentType;

    @DBRef
    private Casa casa;

    @DBRef
    private Condominio condominio;

    private String ruta;

    @NotNull
    private Clasificacion clasificacion;

    private String descripcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
