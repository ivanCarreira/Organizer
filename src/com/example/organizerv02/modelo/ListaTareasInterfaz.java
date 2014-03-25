package com.example.organizerv02.modelo;

import com.example.organizerv02.modelo.datos.TareaVO;

public interface ListaTareasInterfaz {
	
	public void guardarTarea(TareaVO tarea);
	public void eliminarTarea(int idTarea);
	public int getSize();
	public TareaVO getTarea(int position);
	public void actualizarTarea(TareaVO tarea);
	public void actualizarCacheListaTareas();
}
