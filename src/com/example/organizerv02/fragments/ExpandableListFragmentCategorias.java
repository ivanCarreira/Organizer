package com.example.organizerv02.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.organizerv02.R;

public class ExpandableListFragmentCategorias extends Fragment {
	private int idFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.idFragment = getArguments() != null ? getArguments().getInt(
				"idFragment") : 0;
	}

	// inflar la vista
	// conseguir referencias a los elementos
	// devolver la vista que se mostrará
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.expandable_list_categorias, container,
				false);
		TextView textView = (TextView) view
				.findViewById(R.id.textViewNumeroFragment);
		String cadena = "Fragment: " + Integer.toString(idFragment+1);
		textView.setText(cadena);
		// listaExpandible =
		// (ExpandableListView)view.findViewById(R.id.expandable_list_plan);
		return view;
	}

	// establecer el adaptador a la lista
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		// meterle datos al adaptador
	}

	public static ExpandableListFragmentCategorias newInstance(int idFragment) {
		ExpandableListFragmentCategorias fragment = new ExpandableListFragmentCategorias();

		Bundle args = new Bundle();
		args.putInt("idFragment", idFragment);
		fragment.setArguments(args);

		return fragment;
	}
}
