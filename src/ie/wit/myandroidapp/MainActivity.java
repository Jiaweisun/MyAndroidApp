package ie.wit.myandroidapp;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import ie.wit.models.DBRestaurantManager;
import ie.wit.models.Restaurant;
import ie.wit.util.GPSLocation;
import ie.wit.util.MapUtils;
import ie.wit.util.NearbyRestaurants;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.Toast;

/****
 * This is MainActivity, Next is LoginActivity
 * 
 * @author Jiawei
 * 
 */
public class MainActivity extends FragmentActivity {
	/**
	 * Tab
	 */
	private TabHost tabHost;
	private static int currentlayout = 0;

	/**
	 * Location
	 */
	GPSLocation gpsLocation;
	GoogleMap mMap;
	Location currentLocation;

//	String url="https://maps.googleapis.com/maps/api/place/radarsearch/json?location=52.254095500000000000,-7.115964800000029000&radius=5000&types=restaurant&sensor=false&key=AIzaSyBBe10BddlgN_IvyuAI7wpJFR76NEtJYKE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gpsLocation=new GPSLocation(this);

		initMap();	
		setFragment();
		changeLayout();
	}	
	/**
	 * this method display the main logic,
	 * which includes getting current location and nearby restaurants information form google API, 
	 * initialization map, and mark these into map.
	 */
	private void initMap() {
		currentLocation=gpsLocation.getCurrentLocation();
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapView)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//set map to standard
		mMap.setMyLocationEnabled(true);//allow to location
		updateLocation(currentLocation);
	}
	
	/**
	 * 
	 * @param location
	 */
	private void updateLocation(Location location) {
		mMap.clear();
		currentLocation=gpsLocation.updateLocation(location);//it makes sure location value is not null
		getRestaurantsFromURL(currentLocation);//it means the nearby restaurants has been parse and saved into database
		/**
		 * open the database, get restaurants data and draw them into map
		 */
		DBRestaurantManager dbResManager=new DBRestaurantManager(this);
		dbResManager.open();
		List<Restaurant> reslist=dbResManager.getAll();
		for(Restaurant res:reslist)
		{
			Location reslocation=new Location(res.getResName());
			reslocation.setLatitude(res.getLatitude());
			reslocation.setLongitude(res.getLongtitude());
			MapUtils.drawUsersCurrentLocation(reslocation,mMap,this,res.getResName());
		}
		dbResManager.close();
		/**
		 * draw user current position
		 */
		MapUtils.drawUsersCurrentLocation(currentLocation,mMap,this,"I am here");
	}
	/**
	 * get restaurants data from google API
	 * @param location
	 */
	public void getRestaurantsFromURL(Location location)
	{
		String locationStr=location.getLatitude()+","+location.getLongitude();
		String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+locationStr+"&rankby=distance&types=restaurant&sensor=false&key=AIzaSyBBe10BddlgN_IvyuAI7wpJFR76NEtJYKE";
		/**
		 * get and parse JSON data form URL
		 */
		new NearbyRestaurants(this).execute(url);
	}
	
	/***
	 * 
	 * 
	 * Tab portion
	 */
	private void changeLayout() {
		tabHost.setCurrentTab(currentlayout);
	}

	private void setFragment() {
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost
				.newTabSpec("res")
				.setIndicator(
						"restaurant",
						this.getResources().getDrawable(
								R.drawable.abc_ic_search))
				.setContent(R.id.splashFragment));
		tabHost.addTab(tabHost
				.newTabSpec("location")
				.setIndicator(
						"location",
						this.getResources().getDrawable(
								R.drawable.action_people))
				.setContent(R.id.googleMap));// googleMap
		tabHost.addTab(tabHost
				.newTabSpec("account")
				.setIndicator(
						"account",
						this.getResources().getDrawable(
								R.drawable.action_eating))
				.setContent(R.id.userSettingsFragment));
		// 设置默认显示布局
		tabHost.setCurrentTab(0);
	}
	
	 private static Boolean isExit = false;  
	    private static Boolean hasTask = false;  
	    Timer tExit = new Timer();  
	    TimerTask task = new TimerTask() {  
	           
	    @Override  
	    public void run() {  
	            isExit = false;  
	            hasTask = true;  
	        }  
	    };  
	    
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        // TODO Auto-generated method stub  
	        Log.i("smallpig", "user key down");
	        if(keyCode == KeyEvent.KEYCODE_BACK){  
	                System.out.println("user back down");                  
	                if(isExit == false ) {  
	                        isExit = true;  
	                        Toast.makeText(this, "doule press existing", Toast.LENGTH_SHORT).show();  
	                        if(!hasTask) {  
	                                tExit.schedule(task, 2000);  
	                        }} else {  
	                            
	                        }  
	                        this.finish();  
	                        System.exit(0);  
	                } 
	        return super.onKeyDown(keyCode, event);        
	        }
}
