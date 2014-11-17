package ie.wit.models;

import ie.wit.myandroidapp.R;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBOrderManager {
	public static final String TABLE_ORDER = "table_order";
	public static final String COLUMN_ID = "orderid";
	public static final String COLUMN_MEALID = "mealid";
	public static final String COLUMN_PRICE = "totalprice";
	public static final String COLUMN_COUNT = "count";
	
	
	private SQLiteDatabase database;
	private DBOpenHelper dbHelper;

	public DBOrderManager(Context context) {
		dbHelper = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		Log.i("oederdb open:", ""+database);
	}

	public void close() {
		database.close();
	}
	
	/***
	 * 
	 * @param c
	 */

	public void insert(Order o) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID,o.getOrderId());
		values.put(COLUMN_MEALID,o.getMealId());
		values.put(COLUMN_PRICE, o.getTotalPrice());
		if(o.getCount()<=1)
			o.setCount(1);
		values.put(COLUMN_COUNT, o.getCount());
		Log.i("values:", ""+values);
		database.insert(TABLE_ORDER, null,values);
//		database.insert(DBDesigner.TABLE_USER, nullColumnHack, values)
		//close();
	}

	public void delete(int id) {

		Log.v("DB", "order deleted with id: " + id);
		database.delete(TABLE_ORDER,
				COLUMN_ID + " = " + id, null);
	}

	public int getMealCount(int mealId) {
		//List<Meal> meals = new ArrayList<Meal>();
		int count=0;
		Cursor cursor = database.rawQuery("SELECT "+COLUMN_COUNT+" FROM "
				+ TABLE_ORDER + " WHERE "
				+ COLUMN_MEALID + " = "+mealId, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Order pojo = new Order();
			pojo.setCount(cursor.getInt(0));
//			Order pojo = toOrder(cursor);
			//meals.add(pojo);
			count=pojo.getCount();
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return count;
	}
	
	public void updateCount(Order pojo, int newCount)
	{
		Log.i("database", ""+database);

		ContentValues values = new ContentValues();
		values.put(COLUMN_COUNT, newCount);
//Log.i("database", ""+database);
		database.update(TABLE_ORDER,
						values,
						COLUMN_ID + " = "
								+ pojo.getOrderId(), null);
	}
	public void update(Order pojo) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		values.put(COLUMN_MEALID,pojo.getMealId());
		values.put(COLUMN_PRICE, pojo.getTotalPrice());
		values.put(COLUMN_COUNT, pojo.getCount());

		long insertId = database
				.update(TABLE_ORDER,
						values,
						COLUMN_ID + " = "
								+ pojo.getOrderId(), null);

	}
	public List<Order> getAll() {
		List<Order> orders = new ArrayList<Order>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_ORDER, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Order pojo = toOrder(cursor);
			orders.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return orders;
	}

	public Order getEachOrder(int mealId) {
		Order pojo = null;

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_ORDER + " WHERE "
				+ COLUMN_MEALID + " = " + mealId, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Order temp = toOrder(cursor);
			pojo = temp;
			cursor.moveToNext();
			Log.i("temp",""+temp);
		}
		// Make sure to close the cursor
		cursor.close();
		return pojo;
	}
	
	private Order toOrder(Cursor cursor) {
		Order pojo = new Order();
		pojo.setOrderId(cursor.getInt(0));
		pojo.setMealId(cursor.getInt(1));
		pojo.setTotalPrice(cursor.getDouble(2));
		pojo.setCount(cursor.getInt(3));
		return pojo;
	}

//	public void setupList() {
//		Meal m1 = new Meal(R.drawable.gallery_photo_1,"Text #1",12.2,"sugar,rice,mice",230.0,1,1,1);//R.drawable.gallery_photo_1
//		Meal m2 = new Meal(R.drawable.gallery_photo_2,"Text #2",22.7,"sugar,rice,mice",230.0,0,1,1);
//		Meal m3 = new Meal(R.drawable.gallery_photo_3,"Text #3",10.0,"sugar,rice,mice",230.0,0,1,1);
//		Meal m4 = new Meal(R.drawable.gallery_photo_4,"Text #4",32.0,"sugar,rice,mice",230.0,0,0,0);
//		Meal m5 = new Meal(R.drawable.gallery_photo_5,"Text #5",36.2,"sugar,rice,mice",230.0,1,0,0);
//		Meal m6 = new Meal(R.drawable.gallery_photo_6,"Text #6",45.0,"sugar,rice,mice",230.0,1,0,0);
//		Meal m7 = new Meal(R.drawable.gallery_photo_7,"Text #7",12.3,"sugar,rice,mice",230.0,1,0,0);
//		Meal m8 = new Meal(R.drawable.gallery_photo_8,"Text #8",16.8,"sugar,rice,mice",230.0,1,0,0);
////		Meal u2 = new Meal();
//		
//		insert(m1);
//		insert(m2);
//		insert(m3);
//		insert(m4);
//		insert(m5);
//		insert(m6);
//		insert(m7);
//		insert(m8);
//	}


}
