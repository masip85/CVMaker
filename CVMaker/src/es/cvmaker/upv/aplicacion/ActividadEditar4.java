package es.cvmaker.upv.aplicacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragment;

public class ActividadEditar4 extends SherlockFragment
{

	String TAG = "Fragment4";
	boolean inflado=false;
		
	int id = 0;
	
	@Override
	public void onDestroyView() {
		((Actividad_Editar) getActivity()).guardarCV4();
		inflado=false;
		
		super.onDestroyView();
		this.onSaveInstanceState(new Bundle());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle a = getArguments();
		if (a!=null) { Toast.makeText(getActivity(), "SOY: " + a.getInt("id"), Toast.LENGTH_SHORT).show(); }
		if(savedInstanceState != null) id = savedInstanceState.getInt("id");
		Toast.makeText(getActivity(), "VALGO: " + id , Toast.LENGTH_SHORT).show();
		setRetainInstance(true);
		if (((Actividad_Editar) getActivity()).cvInicializado[3] == true) {
			View view = inflater.inflate(
					R.layout.actividad_cv4_idiomas, container, false);

			((Actividad_Editar) getActivity()).FragmentView[3] = view;
			((Actividad_Editar) getActivity()).inflarCV4();
			inflado=true;
			view = ((Actividad_Editar) getActivity()).FragmentView[3];
			return view;
		} else {
			View view = inflater.inflate(
					R.layout.actividad_cv4_idiomas, container, false);
			((Actividad_Editar) getActivity()).FragmentView[3] = view;
			((Actividad_Editar) getActivity()).inicializarCV4();
			((Actividad_Editar) getActivity()).cvInicializado[3] = true;
			return view;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (inflado==true){
			((Actividad_Editar) getActivity()).reconstruirCV4();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle estadoGuardado){
		super.onSaveInstanceState(estadoGuardado);
		Toast.makeText(getActivity(), "SALVAMOS", Toast.LENGTH_SHORT).show();
		estadoGuardado.putInt("id", id);
	}

//	@Override
//	public void onRestoreInstanceState(Bundle estadoGuardado){
//		super.onRestoreInstanceState(estadoGuardado);
//		if (estadoGuardado != null) {
//			estado = estadoGuardado.getInt("estado");
//		}
//	}
	
}
