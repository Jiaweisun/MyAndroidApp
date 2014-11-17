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
//					// 当Provider的状态改变时
//					@Override
//					public void onStatusChanged(String provider, int status,
//							Bundle extras) {
//					}
//
//					@Override
//					public void onProviderEnabled(String provider) {
//						// 当GPS LocationProvider可用时，更新位置
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
//						// 当GPS定位信息发生改变时，更新位置
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
		  //创建LocationManager对象  
		  locManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);  
		//判断GPS是否正常启动  
	        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){  
	            Toast.makeText(ctx, "请开启GPS导航...", Toast.LENGTH_SHORT).show();  
	            //返回开启GPS导航设置界面  
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
     * 返回查询条件 
     * @return 
     */  
    private Criteria getCriteria(){  
        Criteria criteria=new Criteria();  
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细   
        criteria.setAccuracy(Criteria.ACCURACY_FINE);      
        //设置是否要求速度  
        criteria.setSpeedRequired(false);  
        // 设置是否允许运营商收费    
        criteria.setCostAllowed(false);  
        //设置是否需要方位信息  
        criteria.setBearingRequired(false);  
        //设置是否需要海拔信息  
        criteria.setAltitudeRequired(false);  
        // 设置对电源的需求    
        criteria.setPowerRequirement(Criteria.POWER_LOW);  
        return criteria;  
    }  
    
    /** 
     * 实时更新文本内容 
     *  
     * @param location 
     */  
    private void updateView(Location location){  
        if(location!=null){  
//            editText.setText("设备位置信息\n\n经度：");  
//            editText.append(String.valueOf(location.getLongitude()));  
//            editText.append("\n纬度：");  
//            editText.append(String.valueOf(location.getLatitude()));  
        }else{  
            //清空EditText对象  
          //  editText.getEditableText().clear();  
        }  
    }  
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
            //第一次定位
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                Log.i(TAG, "第一次定位");
                break;
            //卫星状态改变
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                Log.i(TAG, "卫星状态改变");
                //获取当前状态
                GpsStatus gpsStatus=locManager.getGpsStatus(null);
                //获取卫星颗数的默认最大值
                int maxSatellites = gpsStatus.getMaxSatellites();
                //创建一个迭代器保存所有卫星 
                Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                int count = 0;     
                while (iters.hasNext() && count <= maxSatellites) {     
                    GpsSatellite s = iters.next();     
                    count++;     
                }   
                System.out.println("搜索到："+count+"颗卫星");
                break;
            //定位启动
            case GpsStatus.GPS_EVENT_STARTED:
                Log.i(TAG, "定位启动");
                break;
            //定位结束
            case GpsStatus.GPS_EVENT_STOPPED:
                Log.i(TAG, "定位结束");
                break;
            }
        };
    };
}
