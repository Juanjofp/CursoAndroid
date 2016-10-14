package com.juanjofp.android.internet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xmlpull.v1.XmlSerializer;

import com.google.android.maps.GeoPoint;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class Noticia {

	public final static String NOTICIAS = "noticias";
	public final static String NOTICIA = "noticia";
	public final static String TITULO = "titulo";
	public final static String RESUMEN = "resumen";
	public final static String ENLACE = "enlace";
	public final static String LATITUD = "latitud";
	public final static String LONGITUD = "longitud";
	public final static String FECHA = "fecha";
	
	private String mTitulo;
	private Date mFecha;
	private String mResumen;
	private String mEnlace;
	
	private double mLatitud;
	private double mLongitud;
	
	public static final SimpleDateFormat mSDF = new SimpleDateFormat("yyMMddHHmmssZ");
	
	
	public Noticia() {
		// TODO Auto-generated constructor stub
	}
	
	public Noticia(String titulo, String resumen,
			String enlace, String fecha, float lat, float lng) {

		this.mTitulo = titulo;
		this.mResumen = resumen;
		this.mEnlace = enlace;
		this.mLatitud = lat;
		
		try {
			this.mFecha = mSDF.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.mFecha = new Date();
		}
		
	}

	public String getTitulo() {
		return mTitulo;
	}

	public Date getFecha() {
		return mFecha;
	}

	public String getResumen() {
		return mResumen;
	}

	public String getEnlace() {
		return mEnlace;
	}
	
	public double getLatitud() {
		return mLatitud;
	}
	
	public double getLongitud() {
		return mLatitud;
	}

	public Location getLocation() {
		Location loc = new Location(LocationManager.GPS_PROVIDER);
		loc.setLatitude(mLatitud);
		loc.setLongitude(mLongitud);
		return loc;
	}
	
	public GeoPoint getGeoPoint(){
		Log.e("Posicion: ","Lat"+mLatitud+" "+mLongitud);
		return new GeoPoint((int)(mLatitud*1E6), (int)(mLongitud*1E6));
	}
	
	public void setTitulo(String titulo) {
		this.mTitulo = titulo;
	}

	public void setFecha(String fecha) {
		try {
			this.mFecha = mSDF.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.mFecha = new Date();
		}
	}

	public void setResumen(String resumen) {
		this.mResumen = resumen;
	}

	public void setEnlace(String enlace) {
		this.mEnlace = enlace;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mTitulo+": "+mEnlace;
	}
	
	public void setLatitud(double lat){
		this.mLatitud = lat;
	}
	
	public void setLongitud(double lng){
		this.mLongitud = lng;
	}
	
	public void serializeMe(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException{
		
		serializer.startTag("", NOTICIA);
		
        serializer.startTag("", TITULO);
        serializer.text(getTitulo());
        serializer.endTag("", TITULO);
        
        serializer.startTag("", RESUMEN);
        serializer.text(getResumen());
        serializer.endTag("", RESUMEN);
        
        serializer.startTag("", ENLACE);
        serializer.text(getEnlace());
        serializer.endTag("", ENLACE);
        
        serializer.startTag("", FECHA);
        serializer.text(mSDF.format(mFecha));
        serializer.endTag("", FECHA);
        
        serializer.startTag("", LATITUD);
        serializer.text(String.valueOf(getLatitud()));
        serializer.endTag("", LATITUD);
        
        serializer.startTag("", LONGITUD);
        serializer.text(String.valueOf(getLongitud()));
        serializer.endTag("", LONGITUD);
        
        serializer.endTag("", NOTICIA);
	}
}
