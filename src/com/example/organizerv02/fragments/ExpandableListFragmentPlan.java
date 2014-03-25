package com.example.organizerv02.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizerv02.Organizer;
import com.example.organizerv02.R;
import com.example.organizerv02.adaptadores.AdaptadorExpandableListPlan;
import com.example.organizerv02.adaptadores.Grupo;
import com.example.organizerv02.modelo.datos.TareaVO;

public class ExpandableListFragmentPlan extends Fragment{
			private int idFragment;
			ExpandableListView eListView;
			AdaptadorExpandableListPlan adaptador;
			SparseArray<Grupo> grupos = new SparseArray<Grupo>();

			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				//aunque no se mostrará cada fragment siempre mantiene un id que indica
				//	la página en la que se muestra del pager
				this.idFragment = getArguments() != null ? getArguments()
						.getInt("idFragment") : 0;	
			}

			// inflar la vista
			// conseguir referencias a los elementos
			// devolver la vista que se mostrará
			@Override
			public View onCreateView(LayoutInflater inflater,
					ViewGroup container, Bundle savedInstanceState) {

				View view = inflater.inflate(R.layout.expandable_list_plan,
						container, false);
				eListView = (ExpandableListView) view.findViewById(R.id.list);
				
/*				TextView textView = (TextView) view.findViewById(R.id.textViewNumeroFragment);
				String cadena = "Fragment: " + Integer.toString(idFragment) + " mierdaaaaa";
				textView.setText(cadena);*/

				return view;
			}
			
			private void cargarDatosFormatoLista() {
/*				for (int j = 0; j <= 3; j++) {
					Grupo grupo = new Grupo("TEST "+j);
					for (int i = 0; i <=5; i++) {
						if (j<2)
						grupo.addTarea("tarea "+i);
						}
					grupos.append(j, grupo);
				}*/
				
				grupos.append(0, new Grupo ("HOY"));
				grupos.append(1, new Grupo ("MAÑANA"));
				grupos.append(2, new Grupo ("PRÓXIMOS 7 DIAS"));
				grupos.append(3, new Grupo ("ALGÚN DIA"));
/*				Grupo grupoHoy = new Grupo ("HOY");
				Grupo grupoMañana = new Grupo ("MAÑANA");
				Grupo grupo7Dias = new Grupo ("PROXIMOS 7 DIAS");
				Grupo grupoAlgunDia = new Grupo ("ALGUN DIA");
				int hoy = 0;
				int mañana = 0;
				int sieteDias = 0;
				int algunDia = 0;*/
				for (int i = 0; i < Organizer.listaTareas.getSize(); i++){
					switch (Organizer.listaTareas.getTarea(i).getFecha()){
					case 0:
//						utiliza la funcionalidad de indexOfKey que
//						if (grupos.indexOfKey(0) < 0) {
//						grupos.append(0, new Grupo ("HOY"));
//						}
						grupos.valueAt(0).addTarea(Organizer.listaTareas.getTarea(i));
						break;
					case 1:
//						if (grupos.indexOfKey(1) < 0) {
//						grupos.append(1, new Grupo ("MAÑANA"));
//						}
						grupos.valueAt(1).addTarea(Organizer.listaTareas.getTarea(i));
						break;
					case 2:
//						if (grupos.indexOfKey(2) < 0) {
//						grupos.append(2, new Grupo ("PRÓXIMOS 7 DIAS"));
//						}
						grupos.valueAt(2).addTarea(Organizer.listaTareas.getTarea(i));
						break;
					case 3:
//						if (grupos.indexOfKey(3) < 0) {
//						grupos.append(3, new Grupo ("ALGÚN DIA"));
//						}
						grupos.valueAt(3).addTarea(Organizer.listaTareas.getTarea(i));
						break;
					}
				}
			}

			// establece el adaptador a la lista y añade los datos
			@Override
			public void onActivityCreated(Bundle savedInstanceState) {

				super.onActivityCreated(savedInstanceState);
				cargarDatosFormatoLista();
				adaptador = new AdaptadorExpandableListPlan(this, grupos, savedInstanceState);
				eListView.setAdapter(adaptador);
				//Expande todos los grupos
				for (int i = 0; i <= 3; i++){
				eListView.expandGroup(i, false);
				}
//				eListView.scrollTo(0, 0);
				//Configura el evento del click en un hijo de la lista
				eListView.setOnChildClickListener(new OnChildClickListener() {
					
					@Override
					public boolean onChildClick(ExpandableListView parent, View v,
							int groupPosition, int childPosition, long id) {
						Toast.makeText(getActivity(), "Has seleccionado un hijo de la lista", Toast.LENGTH_LONG).show();
						return true;
					}
				});
				//Configura el evento del click en una cabecera de la lista. No funciona por tener el botón. Buscar solución
				eListView.setOnGroupClickListener(new OnGroupClickListener() {
					
					@Override
					public boolean onGroupClick(ExpandableListView parent, View v,
							int groupPosition, long id) {
						// TODO Auto-generated method stub
						eListView.expandGroup(groupPosition, true);
						return true;
					}
				});
			}
			
			

			@Override
			public void onResume() {
				// TODO Auto-generated method stub
				super.onResume();
				cargarDatosFormatoLista();
				adaptador.notifyDataSetChanged();
				//Toast.makeText(getActivity(), "evento", Toast.LENGTH_LONG ).show();
			}

			public static ExpandableListFragmentPlan newInstance(int idFragment) {
				ExpandableListFragmentPlan fragment = new ExpandableListFragmentPlan();

				Bundle args = new Bundle();
				args.putInt("idFragment", idFragment);
				fragment.setArguments(args);

				return fragment;
			}		
		}
