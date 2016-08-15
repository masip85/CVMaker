package es.cvmaker.upv.aplicacion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragment;

public class ActividadEditar6 extends SherlockFragment
{

	String TAG = "Fragment6";
	boolean inflado=false;
	@Override
	public void onDestroyView() {
		((Actividad_Editar) getActivity()).guardarCV6();
		inflado=false;
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		if (((Actividad_Editar) getActivity()).cvInicializado[5] == true) {
			View view = inflater.inflate(
					R.layout.actividad_cv6_generico, container, false);

			((Actividad_Editar) getActivity()).FragmentView[5] = view;
			((Actividad_Editar) getActivity()).inflarCV6();
			inflado=true;
			view = ((Actividad_Editar) getActivity()).FragmentView[5];
			return view;
		} else {
			View view = inflater.inflate(
					R.layout.actividad_cv6_generico, container, false);
			((Actividad_Editar) getActivity()).FragmentView[5] = view;
			((Actividad_Editar) getActivity()).inicializarCV6();
			((Actividad_Editar) getActivity()).cvInicializado[5] = true;
			return view;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (inflado==true){
			((Actividad_Editar) getActivity()).reconstruirCV6();
			
		}
		
	
	}
	
}
