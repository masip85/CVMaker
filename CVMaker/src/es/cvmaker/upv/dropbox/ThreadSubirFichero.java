package es.cvmaker.upv.dropbox;

import java.io.File;
import java.io.FileInputStream;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;

public class ThreadSubirFichero extends Thread {

	private DropboxAPI<AndroidAuthSession> dropbox;
	private String rutaFichero = "";	
	
	public ThreadSubirFichero(DropboxAPI<AndroidAuthSession> sesion, String ruta) {
		dropbox = sesion;
		rutaFichero = ruta;
	}

	@Override
	public void run() {
		FileInputStream inputStream = null;
		try {
			dropbox.getSession().finishAuthentication();

			String aux[] = rutaFichero.split("/");
			String nombreFichero = aux[aux.length - 1];

			File file = new File(rutaFichero);
			inputStream = new FileInputStream(file);
			dropbox.putFile("/CVMaker/" + nombreFichero, inputStream,
					file.length(), null, null);
		} catch (Exception e) {
			dropbox.getSession().unlink();
		}
		dropbox.getSession().unlink();
	}
}
