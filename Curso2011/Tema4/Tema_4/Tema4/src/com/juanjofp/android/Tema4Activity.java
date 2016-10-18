package com.juanjofp.android;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Tema4Activity extends Activity {
	
	private static final int RESPONSE_TAKE_PHOTO = 0;
	
	public static final int NOTIFICATION_ID = 12345;
	
	private final static int DLG_PROGRESSBAR = 1;
	private final static int DLG_BUTTONS = 2;
	private final static int DLG_LIST = 3;
	private final static int DLG_LIST_SELECT = 4;
	
	private ImageView mImage;
	private EditText etEditText;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mImage = (ImageView)findViewById(R.id.imageView1);
        registerForContextMenu(mImage);
        etEditText = (EditText)findViewById(R.id.etComponenteTextView);
        registerForContextMenu(etEditText);
    }
    
    public void takePhoto(View view){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult(intent, RESPONSE_TAKE_PHOTO);	
    }
    
    public void shareLink(View v){
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.setType("text/plain");
    	intent.putExtra(Intent.EXTRA_TEXT, "Curso de Android en el centic!!! http://centic.es");
    	startActivity(Intent.createChooser(intent, "Compartir con:"));
    }
    
    public void showWeb(View v){
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setData(Uri.parse("http://centic.es"));
    	startActivity(Intent.createChooser(intent, "Compartir con:"));
    }
    
    public void makeCall(View v){
    	Intent intent = new Intent(Intent.ACTION_DIAL);
    	intent.setData(Uri.parse("tel:123456789"));
    	startActivity(intent);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(requestCode == RESPONSE_TAKE_PHOTO && resultCode == Activity.RESULT_OK){
    		BitmapFactory.Options options = new BitmapFactory.Options();
    	    options.inSampleSize = 4;

    	    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");  
    	    mImage.setImageBitmap(thumbnail);

    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_principal, menu);
    	//Fin del menu
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId()) {
		case R.id.menu_principla_mapas:
			Toast.makeText(this, "Soy el menu de mapas", Toast.LENGTH_LONG).show();
			return true;
		case R.id.menu_principla_call:
			makeCall(null);
			return true;
		case R.id.menu_principla_foto:
			takePhoto(null);
			return true;
		case R.id.menu_principla_4:
			lanzarNotificacion(null);
			return true;
		case R.id.menu_principla_5:
			Toast.makeText(this, "Soy el menu 5", Toast.LENGTH_LONG).show();
			return true;
		case R.id.menu_contextual_principal5_1:
			showDialog(DLG_LIST);
			return true;
		case R.id.menu_contextual_principal5_2:
			showDialog(DLG_LIST_SELECT);
			return true;
		case R.id.menu_principla_6:
			showDialog(DLG_BUTTONS);
			return true;
		case R.id.menu_principla_7:
			showDialog(DLG_PROGRESSBAR);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	menu.findItem(R.id.menu_principla_4).setTitle("Notificacion");
    	return true;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	int menuXML = (v == mImage)?R.menu.menu_contextual_componente1:R.menu.menu_contextual_componente2;
    	inflater.inflate(menuXML, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
    	return true;
    }
    
    @Override
    public void onContextMenuClosed(Menu menu) {
    	// TODO Auto-generated method stub
    	super.onContextMenuClosed(menu);
    	Toast.makeText(this, "Menu Contextual finalizado", Toast.LENGTH_LONG).show();
    }
    
    public void lanzarNotificacion(View view){
		
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = android.R.drawable.ic_menu_help;
		CharSequence tickerText = "Soy una notificacion";
		long when = System.currentTimeMillis();
		
		Notification notification = new Notification(icon, tickerText, when);
		notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "3");
		//Requiere permiso VIBRATE
		long[] vibrate = {0,100,200,300};
		notification.vibrate = vibrate;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		CharSequence contentTitle = "Notificacion al usuario";
		CharSequence contentText = "Buenos tardes!!";
		Intent notificationIntent = new Intent(this, NotificacionActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, notification);
	}
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	// TODO Auto-generated method stub
    	switch (id) {
		case DLG_PROGRESSBAR:
			return dialogoProgressBar();
		case DLG_BUTTONS:
			return dialogButtons();
		case DLG_LIST:
			return dialogLista();
		case DLG_LIST_SELECT:
			return dialogListaSelect();
		default:
			return dialogButtons();
		}
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
    	// TODO Auto-generated method stub
    	Toast.makeText(this, dialog.toString(), Toast.LENGTH_SHORT).show();
    }
    
    public void lanzarDialogo(View view){
    	showDialog(DLG_BUTTONS);
    }
    
    private Dialog dialogoProgressBar(){
    	ProgressDialog pd = ProgressDialog.show(this, "", "Cargando. Espere un momentito...", true);
    	pd.setCancelable(true);
    	return pd;
    }
    
    private Dialog dialogButtons(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to exit?")
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                Tema4Activity.this.finish();
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	return builder.create();
    }
    
    private Dialog dialogLista(){
    	final CharSequence[] items = {"Red", "Green", "Blue"};

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Pick a color");
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
    	    }
    	});
    	return builder.create();
    }
    
    private Dialog dialogListaSelect(){
    	final CharSequence[] items = {"Red", "Green", "Blue"};

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Pick a color");
    	builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
    	    }
    	});
    	return builder.create();
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	showDialog(DLG_BUTTONS);
    }
}