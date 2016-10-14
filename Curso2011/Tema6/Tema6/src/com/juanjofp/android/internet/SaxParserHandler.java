package com.juanjofp.android.internet;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserHandler extends DefaultHandler {

	private List<Noticia> noticias;
    private Noticia currentNews;
    private StringBuilder builder;
    
    public List<Noticia> getNoticias(){
        return this.noticias;
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
    	
        super.endElement(uri, localName, name);
        
        if (this.currentNews != null){
            if (localName.equalsIgnoreCase(Noticia.TITULO)){
            	currentNews.setTitulo(builder.toString());
            } else if (localName.equalsIgnoreCase(Noticia.RESUMEN)){
            	currentNews.setResumen(builder.toString());
            } else if (localName.equalsIgnoreCase(Noticia.ENLACE)){
            	currentNews.setEnlace(builder.toString());
            } else if (localName.equalsIgnoreCase(Noticia.FECHA)){
            	currentNews.setFecha(builder.toString());
            }else if (localName.equalsIgnoreCase(Noticia.LATITUD)){
            	currentNews.setLatitud(Float.valueOf(builder.toString()));
            }else if (localName.equalsIgnoreCase(Noticia.LONGITUD)){
            	currentNews.setLongitud(Float.valueOf(builder.toString()));
            } else if (localName.equalsIgnoreCase(Noticia.NOTICIA)){
                noticias.add(currentNews);
            }
            builder.setLength(0);    
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        noticias = new ArrayList<Noticia>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(Noticia.NOTICIA)){
            currentNews = new Noticia();
        }
    }
}
