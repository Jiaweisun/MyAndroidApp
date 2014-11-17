package ie.wit.myandroidapp;

import java.util.Iterator;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 
 * @author Jiawei
 *
 */
public class LocationFragment extends Fragment{

	Context ctx;
	GoogleMap mMap; 
	LocationManager locManager;
	private Location location;  
	private String bestProvider; 
	private final String TAG="LocationActivity";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ctx=this.getActivity();
		View view =inflater.inflate(R.layout.location, container,false);
//		initProvider();
//		mMap=((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();  
//		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//		locManager.requestLocationUpdates(bestProvider, 3 * 1000, 8,
//				new LocationListener() {
//					// ��Provider��״̬�ı�ʱ
//					@Override
//					public void onStatusChanged(String provider, int status,
//							Bundle extras) {
//					}
//
//					@Override
//					public void onProviderEnabled(String provider) {
//						// ��GPS LocationProvider����ʱ������λ��
//						location = locManager.getLastKnownLocation(provider);
//						Log.i("location", "" + location);
//					}
//
//					@Override
//					public void onProviderDisabled(String provider) {
//						updateLocation(null);
//					}
//
//					@Override
//					public void onLocationChanged(Location location) {
//						// ��GPS��λ��Ϣ�����ı�ʱ������λ��
//						updateLocation(location);
//					}
//				});

		return view;
		// Add code to print out the key hash
				/**
				 * try { PackageInfo info = getPackageManager().getPackageInfo(
				 * "ie.wit.myandroidapp", PackageManager.GET_SIGNATURES); for (Signature
				 * signature : info.signatures) { MessageDigest md =
				 * MessageDigest.getInstance("SHA"); md.update(signature.toByteArray());
				 * Log.d("KeyHash:", Base64.encodeToString(md.digest(),
				 * Base64.DEFAULT)); } } catch (NameNotFoundException e) {
				 * 
				 * } catch (NoSuchAlgorithmException e) {
				 * 
				 * }
				 ***/
	}
	
	
	private void initProvider() {  
		  //����LocationManager����  
		  locManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);  
		//�ж�GPS�Ƿ���������  
	        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){  
	            Toast.makeText(ctx, "�뿪��GPS����...", Toast.LENGTH_SHORT).show();  
	            //���ؿ���GPS�������ý���  
	            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);     
	            startActivityForResult(intent,0);   
	            return;  
	        }  
	          
		  // List all providers:  
		  //List<String> providers = locManager.getAllProviders();  
		 // Criteria criteria = new Criteria();  
		  bestProvider = locManager.getBestProvider(getCriteria(), false);  
		  location = locManager.getLastKnownLocation(bestProvider);  
		  updateView(location);
		  locManager.addGpsStatusListener(listener); 
//		  System.out.println("latitute:"+location.getLatitude()+"longtitude: " + location.getLongitude());  
		}  
	/** 
     * ���ز�ѯ���� 
     * @return 
     */  
    private Criteria getCriteria(){  
        Criteria criteria=new Criteria();  
        //���ö�λ��ȷ�� Criteria.ACCURACY_COARSE�Ƚϴ��ԣ�Criteria.ACCURACY_FINE��ȽϾ�ϸ   
        criteria.setAccuracy(Criteria.ACCURACY_FINE);      
        //�����Ƿ�Ҫ���ٶ�  
        criteria.setSpeedRequired(false);  
        // �����Ƿ�������Ӫ���շ�    
        criteria.setCostAllowed(false);  
        //�����Ƿ���Ҫ��λ��Ϣ  
        criteria.setBearingRequired(false);  
        //�����Ƿ���Ҫ������Ϣ  
        criteria.setAltitudeRequired(false);  
        // ���öԵ�Դ������    
        criteria.setPowerRequirement(Criteria.POWER_LOW);  
        return criteria;  
    }  
    
    /** 
     * ʵʱ�����ı����� 
     *  
     * @param location 
     */  
    private void updateView(Location location){  
        if(location!=null){  
//            editText.setText("�豸λ����Ϣ\n\n���ȣ�");  
//            editText.append(String.valueOf(location.getLongitude()));  
//            editText.append("\nγ�ȣ�");  
//            editText.append(String.valueOf(location.getLatitude()));  
        }else{  
            //���EditText����  
          //  editText.getEditableText().clear();  
        }  
    }  
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
            //��һ�ζ�λ
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                Log.i(TAG, "��һ�ζ�λ");
                break;
            //����״̬�ı�
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                Log.i(TAG, "����״̬�ı�");
                //��ȡ��ǰ״̬
                GpsStatus gpsStatus=locManager.getGpsStatus(null);
                //��ȡ���ǿ�����Ĭ�����ֵ
                int maxSatellites = gpsStatus.getMaxSatellites();
                //����һ�������������������� 
                Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                int count = 0;     
                while (iters.hasNext() && count <= maxSatellites) {     
                    GpsSatellite s = iters.next();     
                    count++;     
                }   
                System.out.println("��������"+count+"������");
                break;
            //��λ����
            case GpsStatus.GPS_EVENT_STARTED:
                Log.i(TAG, "��λ����");
                break;
            //��λ����
            case GpsStatus.GPS_EVENT_STOPPED:
                Log.i(TAG, "��λ����");
                break;
            }
        };
    };
}
