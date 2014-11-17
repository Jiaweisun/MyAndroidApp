package ie.wit.util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Settings;
import android.util.Log;

/**
 * this class includes
 * 1. getAddress from position
 * 2. mark to map
 * 3. Dialog alert template
 * @author Jiawei
 *
 */
public class MapUtils {

	/**
	 * the main function is to convert the longitude and latitude into address.
	 * @param location
	 * @param context
	 * @return address information
	 */
	public static String getAddressFromLocation(Location location,Context context)
	{
		String str="No found address";
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            /*
             * Return 1 address.
             */
            addresses = geocoder.getFromLocation(location.getLatitude(),
            		location.getLongitude(), 5);
        } catch (IOException e1) {
        Log.e("LocationSampleActivity",
                "IO Exception in getFromLocation()");
        return str;
        } catch (IllegalArgumentException e2) {
        e2.printStackTrace();
        return str;
        }
        // If the reverse geocode returned an address
        if (addresses != null && addresses.size() > 0) {
            // Get the first address
            Address address = addresses.get(0);
            /*
             * Format the first line of address (if available),
             * city, and country name.
             */
            String addressText = String.format(
                    "%s, %s, %s",
                    // If there's a street address, add it
                    address.getMaxAddressLineIndex() >= 3 ?
                            address.getAddressLine(3)+","+address.getAddressLine(2) : address.getAddressLine(2),
                    // Locality is usually a city
                    address.getLocality(),
                    // The country of the address
                    address.getCountryName());
            // Return the text
            return addressText;
        } else {
            return str;
        }
	}
	
	/**
	 * set markable parameters
	 * @param dLat
	 * @param dLong
	 * @return
	 */
	private static MarkerOptions setMarkable(Location location,Context context,String resName)
	{
		//String address=getAddressFromLocation(location, context);
		MarkerOptions markerOpt = new MarkerOptions();
		markerOpt.position(new LatLng(location.getLatitude(), location.getLongitude()));
		markerOpt.draggable(false);
		markerOpt.visible(true);
		markerOpt.title(resName);
		markerOpt.anchor(0.5f, 0.5f);
		markerOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				//.fromResource(android.R.drawable.ic_menu_mylocation));
		
		return markerOpt;
	}
//	public static double distanceBetweenPoints(Location StartP, Location EndP) {
//		return StartP.distanceTo(EndP)/1000; // return as Kms
//	  }

	
	/**
	 * draw location in map using marker and move camera
	 * @param location
	 * @param map
	 * @param context
	 * @param resName
	 */
	public static void drawUsersCurrentLocation(Location location,GoogleMap map,Context context,String resName) {
		map.addMarker(setMarkable(location,context,resName));
		//move camera to current position;
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(location.getLatitude(), location.getLongitude())) // Sets the center of the map
				.zoom(15) // rate
				.bearing(0) // Sets the orientation of the camera to east
				.tilt(30) // Sets the tilt of the camera to 30 degrees
				.build(); // Creates a CameraPosition from the builder
		map.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

//	public static void removeUsersCurrentLocation(Marker m) {
//		m.remove();
//	}
//	
//	public static LatLng locationToLatLng(Location loc) {
//	    if(loc != null)
//	        return new LatLng(loc.getLatitude(), loc.getLongitude());
//	    return null;
//	}

	public static void showSettingsAlert(final Context context){
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	 
	        // Setting Dialog Title
	        alertDialog.setTitle("GPS is settings");
	 
	        // Setting Dialog Message
	       // String wifiMessage = " and your wifi will be disconnected for best results...\n";
	        String message = "GPS is not enabled, You'll get better Results with GPS on\n";
	        
//	        if(isWiFiEnabled(context)) 
//	        	message += wifiMessage;
//	        	
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
	 
	        // Showing Alert Message
	        alertDialog.show();
	    }
	 	
//	public static boolean isGPSEnabled(final Context context)
//	{
//		String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//	    return provider.contains("gps"); 
//	}
//	
//	public static boolean isWiFiEnabled(final Context context)
//	{
//		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 	
//		return wifiManager.isWifiEnabled();
//	}
//	
//	private static void disableWiFi(final Context context) {
//		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//		wifiManager.setWifiEnabled(false);
//	}

}
