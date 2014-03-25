package com.example.organizerv02.modelo.datos;

import java.util.ArrayList;


public class TareaVO {
	private int idTarea;
	private String nombreTarea;
	private int fecha; //Aquí se guardará el valor de hoy, mañana, 7 dias, en esta vida. (Constantes en la interfaz fechas)
	private AnotacionVO anotaciones; //Esto será una lista ya que se podrán guardar varias anotaciones, pero por lo de ahora solo puede tener una
	private ArrayList<NotificacionVO> notificaciones;
	
	public TareaVO(){
		
	}
	
	public TareaVO(String nombreTarea, int fecha){
		this.nombreTarea = nombreTarea;
		this.fecha = fecha;
	}
	
	public TareaVO(int idTarea, String nombreTarea, int fecha){
		this.idTarea = idTarea;
		this.nombreTarea = nombreTarea;
		this.fecha = fecha;
	}
	
	public int getIdTarea() {
		return idTarea;
	}
	
	public void setIdTarea(int idTarea) {
		this.idTarea = idTarea;
	}
	
	public String getNombreTarea() {
		return nombreTarea;
	}
	
	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}
	
	public int getFecha() {
		return fecha;
	}
	
	public void setFecha(int fecha) {
		this.fecha = fecha;
	}
	
	public AnotacionVO getAnotaciones() {
		return anotaciones;
	}
	
	public void setAnotaciones(AnotacionVO anotaciones) {
		this.anotaciones = anotaciones;
	}
	
	public ArrayList<NotificacionVO> getNotificaciones() {
		return notificaciones;
	}
	
	public void setNotificaciones(ArrayList<NotificacionVO> notificaciones) {
		this.notificaciones = notificaciones;
	}
}
