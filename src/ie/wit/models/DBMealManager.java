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

public class DBMealManager {
	
	public static final String TABLE_MEAL = "table_meal";
	public static final String COLUMN_ID = "mealid";
	public static final String COLUMN_IMAGEID = "mealImageId";
	public static final String COLUMN_NAME = "mealName";
	public static final String COLUMN_PRICE = "mealprice";
	public static final String COLUMN_INGREDIENT = "ingredient";
	public static final String COLUMN_ENERGY = "energy";
	public static final String COLUMN_SALE="sale";
	//public static final String COLUMN_COUNT="count";
	public static final String COLUMN_ORDER="isOrder";
	
	private SQLiteDatabase database;
	private DBOpenHelper dbHelper;

	public DBMealManager(Context context) {
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

	public void insert(Meal m) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_IMAGEID, m.getMealImageIds());
		values.put(COLUMN_NAME, m.getMealname());
		values.put(COLUMN_PRICE,m.getMealprice());
		values.put(COLUMN_INGREDIENT, m.getIngredient());
		values.put(COLUMN_ENERGY, m.getEnerger());
		values.put(COLUMN_SALE, m.getSale());
		values.put(COLUMN_ORDER, m.getOrder());
		//values.put(COLUMN_COUNT, m.getCount());
		
		database.insert(TABLE_MEAL, null,values);
//		database.insert(DBDesigner.TABLE_USER, nullColumnHack, values)
		//close();
	}

	public void delete(int id) {

		Log.v("DB", "meal deleted with id: " + id);
		database.delete(TABLE_MEAL,
				COLUMN_ID + " = " + id, null);
	}

	public void update(Meal pojo) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		values.put(COLUMN_IMAGEID, pojo.getMealImageIds());
		values.put(COLUMN_NAME, pojo.getMealname());
		values.put(COLUMN_PRICE, pojo.getMealprice());
		values.put(COLUMN_INGREDIENT, pojo.getIngredient());
		values.put(COLUMN_ENERGY, pojo.getEnerger());
		values.put(COLUMN_SALE, pojo.getSale());
		values.put(COLUMN_ORDER, pojo.getOrder());
		//values.put(COLUMN_COUNT, pojo.getCount());
		//values.put(DBDesigner.COLUMN_FAV, pojo.getFavourite());

		long insertId = database
				.update(TABLE_MEAL,
						values,
						COLUMN_ID + " = "
								+ pojo.getMealid(), null);

	}
	public void updateOrder(Meal pojo,int order)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_ORDER, order);
//Log.i("database", ""+database);
		database.update(TABLE_MEAL,
						values,
						COLUMN_ID + " = "
								+ pojo.getMealid(), null);
	}
//	public void updateCount(Meal pojo, int newCount)
//	{
//		Log.i("database", ""+database);
//
//		ContentValues values = new ContentValues();
//		values.put(COLUMN_COUNT, newCount);
////Log.i("database", ""+database);
//		database.update(TABLE_MEAL,
//						values,
//						COLUMN_ID + " = "
//								+ pojo.getMealid(), null);
//	}

	public List<Meal> getAll() {
		List<Meal> meals = new ArrayList<Meal>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_MEAL, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Meal pojo = toMeal(cursor);
			meals.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return meals;
	}

	public Meal getEachMeal(int id) {
		Meal pojo = null;

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_MEAL + " WHERE "
				+ COLUMN_ID + " = " + id, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Meal temp = toMeal(cursor);
			pojo = temp;
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return pojo;
	}
	

	public List<Meal> getSales() {
		List<Meal> meals = new ArrayList<Meal>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_MEAL + " WHERE "
				+ COLUMN_SALE + " = 1", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Meal pojo = toMeal(cursor);
			meals.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return meals;
	}
	
	public List<Meal> getOrders() {
		List<Meal> meals = new ArrayList<Meal>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_MEAL + " WHERE "
				+ COLUMN_ORDER + " = 1 ", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Meal pojo = toMeal(cursor);
			meals.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return meals;
	}
	
//	public int getMealCount(int mealId) {
//		//List<Meal> meals = new ArrayList<Meal>();
//		int count=0;
//		Cursor cursor = database.rawQuery("SELECT "+COLUMN_ORDER+" FROM "
//				+ TABLE_MEAL + " WHERE "
//				+ COLUMN_ID + " = "+mealId, null);
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			Meal pojo = toMeal(cursor);
//			//meals.add(pojo);
//			count=pojo.getCount();
//			cursor.moveToNext();
//		}
//		// Make sure to close the cursor
//		cursor.close();
//		return count;
//	}
	
	private Meal toMeal(Cursor cursor) {
		Meal pojo = new Meal();
		pojo.setMealid(cursor.getInt(0));
		pojo.setMealImageIds(cursor.getInt(1));
		pojo.setMealname(cursor.getString(2));
		pojo.setMealprice(cursor.getDouble(3));
		pojo.setIngredient(cursor.getString(4));
		pojo.setEnerger(cursor.getDouble(5));
		pojo.setSale(cursor.getInt(6));
		pojo.setOrder(cursor.getInt(7));
		//pojo.setCount(cursor.getInt(8));
		return pojo;
	}

	public void setupList() {
		Meal m1 = new Meal(R.drawable.wigwammer,"Wigwammer",2.6,"Premium double decker pepperoni, double extra 100% mozzarella",445.0,1,1);//R.drawable.gallery_photo_1
		Meal m2 = new Meal(R.drawable.chicken_pizza,"Chicken Apache",5.8,"Glute,Milk/Lactose, Wheat",349.0,0,1);
		Meal m3 = new Meal(R.drawable.appetizers,"Hiawatha (Hawaiian)",7.7,"sugar,rice,mice",364.0,0,1);
		Meal m4 = new Meal(R.drawable.vegetable_pizza,"Vegetarian",2.9,"sugar,rice,mice",344.0,0,0);
		Meal m5 = new Meal(R.drawable.greek_pizza,"Greek Kebab Pizza",9.1,"sugar,rice,mice",230.0,1,0);
		Meal m6 = new Meal(R.drawable.seafood,"Mexican Pepper VolcanoSpicy",5.9,"sugar,rice,mice",230.0,1,0);
		Meal m7 = new Meal(R.drawable.geronimo_spicy,"GeronimoSpicy",2.9,"sugar,rice,mice",230.0,1,0);
		Meal m8 = new Meal(R.drawable.bacon_pizza,"Bacon Apache",8.4,"sugar,rice,mice",470.0,0,0);
		Meal m9 = new Meal(R.drawable.super_pizza,"Super Platter",5.4,"sugar,rice,mice",230.0,1,0);
		Meal m10 = new Meal(R.drawable.anylargepizza_banner,"Any Pizza,size",6.3,"sugar,rice,mice",230.0,1,0);
		Meal m11 = new Meal(R.drawable.rooster_wraps,"Rooster Wrap",7.4,"sugar,rice,mice",230.0,1,0);
		Meal m12 = new Meal(R.drawable.family_meal,"Family Meal Pizza",18.0,"sugar,rice,mice",230.0,1,0);
		
		insert(m1);
		insert(m2);
		insert(m3);
		insert(m4);
		insert(m5);
		insert(m6);
		insert(m7);
		insert(m8);
		insert(m9);
		insert(m10);
		insert(m11);
		insert(m12);
	}

}
