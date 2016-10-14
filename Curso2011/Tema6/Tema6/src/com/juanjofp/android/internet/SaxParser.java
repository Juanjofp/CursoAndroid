package com.juanjofp.android.internet;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxParser{

	public List<Noticia> parse(InputStream is) {
		
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        try {
        	
            SAXParser parser = factory.newSAXParser();
            SaxParserHandler handler = new SaxParserHandler();
            parser.parse(is, handler);
            return handler.getNoticias();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        
    }
}
