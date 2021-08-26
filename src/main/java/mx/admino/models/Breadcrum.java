package mx.admino.models;

public class Breadcrum {

	public Breadcrum(String etiqueta, String url, Boolean activo) {
		super();
		this.etiqueta = etiqueta;
		this.url = url;
		this.activo = activo;
	}

	private String etiqueta;
	
	private String url;
	
	private Boolean activo;

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
