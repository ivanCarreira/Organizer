package com.example.organizerv02.modelo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.Context;
import android.widget.Toast;

import com.example.organizerv02.modelo.datos.TareaVO;

public class SingletonListaTareasFicheroInterno implements ListaTareasInterfaz{
	private static SingletonListaTareasFicheroInterno INSTANCE;
	private final static String FICHERO = "tareas.txt";
	private Context context;
	private Vector<TareaVO> cacheTareas = new Vector<TareaVO>();
	
	private SingletonListaTareasFicheroInterno(Context context){
		this.context = context;
		actualizarCacheListaTareas();
	}
	
	public static SingletonListaTareasFicheroInterno getInstance(Context context){
		if (INSTANCE == null){
			INSTANCE = new SingletonListaTareasFicheroInterno(context);
		}
		return INSTANCE;
	}

	@Override
	public void guardarTarea(TareaVO tarea) {

			try {
				FileOutputStream fos = context.openFileOutput(FICHERO, Context.MODE_APPEND);
				String linea = tarea.getNombreTarea() + " " + tarea.getFecha() + "\r\n";
				fos.write(linea.getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			actualizarCacheListaTareas();
	}

	@Override
	public void eliminarTarea(int idTarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return cacheTareas.size();
	}

	@Override
	public TareaVO getTarea(int position) {
		// TODO Auto-generated method stub
		return cacheTareas.get(position);
	}

	@Override
	public void actualizarTarea(TareaVO tarea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarCacheListaTareas() {
		cacheTareas.removeAllElements();
		try {
			FileInputStream fis = context.openFileInput(FICHERO);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String linea = br.readLine();
			while (linea != null){
				TareaVO tarea = new TareaVO();
				String[] token = linea.split(" ");
				tarea.setNombreTarea(token[0]);
				tarea.setFecha(Integer.parseInt(token[1]));
				cacheTareas.add(tarea);
				linea = br.readLine();
			}
			fis.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			try {
				FileOutputStream fos = context.openFileOutput(FICHERO, Context.MODE_PRIVATE);
				Toast.makeText(context, "Se ha creado el archivo: "+FICHERO , Toast.LENGTH_LONG).show();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
