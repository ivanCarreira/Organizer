package com.example.organizerv02.adaptadores;

import java.util.ArrayList;
import java.util.List;

import com.example.organizerv02.modelo.datos.TareaVO;

//Cada grupo contiene su nombre y la lista de elementos que contiene que se asocia con cada elemento expandible de la lista
public class Grupo {
	private String cabecera;
	private final List<TareaVO> tareas = new ArrayList<TareaVO>(); //modificar para TareaVO
	
	public Grupo(String cabecera){
		this.cabecera = cabecera;
	}
	
	public String getCabecera() {
		return cabecera;
	}
	
	public TareaVO getTarea(int position){ //modificar para TareaVO
		return tareas.get(position);
	}
	
	public void addTarea(TareaVO tarea){ //modificar para TareaVO
		tareas.add(tarea);
	}
	
	public int getSizeTareasGrupo(){
		return tareas.size();
	}
}
