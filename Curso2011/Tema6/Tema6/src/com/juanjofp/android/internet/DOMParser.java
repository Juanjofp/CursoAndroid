package com.juanjofp.android.internet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMParser {

	public List<Noticia> parse(InputStream is){
	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    List<Noticia> noticias = new ArrayList<Noticia>();
	    
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(is);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName(Noticia.NOTICIA);
			
			//Recorremos las noticias
			for (int i = 0; i < items.getLength(); i++) {
				Noticia noticia = new Noticia();
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				
				//Para cada noticia
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String name = property.getNodeName();
					if (name.equalsIgnoreCase(Noticia.TITULO)) {
						noticia.setTitulo(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase(Noticia.RESUMEN)) {
						noticia.setResumen(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase(Noticia.ENLACE)) {
						noticia.setEnlace(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase(Noticia.FECHA)) {
						noticia.setFecha(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase(Noticia.LATITUD)) {
						noticia.setLatitud(Float.valueOf(property.getFirstChild().getNodeValue()));
					} else if (name.equalsIgnoreCase(Noticia.LONGITUD)) {
						noticia.setLongitud(Float.valueOf(property.getFirstChild().getNodeValue()));
					}
					
				}
				
				noticias.add(noticia);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return noticias;
	}
}
