package com.juanjofp.android.internet;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LocationSample extends Activity {
	
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private static final int DLG_ENABLE_GPS_FROM_SETTING = 1;
	private static final int RESULT_ENABLE_GPS_FROM_SETTING = 1;
	
	private LocationManager mLocationManager;
	private Location mCurrentLocation;
	
	private static boolean GPS_ASKED = false;
	
	private TextView mTvSalida, mTvDireccion;
	
	// Define a listener that responds to location updates
	LocationListener mLocationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	      // Called when a new location is found by the network location provider.
	      if(isBetterLocation(location, mCurrentLocation)){
	    	  mCurrentLocation = location;
	    	  Toast.makeText(LocationSample.this, "Best Position found: "+location.toString(), Toast.LENGTH_LONG).show();
	    	  mTvSalida.setText(mCurrentLocation.toString());
	      }
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    	Toast.makeText(LocationSample.this, "Nuevo estado: "+provider+" "+status, Toast.LENGTH_LONG).show();
	    }

	    public void onProviderEnabled(String provider) {
	    	Toast.makeText(LocationSample.this, provider+" apagado", Toast.LENGTH_LONG).show();
	    }

	    public void onProviderDisabled(String provider) {
	    	Toast.makeText(LocationSample.this, provider+" apagado", Toast.LENGTH_LONG).show();
	    }
	  };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_sample_layout);
		
		mTvSalida = (TextView)findViewById(R.id.tvLocation);
		mTvDireccion = (TextView)findViewById(R.id.tvGeocoder);
		
		mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(mCurrentLocation == null)
			mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !GPS_ASKED) {
			showDialog(DLG_ENABLE_GPS_FROM_SETTING);
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		String locationProviderNetwork = LocationManager.NETWORK_PROVIDER;
		String locationProviderGPS = LocationManager.GPS_PROVIDER;

		mLocationManager.requestLocationUpdates(locationProviderNetwork, 0, 0, mLocationListener);
		
		if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			mLocationManager.requestLocationUpdates(locationProviderGPS, 0, 0, mLocationListener);
		}
		

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mLocationManager.removeUpdates(mLocationListener);
	}

	
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}
	
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		// return super.onCreateDialog(id);
		switch (id) {
		case DLG_ENABLE_GPS_FROM_SETTING:
			return createGPSDialog();
		default:
			return null;
		}
	}
	
	private Dialog createGPSDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"El Sistema GPS esta desactivado, Deseas Activarlo?")
				.setCancelable(false).setPositiveButton("Si",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								
								Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivityForResult(intent,RESULT_ENABLE_GPS_FROM_SETTING);
								
								dialog.dismiss();
							}
						}).setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.dismiss();
							}
						});
		GPS_ASKED = true;
		return builder.create();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);

		
		 if(requestCode == RESULT_ENABLE_GPS_FROM_SETTING){ 
			 if(resultCode == Activity.RESULT_OK){
				 Toast.makeText(this, "GPS activado por el usuario.", Toast.LENGTH_LONG).show();
				 mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
			 }
		 } 
		 
	}
	
	public void getAddress(View view){
		if(mCurrentLocation == null){
			Toast.makeText(this, "Necesito obtener una posición primero", Toast.LENGTH_LONG).show();
			return;
		}
		
		
		try {
			
			Geocoder gc = new Geocoder(this);
			List<Address> direcciones = gc.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
			
			if(direcciones.size() > 0){
				StringBuilder sb = new StringBuilder();
				sb.append(direcciones.get(0).getLocality()).append("\n");
				sb.append(direcciones.get(0).getPostalCode()).append("\n");
				sb.append(direcciones.get(0).getCountryName()).append("\n");
				mTvDireccion.setText(sb.toString());
				
			}else{
				Toast.makeText(this, "Direccion no encontrada", Toast.LENGTH_LONG).show();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Location getLocationFromAddress(String direccion){
		
		try {
			
			Geocoder gc = new Geocoder(this);
			List<Address> direcciones = gc.getFromLocationName(direccion, 1);
			
			if(direcciones.size() > 0){
				Location loc = new Location(LocationManager.GPS_PROVIDER);
				loc.setLatitude(direcciones.get(0).getLatitude());
				loc.setLongitude(direcciones.get(0).getLongitude());
				
				return loc;
				
			}else{
				Toast.makeText(this, "Direccion no encontrada", Toast.LENGTH_LONG).show();
				return null;
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
