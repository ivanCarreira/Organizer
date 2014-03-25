package com.example.organizerv02.modelo;

import java.util.ArrayList;
import java.util.Vector;

import com.example.organizerv02.modelo.datos.InterfazFechas;
import com.example.organizerv02.modelo.datos.TareaVO;

public class SingletonListaTareasArray implements ListaTareasInterfaz{
	private static SingletonListaTareasArray INSTANCE;
	private Vector<TareaVO> tareas;
	 
	private SingletonListaTareasArray() {
		 tareas = new Vector<TareaVO>();
		 
		//Provisional mientras no tengo forma de administrar tareas desde la app
			
			tareas.add(new TareaVO(101, "Práctica Móviles" , InterfazFechas.HOY));
			tareas.add(new TareaVO(102, "Entrega Empresas" , InterfazFechas.HOY));
			tareas.add(new TareaVO(103, "ERP Sistemas de Xestion" , InterfazFechas.MAÑANA));
			tareas.add(new TareaVO(104, "Recuperación Interfaces" , InterfazFechas.SIETEDIAS));
			tareas.add(new TareaVO(105, "Descargar JodaTime" , InterfazFechas.SIETEDIAS));
			tareas.add(new TareaVO(106, "Entrega ERP" , InterfazFechas.SIETEDIAS));
			tareas.add(new TareaVO(107, "Modelo DOM" , InterfazFechas.ALGUNDIA));
			tareas.add(new TareaVO(108, "Spring e Hibernate" , InterfazFechas.ALGUNDIA));
	 }
	 
	 public static SingletonListaTareasArray getInstance() {
		 if (INSTANCE == null){
			 INSTANCE = new SingletonListaTareasArray();
		 }
		 return INSTANCE;
	 }
	 
	@Override
	public void guardarTarea(TareaVO tarea) {
		tareas.add(tarea);
	}

	@Override
	public void eliminarTarea(int idTarea) {
		
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return tareas.size();
	}

	@Override
	public TareaVO getTarea(int position) {
		// TODO Auto-generated method stub
		return tareas.elementAt(position);
	}

	@Override
	public void actualizarTarea(TareaVO tarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarCacheListaTareas() {
		// TODO Auto-generated method stub
		
	}

}
