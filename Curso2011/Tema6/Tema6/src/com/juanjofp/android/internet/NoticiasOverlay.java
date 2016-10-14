package com.juanjofp.android.internet;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class NoticiasOverlay extends ItemizedOverlay<OverlayItem> {

	private List<Noticia> mNoticias;
	private Context mContext;
	
	public NoticiasOverlay(Drawable defaultMarker, Context ctx) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
		mContext = ctx;
	}
	
	public void addNoticias( List<Noticia> noticias){
		mNoticias = noticias;
		populate();
	}

	@Override
	protected OverlayItem createItem(int idx) {
		// TODO Auto-generated method stub
		Noticia n = mNoticias.get(idx);
		return new OverlayItem(n.getGeoPoint(), n.getTitulo(), n.getEnlace());
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		if(mNoticias != null)
			return mNoticias.size();
		return 0;
	}
	
	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		Noticia n = mNoticias.get(index);
		Toast.makeText(mContext, n.getTitulo(), Toast.LENGTH_LONG).show();
		return true;
	}

}
