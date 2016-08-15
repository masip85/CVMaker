package es.cvmaker.upv.dropbox;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.artifex.mupdf.R;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

public class Actividad_dropbox extends Activity {

	final static private String APP_KEY         = "zeqtroq0nfeduqk";
	final static private String APP_SECRET      = "1ksumbbd9shxx1w";
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;
	// final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;

	private DropboxAPI<AndroidAuthSession> dropbox;
	private int estado = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys,
				ACCESS_TYPE);
		dropbox = new DropboxAPI<AndroidAuthSession>(session);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if(estado == 1){
			dropbox.getSession().startAuthentication(Actividad_dropbox.this);
		}
		if (dropbox.getSession().authenticationSuccessful()) {
			Thread subirFichero = new ThreadSubirFichero(dropbox, getIntent()
					.getExtras().getString("pathFile"));
			subirFichero.start();
			Toast.makeText(this, getString(R.string.subirDropbox), Toast.LENGTH_SHORT).show();
			finish();
		}
		if(estado == 2) finish();
		estado++;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle estadoGuardado){
		super.onSaveInstanceState(estadoGuardado);
			estadoGuardado.putInt("estado", estado);
	}

	@Override
	protected void onRestoreInstanceState(Bundle estadoGuardado){
		super.onRestoreInstanceState(estadoGuardado);
		if (estadoGuardado != null) {
			estado = estadoGuardado.getInt("estado");
		}
	}

}
