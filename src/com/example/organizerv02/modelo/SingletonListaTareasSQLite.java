package com.example.organizerv02.modelo;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.organizerv02.modelo.datos.AnotacionVO;
import com.example.organizerv02.modelo.datos.InterfazFechas;
import com.example.organizerv02.modelo.datos.TareaVO;

public class SingletonListaTareasSQLite extends SQLiteOpenHelper implements ListaTareasInterfaz{
	private static SingletonListaTareasSQLite INSTANCE;
	static final String BBDD = "tareas";
	private Vector<TareaVO> cacheTareas = new Vector<TareaVO>();
	
	private SingletonListaTareasSQLite(Context context) {
		super(context, BBDD, null, 2);
		
/*		guardarTarea(new TareaVO("Práctica Móviles" , InterfazFechas.HOY));
		guardarTarea(new TareaVO("Entrega Empresas" , InterfazFechas.HOY));
		guardarTarea(new TareaVO("ERP Sistemas de Xestion" , InterfazFechas.MAÑANA));
		guardarTarea(new TareaVO("Recuperación Interfaces" , InterfazFechas.SIETEDIAS));
		guardarTarea(new TareaVO("Descargar JodaTime" , InterfazFechas.SIETEDIAS));
		guardarTarea(new TareaVO("Entrega ERP" , InterfazFechas.SIETEDIAS));
		guardarTarea(new TareaVO("Modelo DOM" , InterfazFechas.ALGUNDIA));
		guardarTarea(new TareaVO("Spring e Hibernate" , InterfazFechas.ALGUNDIA));*/
		actualizarCacheListaTareas();
	 }
	 
	
	 public static SingletonListaTareasSQLite getInstance(Context context) {
		 if (INSTANCE == null){
			 INSTANCE = new SingletonListaTareasSQLite(context);
			 
		 }
		 return INSTANCE;
	 }
	 
	 //Métodos de SQLiteOpenHelper
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Tareas ("+
				"idTarea INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"nombre TEXT,"+
				"fecha INTEGER)");
		db.execSQL("CREATE TABLE Anotaciones ("+
				"idAnotacion INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"contenido TEXT,"+
				"tarea INTEGER,"+
				"FOREIGN KEY (tarea) REFERENCES Tareas(idTarea))");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	//Métodos de la interfaz
	@Override
	public void guardarTarea(TareaVO tarea) {
		SQLiteDatabase db = getWritableDatabase();
		if (tarea.getAnotaciones() == null){
			db.execSQL("INSERT INTO Tareas "+
					"VALUES (null,'"+tarea.getNombreTarea()+"',"+tarea.getFecha()+")");
			actualizarCacheListaTareas();
		}
		else {
			//inserta la tarea en la tabla tareas
			db.execSQL("INSERT INTO Tareas "+
					"VALUES (null,'"+tarea.getNombreTarea()+"',"+tarea.getFecha()+")");
			//consulta el id que tiene la tarea que acabamos de insertar
			Cursor cursor = db.rawQuery("SELECT idTarea "+
					"FROM Tareas "+
					"WHERE nombre ='"+tarea.getNombreTarea()+"'", null);
			cursor.moveToNext();
			int idTarea = cursor.getInt(0);
			cursor.close();
			//inserta la anotacion con el id de la tarea como clave ajena
			db.execSQL("INSERT INTO Anotaciones "+
					"VALUES (null,'"+tarea.getAnotaciones().getContenido()+"',"+idTarea+")");
			actualizarCacheListaTareas();
		}
	}

	@Override
	public void eliminarTarea(int idTarea) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getSize() {
		return cacheTareas.size();
	}


	@Override
	public TareaVO getTarea(int position) {
		// TODO Auto-generated method stub
		return cacheTareas.elementAt(position);
	}
	
	public void actualizarCacheListaTareas(){
		cacheTareas.removeAllElements();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT idTarea, nombre, fecha, idAnotacion, contenido "+
					"FROM Tareas LEFT JOIN Anotaciones ON Tareas.idTarea = Anotaciones.tarea", null);
		while (cursor.moveToNext()){
			TareaVO tarea = new TareaVO(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
			tarea.setAnotaciones(new AnotacionVO(cursor.getInt(3),cursor.getString(4)));
			cacheTareas.add(tarea);
		}
		cursor.close();
	}


	@Override
	public void actualizarTarea(TareaVO tarea) {
		// TODO Auto-generated method stub
		
	}
}
