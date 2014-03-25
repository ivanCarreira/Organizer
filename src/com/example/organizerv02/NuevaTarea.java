package com.example.organizerv02;

import com.example.organizerv02.modelo.datos.AnotacionVO;
import com.example.organizerv02.modelo.datos.TareaVO;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class NuevaTarea extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_tarea);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nueva_tarea, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void guardarTarea(View v){
		TareaVO nuevaTarea = new TareaVO();
		Bundle datos = getIntent().getExtras();
		int fecha = datos.getInt("fecha");
		EditText nombre = (EditText)findViewById(R.id.etTitulo);
		EditText anotacion = (EditText)findViewById(R.id.etCuerpo);
		nuevaTarea.setNombreTarea(nombre.getText().toString());
		nuevaTarea.setFecha(fecha);
		
		if (!anotacion.getText().toString().trim().isEmpty()){
			AnotacionVO anota = new AnotacionVO();
			anota.setContenido(anotacion.getText().toString());
			nuevaTarea.setAnotaciones(anota);
		}
		Organizer.listaTareas.guardarTarea(nuevaTarea);
		this.finish();
	}

}
