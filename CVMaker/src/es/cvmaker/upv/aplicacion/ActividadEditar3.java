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

public class ActividadEditar3 extends SherlockFragment
{

	String TAG = "Fragment3";
	boolean inflado=false;
	@Override
	public void onDestroyView() {
		((Actividad_Editar) getActivity()).guardarCV3();
		inflado=false;
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		if (((Actividad_Editar) getActivity()).cvInicializado[2] == true) {
			View view = inflater.inflate(
					R.layout.actividad_cv3_experiencialaboral, container, false);

			((Actividad_Editar) getActivity()).FragmentView[2] = view;
			((Actividad_Editar) getActivity()).inflarCV3();
			inflado=true;
			view = ((Actividad_Editar) getActivity()).FragmentView[2];
			return view;
		} else {
			View view = inflater.inflate(
					R.layout.actividad_cv3_experiencialaboral, container, false);
			((Actividad_Editar) getActivity()).FragmentView[2] = view;
			((Actividad_Editar) getActivity()).inicializarCV3();
			((Actividad_Editar) getActivity()).cvInicializado[2] = true;
			return view;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (inflado==true){
			((Actividad_Editar) getActivity()).reconstruirCV3();
			
		}
		
	
	}
	
}
