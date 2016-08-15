package es.cvmaker.upv.aplicacion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.artifex.mupdf.MuPDFActivity;
import com.artifex.mupdf.R;

public class Actividad_Principal extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_principal);
	}

	public void nuevoCV(View v) {
		startActivity(new Intent(this, Actividad_Editar.class));
	}

	public void abrirCV(View v) {
		Intent intent = new Intent(this,
				es.cvmaker.upv.navegador.FileChooser.class);
		intent.putExtra("tipoFichero", "pdf");
		intent.putExtra("directorio", Environment.getExternalStorageDirectory() + "/CVMaker");
		startActivityForResult(intent, ABRIR_DOCUMENTO);
	}

	private final int ABRIR_DOCUMENTO = 1000;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ABRIR_DOCUMENTO & resultCode == RESULT_OK) {
			String rutaPDF = data.getExtras().getString(
					"RutaArchivoSeleccionado");
			Intent i = new Intent(this, MuPDFActivity.class);
			i.putExtra("ruta", rutaPDF);
			startActivity(i);
		}
	}
	
	public void ejemploCV(View v) throws IOException {
		File directorio = new File(Environment.getExternalStorageDirectory() + "/CVMaker/demo/");
		if(!directorio.exists()) directorio.mkdirs();
		
		File destino = new File(Environment.getExternalStorageDirectory() + "/CVMaker/demo/cvejemplo.pdf");
		
		if(destino.exists()) {
			Intent i = new Intent(this, MuPDFActivity.class);
			i.putExtra("ruta", Environment.getExternalStorageDirectory() + "/CVMaker/demo/cvejemplo.pdf");
			startActivity(i);
		} else {
			try {
				InputStream   in = this.getResources().openRawResource(R.raw.cvejemplo);
				OutputStream out = new FileOutputStream(destino);
				byte[] buf = new byte[1024];
				int len;
				try {
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					Intent i = new Intent(this, MuPDFActivity.class);
					i.putExtra("ruta", Environment.getExternalStorageDirectory() + "/CVMaker/demo/cvejemplo.pdf");
					startActivity(i);
				} catch (Exception e) {
					Toast.makeText(this, getString(R.string.cvperdido), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Toast.makeText(this, getString(R.string.cvperdido), Toast.LENGTH_SHORT).show();
			}
		}
	}

}
