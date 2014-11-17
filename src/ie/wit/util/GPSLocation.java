package ie.wit.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

/**
 * This class main to get current position and update position
 * @author Jiawei
 *
 */
public class GPSLocation {

	LocationManager locManager;
	Location location;
	private String bestProvider = "";//may be gps,net or network
	private Activity activity;

	public GPSLocation(Activity activity) {
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}

	/**
	 * default location
	 */
	private static final Location WATERFORD = new Location("") {
		{
			setLatitude(52.254095500000000000);
			setLongitude(-7.115964800000029000);
		}
	};

	/**
	 * open gps or network
	 * 
	 * @return
	 */
	private LocationManager getEnabled() {
		locManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		// open GPS or not
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			showSettingsAlert(this.getActivity());
		}
		return locManager;
	}

	/**
	 * get current location, in this method getEnabled method will be called to test and init location manager.
	 * @return
	 */
	public Location getCurrentLocation() {
		locManager = this.getEnabled();
		Log.i("net",locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)+ "");
		bestProvider = locManager.getBestProvider(getCriteria(), false);
		location = locManager.getLastKnownLocation(bestProvider);
//		Log.i("locManager", "" + locManager);
//		Log.i("bestProvider", "" + bestProvider);
//		Log.i("location", "" + location);
		return location;
	}

	/**
	 * 
	 * @param location
	 * @return location
	 * location will be reset if the location is null, otherwise, it return the original value
	 */
	public Location updateLocation(Location location) {
		Location newLocation=location;
		if (newLocation==null) {
			newLocation=new Location("");
			newLocation.setLongitude(WATERFORD.getLongitude());
			newLocation.setLatitude(WATERFORD.getLatitude());
		}
		Log.i("newLocaiton",""+newLocation);
		return newLocation;
	}

	/**
	 * dialog
	 * @param context
	 */
	public static void showSettingsAlert(final Context context){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
       // String wifiMessage = " and your wifi will be disconnected for best results...\n";
        String message = "GPS is not enabled, You'll get better Results with GPS on\n";
        
        alertDialog.setMessage(message + "Do you want to continue to Settings?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            //	disableWiFi(context); 
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        AlertDialog alter=alertDialog.create();
 
        // Showing Alert Message
        alter.show();
    }
	 private Criteria getCriteria() {
	 Criteria criteria = new Criteria();
	 criteria.setAccuracy(Criteria.ACCURACY_FINE);
	 criteria.setSpeedRequired(false);
	 criteria.setCostAllowed(false);
	 criteria.setBearingRequired(false);
	 criteria.setAltitudeRequired(false);
	 criteria.setPowerRequirement(Criteria.POWER_LOW);
	 return criteria;
	 }

}
