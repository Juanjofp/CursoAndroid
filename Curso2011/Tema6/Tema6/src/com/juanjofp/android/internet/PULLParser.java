package com.juanjofp.android.internet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class PULLParser {

	 public List<Noticia> parse(InputStream is) {
		 
	        List<Noticia> noticias = null;
	        XmlPullParser parser = Xml.newPullParser();
	        
	        try {
	            // auto-detect the encoding from the stream
	            parser.setInput(is, null);
	            int eventType = parser.getEventType();
	            Noticia currentNews = null;
	            boolean done = false;
	            
	            while (eventType != XmlPullParser.END_DOCUMENT && !done){
	                String name = null;
	                switch (eventType){
	                    case XmlPullParser.START_DOCUMENT:
	                        noticias = new ArrayList<Noticia>();
	                        break;
	                        
	                    case XmlPullParser.START_TAG:
	                    	
	                        name = parser.getName();
	                        if (name.equalsIgnoreCase(Noticia.NOTICIA)){
	                            currentNews = new Noticia();
	                        } else if (currentNews != null){
	                        	
	                            if (name.equalsIgnoreCase(Noticia.TITULO)){
	                                currentNews.setTitulo(parser.nextText());
	                            } else if (name.equalsIgnoreCase(Noticia.RESUMEN)){
	                            	currentNews.setResumen(parser.nextText());
	                            } else if (name.equalsIgnoreCase(Noticia.ENLACE)){
	                            	currentNews.setEnlace(parser.nextText());
	                            } else if (name.equalsIgnoreCase(Noticia.FECHA)){
	                            	currentNews.setFecha(parser.nextText());
	                            } else if (name.equalsIgnoreCase(Noticia.LATITUD)){
	                            	currentNews.setLatitud(Float.valueOf(parser.nextText()));
	                            } else if (name.equalsIgnoreCase(Noticia.LONGITUD)){
	                            	currentNews.setLongitud(Float.valueOf(parser.nextText()));
	                            }    
	                            
	                        }
	                        break;
	                        
	                    case XmlPullParser.END_TAG:
	                        name = parser.getName();
	                        
	                        if (name.equalsIgnoreCase(Noticia.NOTICIA) && currentNews != null){
	                            noticias.add(currentNews);
	                        } else if (name.equalsIgnoreCase(Noticia.NOTICIAS)){
	                            done = true;
	                        }
	                        
	                        break;
	                }
	                eventType = parser.next();
	            }
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	        return noticias;
	    }
}
