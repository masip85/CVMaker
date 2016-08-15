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

public class ActividadEditarExtra extends SherlockFragment {
	Integer contadorGenericoExtra;
	Integer posTabActual;
	Integer contadorGenExtra2;

	String TAG = "Fragment2";
	boolean inflado = false;
	View view;

	@Override
	public void onDestroyView() {
		((Actividad_Editar) getActivity()).guardarCVExtra(contadorGenExtra2);
		inflado = false;
		view.getTag().toString();
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (((Actividad_Editar) getActivity()).cvInicializado[posTabActual] == true) {
			View view = inflater.inflate(
					R.layout.actividad_cv6_generico, container, false);
			((Actividad_Editar) getActivity()).FragmentView[posTabActual] = view;
			((Actividad_Editar) getActivity()).inflarCVExtra(contadorGenExtra2);
			view = ((Actividad_Editar) getActivity()).FragmentView[contadorGenExtra2];
			inflado=true;
			return view;
			
		} else {
			View view = inflater.inflate(
					R.layout.actividad_cv6_generico, container, false);
			((Actividad_Editar) getActivity()).FragmentView[posTabActual] = view;
			 ((Actividad_Editar) getActivity()).tabExtra[contadorGenExtra2-1] = view;
			 ((Actividad_Editar) getActivity()).inicializarCVExtra();
			((Actividad_Editar) getActivity()).cvInicializado[posTabActual] = true;
			view.setTag("");
			return view;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (inflado == true) {
			((Actividad_Editar) getActivity()).reconstruirCVExtra(contadorGenExtra2);

		}

	}

}
