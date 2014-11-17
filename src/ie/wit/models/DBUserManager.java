package ie.wit.models;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBUserManager {

	public static final String TABLE_USER = "table_user";
	public static final String COLUMN_ID = "userid";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_PHONENUM = "phoneNumber";
	public static final String COLUMN_ADDRESS = "address";
	private SQLiteDatabase database;
	private DBOpenHelper dbHelper;

	public DBUserManager(Context context) {
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

	public void insert(User c) {
		//open();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, c.getName());
		values.put(COLUMN_PASSWORD,c.getPassword());
		values.put(COLUMN_PHONENUM, c.getPhoneNumber());
		values.put(COLUMN_ADDRESS, c.getAddress());

		database.insert(TABLE_USER, null,values);
//		database.insert(DBDesigner.TABLE_USER, nullColumnHack, values)
		//close();
	}

	public void delete(int id) {

		Log.v("DB", "user deleted with id: " + id);
		database.delete(TABLE_USER,
				COLUMN_ID + " = " + id, null);
	}

	public void update(User pojo) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, pojo.getName());
		values.put(COLUMN_PASSWORD, pojo.getPassword());
		values.put(COLUMN_PHONENUM, pojo.getPhoneNumber());
		values.put(COLUMN_ADDRESS, pojo.getAddress());
		//values.put(DBDesigner.COLUMN_FAV, pojo.getFavourite());

		long insertId = database
				.update(TABLE_USER,
						values,
						COLUMN_ID + " = "
								+ pojo.getUserid(), null);

	}

	public List<User> getAll() {
		List<User> users = new ArrayList<User>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_USER, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User pojo = toUser(cursor);
			users.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return users;
	}

	public User get(int id) {
		User pojo = null;

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ TABLE_USER + " WHERE "
				+ COLUMN_ID + " = " + id, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User temp = toUser(cursor);
			pojo = temp;
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return pojo;
	}

//	public List<Coffee> getFavourites() {
//		List<Coffee> coffees = new ArrayList<Coffee>();
//		Cursor cursor = database.rawQuery("SELECT * FROM "
//				+ DBDesigner.TABLE_USER + " WHERE "
//				+ DBDesigner.COLUMN_FAV + " = 1", null);
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			Coffee pojo = toUser(cursor);
//			coffees.add(pojo);
//			cursor.moveToNext();
//		}
//		// Make sure to close the cursor
//		cursor.close();
//		return coffees;
//	}
	
//	public boolean deleteCoffee(Coffee coffee)
//	{
//		//Cursor cursor=database.
//		return false;
//	}
	
	private User toUser(Cursor cursor) {
		User pojo = new User();
		pojo.setUserid(cursor.getInt(0));
		pojo.setName(cursor.getString(1));
		pojo.setPassword(cursor.getString(2));
		pojo.setPhoneNumber(cursor.getString(3));
		pojo.setAddress(cursor.getString(4));
		return pojo;
	}

	public void setupList() {
		User u1 = new User("jws", "123456","877079291","41 holly...");
		User u2 = new User("wing", "123456","0867079291","41 holly...");
		
		insert(u1);
		insert(u2);
	}
}
