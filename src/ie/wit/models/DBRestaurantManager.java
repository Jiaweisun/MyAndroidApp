package ie.wit.models;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBRestaurantManager {
//	private String priceLevel;// from 0-4
//	private double rating; //people rating from 1.0-5.0
//	private boolean isOpen;
	
	public static final String TABLE_RESTAURANT = "table_restaurant";
	public static final String COLUMN_ID = "resid";
	public static final String COLUMN_IMAGE = "resImage";
	public static final String COLUMN_NAME = "resName";
	public static final String COLUMN_LONGT="longtitude";
	public static final String COLUMN_LADT="latitude";
	public static final String COLUMN_PRICELEVEL="pricelevel";
	public static final String COLUMN_RATING="rating";
	public static final String COLUMN_OPEN="open";
	
	private SQLiteDatabase database;
	private DBOpenHelper dbHelper;

	public DBRestaurantManager(Context context) {
		dbHelper = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		Log.i("db open:", ""+database);
	}

	public void close() {
		database.close();
	}
	
	/***
	 * 
	 * @param c
	 */

	public void insert(Restaurant res) {
		ContentValues values = new ContentValues();
//		values.put(COLUMN_IMAGEID, res.getResImageId());
		values.put(COLUMN_IMAGE, res.getResImage());
		values.put(COLUMN_NAME, res.getResName());
		values.put(COLUMN_LONGT,res.getLongtitude());
		values.put(COLUMN_LADT, res.getLatitude());
		values.put(COLUMN_PRICELEVEL, res.getPriceLevel());
		values.put(COLUMN_RATING, res.getRating());
		values.put(COLUMN_OPEN, res.getOpen());
		
		database.insert(TABLE_RESTAURANT, null,values);
//		database.insert(DBDesigner.TABLE_USER, nullColumnHack, values)
		//close();
	}
	

	public void delete(int id) {

		Log.v("DB", "meal deleted with id: " + id);
		database.delete(TABLE_RESTAURANT,
				COLUMN_ID + " = " + id, null);
	}

	public void update(Restaurant res) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		values.put(COLUMN_IMAGE, res.getResImage());
		values.put(COLUMN_NAME, res.getResName());
		values.put(COLUMN_LONGT,res.getLongtitude());
		values.put(COLUMN_LADT, res.getLatitude());
		values.put(COLUMN_PRICELEVEL, res.getPriceLevel());
		values.put(COLUMN_RATING, res.getRating());
		values.put(COLUMN_OPEN, res.getOpen());
		//values.put(DBDesigner.COLUMN_FAV, pojo.getFavourite());

		long insertId = database
				.update(TABLE_RESTAURANT,
						values,
						COLUMN_ID + " = "
								+ res.getResId(), null);

	}

	public List<Restaurant> getAll() {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_RESTAURANT, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Restaurant pojo = toRestaurant(cursor);
			restaurants.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return restaurants;
	}

	public Restaurant get(int id) {
		Restaurant pojo = null;

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_RESTAURANT + " WHERE "
				+ COLUMN_ID + " = " + id, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Restaurant temp = toRestaurant(cursor);
			pojo = temp;
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return pojo;
	}
	
	private Restaurant toRestaurant(Cursor cursor) {
		Restaurant pojo = new Restaurant();
		pojo.setResId(cursor.getInt(0));
		pojo.setResImage(cursor.getString(1));
		pojo.setResName(cursor.getString(2));
		pojo.setLongtitude(cursor.getDouble(3));
		pojo.setLatitude(cursor.getDouble(4));
		pojo.setPriceLevel(cursor.getString(5));
		pojo.setRating(cursor.getDouble(6));
		pojo.setOpen(cursor.getString(7));
		return pojo;
	}
	public void delete() {

		//Log.v("DB", "Res deleted with id: " + id);
		database.delete(TABLE_RESTAURANT, null, null);
	}


//	public void setupList() {
//		Restaurant res1=new Restaurant(R.drawable.gallery_photo_1, "Text #1",-7.115964801111129000, 52.254095500000000000, 10);
//		Restaurant res2=new Restaurant(R.drawable.gallery_photo_2, "Text #2",-7.115964800000029000, 52.254095500000000000, 10);
//		Restaurant res3=new Restaurant(R.drawable.gallery_photo_3, "Text #3",-7.115789800000029000, 52.254095500000000000, 10);
//		Restaurant res4=new Restaurant(R.drawable.gallery_photo_4, "Text #4",-7.115964800000029000, 52.254095500000000000, 10);
//		Restaurant res5=new Restaurant(R.drawable.gallery_photo_5, "Text #5",-7.115964800000029000, 52.254095500000000000, 10);
//		Restaurant res6=new Restaurant(R.drawable.gallery_photo_6, "Text #6",-7.115964800034029000, 52.254095500000000000, 10);
//		Restaurant res7=new Restaurant(R.drawable.gallery_photo_7, "Text #7",-7.115964800000029000, 52.254095500456000000, 10);
//		Restaurant res8=new Restaurant(R.drawable.gallery_photo_8, "Text #8",-7.115964800000029000, 52.254095500000000000, 10);
//		
//		insert(res1);
//		insert(res2);
//		insert(res3);
//		insert(res4);
//		insert(res5);
//		insert(res6);
//		insert(res7);
//		insert(res8);
//	}
}
