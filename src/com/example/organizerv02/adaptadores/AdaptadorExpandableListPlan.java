package com.example.organizerv02.adaptadores;

import com.example.organizerv02.NuevaTarea;
import com.example.organizerv02.R;
import com.example.organizerv02.modelo.datos.TareaVO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class AdaptadorExpandableListPlan extends BaseExpandableListAdapter{
	private final SparseArray<Grupo> grupos; //aquí se guardan los datos que se mostraran en una lista de grupos
	private Fragment fragment;
	private LayoutInflater inflater;
	
	public AdaptadorExpandableListPlan(Fragment fragment, SparseArray<Grupo> grupos, Bundle b) {
		this.fragment = fragment;
		this.grupos = grupos;
		inflater = fragment.getLayoutInflater(b);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return grupos.get(groupPosition).getTarea(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0; //para qué sirven estos métodos?? En el listView tb lo hay...
	}

	//Devuelve la vista de cada tarea
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final TareaVO child = (TareaVO) getChild(groupPosition, childPosition); //Sustituir String por TareaVO
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fila_tarea, parent, false);
		}
		
		TextView nombreTarea = (TextView) convertView.findViewById(R.id.nombre_tarea);
		nombreTarea.setText(child.getNombreTarea()); //Sustituir cuando funcionen tareas por child.getNombreTarea()
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return grupos.get(groupPosition).getSizeTareasGrupo();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return grupos.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return grupos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0; //Este método no sé para que es... ????????
	}

	//Devuelve la vista de cada cabecera
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final Grupo group = (Grupo) getGroup(groupPosition);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fila_grupo, parent, false);
		}
		
		CheckedTextView nombreGrupo = (CheckedTextView) convertView.findViewById(R.id.nombre_grupo);
		nombreGrupo.setText(group.getCabecera());
		nombreGrupo.setChecked(isExpanded);
		
		//añade el boton y su correspondiente evento al click
		Button bNuevaTarea = (Button) convertView.findViewById(R.id.bNueva_tarea);
		bNuevaTarea.setTag(Integer.valueOf(groupPosition));
		bNuevaTarea.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent (fragment.getActivity(), NuevaTarea.class);
				int fecha = (Integer)v.getTag();
				intent.putExtra("fecha", fecha);
//				Toast.makeText(fragment.getActivity(), "Ha presionado "+fecha, Toast.LENGTH_LONG).show();
				fragment.startActivity(intent);
			}
		});
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	//quizás lo tenga que cambiar
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
