package com.example.organizerv02;

import com.example.organizerv02.modelo.SingletonListaTareasArray;
import com.example.organizerv02.modelo.SingletonListaTareasFicheroExterno;
import com.example.organizerv02.modelo.SingletonListaTareasFicheroInterno;
import com.example.organizerv02.modelo.SingletonListaTareasFicheroXMLDOM;
import com.example.organizerv02.modelo.SingletonListaTareasSQLite;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.widget.Toast;

public class Preferencias extends PreferenceActivity implements
OnSharedPreferenceChangeListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		//Toast.makeText(this, "Las preferencias han cambiado en: " + key, Toast.LENGTH_LONG).show();
		Log.d(this.getClass().toString(), "Se ha elegido: "+ sharedPreferences.getString("guardado_datos", "0"));
		Log.d(this.getClass().toString(), "Se ha elegido: "+ key);
		
		if (key.equals("guardado_datos")) {
			Log.d(this.getClass().toString(), "Se ha elegido: "+sharedPreferences.getString("guardado_datos", "0"));
			switch(Integer.parseInt(sharedPreferences.getString("guardado_datos", "0"))){
			case 0:
				Organizer.listaTareas = SingletonListaTareasArray.getInstance();
				Log.d(this.getClass().toString(), "Ahora la lista de tareas es: SingletonListaTareasArray");
				break;
			case 1:
				Organizer.listaTareas = SingletonListaTareasFicheroXMLDOM.getInstance(this);
				Log.d(this.getClass().toString(), "Ahora la lista de tareas es: SingletonListaTareasFicheroXMLDOM");
				break;
			case 2:
				Organizer.listaTareas = SingletonListaTareasFicheroInterno.getInstance(this);
				Log.d(this.getClass().toString(), "Ahora la lista de tareas es: SingletonListaTareasFicheroInterno");
				break;
			case 3:
				Organizer.listaTareas = SingletonListaTareasFicheroExterno.getInstance(this);
				Log.d(this.getClass().toString(), "Ahora la lista de tareas es: SingletonListaTareasFicheroExterno");
				break;
			case 4:
				Organizer.listaTareas = SingletonListaTareasSQLite.getInstance(this);
				Log.d(this.getClass().toString(), "Ahora la lista de tareas es: SingletonListaTareasSQLite");
				break;
			}
		}

	}

}
