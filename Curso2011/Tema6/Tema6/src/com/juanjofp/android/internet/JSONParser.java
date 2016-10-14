package com.juanjofp.android.internet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	public List<Noticia> parse(InputStream is) throws JSONException, IOException{
		
		List<Noticia> noticias = new ArrayList<Noticia>();
		
		JSONArray array = new JSONArray(readInputStream(is));
		
		for(int i=0;i < array.length(); i++){
			Noticia noticia = new Noticia();
			JSONObject json = array.getJSONObject(i);
			noticia.setTitulo(json.getString(Noticia.TITULO));
			noticia.setResumen(json.getString(Noticia.RESUMEN));
			noticia.setEnlace(json.getString(Noticia.ENLACE));
			noticia.setFecha(json.getString(Noticia.FECHA));
			noticia.setLatitud(json.getDouble(Noticia.LATITUD));
			noticia.setLongitud(json.getDouble(Noticia.LONGITUD));
			
			noticias.add(noticia);
		}
		
		return noticias;
	}
	
	public String readInputStream(InputStream is) throws IOException{
		int c = 0;
		StringBuilder sb = new StringBuilder();
		while ((c = is.read()) != -1)
			sb.append((char) c);
		is.close();
		return sb.toString();
	}
}
