package ie.wit.util;

import ie.wit.models.DBRestaurantManager;
import ie.wit.models.Restaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * get nearby restaurants location from current location
 * @author Jiawei
 *
 */
public class NearbyRestaurants extends AsyncTask<String, Void, String> {

	Activity activity;
	ProgressDialog dialog;
	public NearbyRestaurants(Activity activity)
	{
		this.activity=activity;
		dialog=new ProgressDialog(activity);
	}

	public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputStream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
	/**
	 * Convert inputStream into¡¡String
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
    }
	
	@Override
	protected void onPreExecute() {
		dialog.setMessage("Loading information");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
        dialog.show();
     }
	
    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0]);
    }
    
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

    	try {
			parseJson(result);
			Log.i("result", result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally
    	{
    		dialog.dismiss();
    	}
   }
    
    /**
     * parse JSON data, in this application, which is from google API.
     * After that, saving them into database.
     * @param in
     * @throws JSONException
     */
	public void parseJson(String in)throws JSONException
	{
		DBRestaurantManager dbResManager=new DBRestaurantManager(activity);
		dbResManager.open();
		if(dbResManager.getAll().size()>0)
			dbResManager.delete();
		JSONObject rootObject=new JSONObject(in);
		JSONArray resultArray=rootObject.getJSONArray("results");
		{
			for(int i=0;i<resultArray.length();i++)
			{
				JSONObject resultObject=resultArray.getJSONObject(i);
				JSONObject geoObject=resultObject.getJSONObject("geometry");
				JSONObject location=geoObject.getJSONObject("location");
				String icon=resultObject.getString("icon");
				String name=resultObject.getString("name");
				String priceLevel="";
				String rating="";
				JSONObject opening_hours=null;
				String open_now="";
				if(resultObject.isNull("price_level"))
					priceLevel = "5";
				else
					priceLevel = resultObject.getString("price_level");
				if(resultObject.has("rating"))
					rating = resultObject.getString("rating");
				else
					rating="5.0";
				if(geoObject.isNull("opening_hours"))
				{
					open_now="No supported time";
				}
				else
				{
					opening_hours=geoObject.getJSONObject("opening_hours");
					open_now=opening_hours.getString("open_now");
				}
					
				Restaurant res=new Restaurant();
				res.setResName(name);
				res.setResImage(icon);
				res.setLatitude(location.getDouble("lat"));
				res.setLongtitude(location.getDouble("lng"));
				switch(priceLevel)
				{
					
					case "0":
						priceLevel="Free";
						break;
					case "1":
						priceLevel="Cheap";
						break;
					case "2":
						priceLevel="Normal";
						break;
					case "3":
						priceLevel="a little expensive";
						break;
					case "4":
						priceLevel="extreamly expensive";
						break;
					case "5":
						priceLevel="No price";
						break;
				}
				res.setPriceLevel(priceLevel);
				res.setRating(Double.parseDouble(rating));
				res.setOpen(open_now);
				dbResManager.insert(res);
				Log.i("res", res.getResName()+"");
			}
				
		}
		dbResManager.close();
	}
}
