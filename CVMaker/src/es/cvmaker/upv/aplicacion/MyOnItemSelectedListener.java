package es.cvmaker.upv.aplicacion;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyOnItemSelectedListener implements OnItemSelectedListener {
	public String seleccionado="2000";
	public int maxEntradas = 25;
	public int seleccionadoPosicion=58;
//	String[][] spinnerItemSeleccionado = new String[4][maxEntradas];

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
         seleccionado = parent.getItemAtPosition(pos).toString();
         seleccionadoPosicion=pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
       
    }
    
    
    public String getSeleccion(){
    	return seleccionado;
    }
    
    public int getSeleccionPosicion(){
    	return seleccionadoPosicion;
    }
    
    
    
}
