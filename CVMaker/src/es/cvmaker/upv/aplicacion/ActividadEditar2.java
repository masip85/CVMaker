package es.cvmaker.upv.aplicacion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragment;

public class ActividadEditar2 extends SherlockFragment

{
	String TAG = "Fragment2";
	boolean inflado=false;
	@Override
	public void onDestroyView() {
		Log.i(TAG, "guardar cv2  a ejecutarse - ondestroyView");
		((Actividad_Editar) getActivity()).guardarCV2();
		inflado=false;
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		if (((Actividad_Editar) getActivity()).cvInicializado[1] == true) {
			View view = inflater.inflate(
					R.layout.actividad_cv2_datosacademicos, container, false);

			((Actividad_Editar) getActivity()).FragmentView[1] = view;
			((Actividad_Editar) getActivity()).inflarCV2();
			inflado=true;
			view = ((Actividad_Editar) getActivity()).FragmentView[1];
			return view;
		} else {
			View view = inflater.inflate(
					R.layout.actividad_cv2_datosacademicos, container, false);
			((Actividad_Editar) getActivity()).FragmentView[1] = view;
			((Actividad_Editar) getActivity()).inicializarCV2();
			((Actividad_Editar) getActivity()).cvInicializado[1] = true;
			return view;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (inflado==true){
			((Actividad_Editar) getActivity()).reconstruirCV2();
			
		}
		
	
	}

}
