package com.example.organizerv02;

import com.example.organizerv02.fragments.ExpandableListFragmentCategorias;
import com.example.organizerv02.fragments.ExpandableListFragmentPlan;
import com.example.organizerv02.modelo.ListaTareasInterfaz;
import com.example.organizerv02.modelo.SingletonListaTareasArray;
import com.example.organizerv02.modelo.SingletonListaTareasFicheroExterno;
import com.example.organizerv02.modelo.SingletonListaTareasFicheroInterno;
import com.example.organizerv02.modelo.SingletonListaTareasFicheroXMLDOM;
import com.example.organizerv02.modelo.SingletonListaTareasSQLite;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Organizer extends FragmentActivity {
	static final int NUM_ITEMS = 2;
	public static ListaTareasInterfaz listaTareas; //pensar esto

	MiFragmentPagerAdapter adaptadorPager;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final ActionBar actionBar = getActionBar();
		
		//LLamada al constructor del FragmentActivity y establecimiento del layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_pager);
		
		//Elección del  tipo de guardado que haremos (aquí pondré un switch para cargar el correcto según las preferencias)
		SharedPreferences preferencias = getSharedPreferences("com.example.organizerv02_preferences", MODE_PRIVATE);
		switch(Integer.parseInt(preferencias.getString("guardado_datos", "0"))){
		case 0:
			listaTareas = SingletonListaTareasArray.getInstance();
			break;
		case 1:
			listaTareas = SingletonListaTareasFicheroXMLDOM.getInstance(this);
			break;
		case 2:
			listaTareas = SingletonListaTareasFicheroInterno.getInstance(this);
			break;
		case 3:
			listaTareas = SingletonListaTareasFicheroExterno.getInstance(this);
			break;
		case 4:
			listaTareas = SingletonListaTareasSQLite.getInstance(this);
			break;
		}
		//Inicialización de nuestro propio PagerAdapter definido como una clase interna más abajo
		//	y fijación del adaptador a la vista pager del layout que se encargará de mostrar
		//	el contenido
		adaptadorPager = new MiFragmentPagerAdapter(
				this.getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adaptadorPager);
		
		//Se fija el tipo de navegación para la ActionBar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		//Crea un TabListener que será llamado cuando el usuario cambie de pestaña
		//	sobreescribiendo los métodos
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		
		//Se añaden los tabs al ActionBar fijándoles el texto mostrado y el listener
		actionBar.addTab(actionBar.newTab().setText("Plan")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Categorías")
				.setTabListener(tabListener));
		
		//Fija un listener al pager para  cuando se cambia la página que se muestra,
		//	sobreescribiendo su método para que actualice el tab seleccionado coordinado
		//	con la página mostrada
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organizer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			lanzarPreferencias(null);
			break;
		}
		return true;
	}
	
	public void lanzarPreferencias(View view) {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	//Clase interna para definir mi propio adaptador para las páginas
	public static class MiFragmentPagerAdapter extends FragmentPagerAdapter {

		public MiFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		//Método que devuelve el fragment que se mostrará en el pager.
		@Override
		public Fragment getItem(int position) {
			int idFragment = position;

			if (idFragment == 0) {
				return ExpandableListFragmentPlan.newInstance(idFragment);
			} 
			else {
				return ExpandableListFragmentCategorias.newInstance(idFragment);
			}
		}
		
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM_ITEMS;
		}
		
		}
	}

