package ie.wit.myandroidapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.GoogleMap;

import ie.wit.models.DBUserManager;
import ie.wit.models.User;
import ie.wit.util.GPSLocation;
import ie.wit.util.MapUtils;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Base{

	Context context;
	private static SharedPreferences sharedsettings;
	private String temp="";
	/***
	 * gcm
	 */
	 public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	    
	    String SENDER_ID = "193420614568";//project number on console.developers.google.com

	    /**
	     * Tag used on log messages.
	     */
	    static final String TAG = "Register Activity";

//	    EditText gcmCode;
	    GoogleCloudMessaging gcm;
	    AtomicInteger msgId = new AtomicInteger();
	   // Context context;

	    String regid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		setContentView(R.layout.register);
		sharedsettings = getSharedPreferences("loginPrefs", 0);
		getPhoneNumber();
		getDefaultAddress();
//		gcmCode=(EditText)findViewById(R.id.reActiveCode);
		  // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);
            Log.i("gcm",gcm+"");
            Log.i("regid",regid+"");
            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
        
		Button activeCode = (Button) findViewById(R.id.btn_actCode);
		activeCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	sendRegistrationIdToBackend(regid);//sending regid to my app server
//				new AsyncTask<Void, Void, String>() {
//	                @Override
//	                protected String doInBackground(Void... params) {
//	                    String msg = "";
//	                    try {
//	                        Bundle data = new Bundle();
//	                        data.putString("my_message", "test msg");
//	                        data.putString("my_action", "com.google.android.gcm.demo.app.ECHO_NOW");
//	                        String id = Integer.toString(msgId.incrementAndGet());
//	                        gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
	                       // msg = getRandomActiveCode();//Sent message
	                       // sendActiveCode();//sending sms code by msg;
	                        
//	                    } catch (IOException ex) {
//	                        msg = "Error :" + ex.getMessage();
//	                    }
//	                    return msg;
	                    
//	                }

//	                @Override
//	                protected void onPostExecute(String msg) {
	                	//gcmCode.append(msg + "\n");
	                	 sendActiveCode();
//	                }
//	            }.execute(null, null, null);
			}
		});
		
		Button reg = (Button) findViewById(R.id.btn_reg);
		reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();

			}
		});

	}
	 /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend(regid);
                    storeRegistrationId(context, regid);
                    Log.i("back regid", msg);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
//                gcmCode.append(msg + "\n");
            }
        }.execute(null, null, null);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        checkPlayServices();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        return getSharedPreferences(RegisterActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
//        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    /**
     * before send http
     */
    public void stricNetwork()
    {
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
    	.detectDiskReads()
    	.detectDiskWrites()
    	.detectNetwork()   // or .detectAll() for all detectable problems
    	.penaltyLog()
    	.build());
    	StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
    	.detectLeakedSqlLiteObjects()
    	.detectLeakedClosableObjects()
    	.penaltyLog()
    	.penaltyDeath()
    	.build());
    }
    
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(String registrationId) {//String deviceId,
    	stricNetwork();
    	Log.d("gcm", "Sending registration ID to my application server");
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.1.29:8080/myAndroidWeb_gcm/");
        try {
          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
          // Get the deviceID
         // nameValuePairs.add(new BasicNameValuePair("deviceid", deviceId));
          nameValuePairs.add(new BasicNameValuePair("regId",
              registrationId));

          post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          HttpResponse response = client.execute(post);
          BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

          String line = "";
          while ((line = rd.readLine()) != null) {
            Log.e("HttpResponse", line);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
	
	//getActiveCode
	public void sendActiveCode()
	{
		temp=getRandomActiveCode();
		CharSequence tel = ((TextView) findViewById(R.id.tel)).getText();
		SmsManager smsmanger=SmsManager.getDefault();  
        //if(isPhoneNumberValid(this.getPhoneNumber())){  
            PendingIntent mPI=PendingIntent.getBroadcast(this, 0, new Intent(), 0);  
            smsmanger.sendTextMessage(tel.toString(), null, "Your active code is:£º"+temp, mPI, null);  
            Toast.makeText(this, "send sucess!", Toast.LENGTH_LONG).show();   
	}
	
	//register
	public void register() {
		/**
		 * username,password,phonenumber
		 * 
		 */

		CharSequence uname = ((TextView) findViewById(R.id.regusername))
				.getText();
		CharSequence ps = ((TextView) findViewById(R.id.regps)).getText();
		CharSequence reps = ((TextView) findViewById(R.id.regreps)).getText();
		CharSequence tel = ((TextView) findViewById(R.id.tel)).getText();
		CharSequence reCode = ((TextView) findViewById(R.id.reActiveCode)).getText();

		CharSequence address = ((TextView) findViewById(R.id.address)).getText();
		/**
		 * validation
		 */
		if (uname.length() <= 0 || ps.length() <= 0 || reps.length() <= 0
				|| tel.length() <= 0) {
			Toast.makeText(this, "info can't be null", Toast.LENGTH_LONG)
					.show();
		}
		if(reCode.length()<=0||!reCode.toString().equals(temp)){
			Toast.makeText(this, "Active Code is wrong", Toast.LENGTH_LONG)
			.show();
		}else if (!ps.toString().equals(reps.toString()))
			Toast.makeText(this, "ps is different", Toast.LENGTH_SHORT).show();
		else {
			
			DBUserManager dbm = new DBUserManager(context);
			User u = new User(uname.toString(), ps.toString(), tel.toString(),
					"default address");
			dbm.open();
			dbm.insert(u);
			dbm.close();
			sharedPerformace();
			Toast.makeText(this, "Register sucessed.", Toast.LENGTH_SHORT).show();
			showActivity();
		}
		/**
		 * get address automatically
		 */
	}
	public void sharedPerformace() {
		/**
		 * record user performance
		 */
		SharedPreferences.Editor editor = sharedsettings.edit();
		editor.putBoolean("loggedin", true);
		editor.commit();
	}

	/***
	 * open another activity and kill current activity
	 */
	public void showActivity() {
		/**
		 * jump to another page
		 */
		Intent intent = new Intent(this, MenuActivity.class);
		this.startActivity(intent);
		FinishActivity();
	}

	/**
	 * kill current activity
	 */
	public void FinishActivity() {
		setResult(RESULT_OK);
		finish();
	}

	/**
	 * createActiveCode
	 * @return
	 */
	public String getRandomActiveCode()
	{
		for(int i=0;i<5;i++){//generate 5 numbers as active code
            int k=(int) (Math.random()*10);  
            temp+=k;  
        }  		
		return temp;
	}
	/**
	 * get phoneNUmber automatically
	 */
	public void getPhoneNumber() {
		EditText phoneNumber = (EditText) findViewById(R.id.tel);
		TelephonyManager phoneMgr = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber.setText(phoneMgr.getLine1Number());
	}
	
	/**
	 * get address
	 */
	Location currentLocation;
	public void getDefaultAddress()
	{
		EditText addresset = (EditText) findViewById(R.id.address);
		GPSLocation gpsLocation=new GPSLocation(this);
		currentLocation=gpsLocation.getCurrentLocation();
		currentLocation=gpsLocation.updateLocation(currentLocation);
		String address=MapUtils.getAddressFromLocation(currentLocation, context);
		addresset.setText(address);
	}

}
