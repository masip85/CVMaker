package es.cvmaker.upv.aplicacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment.SavedState;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.view.menu.ActionMenu;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.artifex.mupdf.MuPDFActivity;
import com.artifex.mupdf.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.cvmaker.upv.aplicacion.GestorBBDD.InfoGenerico;

public class Actividad_Editar extends SherlockFragmentActivity {

	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	Handler handler = new Handler();
	TextView tabCenter;
	TextView tabText;

	int maxModulos = 3;
	int maxEntradas = 10;
	TabHost tabHost;
	View FragmentView[] = new View[maxEntradas];
	// layoutsdel tab que contiene todos los modulos
	LinearLayout cvLayoutModulos[] = new LinearLayout[maxEntradas];
	// array de layouts donde se mete cada modulo
	View cvLayoutModulo[][] = new View[maxEntradas][maxModulos]; //
	View tabExtra[] = new View[maxEntradas];
	// array de layouts que contiene el mas_menos
	LinearLayout cvLayoutMasMenos[] = new LinearLayout[maxEntradas]; //
	// Ojo,al maxEntradas [cv2 cv3 cv4 cv6 ... maxEntradas] <- Es donde hay
	// modulos
	// [cv2 cv3 cv4 cv6 ... maxEntradas]<- Es donde hay modulos
	Integer cvContadorModulos[] = new Integer[maxEntradas];
	// Este contador cuenta todo, y sirve para manejar los tabs
	boolean cvTabSelectedFirst[] = new boolean[maxEntradas];

	int tagTabActual;
	int posTabActual;
	static int id = 1;
	// / / / / / / / / / / / / / / / / /CV1 / / / / / / / / / / / / / / / / / /
	Drawable foto;
	public static final int PETICION_CAMARA = 2000;
	public static final int PETICION_CARGARFOTO = 1000;
	String rutaFicheroSeleccionado = "";
	EditText cv1EditNombre;
	EditText cv1EditEmail;
	EditText cv1EditLocalidad;
	EditText cv1EditTelefono;
	DatePicker cv1DatePicker;
	TextView cv1FechaNacimiento;
	int dia = 15;
	int mes = 7;
	int ano = 2000;
	// / / / / / / / / / / / / / / / / /CV2 / / / / / / / / / / / / / / / /
	EditText cv2EditLocalidad[] = new EditText[maxModulos];
	EditText cv2EditCentro[] = new EditText[maxModulos];
	EditText cv2EditEstudios[] = new EditText[maxModulos];
	// / / / / / / / / / / / / / / / / /CV3 / / / / / / / / / / / / / / / /
	EditText cv3EditTrabajo[] = new EditText[maxModulos];
	EditText cv3EditEmpresa[] = new EditText[maxModulos];
	EditText cv3EditDescripcion[] = new EditText[maxModulos];
	// / / / / / / / / / / / / / / / / /CV2 y CV3 / / / / / / / / / / / / / / /
	// [cv2 cv3 cv4 cv6... maxEntradas] <- Es donde hay modulos
	// array spinners año inicio de todos los tabs y modulos
	Spinner spinnerArray1[][] = new Spinner[2][maxModulos];
	// array spinners año fin de todos los tabs y modulos
	Spinner spinnerArray2[][] = new Spinner[2][maxModulos];
	// Ojo solo hay spinners en cv2 y cv3
	String spinnerItemSeleccionado1[][] = new String[2][maxModulos];
	// la primera dimension de los spinners te dice en que cv se encuentra: cv2
	// o cv3
	String spinnerItemSeleccionado2[][] = new String[2][maxModulos];
	// respectivos listeners de los spinners MyOnItemSelectedListener
	MyOnItemSelectedListener spinnerListener1[][] = new MyOnItemSelectedListener[2][maxModulos];
	MyOnItemSelectedListener spinnerListener2[][] = new MyOnItemSelectedListener[2][maxModulos];
	// / / / / / / / / / / / / / / / / /CV4 / / / / / / / / / / / / / / / /
	// la primera dimension es para los 3 tipos de habilidad(Escrito,hablado,y
	// lectura
	SeekBar cv4SeekBarIdioma[][] = new SeekBar[3][maxModulos];
	TextView cv4TextViewIdioma[][] = new TextView[3][maxModulos];
	EditText cv4EditIdioma[] = new EditText[maxModulos];
	// / / / / / / / / / / / / / / / / /CV5 / / / / / / / / / / / / / / / /
	CheckBox cv5CheckBox_1;
	CheckBox cv5CheckBox_2;
	CheckBox cv5CheckBox_3;
	CheckBox cv5CheckBox_4;
	// / / / / / / / / / / / / / / / / /CV5 / / / / / / / / / / / / / / / /
	EditText cv6EditTitulo[] = new EditText[maxEntradas];
	EditText cv6EditSubtitulo[][] = new EditText[maxEntradas][maxModulos];
	EditText cv6EditCampo[][] = new EditText[maxEntradas][maxModulos];
	int contadorGenericoExtra = 0;
	// / / / / / / / / / / / / / / / / /CV6 / / / / / / / / / / / / / / / /
	int tagTabCreado;
	int tabCreado;
	ActionBar bar;
	ActionMenu auxMenu;
	ActionMode mMode;
	String nombrePDF = "";
	View viewMenuActionBar;
	EditText editCollapsable;
	View barra[][] = new View[maxEntradas][maxModulos];
	boolean cvInicializado[] = new boolean[maxEntradas + 2];
	Bundle bundleCV1 = new Bundle();
	Bundle bundleCV2 = new Bundle();
	Bundle bundleCV3 = new Bundle();
	Bundle bundleCV4 = new Bundle();
	Bundle bundleCV5 = new Bundle();
	Bundle bundleCV6 = new Bundle();
	Bundle bundleCVExtra[] = new Bundle[maxEntradas + 2];

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// ProgressBarIndeterminate
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.actividad_cv_scroll_tab);
		

		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);

		setContentView(mViewPager);
		bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(false);

		mTabsAdapter = new TabsAdapter(this, mViewPager);

		mTabsAdapter.addTab(
				bar.newTab()
						.setText(getResources().getText(R.string.cv1Titulo)),
				ActividadEditar1.class, null);
		mTabsAdapter.addTab(
				bar.newTab()
						.setText(getResources().getText(R.string.cv2Titulo)),
				ActividadEditar2.class, null);
		mTabsAdapter.addTab(
				bar.newTab()
						.setText(getResources().getText(R.string.cv3Titulo)),
				ActividadEditar3.class, null);
		mTabsAdapter.addTab(
				bar.newTab()
						.setText(getResources().getText(R.string.cv4Titulo)),
				ActividadEditar4.class, null);
		mTabsAdapter.addTab(
				bar.newTab()
						.setText(getResources().getText(R.string.cv5Titulo)),
				ActividadEditar5.class, null);
		mTabsAdapter.addTab(
				bar.newTab()
						.setText(getResources().getText(R.string.cv6Titulo)),
				ActividadEditar6.class, null);
		for (int j = 0; j < cvContadorModulos.length; j++) {
			cvContadorModulos[j] = 0; // Inicializo contadores de modulos
		}
		cv1FechaNacimiento = null;
		for (int j = 0; j < maxEntradas + 2; j++) {
			cvInicializado[j] = false;
		}
	}

	public void inicializarCV1() {

		ImageView imageView = (ImageView) FragmentView[0]
				.findViewById(R.id.cv1ImageViewFoto);
		Drawable drawable = getResources().getDrawable(R.drawable.silueta);
		imageView.setImageDrawable(drawable);
		// Defino los EditText
		cv1EditNombre = (EditText) FragmentView[0]
				.findViewById(R.id.cv1EditNombre);
		cv1EditEmail = (EditText) FragmentView[0]
				.findViewById(R.id.cv1EditEmail);
		cv1EditLocalidad = (EditText) FragmentView[0]
				.findViewById(R.id.cv1EditLocalidad);
		cv1EditTelefono = (EditText) FragmentView[0]
				.findViewById(R.id.cv1EditTelefono);

		cv1FechaNacimiento = (TextView) FragmentView[0]
				.findViewById(R.id.cv1TextViewFechaNacimiento2);
		cv1FechaNacimiento.setText(Integer.toString(dia) + "/"
				+ Integer.toString(mes + 1) + "/" + Integer.toString(ano));

	}

	public void guardarCV1() {

		bundleCV1.putString("rutaFicheroSeleccionado", rutaFicheroSeleccionado);
		bundleCV1.putString("cv1EditEmail", cv1EditEmail.getText().toString());
		bundleCV1.putString("cv1EditTelefono", cv1EditTelefono.getText()
				.toString());
		bundleCV1.putString("cv1EditLocalidad", cv1EditLocalidad.getText()
				.toString());
		bundleCV1
				.putString("cv1EditNombre", cv1EditNombre.getText().toString());
		bundleCV1.putString("cv1FechaDeNacimiento", cv1FechaNacimiento
				.getText().toString());
		Log.i("He guardado cv1", "- "+cv1FechaNacimiento.getText().toString());

	}

	public void reconstruirCV1() {
		if (!bundleCV1.getString("rutaFicheroSeleccionado").equals("")) {
			File origen = new File(rutaFicheroSeleccionado);
			if (origen.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(origen
						.getAbsolutePath());
				ImageView myImage = (ImageView) findViewById(R.id.cv1ImageViewFoto);
				myImage.setImageBitmap(myBitmap);
			}
		} else{
			ImageView imageView = (ImageView) FragmentView[0]
					.findViewById(R.id.cv1ImageViewFoto);
			Drawable drawable = getResources().getDrawable(R.drawable.silueta);
			imageView.setImageDrawable(drawable);
		}
		
		cv1FechaNacimiento = (TextView) FragmentView[0]
				.findViewById(R.id.cv1TextViewFechaNacimiento2);
		Log.i("Reconstruyo cv1", "- "+bundleCV1.getString("c"));
		cv1FechaNacimiento.setText(bundleCV1.getString("cv1FechaDeNacimiento"));
		
	}

	public void inicializarCV2() {
		cvLayoutModulos[0] = (LinearLayout) FragmentView[1]
				.findViewById(R.id.cv2LayoutModulos);

		cvLayoutModulo[0][cvContadorModulos[0]] = (View) FragmentView[1]
				.findViewById(R.id.cv2LayoutModulo);

		setSpinner(R.array.ano, R.id.cv2SpinnerAnoInicio, 0, 0,
				cvContadorModulos[0]);
		setSpinner(R.array.ano, R.id.cv2SpinnerAnoFin, 0, 1,
				cvContadorModulos[0]);

		listenSpinner(0, 0, 0);
		listenSpinner(0, 1, 0);

		// Defino los EditText
		cv2EditEstudios[cvContadorModulos[0]] = (EditText) cvLayoutModulo[0][cvContadorModulos[0]]
				.findViewById(R.id.cv2EditEstudios);
		cv2EditCentro[cvContadorModulos[0]] = (EditText) cvLayoutModulo[0][cvContadorModulos[0]]
				.findViewById(R.id.cv2EditCentro);
		cv2EditLocalidad[cvContadorModulos[0]] = (EditText) cvLayoutModulo[0][cvContadorModulos[0]]
				.findViewById(R.id.cv2EditLocalidad);
	}

	public void guardarCV2() {
		String estudios[] = new String[maxModulos];
		String centro[] = new String[maxModulos];
		String localidad[] = new String[maxModulos];
		int inicio[] = new int[maxModulos];
		int fin[] = new int[maxModulos];

		for (int i = 0; i <= cvContadorModulos[0]; i++) {

			estudios[i] = cv2EditEstudios[i].getText().toString();
			Log.i("Guardo estudios", " " + i + " "
					+ cv2EditEstudios[i].getText().toString());
			centro[i] = cv2EditCentro[i].getText().toString();
			localidad[i] = cv2EditLocalidad[i].getText().toString();
			inicio[i] = spinnerListener1[0][i].getSeleccionPosicion();
			fin[i] = spinnerListener2[0][i].getSeleccionPosicion();
			Log.i("Bucle Guardando", "It: " + i);
			Log.i("Bucle Guardando", "inicio: " + i + " " + inicio[i]);
			Log.i("Bucle Guardando", "fin: " + i + " " + fin[i]);
		}
		bundleCV2.putStringArray("estudios", estudios);
		bundleCV2.putStringArray("centro", centro);
		bundleCV2.putStringArray("localidad", localidad);
		bundleCV2.putIntArray("inicio", inicio);
		bundleCV2.putIntArray("fin", fin);
	}

	public void inflarCV2() {

		// La funciona inicializar aqui no vale. Duplico de nuevo
		cvLayoutModulos[0] = (LinearLayout) FragmentView[1]
				.findViewById(R.id.cv2LayoutModulos);
		cvLayoutModulo[0][0] = (View) cvLayoutModulos[0]
				.findViewById(R.id.cv2LayoutModulo);

		cv2EditEstudios[0] = (EditText) cvLayoutModulo[0][0]
				.findViewById(R.id.cv2EditEstudios);
		cv2EditCentro[0] = (EditText) cvLayoutModulo[0][0]
				.findViewById(R.id.cv2EditCentro);
		cv2EditLocalidad[0] = (EditText) cvLayoutModulo[0][0]
				.findViewById(R.id.cv2EditLocalidad);

		setSpinner(R.array.ano, R.id.cv2SpinnerAnoInicio, 0, 0, 0);
		setSpinner(R.array.ano, R.id.cv2SpinnerAnoFin, 0, 1, 0);
		listenSpinner(0, 0, 0);
		listenSpinner(0, 1, 0);

		for (int i = 1; i <= cvContadorModulos[0]; i++) {
			lanzarcvMasReconstruir(0, 1, i);
		}

	}

	public void reconstruirCV2() {

		String estudios[] = new String[maxModulos];
		String centro[] = new String[maxModulos];
		String localidad[] = new String[maxModulos];
		int inicio[] = new int[maxModulos];
		int fin[] = new int[maxModulos];
		estudios = bundleCV2.getStringArray("estudios");
		centro = bundleCV2.getStringArray("centro");
		localidad = bundleCV2.getStringArray("localidad");
		inicio = bundleCV2.getIntArray("inicio");
		fin = bundleCV2.getIntArray("fin");

		for (int i = 0; i <= cvContadorModulos[0]; i++) {
			Log.i("Reconstruyo estudios", " " + i + " " + estudios[i]);
			Log.i("Reconstruyo estudios", " " + cv2EditEstudios[i].getId());
			cv2EditEstudios[i].setText(estudios[i]);
			cv2EditCentro[i].setText(centro[i]);
			cv2EditLocalidad[i].setText(localidad[i]);
			Log.i("Bucle reconstruyendo", "It: " + i);
			Log.i("Bucle reconstruyendo", "inicio: " + i + " " + inicio[i]);
			Log.i("Bucle reconstruyendo", "fin: " + i + " " + fin[i]);
			spinnerArray1[0][i].setSelection(inicio[i]);
			spinnerArray2[0][i].setSelection(fin[i]);
		}

	}

	public void inicializarCV3() {
		Log.i("ÑAAAAAA","dfdfdfdsfdf "+cvContadorModulos[1]+" ");
		cvLayoutModulos[1] = (LinearLayout) FragmentView[2]
				.findViewById(R.id.cv3LayoutModulos);
		cvLayoutModulo[1][cvContadorModulos[1]] = (View) FragmentView[2]
				.findViewById(R.id.cv3LayoutModulo);
		setSpinner(R.array.ano, R.id.cv3SpinnerAnoInicio, 1, 0,
				cvContadorModulos[1]);
		setSpinner(R.array.ano, R.id.cv3SpinnerAnoFin, 1, 1,
				cvContadorModulos[1]);
		listenSpinner(1, 0, 0);
		listenSpinner(1, 1, 0);
		// Defino los EditText
		cv3EditTrabajo[cvContadorModulos[1]] = (EditText) cvLayoutModulo[1][cvContadorModulos[1]]
				.findViewById(R.id.cv3EditTrabajo);
		cv3EditEmpresa[cvContadorModulos[1]] = (EditText) cvLayoutModulo[1][cvContadorModulos[1]]
				.findViewById(R.id.cv3EditEmpresa);
		cv3EditDescripcion[cvContadorModulos[1]] = (EditText) cvLayoutModulo[1][cvContadorModulos[1]]
				.findViewById(R.id.cv3EditDescripcion);
	}

	public void guardarCV3() {
		String trabajo[] = new String[maxModulos];
		String empresa[] = new String[maxModulos];
		String descripcion[] = new String[maxModulos];
		int inicio[] = new int[maxModulos];
		int fin[] = new int[maxModulos];
		for (int i = 0; i <= cvContadorModulos[1]; i++) {
			trabajo[i] = cv3EditTrabajo[i].getText().toString();
			empresa[i] = cv3EditEmpresa[i].getText().toString();
			descripcion[i] = cv3EditDescripcion[i].getText().toString();
			inicio[i] = spinnerListener1[1][i].getSeleccionPosicion();
			fin[i] = spinnerListener2[1][i].getSeleccionPosicion();
		}
		bundleCV3.putStringArray("trabajo", trabajo);
		bundleCV3.putStringArray("empresa", empresa);
		bundleCV3.putStringArray("descripcion", descripcion);
		bundleCV3.putIntArray("inicio", inicio);
		bundleCV3.putIntArray("fin", fin);
	}

	public void inflarCV3() {

		// La funciona inicializar aqui no vale. Duplico de nuevo
		cvLayoutModulos[1] = (LinearLayout) FragmentView[2]
				.findViewById(R.id.cv3LayoutModulos);
		cvLayoutModulo[1][0] = (View) FragmentView[2]
				.findViewById(R.id.cv3LayoutModulo);
		setSpinner(R.array.ano, R.id.cv3SpinnerAnoInicio, 1, 0, 0);
		setSpinner(R.array.ano, R.id.cv3SpinnerAnoFin, 1, 1, 0);
		listenSpinner(1, 0, 0);
		listenSpinner(1, 1, 0);
		// Defino los EditText
		cv3EditTrabajo[0] = (EditText) cvLayoutModulo[1][0]
				.findViewById(R.id.cv3EditTrabajo);
		cv3EditEmpresa[0] = (EditText) cvLayoutModulo[1][0]
				.findViewById(R.id.cv3EditEmpresa);
		cv3EditDescripcion[0] = (EditText) cvLayoutModulo[1][0]
				.findViewById(R.id.cv3EditDescripcion);

		for (int i = 1; i <= cvContadorModulos[1]; i++) {
			lanzarcvMasReconstruir(1, 2, i);
		}

	}

	public void reconstruirCV3() {

		String trabajo[] = new String[maxModulos];
		String empresa[] = new String[maxModulos];
		String descripcion[] = new String[maxModulos];
		int inicio[] = new int[maxModulos];
		int fin[] = new int[maxModulos];
		trabajo = bundleCV3.getStringArray("trabajo");
		empresa = bundleCV3.getStringArray("empresa");
		descripcion = bundleCV3.getStringArray("descripcion");
		inicio = bundleCV3.getIntArray("inicio");
		fin = bundleCV3.getIntArray("fin");

		for (int i = 0; i <= cvContadorModulos[1]; i++) {
			cv3EditTrabajo[i].setText(trabajo[i]);
			cv3EditEmpresa[i].setText(empresa[i]);
			cv3EditDescripcion[i].setText(descripcion[i]);
			Log.i("dfdfdsfsd"+inicio[i],"----");
			spinnerArray1[1][i].setSelection(inicio[i]);
			spinnerArray2[1][i].setSelection(fin[i]);
		}
	}

	public void inicializarCV4() {
		cvLayoutModulos[2] = (LinearLayout) FragmentView[3]
				.findViewById(R.id.cv4LayoutModulos);
		cvLayoutModulo[2][cvContadorModulos[2]] = (View) FragmentView[3]
				.findViewById(R.id.cv4LayoutModulo);
		Log.i("Guardo idioma","inicializo idioma");
		cv4EditIdioma[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[2][cvContadorModulos[2]]
				.findViewById(R.id.cv4EditIdioma);
		setSeekBar(0, cvContadorModulos[2], R.id.cv4SeekBarHablado,
				R.id.cv4TextViewSeekBarHablado,R.string.cv4bueno);
		setSeekBar(1, cvContadorModulos[2], R.id.cv4SeekBarEscrito,
				R.id.cv4TextViewSeekBarEscrito,R.string.cv4bueno);
		setSeekBar(2, cvContadorModulos[2], R.id.cv4SeekBarLectura,
				R.id.cv4TextViewSeekBarLectura,R.string.cv4bueno);
		listenSeekBars(cvContadorModulos[2]);
	}

	public void inflarCV4() {
		// Inicializacion primero modulo
		cvLayoutModulos[2] = (LinearLayout) FragmentView[3]
				.findViewById(R.id.cv4LayoutModulos);
		cvLayoutModulo[2][0] = (View) FragmentView[3]
				.findViewById(R.id.cv4LayoutModulo);
		Log.i("Guardo idioma","inicializo idioma en inflar");
		cv4EditIdioma[0] = (EditText) cvLayoutModulo[2][0]
				.findViewById(R.id.cv4EditIdioma);
		setSeekBar(0, 0, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4bueno);
		setSeekBar(1, 0, R.id.cv4SeekBarEscrito, R.id.cv4TextViewSeekBarEscrito,R.string.cv4bueno);
		setSeekBar(2, 0, R.id.cv4SeekBarLectura, R.id.cv4TextViewSeekBarLectura,R.string.cv4bueno);
		listenSeekBars(0);
		// Inflo siguientes modulos
		for (int i = 1; i <= cvContadorModulos[2]; i++) {
			lanzarcvMasReconstruir(2, 3, i);
		}

	}

	public void guardarCV4() {
		String idioma[] = new String[maxModulos];
		String hablado[] = new String[maxModulos];
		String escrito[] = new String[maxModulos];
		String lectura[] = new String[maxModulos];
		for (int i = 0; i <= cvContadorModulos[2]; i++) {
			idioma[i] = cv4EditIdioma[i].getText().toString();
			lectura[i] = cv4TextViewIdioma[2][i].getText().toString();
			hablado[i] = cv4TextViewIdioma[0][i].getText().toString();
			escrito[i] = cv4TextViewIdioma[1][i].getText().toString();
			Log.i("Guardo idioma"+i,idioma[i]);
		}
		bundleCV4.putStringArray("idioma", idioma);
		bundleCV4.putStringArray("hablado", hablado);
		bundleCV4.putStringArray("lectura", lectura);
		bundleCV4.putStringArray("escrito", escrito);
	}

	public void reconstruirCV4() {

		String idioma[] = new String[maxModulos];
		String hablado[] = new String[maxModulos];
		String escrito[] = new String[maxModulos];
		String lectura[] = new String[maxModulos];
		idioma = bundleCV4.getStringArray("idioma");
		hablado = bundleCV4.getStringArray("hablado");
		escrito = bundleCV4.getStringArray("escrito");
		lectura = bundleCV4.getStringArray("lectura");

		for (int i = 0; i <= cvContadorModulos[2]; i++) {
			Log.i("Reconstruyo idioma "," "+i);
			cv4EditIdioma[i].setText(idioma[i]);
//			cv4TextViewIdioma[0][i].setText(hablado[i]);
//			cv4TextViewIdioma[0][i].setText(escrito[i]);
//			cv4TextViewIdioma[0][i].setText(lectura[i]);
			
			if (hablado[i].equals(getString(R.string.cv4regular))){
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4regular);
			}else if(hablado[i].equals(getString(R.string.cv4bueno))){
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4bueno);
			}else if(hablado[i].equals(getString(R.string.cv4muyBueno))){
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4muyBueno);
			}else{
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4excelente);
			}
			if (escrito[i].equals(getString(R.string.cv4regular))){
				setSeekBar(1, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4regular);
				
			}else if(escrito[i].equals(getString(R.string.cv4bueno))){
				setSeekBar(1, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4bueno);
				
			}else if(escrito[i].equals(getString(R.string.cv4muyBueno))){
				setSeekBar(1, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4muyBueno);
				
			}else{
				setSeekBar(1, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4excelente);
				
			}
			if (lectura[i].equals(getString(R.string.cv4regular))){
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4regular);
				
			}else if(lectura[i].equals(getString(R.string.cv4bueno))){
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4bueno);
				
			}else if(lectura[i].equals(getString(R.string.cv4muyBueno))){
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4muyBueno);
				
			}else{
				setSeekBar(0, i, R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4excelente);
				
			}
			
			
		}
	}

	public void inicializarCV5() {
		cv5CheckBox_1 = (CheckBox) FragmentView[4]
				.findViewById(R.id.cv5CheckBox1);
		cv5CheckBox_2 = (CheckBox) FragmentView[4]
				.findViewById(R.id.cv5CheckBox2);
		cv5CheckBox_3 = (CheckBox) FragmentView[4]
				.findViewById(R.id.cv5CheckBox3);
		cv5CheckBox_4 = (CheckBox) FragmentView[4]
				.findViewById(R.id.cv5CheckBox4);
	}

	public void guardarCV5() {
		Boolean checkedBox[] = new Boolean[4];
		Bundle bundleCV5 = new Bundle();
		checkedBox[0] = cv5CheckBox_1.isChecked();
		checkedBox[1] = cv5CheckBox_2.isChecked();
		checkedBox[2] = cv5CheckBox_3.isChecked();
		checkedBox[3] = cv5CheckBox_4.isChecked();

		// No funciona put boolean array :S
		bundleCV5.putBoolean("cv5CheckBox_1", checkedBox[0]);
		bundleCV5.putBoolean("cv5CheckBox_2", checkedBox[1]);
		bundleCV5.putBoolean("cv5CheckBox_3", checkedBox[2]);
		bundleCV5.putBoolean("cv5CheckBox_4", checkedBox[3]);

	}
	
	public void inicializarCV6() {
		cvLayoutModulos[3] = (LinearLayout) FragmentView[5]
				.findViewById(R.id.cv6LayoutModulos);
		cvLayoutModulo[3][cvContadorModulos[3]] = (View) FragmentView[5]
				.findViewById(R.id.cv6LayoutModulo);
		cv6EditTitulo[0] = (EditText) FragmentView[5]
				.findViewById(R.id.cv6EditTituloCampo);
		cv6EditSubtitulo[0][cvContadorModulos[3]] = (EditText) FragmentView[5]
				.findViewById(R.id.cv6EditSubtituloCampo);
		cv6EditCampo[0][cvContadorModulos[3]] = (EditText) FragmentView[5]
				.findViewById(R.id.cv6EditCampo);
	}

	public void guardarCV6() {
		String subtitulo[] = new String[maxModulos];
		String campo[] = new String[maxModulos];
		for (int i = 0; i <= cvContadorModulos[3]; i++) {
			subtitulo[i] = cv6EditSubtitulo[0][i].getText().toString();
			campo[i] = cv6EditCampo[0][i].getText().toString();
		}
		bundleCV6.putString("titulo", cv6EditTitulo[0].getText()
				.toString());
		bundleCV6.putStringArray("subtitulo", subtitulo);
		bundleCV6.putStringArray("campo", campo);
	}

	public void inflarCV6() {

		// La funciona inicializar aqui no vale. Duplico de nuevo
		cvLayoutModulos[3] = (LinearLayout) FragmentView[5]
				.findViewById(R.id.cv6LayoutModulos);
		cvLayoutModulo[3][0] = (View) FragmentView[5]
				.findViewById(R.id.cv6LayoutModulo);
		cv6EditTitulo[0] = (EditText) FragmentView[5]
				.findViewById(R.id.cv6EditTituloCampo);
		cv6EditSubtitulo[0][0] = (EditText) FragmentView[5]
				.findViewById(R.id.cv6EditSubtituloCampo);
		cv6EditCampo[0][0] = (EditText) FragmentView[5]
				.findViewById(R.id.cv6EditCampo);

		for (int i = 1; i <= cvContadorModulos[3]; i++) {
			lanzarcvMasReconstruir(3, 5, i);
		}

	}
	
	public void reconstruirCV6() {
		String titulo;
		String subtitulo[] = new String[maxModulos];
		String campo[] = new String[maxModulos];
		
		titulo = bundleCV6.getString("titulo");
		subtitulo = bundleCV6.getStringArray("subtitulo");
		campo = bundleCV6.getStringArray("campo");
		cv6EditTitulo[0].setText(titulo);
		
		for (int i = 0; i <= cvContadorModulos[3]; i++) {
			cv6EditCampo[0][i].setText(campo[i]);
			cv6EditSubtitulo[0][i].setText(subtitulo[i]);
			
		}
		

	}
	
	
	public void inicializarCVExtra() {

		tabExtra[contadorGenericoExtra - 1].setTag(tagTabCreado);
		tabExtra[contadorGenericoExtra - 1].setId(findId());
		// Defino los elementos de los nuevos tabs
		// Recordar que los modulos con "+" para añadir informacion son:[cv2 cv3
		// cv4 cv6 cv7... maxEntradas]
		// Así que la primera posicion a usar aqui seria la 4 ->tagTabCreado
		cvLayoutModulos[tagTabCreado] = (LinearLayout) tabExtra[contadorGenericoExtra - 1]
				.findViewById(R.id.cv6LayoutModulos);
		cvLayoutModulo[tagTabCreado][cvContadorModulos[tagTabCreado]] = (View) tabExtra[contadorGenericoExtra - 1]
				.findViewById(R.id.cv6LayoutModulo);

		cv6EditTitulo[contadorGenericoExtra] = (EditText) tabExtra[contadorGenericoExtra - 1]
				.findViewById(R.id.cv6EditTituloCampo);
		cv6EditSubtitulo[contadorGenericoExtra][cvContadorModulos[tagTabCreado]] = (EditText) cvLayoutModulo[tagTabCreado][cvContadorModulos[tagTabCreado]]
				.findViewById(R.id.cv6EditSubtituloCampo);
		cv6EditCampo[contadorGenericoExtra][cvContadorModulos[tagTabCreado]] = (EditText) cvLayoutModulo[tagTabCreado][cvContadorModulos[tagTabCreado]]
				.findViewById(R.id.cv6EditCampo);

		if (tagTabCreado == maxEntradas - 1) {
			// Busco el boton nuevo del layout generado
			View view3 = (View) cvLayoutModulos[tagTabCreado].getParent()
					.getParent();
			// Ya no puedo poner mas genericos. desactivo y no hay btn siguiente
			Button botonNuevo = (Button) view3
					.findViewById(R.id.cv6BotonNuevoGenerico);
			botonNuevo.setEnabled(false);
			botonNuevo.setText(R.string.cv6_max);
		} else {
			View view3 = (View) cvLayoutModulos[tagTabCreado].getParent()
					.getParent();
			view3.findViewById(R.id.cv6BotonNuevoGenerico).setEnabled(true);
		}

	}
	
	public void guardarCVExtra(int contadorGenExtra2) {
		if (contadorGenericoExtra > 0) {
				
				String subtitulo[] = new String[maxModulos];
				String campo[] = new String[maxModulos];
				for (int i = 0; i <= cvContadorModulos[3 + contadorGenExtra2]; i++) {
					subtitulo[contadorGenExtra2] = cv6EditSubtitulo[contadorGenExtra2][i].getText().toString();
					campo[contadorGenExtra2] = cv6EditCampo[contadorGenExtra2][i].getText().toString();
				}
				
				bundleCVExtra[contadorGenExtra2].putString("titulo", cv6EditTitulo[contadorGenExtra2]
						.getText().toString());
				bundleCVExtra[contadorGenExtra2].putStringArray("subtitulo", subtitulo);
				bundleCVExtra[contadorGenExtra2].putStringArray("campo", campo);
		}
	}
	
	
	public void inflarCVExtra(int contadorGenExtra2) {

		// La funciona inicializar aqui no vale. Duplico de nuevo
		cvLayoutModulos[3+contadorGenExtra2] = (LinearLayout) FragmentView[5+contadorGenExtra2]
				.findViewById(R.id.cv6LayoutModulos);
		cvLayoutModulo[3+contadorGenExtra2][0] = (View) FragmentView[5+contadorGenExtra2]
				.findViewById(R.id.cv6LayoutModulo);
		cv6EditTitulo[contadorGenExtra2] = (EditText) FragmentView[5+contadorGenExtra2]
				.findViewById(R.id.cv6EditTituloCampo);
		cv6EditSubtitulo[contadorGenExtra2][0] = (EditText) FragmentView[5+contadorGenExtra2]
				.findViewById(R.id.cv6EditSubtituloCampo);
		cv6EditCampo[contadorGenExtra2][0] = (EditText) FragmentView[5+contadorGenExtra2]
				.findViewById(R.id.cv6EditCampo);

		for (int i = 1; i <= cvContadorModulos[3+contadorGenExtra2]; i++) {
			lanzarcvMasReconstruir(3+contadorGenExtra2, 6+contadorGenExtra2, i);
		}

	}
	
	public void reconstruirCVExtra(int contadorGenExtra2) {
		String titulo;
		String subtitulo[] = new String[maxModulos];
		String campo[] = new String[maxModulos];
		
		titulo = bundleCVExtra[contadorGenExtra2].getString("titulo");
		subtitulo = bundleCVExtra[contadorGenExtra2].getStringArray("subtitulo");
		campo = bundleCVExtra[contadorGenExtra2].getStringArray("campo");
		cv6EditTitulo[contadorGenExtra2].setText(titulo);
		
		for (int i = 0; i <= cvContadorModulos[3+contadorGenExtra2]; i++) {
			cv6EditCampo[0][i].setText(campo[i]);
			cv6EditSubtitulo[0][i].setText(subtitulo[i]);
			
		}
		
	}
	
	public static class TabsAdapter extends FragmentPagerAdapter implements

			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		private ActionBar.Tab tabActual;

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mActionBar = activity.getSupportActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			Fragment a = Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
			Bundle bundle = new Bundle();
			bundle.putInt("id", 33);
			a.setArguments(bundle);
			return a;
		}

		public Integer getTabPosition() {
			return tabActual.getPosition();
		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		public void onPageScrollStateChanged(int state) {
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {

			Object tag = tab.getTag();
			for (int i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}

			tabActual = tab;

		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

	}
	

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PETICION_CARGARFOTO & resultCode == RESULT_OK) {
			rutaFicheroSeleccionado = data.getExtras().getString(
					"RutaArchivoSeleccionado");

			/** Se verifica que la img seleccionada no este ya en CVMaker/img **/
			String[] x = rutaFicheroSeleccionado.split("/");
			if(!x[x.length-3].equals("CVMaker")) {
				File origen = new File(rutaFicheroSeleccionado);
				if (origen.exists()) {
					Bitmap myBitmap = BitmapFactory.decodeFile(origen
							.getAbsolutePath());
					ImageView myImage = (ImageView) findViewById(R.id.cv1ImageViewFoto);
					myImage.setImageBitmap(myBitmap);
	
					/* Se almacena en /CVMaker/img/ */
					File directorio = new File(
							Environment.getExternalStorageDirectory()
									+ "/CVMaker/img/");
					if (!directorio.exists())
						directorio.mkdirs();
	
					String[] aux = rutaFicheroSeleccionado.split("/");
					File destino = new File(
							Environment.getExternalStorageDirectory()
									+ "/CVMaker/img/" + aux[aux.length - 1]);
	
					try {
						InputStream in = new FileInputStream(origen);
						OutputStream out = new FileOutputStream(destino);
						byte[] buf = new byte[1024];
						int len;
						try {
							while ((len = in.read(buf)) > 0) {
								out.write(buf, 0, len);
							}
						} catch (IOException e) {
							rutaFicheroSeleccionado = "";
						}
					} catch (FileNotFoundException e) {
						rutaFicheroSeleccionado = "";
					}
				} else
					rutaFicheroSeleccionado = "";
			} else {
				// Se asigna sin hacer copia del img
				Bitmap myBitmap = BitmapFactory.decodeFile(rutaFicheroSeleccionado);
				ImageView myImage = (ImageView) findViewById(R.id.cv1ImageViewFoto);
				myImage.setImageBitmap(myBitmap);
			}
		}
		if (requestCode == PETICION_CAMARA) {
			try {
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				ImageView imageView = (ImageView) findViewById(R.id.cv1ImageViewFoto);
				imageView.setImageBitmap(photo);
				try {
					File directorio = new File(
							Environment.getExternalStorageDirectory()
									+ "/CVMaker/img/");
					if (!directorio.exists())
						directorio.mkdirs();

					rutaFicheroSeleccionado = Environment
							.getExternalStorageDirectory()
							+ "/CVMaker/img/"
							+ "IMG_" + Math.random() * 100000 + ".jpg";
					FileOutputStream out = new FileOutputStream(
							rutaFicheroSeleccionado);
					photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
				} catch (Exception e) {
					rutaFicheroSeleccionado = "";
				}

			} catch (Exception e) {
			}
		}
	}
	

	public void setSeekBar(int Habilidad, int cvContador, int resSeekBarId,
			int resTextView,int resSetString) {
		cv4SeekBarIdioma[Habilidad][cvContador] = (SeekBar) cvLayoutModulo[2][cvContador]
				.findViewById(resSeekBarId);
		cv4SeekBarIdioma[Habilidad][cvContador].setMax(100);
		cv4SeekBarIdioma[Habilidad][cvContador].setProgress(30);
		cv4TextViewIdioma[Habilidad][cvContador] = (TextView) cvLayoutModulo[2][cvContador]
				.findViewById(resTextView);
		cv4TextViewIdioma[Habilidad][cvContador].setText(resSetString);
		
		if ((getString(resSetString)).equals(getString(R.string.cv4regular))) {
			Log.i("mete un reg","setSeekBar"+cvContador);
			cv4SeekBarIdioma[Habilidad][cvContador].setProgress(15);
		} else if ((getString(resSetString)).equals(getString(R.string.cv4bueno))) {
			Log.i("mete un bueno","setSeekBar"+cvContador);
			cv4SeekBarIdioma[Habilidad][cvContador].setProgress(30);
		} else if ((getString(resSetString)).equals(getString(R.string.cv4muyBueno))) {
			Log.i("mete un muy bueno","setSeekBar"+cvContador);
			cv4SeekBarIdioma[Habilidad][cvContador].setProgress(60);
		} else {
			Log.i("mete un excelente","setSeekBar"+cvContador);
			cv4SeekBarIdioma[Habilidad][cvContador].setProgress(90);
		}
		
		
		
		
		
	}
	

	public void listenSeekBars(final int cvContador) {
		cv4SeekBarIdioma[0][cvContador]
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (seekBar.getProgress() < 25) {
							cv4TextViewIdioma[0][cvContador]
									.setText(getString(R.string.cv4regular));
						} else if (seekBar.getProgress() < 50) {
							cv4TextViewIdioma[0][cvContador]
									.setText(getString(R.string.cv4bueno));
						} else if (seekBar.getProgress() < 75) {
							cv4TextViewIdioma[0][cvContador]
									.setText(getString(R.string.cv4muyBueno));
						} else {
							cv4TextViewIdioma[0][cvContador]
									.setText(getString(R.string.cv4excelente));
						}
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
		cv4SeekBarIdioma[1][cvContador]
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (seekBar.getProgress() < 25) {
							cv4TextViewIdioma[1][cvContador]
									.setText(getString(R.string.cv4regular));
						} else if (seekBar.getProgress() < 50) {
							cv4TextViewIdioma[1][cvContador]
									.setText(getString(R.string.cv4bueno));
						} else if (seekBar.getProgress() < 75) {
							cv4TextViewIdioma[1][cvContador]
									.setText(getString(R.string.cv4muyBueno));
						} else {
							cv4TextViewIdioma[1][cvContador]
									.setText(getString(R.string.cv4excelente));
						}

					}

					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
		cv4SeekBarIdioma[2][cvContador]
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (seekBar.getProgress() < 25) {
							cv4TextViewIdioma[2][cvContador]
									.setText(getString(R.string.cv4regular));
						} else if (seekBar.getProgress() < 50) {
							cv4TextViewIdioma[2][cvContador]
									.setText(getString(R.string.cv4bueno));
						} else if (seekBar.getProgress() < 75) {
							cv4TextViewIdioma[2][cvContador]
									.setText(getString(R.string.cv4muyBueno));
						} else {
							cv4TextViewIdioma[2][cvContador]
									.setText(getString(R.string.cv4excelente));
						}

					}

					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
	}
	

	public void setSpinner(int resStringId, int resSpinnerId, int tabTag,
			int pos, int cvContador) {
		Log.i("Set Spinner array"," "+tabTag+" "+cvContador+" pos: "+pos);
		String[] stringItemsSpinner = getResources()
				.getStringArray(resStringId);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stringItemsSpinner);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		if (pos == 0) {
			spinnerArray1[tabTag][cvContador] = (Spinner) cvLayoutModulo[tabTag][cvContador]
					.findViewById(resSpinnerId);
			spinnerArray1[tabTag][cvContador].setAdapter(adapter);
			spinnerArray1[tabTag][cvContador].setSelection(58);
		} else {
			spinnerArray2[tabTag][cvContador] = (Spinner) cvLayoutModulo[tabTag][cvContador]
					.findViewById(resSpinnerId);
			spinnerArray2[tabTag][cvContador].setAdapter(adapter);
			spinnerArray2[tabTag][cvContador].setSelection(58);
		}
	}
	

	public void listenSpinner(int tabTag, int pos, int cvContador) {

		if (pos == 0) {
			spinnerListener1[tabTag][cvContador] = new MyOnItemSelectedListener();
			spinnerArray1[tabTag][cvContador]
					.setOnItemSelectedListener(spinnerListener1[tabTag][cvContador]);
			spinnerItemSeleccionado1[tabTag][cvContador] = spinnerListener1[tabTag][cvContador]
					.getSeleccion();
		} else {
			spinnerListener2[tabTag][cvContador] = new MyOnItemSelectedListener();
			spinnerArray2[tabTag][cvContador]
					.setOnItemSelectedListener(spinnerListener2[tabTag][cvContador]);
			spinnerItemSeleccionado2[tabTag][cvContador] = spinnerListener2[tabTag][cvContador]
					.getSeleccion();
		}
	}
	

	public void lanzarcvMas(View view) {
		// cojo el tag del layout madre de cada tab
		View view2 = (View) view.getParent().getParent().getParent()
				.getParent();
		tagTabActual = Integer.parseInt(view2.getTag().toString());
		int RLayoutModulos = 0, RBotonMas = 0, RBotonMenos = 0, RLayoutModulo = 0, RLayoutMasMenos = 0;
		// cv=0 - Tab2-Academicos // cv=1 Tab3-Laboral//
		// cv=2 Tab4-Idiomas // cv=3 -Tab6 Generico
		// cv=[4 maxEntradas] - Tab7... Tab"x" Genericos extras
		// Recordar que el contador de modulos la misma relacion:
		// [0:Tab2 1:tab3 2:tab4 3:tab6 4:tab7xtra ... maxEntradas] <- Es donde
		// hay modulos
		switch (tagTabActual) {
		case 0:
			RLayoutModulos = R.id.cv2LayoutModulos;
			RBotonMas = R.id.cv2BotonMas;
			RBotonMenos = R.id.cv2BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv2_datosacademicos_mas_menos;
			RLayoutModulo = R.layout.actividad_cv2_datosacademicos_modulo;
			break;
		case 1:
			RLayoutModulos = R.id.cv3LayoutModulos;
			RBotonMas = R.id.cv3BotonMas;
			RBotonMenos = R.id.cv3BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv3_experiencialaboral_mas_menos;
			RLayoutModulo = R.layout.actividad_cv3_experiencialaboral_modulo;
			break;
		case 2:
			RLayoutModulos = R.id.cv4LayoutModulos;
			RBotonMas = R.id.cv4BotonMas;
			RBotonMenos = R.id.cv4BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv4_idiomas_mas_menos;
			RLayoutModulo = R.layout.actividad_cv4_idiomas_modulo;
			break;
		case 3:
			RLayoutModulos = R.id.cv6LayoutModulos;
			RBotonMas = R.id.cv6BotonMas;
			RBotonMenos = R.id.cv6BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv6_generico_mas_menos;
			RLayoutModulo = R.layout.actividad_cv6_generico_modulo;
			break;
		default:
			RLayoutModulos = R.id.cv6LayoutModulos;
			RBotonMas = R.id.cv6BotonMas;
			RBotonMenos = R.id.cv6BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv6_generico_mas_menos;
			RLayoutModulo = R.layout.actividad_cv6_generico_modulo;
			break;
		}
		// Ahora, añado un nuevo modulo
		cvContadorModulos[tagTabActual]++;
		posTabActual = mTabsAdapter.getTabPosition();
		Log.i(Integer.toString(posTabActual),
				Integer.toString(cvContadorModulos[tagTabActual]));

		if (cvContadorModulos[tagTabActual] < maxModulos) {
			if (tagTabActual <= 3) {
				// Añado el layout correspondiete
				// Ojo!!!! a partir del tag 4, esto se define en la funcion
				// lanzar nuevo,si no pongo esta condicion se sobreescribe
				cvLayoutModulos[tagTabActual] = (LinearLayout) FragmentView[posTabActual]
						.findViewById(RLayoutModulos);
			}

			// Añado con inflate. Lo hago en dos pasos en este paso previo me
			// guardo en un View el nuevo modulo
			cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]] = this
					.getLayoutInflater().inflate(RLayoutModulo, null, true);
			// Añado este modulo en su layout de modulos
			cvLayoutModulos[tagTabActual]
					.addView(
							cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]],
							cvContadorModulos[tagTabActual]);
			// Añado el layout de los botones mas menos de nuevo
			Button botonMenos2 = (Button) cvLayoutModulos[tagTabActual]
					.findViewById(RBotonMenos);
			// Boton menos visible
			botonMenos2.setVisibility(ImageButton.VISIBLE);
			if (cvContadorModulos[tagTabActual] == maxModulos - 1) {
				Button botonMas2 = (Button) cvLayoutModulos[tagTabActual]
						.findViewById(RBotonMas);
				botonMas2.setEnabled(false);
			}

		}
		switch (tagTabActual) {
		case 0:
			// Defino los nuevos EditText
			cv2EditEstudios[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv2EditEstudios);
			cv2EditCentro[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv2EditCentro);
			cv2EditLocalidad[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv2EditLocalidad);
			// Preparo los nuevos Spinners
			setSpinner(R.array.ano, R.id.cv2SpinnerAnoInicio, tagTabActual, 0,
					cvContadorModulos[tagTabActual]);
			listenSpinner(tagTabActual, 0, cvContadorModulos[tagTabActual]);
			setSpinner(R.array.ano, R.id.cv2SpinnerAnoFin, tagTabActual, 1,
					cvContadorModulos[tagTabActual]);
			listenSpinner(tagTabActual, 1, cvContadorModulos[tagTabActual]);
			break;
		case 1:
			// Defino los nuevos EditText
			cv3EditTrabajo[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv3EditTrabajo);
			cv3EditEmpresa[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv3EditEmpresa);
			cv3EditDescripcion[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv3EditDescripcion);
			// Preparo los nuevos Spinners
			setSpinner(R.array.ano, R.id.cv3SpinnerAnoInicio, tagTabActual, 0,
					cvContadorModulos[tagTabActual]);
			listenSpinner(tagTabActual, 0, cvContadorModulos[tagTabActual]);
			setSpinner(R.array.ano, R.id.cv3SpinnerAnoFin, tagTabActual, 1,
					cvContadorModulos[tagTabActual]);
			listenSpinner(tagTabActual, 1, cvContadorModulos[tagTabActual]);
			break;
		case 2:
			// Defino el nuevo EditText
			cv4EditIdioma[cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv4EditIdioma);
			// Defino los nuevos SeekBars
			setSeekBar(0, cvContadorModulos[tagTabActual],
					R.id.cv4SeekBarHablado, R.id.cv4TextViewSeekBarHablado,R.string.cv4bueno);
			setSeekBar(1, cvContadorModulos[tagTabActual],
					R.id.cv4SeekBarEscrito, R.id.cv4TextViewSeekBarEscrito,R.string.cv4bueno);
			setSeekBar(2, cvContadorModulos[tagTabActual],
					R.id.cv4SeekBarLectura, R.id.cv4TextViewSeekBarLectura,R.string.cv4bueno);
			listenSeekBars(cvContadorModulos[tagTabActual]);
			break;
		default:
			cv6EditSubtitulo[contadorGenericoExtra][cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv6EditSubtituloCampo);
			cv6EditCampo[contadorGenericoExtra][cvContadorModulos[tagTabActual]] = (EditText) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual]]
					.findViewById(R.id.cv6EditCampo);
			break;
		}
	}
	

	public void lanzarcvMasReconstruir(int tagTabActual, int posTabActual, int i) {
		int RLayoutModulos = 0, RBotonMas = 0, RBotonMenos = 0, RLayoutModulo = 0, RLayoutMasMenos = 0;
		Log.i("RecontruyendoMas", "tagTabactual " + tagTabActual
				+ " posTabActual " + posTabActual + " i " + i);
		switch (tagTabActual) {
		case 0:
			RLayoutModulos = R.id.cv2LayoutModulos;
			RBotonMas = R.id.cv2BotonMas;
			RBotonMenos = R.id.cv2BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv2_datosacademicos_mas_menos;
			RLayoutModulo = R.layout.actividad_cv2_datosacademicos_modulo;
			break;
		case 1:
			RLayoutModulos = R.id.cv3LayoutModulos;
			RBotonMas = R.id.cv3BotonMas;
			RBotonMenos = R.id.cv3BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv3_experiencialaboral_mas_menos;
			RLayoutModulo = R.layout.actividad_cv3_experiencialaboral_modulo;
			break;
		case 2:
			RLayoutModulos = R.id.cv4LayoutModulos;
			RBotonMas = R.id.cv4BotonMas;
			RBotonMenos = R.id.cv4BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv4_idiomas_mas_menos;
			RLayoutModulo = R.layout.actividad_cv4_idiomas_modulo;
			break;
		case 3:
			RLayoutModulos = R.id.cv6LayoutModulos;
			RBotonMas = R.id.cv6BotonMas;
			RBotonMenos = R.id.cv6BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv6_generico_mas_menos;
			RLayoutModulo = R.layout.actividad_cv6_generico_modulo;
			break;
		default:
			RLayoutModulos = R.id.cv6LayoutModulos;
			RBotonMas = R.id.cv6BotonMas;
			RBotonMenos = R.id.cv6BotonMenos;
			RLayoutMasMenos = R.layout.actividad_cv6_generico_mas_menos;
			RLayoutModulo = R.layout.actividad_cv6_generico_modulo;
			break;
		}

		Log.i(Integer.toString(posTabActual), Integer.toString(i));

		if (i < maxModulos) {
			if (tagTabActual <= 3) {
				// Añado el layout correspondiete
				// Ojo!!!! a partir del tag 4, esto se define en la funcion
				// lanzar nuevo,si no pongo esta condicion se sobreescribe
				cvLayoutModulos[tagTabActual] = (LinearLayout) FragmentView[posTabActual]
						.findViewById(RLayoutModulos);
			}

			// Añado con inflate. Lo hago en dos pasos en este paso previo me
			// guardo en un View el nuevo modulo
			cvLayoutModulo[tagTabActual][i] = this.getLayoutInflater().inflate(
					RLayoutModulo, null, true);
			// Añado este modulo en su layout de modulos
			cvLayoutModulos[tagTabActual].addView(
					cvLayoutModulo[tagTabActual][i], i);
			// Añado el layout de los botones mas menos de nuevo
			Button botonMenos2 = (Button) cvLayoutModulos[tagTabActual]
					.findViewById(RBotonMenos);
			// Boton menos visible
			botonMenos2.setVisibility(ImageButton.VISIBLE);
			if (i == maxModulos - 1) {
				Button botonMas2 = (Button) cvLayoutModulos[tagTabActual]
						.findViewById(RBotonMas);
				botonMas2.setEnabled(false);
			}

		}
		switch (tagTabActual) {

		case 0:
			// Defino los nuevos EditText
			cv2EditEstudios[i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv2EditEstudios);
			cv2EditCentro[i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv2EditCentro);
			cv2EditLocalidad[i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv2EditLocalidad);
			// Preparo los nuevos Spinners
			setSpinner(R.array.ano, R.id.cv2SpinnerAnoInicio, tagTabActual, 0,
					i);
			listenSpinner(tagTabActual, 0, i);
			setSpinner(R.array.ano, R.id.cv2SpinnerAnoFin, tagTabActual, 1, i);
			listenSpinner(tagTabActual, 1, i);
			break;
		case 1:
			// Defino los nuevos EditText
			cv3EditTrabajo[i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv3EditTrabajo);
			cv3EditEmpresa[i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv3EditEmpresa);
			cv3EditDescripcion[i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv3EditDescripcion);
			// Preparo los nuevos Spinners
			setSpinner(R.array.ano, R.id.cv3SpinnerAnoInicio, tagTabActual, 0,
					i);
			listenSpinner(tagTabActual, 0, i);
			setSpinner(R.array.ano, R.id.cv3SpinnerAnoFin, tagTabActual, 1, i);
			listenSpinner(tagTabActual, 1, i);
			break;
		case 2:
			// Defino el nuevo EditText
			cv4EditIdioma[i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv4EditIdioma);
			// Defino los nuevos SeekBars
			setSeekBar(0, i, R.id.cv4SeekBarHablado,
					R.id.cv4TextViewSeekBarHablado,R.string.cv4bueno);
			setSeekBar(1, i, R.id.cv4SeekBarEscrito,
					R.id.cv4TextViewSeekBarEscrito,R.string.cv4bueno);
			setSeekBar(2, i, R.id.cv4SeekBarLectura,
					R.id.cv4TextViewSeekBarLectura,R.string.cv4bueno);
			listenSeekBars(i);
			break;
		default:
			cv6EditSubtitulo[contadorGenericoExtra][i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv6EditSubtituloCampo);
			cv6EditCampo[contadorGenericoExtra][i] = (EditText) cvLayoutModulo[tagTabActual][i]
					.findViewById(R.id.cv6EditCampo);
			break;
		}
	}
	

	public void lanzarcvMenos(View view) {
		View view2 = (View) view.getParent().getParent().getParent()
				.getParent(); // cojo el tag del layout madre de cada tab
		if (cvContadorModulos[tagTabActual] > 0) {
			cvContadorModulos[tagTabActual]--;
			// Borro el modulo mas menos anterior
			((ViewManager) cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual] + 1]
					.getParent())
					.removeView(cvLayoutModulo[tagTabActual][cvContadorModulos[tagTabActual] + 1]);
			// Si solo hay un modulo desaparece el botón menos
			if (cvContadorModulos[tagTabActual] == 0) {
				view.setVisibility(ImageButton.INVISIBLE);
			}
		}
		// Pongo el botonMas a Enabled de nuevo:
		if (cvContadorModulos[tagTabActual] < maxEntradas) {
			// Recojo el botón mas hermano del menos que invocado esta funcion
			// para poder reactivarlo
			RelativeLayout aux = (RelativeLayout) view.getParent();
			Button botonMas = (Button) aux.getChildAt(0);
			botonMas.setEnabled(true);
		}
	}
	

	public void cvLanzarNuevoGenerico(View view) {
		contadorGenericoExtra++;
		// Ahora el boton nuevo del tab que se ha lanzado se borra
				((ViewManager) view.getParent()).removeView(view);
		
		
		View view2 = (View) view.getParent();
		// Primero activo el boton siguiente del layout generico correspondiente

		tagTabActual = Integer.parseInt(view2.getTag().toString());
		tagTabCreado = tagTabActual + 1;
		posTabActual = mTabsAdapter.getTabPosition();
		tabCreado = posTabActual + 1;

		// Estoy creando el tab numero tabcreado. Añado con inflate.
		// Lo hago en dos pasos en este paso previo me guardo en un View
		// el nuevo modulo
		// tabExtra[contadorGenericoExtra - 1] = (View) this.getLayoutInflater()
		// .inflate(R.layout.actividad_cv6_generico, null, true);

		// // Añado un tab nuevo (Modo Sherlock)
		mTabsAdapter.addTab(
				bar.newTab().setText(
						getResources().getText(R.string.cv6Titulo) + " "
								+ Integer.toString(contadorGenericoExtra + 1)),
				ActividadEditarExtra.class, null);
		// Ahora voy al tab nuevo
		
		bar.selectTab(bar.getTabAt(tabCreado));
	}
	

	// Devuelve un Id no usado
	public int findId() {
		View v = findViewById(id);
		while (v != null) {
			v = findViewById(++id);
		}
		return id++;
	}
	

	public void lanzarcv1TomarFoto(View view) {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, PETICION_CAMARA);
	}
	

	public void lanzarcv1CargarFoto(View view) {

		Intent intent = new Intent(this,
				es.cvmaker.upv.navegador.FileChooser.class);
		intent.putExtra("tipoFichero", "Imagen");
		intent.putExtra("directorio", Environment.getExternalStorageDirectory()
				+ "/");
		startActivityForResult(intent, PETICION_CARGARFOTO);
	}
	

	public class DatePickerDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {

					cv1FechaNacimiento.setText(dayOfMonth + "/"
							+ (monthOfYear + 1) + "/" + year);
				}
			};
			String aux[] = cv1FechaNacimiento.getText().toString().split("/");
			return new DatePickerDialog(getActivity(), listener,
					Integer.parseInt(aux[2]), Integer.parseInt(aux[1])-1,
					Integer.parseInt(aux[0]));
		}
	}
	

	public void lanzarDatePickerDialogo(View v) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(new DatePickerDialogFragment(), "date_picker");
		ft.commit();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getSupportMenuInflater().inflate(R.menu.action_bar_cv_items, menu);
		View v = (View) menu.findItem(R.id.cvActionModesCollapsableItem)
				.getActionView();

		editCollapsable = (EditText) v
				.findViewById(R.id.cvActionModesCollapsableEdit);
		Button buttonCollapsable = (Button) v
				.findViewById(R.id.cvActionModesCollapsableButton);

		buttonCollapsable.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mMode = startActionMode(new ActionModeConfirmar());
			}
		});
		// menu.add("saveCV").setIcon(R.drawable.ic_content_save)
		// .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}
	

	// Eventos sobre botones del menu
	public boolean onOptionsItemSelected(MenuItem item) {
		// if (item.getTitleCondensed().toString().equals("generarCV")) {
		// // el collapse ya se lanza solo por la propiedad del xml
		// }
		// if (item.getTitleCondensed().toString().equals("saveCV")) {
		// }
		// No hay boton home ni save por ahora
//		if (item.getItemId() == android.R.id.home) {
//			Log.i("holaaa", "dfjdljkfdjlkkf");
//			item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//				public boolean onMenuItemClick(MenuItem item) {
//					Actividad_Editar.this.volver();
//
//					return true;
//				}
//			});
//
//		}

		return true;
	}
	
	// Con esto genero el colapse. Inflo el menu layout que quiera

	private final class ActionModeConfirmar implements ActionMode.Callback {

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			getSupportMenuInflater().inflate(
					R.menu.action_bar_cv_items_seguridad, menu);
			return true;
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

			if (item.getTitle().equals("Confirmar")) {
				
				setSupportProgressBarIndeterminateVisibility(true);
				try {
					nombrePDF = editCollapsable.getText().toString();
					generarCV(nombrePDF);
				} catch (Exception e) {
					setSupportProgressBarIndeterminateVisibility(false);
				}
			}
			mode.finish();
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
			Log.i("onDestroy", "----");

		}
	}

Document documento;
	
	public void generarCV(String nombreFichero) {
		nombreFichero = (nombreFichero.length() == 0 ) ? "CV" + (int)Math.floor((Math.random()*10000+1)) : nombreFichero;
		
		GestorBBDD gestor = new GestorBBDD(this);
		gestor.setCurriculum(nombreFichero, cv1EditNombre.getText().toString());
		gestor.setCurriculumCV1(cv1EditNombre.getText().toString(),
				cv1FechaNacimiento.getText().toString(),
				cv1EditEmail.getText().toString(),
				cv1EditTelefono.getText().toString(),
				cv1EditLocalidad.getText().toString(),
				rutaFicheroSeleccionado);
		gestor.setCurriculumCV2(cv2EditEstudios, cv2EditCentro, cv2EditLocalidad, spinnerListener1[0], spinnerListener2[0], cvContadorModulos[0]+1 );
		try {
			gestor.setCurriculumCV3(cv3EditTrabajo, cv3EditEmpresa, cv3EditDescripcion, spinnerListener1[1], spinnerListener2[1], cvContadorModulos[1]+1 );
		} catch (Exception e) {}
		gestor.setCurriculumCV4(cv4EditIdioma, cv4TextViewIdioma, cvContadorModulos[2]+1 );
		try {
			gestor.setCurriculumCV5(cv5CheckBox_1, cv5CheckBox_2, cv5CheckBox_3, cv5CheckBox_4);
		} catch (Exception e) {}
		try {
			gestor.setCurriculumCV6(cv6EditTitulo, cv6EditSubtitulo, cv6EditCampo , contadorGenericoExtra, cvContadorModulos);
		} catch (Exception e) {}
		
		generarPDF(gestor);
	}
	
	public void generarPDF(GestorBBDD gestor) {
		
		Vector<String>         datosPersonales; try { datosPersonales = gestor.getCV1(); } catch (Exception e) { datosPersonales = null; }
		Vector<Vector<String>> datosAcademicos; try { datosAcademicos = gestor.getCV2(); } catch (Exception e) { datosAcademicos = null; }
		Vector<Vector<String>> datosLaborales;  try { datosLaborales  = gestor.getCV3(); } catch (Exception e) { datosLaborales  = null; }
		Vector<Vector<String>> datosIdioma;     try { datosIdioma     = gestor.getCV4(); } catch (Exception e) { datosIdioma     = null; }
		Vector<String>         habilidades;     try { habilidades     = gestor.getCV5(); } catch (Exception e) { habilidades     = null; }
		Vector<InfoGenerico>   generico;		try { generico		  = gestor.getCV6(); } catch (Exception e) { generico        = null; }

		documento = new Document(PageSize.A4, 55, 50, 50, 50);
		FileOutputStream ficheroPdf;
		try {
			File directorio = new File(Environment.getExternalStorageDirectory() + "/CVMaker/");
			if(!directorio.exists()) directorio.mkdirs();
			
			ficheroPdf = new FileOutputStream(Environment.getExternalStorageDirectory() + "/CVMaker/" + gestor.getNombreFichero());
			PdfWriter.getInstance(documento,ficheroPdf);
			documento.open();
			
			/**TITULO**/
			Paragraph p = new Paragraph("Currículum Vitae", FontFactory.getFont("Calibri", 14, Font.BOLD,BaseColor.BLACK));
			p.setAlignment(Element.ALIGN_CENTER);
			intro();
			documento.add(p);
			
			
			/**DATOS PERSONALES**/
			intro();
			p = new Paragraph(getString(R.string.datosPersonales), FontFactory.getFont("Calibri", 14, Font.BOLD,BaseColor.BLACK));
			documento.add(p);
			intro();
			PdfPTable tabla;
			if (datosPersonales != null) {
				String[] fecha = datosPersonales.get(1).split("/");
				String fechaFormateada = fecha[0].toString() + " de " + getMes(fecha[1]).toString() + " de " + fecha[2].toString();

				float anchoColumnasDP[] = { 0.4f, 0.5f };
				tabla = new PdfPTable(anchoColumnasDP);
				tabla.setWidthPercentage(65);
				tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
				tabla.addCell(celda(getString(R.string.nombre)));
				tabla.addCell(celda(datosPersonales.get(0).toString()));
				tabla.addCell(celda(getString(R.string.fechaNacimiento)));
				tabla.addCell(celda(fechaFormateada));
				tabla.addCell(celda(getString(R.string.email)));
				tabla.addCell(celda(datosPersonales.get(2).toString()));
				tabla.addCell(celda(getString(R.string.telefono)));
				tabla.addCell(celda(datosPersonales.get(3).toString()));
				tabla.addCell(celda(getString(R.string.residencia)));
				tabla.addCell(celda(datosPersonales.get(4).toString()));
				documento.add(tabla);
				foto();
			} else {
				float anchoColumnasDP[] = { 0.4f, 0.5f };
				tabla = new PdfPTable(anchoColumnasDP);
				tabla.setWidthPercentage(65);
				tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				tabla.addCell(celda(getString(R.string.nombre)));
				tabla.addCell(celda(" "));
				tabla.addCell(celda(getString(R.string.fechaNacimiento)));
				tabla.addCell(celda(" "));
				tabla.addCell(celda(getString(R.string.email)));
				tabla.addCell(celda(" "));
				tabla.addCell(celda(getString(R.string.telefono)));
				tabla.addCell(celda(" "));
				tabla.addCell(celda(getString(R.string.residencia)));
				tabla.addCell(celda(" "));
				documento.add(tabla);
				foto();
			}
			
			try {
				/**DATOS ACADEMICOS**/
				if (datosAcademicos != null) {
					if (datosAcademicos.get(0).get(0).length() > 0) {
						intro();
						p = new Paragraph(getString(R.string.datosAcademicos), FontFactory.getFont("Calibri", 14, Font.BOLD,BaseColor.BLACK));
						documento.add(p);
						intro();
						
						float anchoColumnasDA[] = { 0.08f,0.5f };
						tabla = new PdfPTable(anchoColumnasDA);
						tabla.setWidthPercentage(100);
						tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell cell;
						for(int i = 0; i < datosAcademicos.size();i++) {
							cell = new PdfPCell(new Paragraph(datosAcademicos.get(i).get(3).toString()+"-"+datosAcademicos.get(i).get(4).toString()));
							cell.setBorder(0);
							cell.setPaddingBottom(10);
							cell.setRowspan(2);
							tabla.addCell(cell);
							tabla.addCell(celdaBold(datosAcademicos.get(i).get(0).toString()));
							String str = datosAcademicos.get(i).get(1).toString() + ", " + datosAcademicos.get(i).get(2).toString();
							tabla.addCell(celdaMiniPadding(str));
						}
						documento.add(tabla);
					}
				}
			} catch (Exception e) {}
			
			try {
				/**EXPERIENCIA LABORAL**/
				if (datosLaborales != null) {
					if(datosLaborales.get(0).get(0).length() > 0) {
						intro();
						p = new Paragraph(getString(R.string.experienciaLaboral), FontFactory.getFont("Calibri", 14, Font.BOLD,BaseColor.BLACK));
						documento.add(p);
						intro();
						
						float anchoColumnasEL[] = { 0.08f,0.5f };
						tabla = new PdfPTable(anchoColumnasEL);
						tabla.setWidthPercentage(100);
						tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						PdfPCell cell;
						for(int i = 0; i < datosLaborales.size();i++) {
							cell = new PdfPCell(new Paragraph(datosLaborales.get(i).get(3).toString()+"-"+datosLaborales.get(i).get(4).toString()));
							cell.setBorder(0);
							cell.setRowspan(3);
							tabla.addCell(cell);
							tabla.addCell(celdaBold(datosLaborales.get(i).get(0).toString()));
							tabla.addCell(celdaMini(datosLaborales.get(i).get(1).toString()));
							tabla.addCell(celdaDescripcion(datosLaborales.get(i).get(2).toString()));
						}
						documento.add(tabla);
					}
				} 
			} catch (Exception e) {}
			
			try {
				/**IDIOMAS**/
				if (datosIdioma != null) {
					if(datosIdioma.get(0).get(0).length() > 0) {
						intro();
						p = new Paragraph(getString(R.string.idiomas), FontFactory.getFont("Calibri", 14, Font.BOLD,BaseColor.BLACK));
						documento.add(p);
						intro();
						
						float anchoColumnasID[] = { 0.04f,0.5f };
						tabla = new PdfPTable(anchoColumnasID);
						tabla.setWidthPercentage(100);
						tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						for(int i = 0; i<datosIdioma.size();i++) {
							if(datosIdioma.get(i).get(0).length() > 0) {
								tabla.addCell(celdaVacia());
								String str = datosIdioma.get(i).get(0).toString()+": "+getString(R.string.hablaIdioma)+" "+datosIdioma.get(i).get(1).toString().toLowerCase()+", "+getString(R.string.escribeIdioma)+" "+datosIdioma.get(i).get(2).toString().toLowerCase()+", "+getString(R.string.leeIdioma)+" "+datosIdioma.get(i).get(3).toString().toLowerCase();
								tabla.addCell(celdaCursiva(str));
							}
						}
						documento.add(tabla);
					}
				} 
			} catch (Exception e) {}
			
			try {
				/**BLOQUE GENERICO**/
				if(generico != null) {
					float anchoColumnasGEN[] = { 0.18f, 0.5f };
					PdfPCell cell;
					for(int i = 0; i<generico.size(); i++) {
						intro();
						p = new Paragraph(generico.get(i).getTitulo(), FontFactory.getFont("Calibri", 14, Font.BOLD, BaseColor.BLACK));
						documento.add(p);
						intro();
						
						tabla = new PdfPTable(anchoColumnasGEN);
						tabla.setWidthPercentage(100);
						tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						Vector<String> subtitulo   = generico.get(i).getSubtitulo();
						Vector<String> descripcion = generico.get(i).getDescripcion();
						for(int j=0;j<subtitulo.size();j++) {
							cell = new PdfPCell(new Paragraph(subtitulo.get(j)));
							cell.setBorder(0);
							cell.setPaddingBottom(10);
							tabla.addCell(cell);
							tabla.addCell(celdaDescripcionJustificada(descripcion.get(j)));
						}
						documento.add(tabla);
					}
				}
			} catch (Exception e) {}
			
			try {
				/**HABILIDADES GENERALES**/
				if (habilidades != null) {
					if(habilidades.size() > 0) {
						intro();
						p = new Paragraph(getString(R.string.habilidadesGenerales), FontFactory.getFont("Calibri", 14, Font.BOLD,BaseColor.BLACK));
						documento.add(p);
						intro();
						
						for(int i = 0; i<habilidades.size();i++) {
							p = new Paragraph(habilidades.get(i), FontFactory.getFont("Calibri", 11, Font.NORMAL,BaseColor.BLACK));
							documento.add(p);
						}
					}
				}
			} catch (Exception e) {}
			
			
			
		} catch (Exception e) {
			Toast.makeText(this, getString(R.string.errItext), Toast.LENGTH_LONG).show();
		}
		documento.close();
		
		setSupportProgressBarIndeterminateVisibility(false);
		String rutaPDF = Environment.getExternalStorageDirectory() + "/CVMaker/" + gestor.getNombreFichero();
		Intent i = new Intent(this, MuPDFActivity.class);
		i.putExtra("ruta", rutaPDF);
		startActivity(i);
	}
	
	private PdfPCell celda(String str) {
		PdfPCell c = new PdfPCell (new Paragraph(str, FontFactory.getFont("Calibri", 11, Font.NORMAL,BaseColor.BLACK)));
		c.setBorder(0);
		return c;
	}
	private PdfPCell celdaBold(String str) {
		PdfPCell c = new PdfPCell (new Paragraph(str, FontFactory.getFont("Calibri", 11, Font.BOLD,BaseColor.BLACK)));
		c.setBorder(0);
		return c;
	}
	private PdfPCell celdaCursiva(String str) {
		PdfPCell c = new PdfPCell (new Paragraph(str, FontFactory.getFont("Calibri", 11, Font.ITALIC,BaseColor.BLACK)));
		c.setBorder(0);
		return c;
	}
	private PdfPCell celdaMini(String str) {
		PdfPCell c = new PdfPCell (new Paragraph(str, FontFactory.getFont("Calibri", 10, Font.NORMAL,BaseColor.BLACK)));
		c.setBorder(0);
		return c;
	}
	private PdfPCell celdaMiniPadding(String str) {
		PdfPCell c = new PdfPCell (new Paragraph(str, FontFactory.getFont("Calibri", 10, Font.ITALIC,BaseColor.BLACK)));
		c.setBorder(0);
		c.setPaddingBottom(10);
		return c;
	}
	private PdfPCell celdaDescripcion(String str) {
		PdfPCell c = new PdfPCell (new Paragraph(str, FontFactory.getFont("Calibri", 10, Font.ITALIC,BaseColor.BLACK)));
		c.setBorder(0);
		c.setPaddingBottom(10);
		c.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		return c;
	}
	private PdfPCell celdaDescripcionJustificada(String str) {
		PdfPCell c = new PdfPCell (new Paragraph(str, FontFactory.getFont("Calibri", 11, Font.NORMAL,BaseColor.BLACK)));
		c.setBorder(0);
		c.setPaddingBottom(10);
		c.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		return c;
	}
	private PdfPCell celdaVacia() {
		PdfPCell c = new PdfPCell();
		c.setBorder(0);
		return c;
	}
	
	private void intro() throws DocumentException {
		documento.add(new Paragraph(" "));
	}
	
	private void foto() {
		try	{
			Image foto = Image.getInstance(rutaFicheroSeleccionado);
			foto.scaleToFit(81, 104);
			foto.setAbsolutePosition(430, 615);
			documento.add(foto);
		}
		catch ( Exception e ){			
		}
	}
	
	public String getMes(String mes) {
		if (mes.equals("1")) return getString(R.string.enero);
		if (mes.equals("2")) return getString(R.string.febrero);
		if (mes.equals("3")) return getString(R.string.marzo);
		if (mes.equals("4")) return getString(R.string.abril);
		if (mes.equals("5")) return getString(R.string.mayo);
		if (mes.equals("6")) return getString(R.string.junio);
		if (mes.equals("7")) return getString(R.string.julio);
		if (mes.equals("8")) return getString(R.string.agosto);
		if (mes.equals("9")) return getString(R.string.septiembre);
		if (mes.equals("10")) return getString(R.string.octubre);
		if (mes.equals("11")) return getString(R.string.noviembre);
		if (mes.equals("12")) return getString(R.string.diciembre);
		return "";
	}
	
}
