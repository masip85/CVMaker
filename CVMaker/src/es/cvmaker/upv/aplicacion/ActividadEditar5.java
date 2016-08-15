package es.cvmaker.upv.aplicacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragment;

public class ActividadEditar5 extends SherlockFragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.actividad_cv5_general, container, false);
		
		 ((Actividad_Editar) getActivity()).FragmentView[4] = view;
		 ((Actividad_Editar) getActivity()).inicializarCV5();
		return view;
	}
	
}
