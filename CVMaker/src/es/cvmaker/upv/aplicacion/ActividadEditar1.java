package es.cvmaker.upv.aplicacion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragment;

public class ActividadEditar1 extends SherlockFragment {

	String TAG = "Fragment1";
	boolean inflado=false;

	@Override
	public void onDestroyView() {
		((Actividad_Editar) getActivity()).guardarCV1();
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (((Actividad_Editar) getActivity()).cvInicializado[0] == true) {
			View view = inflater.inflate(
					R.layout.actividad_cv1_datospersonales, container, false);

			((Actividad_Editar) getActivity()).FragmentView[0] = view;
			// No hace falta inflar
			inflado=true;
			view = ((Actividad_Editar) getActivity()).FragmentView[0];
			return view;
		} else {
			View view = inflater.inflate(
					R.layout.actividad_cv1_datospersonales, container, false);
			((Actividad_Editar) getActivity()).FragmentView[0] = view;
			((Actividad_Editar) getActivity()).inicializarCV1();
			((Actividad_Editar) getActivity()).cvInicializado[0] = true;
			return view;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (inflado==true){
			((Actividad_Editar) getActivity()).reconstruirCV1();
		}
		
	
	}

}
