package com.example.organizerv02.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.organizerv02.modelo.datos.TareaVO;

public class SingletonListaTareasFicheroExterno implements ListaTareasInterfaz{
	private static SingletonListaTareasFicheroExterno INSTANCE;
	private final static String FICHERO = Environment.getExternalStorageDirectory() + "/Android/data/com.example.organizerv02/files/listaTareas.txt";
	private Context context;
	private Vector<TareaVO> cacheTareas = new Vector<TareaVO>();
	
	private SingletonListaTareasFicheroExterno(Context context){
		this.context = context;
		File ruta = new File(Environment.getExternalStorageDirectory()+"/Android/data/com.example.organizerv02/files/");
		if(!ruta.exists()){
			ruta.mkdirs();
			File archivo = new File(ruta.getAbsolutePath(), "listaTareas.txt");
			try {
				boolean comprobacion = archivo.createNewFile();
				Log.d(this.getClass().toString(), "Archivo creado " + comprobacion);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(this.getClass().toString(), "No se pudo crear el archivo");
			}
			Log.d(this.getClass().toString(), "Cree el archivo");
		}
		actualizarCacheListaTareas();
	}
	
	public static SingletonListaTareasFicheroExterno getInstance(Context context){
		if (INSTANCE == null){
			INSTANCE = new SingletonListaTareasFicheroExterno(context);
		}
		return INSTANCE;
	}

	@Override
	public void guardarTarea(TareaVO tarea) {
			String estadoSD = Environment.getExternalStorageState();
			if (!estadoSD.equals(Environment.MEDIA_MOUNTED)){
				Toast.makeText(context, "No se puede escribir en la memoria externa", Toast.LENGTH_LONG).show();
			}else{
			try {
				FileOutputStream fos = new FileOutputStream(FICHERO, true);
				String linea = tarea.getNombreTarea() + " " + tarea.getFecha() + "\r\n";
				fos.write(linea.getBytes());
				Log.d(this.getClass().toString(), "Cree el archivo");
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.d(FICHERO, "Error al guardar la tarea");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		String estadoSD = Environment.getExternalStorageState();
		if (!estadoSD.equals(Environment.MEDIA_MOUNTED)&&!estadoSD.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			Toast.makeText(context, "No se encuentra la memoria externa", Toast.LENGTH_LONG).show();
		}else{
		try {
			FileInputStream fis = new FileInputStream(FICHERO);
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
				FileOutputStream fos = new FileOutputStream(FICHERO, true);
				Log.d("Error al actualizar", "Ahora se debería de haber creado el fichero");
				try {
					fos.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					Log.d("actualizar", "He petao");
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
}
