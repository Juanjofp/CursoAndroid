package com.juanjofp.android.internet;

import java.util.List;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MapSample extends MapActivity {

	private MapView mapView;
	private NoticiasOverlay mNoticias;
	private MyLocationOverlay mPosition;
	
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.mapview_layout);
		
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
		mapView.getController().setZoom(12);
		
		//Noticias
		PULLParser pull = new PULLParser();
        List<Noticia> noticias = pull.parse(getResources().openRawResource(R.raw.noticias_xml));
        mNoticias = new NoticiasOverlay(getResources().getDrawable(R.drawable.noticias), this);
        mNoticias.addNoticias(noticias);
        
        if(noticias != null && noticias.size() > 0)
        	mapView.getController().animateTo(noticias.get(0).getGeoPoint());
        
        //Posicion
        mPosition = new MyLocationOverlay(this, mapView);
        mPosition.enableCompass();
        mPosition.enableMyLocation();
        mPosition.runOnFirstFix(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				mapView.getController().animateTo(mPosition.getMyLocation());
			}
		});
        
        //Capas
		mapView.getOverlays().add(mNoticias);
		mapView.getOverlays().add(mPosition);
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
