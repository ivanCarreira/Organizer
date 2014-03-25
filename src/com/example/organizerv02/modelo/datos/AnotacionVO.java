package com.example.organizerv02.modelo.datos;

public class AnotacionVO {
	int idAnotacion;
	String contenido;
	
	public AnotacionVO(){
		
	}
	
	public AnotacionVO(String anotacion){
		this.contenido = anotacion;
	}
	
	public AnotacionVO(int idAnotacion, String anotacion){
		this.idAnotacion = idAnotacion;
		this.contenido = anotacion;
	}

	public int getIdAnotacion() {
		return idAnotacion;
	}

	public void setIdAnotacion(int idAnotacion) {
		this.idAnotacion = idAnotacion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String anotacion) {
		this.contenido = anotacion;
	}
}
