package com.example.organizerv02.modelo.datos;

import java.util.Date;

public class NotificacionVO {
	int idNotificacion;
	Date fecha;
	
	public NotificacionVO(Date fecha){
		this.fecha = fecha;
	}
	
	public NotificacionVO(int idNotificacion, Date fecha){
		this.idNotificacion = idNotificacion;
		this.fecha = fecha;
	}
	
	public int getIdNotificacion() {
		return idNotificacion;
	}
	
	public void setIdNotificacion(int idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
