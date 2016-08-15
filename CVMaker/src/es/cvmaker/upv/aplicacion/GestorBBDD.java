package es.cvmaker.upv.aplicacion;

import java.io.File;
import java.util.Calendar;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class GestorBBDD extends SQLiteOpenHelper {

	private long idCV;
	
	public GestorBBDD(Context context) {
		super(context, "BBDD_CVMaker", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE curriculum (_id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT, autor TEXT, ruta TEXT)");
		db.execSQL("CREATE TABLE cv1 (_id INTEGER PRIMARY KEY AUTOINCREMENT, id_cv INTEGER, nombre TEXT, nacimiento TEXT, email TEXT, telf TEXT, localidad TEXT, rutafoto TEXT)");
		db.execSQL("CREATE TABLE cv2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, id_cv INTEGER, estudios TEXT, centro TEXT, localidad TEXT, inicio TEXT, fin TEXT)");
		db.execSQL("CREATE TABLE cv3 (_id INTEGER PRIMARY KEY AUTOINCREMENT, id_cv INTEGER, trabajo TEXT, empresa TEXT, descripcion TEXT, inicio TEXT, fin TEXT)");
		db.execSQL("CREATE TABLE cv4 (_id INTEGER PRIMARY KEY AUTOINCREMENT, id_cv INTEGER, idioma TEXT, hablado TEXT, escrito TEXT, lectura TEXT)");
		db.execSQL("CREATE TABLE cv5 (_id INTEGER PRIMARY KEY AUTOINCREMENT, id_cv INTEGER, cualidad INTEGER)");
		db.execSQL("CREATE TABLE cualidades (_id INTEGER PRIMARY KEY AUTOINCREMENT, cod TEXT, descripcion TEXT)");
		db.execSQL("CREATE TABLE cv6_titulo (_id INTEGER PRIMARY KEY AUTOINCREMENT, id_cv INTEGER, texto INTEGER)");
		db.execSQL("CREATE TABLE cv6_subtitulo (_id INTEGER PRIMARY KEY AUTOINCREMENT, id_cv INTEGER, id_titulo INTEGER, texto TEXT, descripcion TEXT)");
		
		String[] cualidades =  {"Experiencia de trabajo en equipo", "Disponibilidad para desplazarse", "Disponibilidad de coche propio", "Disponibilidad para viajar"};
		String[] cod =  {"equipo", "desplazarse", "coche", "viajar"};
		for(int i=0;i<cualidades.length;i++) {
			db.execSQL("INSERT INTO cualidades VALUES (null, '"+cod[i]+"' ,'"+cualidades[i]+"')");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public void setCurriculum(String nomFichero, String autor) {
		
		// FECHA
		Calendar f = Calendar.getInstance();
		String fecha = f.get(Calendar.DAY_OF_MONTH)+"/"+(f.get(Calendar.MONTH)+1)+"/"+f.get(Calendar.YEAR);

		// RUTA PDF
		File ruta = new File(Environment.getExternalStorageDirectory() + "/CVMaker/");
		if(!ruta.exists()) ruta.mkdirs();
		
		// GUARDA
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO curriculum VALUES (null, '"+fecha+"', '"+autor+"', '"+ruta+"/"+nomFichero+".pdf')");
		
		// RECUPERA ID ASIGNADO
		SQLiteDatabase b = getReadableDatabase();
		Cursor c = b.rawQuery("SELECT _id FROM curriculum ORDER BY _id DESC limit 1", null);
		if (c != null && c.moveToFirst()) {
			idCV = c.getLong(0);
		}
	}
	
	public void setCurriculumCV1(String nombre, String nacimiento, String email, String telf, String localidad, String rutaFoto) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO cv1 VALUES (null, "+idCV+", '"+nombre+"', '"+nacimiento+"', '"+email+"', '"+telf+"', '"+localidad+"', '"+rutaFoto+"')");
	}
	
	public void setCurriculumCV2(EditText[] estudios, EditText[] centro, EditText[] localidad, MyOnItemSelectedListener[] inicio, MyOnItemSelectedListener[] fin, int cont) { 
		SQLiteDatabase db = getWritableDatabase();
		for(int i=0;i<cont;i++) {
			db.execSQL("INSERT INTO cv2 VALUES (null, "+idCV+", '"+estudios[i].getText().toString()+"', '"+centro[i].getText().toString()+"', '"+localidad[i].getText().toString()+"', '"+inicio[i].getSeleccion()+"', '"+fin[i].getSeleccion()+"')");
		}
	}
	
	public void setCurriculumCV3(EditText[] trabajo, EditText[] empresa, EditText[] descripcion, MyOnItemSelectedListener[] inicio, MyOnItemSelectedListener[] fin, int cont) throws Exception { 
		SQLiteDatabase db = getWritableDatabase();
		for(int i=0;i<cont;i++) {
			db.execSQL("INSERT INTO cv3 VALUES (null, "+idCV+", '"+trabajo[i].getText().toString()+"', '"+empresa[i].getText().toString()+"', '"+descripcion[i].getText().toString()+"', '"+inicio[i].getSeleccion()+"', '"+fin[i].getSeleccion()+"')");
		}
	}
	
	public void setCurriculumCV4(EditText[] idioma, TextView[][] nivel, int cont) { 
		SQLiteDatabase db = getWritableDatabase();
		for(int i=0;i<cont;i++) {
			try {
				db.execSQL("INSERT INTO cv4 VALUES (null, "+idCV+", '"+idioma[i].getText().toString()+"', '"+nivel[0][i].getText().toString()+"', '"+nivel[1][i].getText().toString()+"', '"+nivel[2][i].getText().toString()+"')");
			} catch (Exception e) {}
		}
	}

	public void setCurriculumCV5(CheckBox equipo, CheckBox desplazarse, CheckBox coche, CheckBox viajar) throws Exception { 
		SQLiteDatabase db = getWritableDatabase();
		if (equipo.isChecked())      db.execSQL("INSERT INTO cv5 VALUES (null, "+idCV+", 'equipo')");
		if (desplazarse.isChecked()) db.execSQL("INSERT INTO cv5 VALUES (null, "+idCV+", 'desplazarse')");
		if (coche.isChecked())       db.execSQL("INSERT INTO cv5 VALUES (null, "+idCV+", 'coche')");
		if (viajar.isChecked())      db.execSQL("INSERT INTO cv5 VALUES (null, "+idCV+", 'viajar')");
	}
	
	public void setCurriculumCV6(EditText[] titulo, EditText[][] subtitulo, EditText[][] descripcion, int numTabs, Integer[] contadorModulos) throws Exception { 

		SQLiteDatabase db = getWritableDatabase();
		SQLiteDatabase re = getReadableDatabase();
		
		for(int i=0; i<numTabs+1; i++) {
			db.execSQL("INSERT INTO cv6_titulo VALUES (null, "+idCV+", '"+titulo[i].getText().toString()+"')");
			long id_titulo = -1;
			// RECUPERA ID_TITULO ASIGNADO
			Cursor c = re.rawQuery("SELECT _id FROM cv6_titulo ORDER BY _id DESC limit 1", null);
			if (c != null && c.moveToFirst()) { id_titulo = c.getLong(0); }
			int numMod = contadorModulos[i+3];
				
			for(int j=0; j<=numMod;j++) {
				db.execSQL("INSERT INTO cv6_subtitulo VALUES (null, "+idCV+", "+id_titulo+", '"+subtitulo[i][j].getText().toString()+"', '"+descripcion[i][j].getText().toString()+"')");
			}
		}
	}
	
	public String getNombreFichero() {
		String n = "";
		try {
			SQLiteDatabase b = getReadableDatabase();
			Cursor c = b.rawQuery("SELECT ruta FROM curriculum WHERE _id ="+idCV, null);
			if (c != null && c.moveToFirst()) {
				n = c.getString(0); 
				String[] aux = n.split("/");
				n = aux[aux.length-1];
		}
		return n;
		} catch (Exception e) {
			return "";
		}
	}
	
	public Vector<String> getCV1() throws Exception {
		Vector<String> resul = new Vector<String>();
		try {
			SQLiteDatabase b = getReadableDatabase();
			Cursor c = b.rawQuery("SELECT nombre, nacimiento, email, telf, localidad, rutafoto FROM cv1 WHERE id_cv ="+ idCV + " limit 1", null);
			while (c.moveToNext()) {
				resul.add(c.getString(0));
				resul.add(c.getString(1));
				resul.add(c.getString(2));
				resul.add(c.getString(3));
				resul.add(c.getString(4));
				resul.add(c.getString(5));
			}
			c.close();
			return  resul;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Vector<Vector<String>> getCV2() throws Exception {
		Vector<Vector<String>> resul = new Vector<Vector<String>>();
			SQLiteDatabase b = getReadableDatabase();
			Cursor c = b.rawQuery("SELECT estudios, centro, localidad, inicio, fin FROM cv2 WHERE id_cv ="+idCV, null);
			while (c.moveToNext()) {
				Vector<String> aux = new Vector<String>();
				aux.add(c.getString(0));
				aux.add(c.getString(1));
				aux.add(c.getString(2));
				aux.add(c.getString(3));
				aux.add(c.getString(4));
				resul.add(aux);
			}
			c.close();
			return  resul;
	}

	public Vector<Vector<String>> getCV3() throws Exception {
		Vector<Vector<String>> resul = new Vector<Vector<String>>();
		SQLiteDatabase b = getReadableDatabase();
		Cursor c = b.rawQuery("SELECT trabajo, empresa, descripcion, inicio, fin FROM cv3 WHERE id_cv ="+idCV, null);
		while (c.moveToNext()) {
			Vector<String> aux = new Vector<String>();
			aux.add(c.getString(0));
			aux.add(c.getString(1));
			aux.add(c.getString(2));
			aux.add(c.getString(3));
			aux.add(c.getString(4));
			resul.add(aux);
		}
		c.close();
		return resul;
	}
	
	public Vector<Vector<String>> getCV4() throws Exception {
		Vector<Vector<String>> resul = new Vector<Vector<String>>();
		SQLiteDatabase b = getReadableDatabase();
		Cursor c = b.rawQuery("SELECT idioma, hablado, escrito, lectura FROM cv4 WHERE id_cv ="+idCV, null);
		while (c.moveToNext()) {
			Vector<String> aux = new Vector<String>();
			aux.add(c.getString(0));
			aux.add(c.getString(1));
			aux.add(c.getString(2));
			aux.add(c.getString(3));
			resul.add(aux);
		}
		c.close();
		return resul;
	}

	public Vector<String> getCV5() throws Exception {
		Vector<String> resul = new Vector<String>();
		SQLiteDatabase b = getReadableDatabase();
		Cursor c = b.rawQuery("SELECT descripcion FROM cv5, cualidades WHERE cod = cualidad AND id_cv ="+idCV, null);
		while (c.moveToNext()) {
			resul.add(c.getString(0));
		}
		return resul;
	}

	public Vector<InfoGenerico> getCV6() throws Exception {
//	public String getCV6() throws Exception {
		Vector<InfoGenerico> resul = new Vector<InfoGenerico>();
		SQLiteDatabase b = getReadableDatabase();
		Cursor c = b.rawQuery("SELECT _id, texto FROM cv6_titulo WHERE id_cv ="+idCV, null);
		String a = "IDCV: " + idCV+" ";
		while (c.moveToNext()) {
			InfoGenerico aux = new InfoGenerico();
			aux.setTitulo(c.getString(1)); // Titulo
			a += "[ Titulo: " + c.getString(1);
			Cursor c2 = b.rawQuery("SELECT s.texto, s.descripcion FROM cv6_titulo t, cv6_subtitulo s WHERE t._id = s.id_titulo AND s.id_cv = "+idCV+" AND id_titulo = "+c.getString(0), null);
			while(c2.moveToNext()) {
				aux.setSubtitulos(c2.getString(0));    // Subtitulos
				a += ", Subtitulo: "+c2.getString(0);
				aux.setDescripciones(c2.getString(1)); // Descripciones
				a += ", Descripcion: "+c2.getString(1)+" ]";
			}
			resul.add(aux);
		}
		return resul;
//		return a;
	}
	
	public Vector<Vector<String>> getListaCV () {
		Vector<Vector<String>> resul = new Vector<Vector<String>>();
		SQLiteDatabase b = getReadableDatabase();
		Cursor c = b.rawQuery("SELECT c.ruta, c.autor, c1.nombre, cv1.rutafoto FROM curriculum c, cv1 c1 WHERE c._id = c1.id_cv", null);
		while (c.moveToNext()) {
			Vector<String> aux = new Vector<String>();
			aux.add(c.getString(0));
			aux.add(c.getString(1));
			aux.add(c.getString(2));
			aux.add(c.getString(3));
			resul.add(aux);
		}
		c.close();
		return resul;
	}
	
	
	public class InfoGenerico {
		private String titulo;
		private Vector<String> subtitulos;
		private Vector<String> descripciones;
		
		public InfoGenerico () {
			titulo        = "";
			subtitulos    = new Vector<String>();
			descripciones = new Vector<String>();
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public Vector<String> getSubtitulo() {
			return subtitulos;
		}
		
		public void setSubtitulos(String subtitulos) {
			this.subtitulos.add(subtitulos);
		}
		
		public Vector<String> getDescripcion() {
			return descripciones;
		}
		
		public void setDescripciones(String descripciones) {
			this.descripciones.add(descripciones);
		}
		
	}
}

