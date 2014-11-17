package ie.wit.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "instantMenu.db";
	private static final int DATABASE_VERSION = 12;

	// Database creation sql statement
	private static final String DATABASE_CREATE_TABLE_USER = "create table "
			+ DBUserManager.TABLE_USER + "( " + DBUserManager.COLUMN_ID + " integer primary key autoincrement, " 
			+ DBUserManager.COLUMN_NAME + " text not null,"
			+ DBUserManager.COLUMN_PASSWORD + " text not null,"
			+ DBUserManager.COLUMN_PHONENUM + " text not null,"
			+ DBUserManager.COLUMN_ADDRESS + " text not null," +
			" UNIQUE (" + DBUserManager.COLUMN_NAME +"));";
	
	private static final String DATABASE_CREATE_TABLE_MEAL = "create table "
			+ DBMealManager.TABLE_MEAL + "( " + DBMealManager.COLUMN_ID + " integer primary key autoincrement, " 
			+ DBMealManager.COLUMN_IMAGEID + " integer not null,"
			+ DBMealManager.COLUMN_NAME + " text not null,"
			+ DBMealManager.COLUMN_PRICE + " double not null,"
			+ DBMealManager.COLUMN_INGREDIENT + " text not null,"
			+ DBMealManager.COLUMN_ENERGY + " double not null,"
			+ DBMealManager.COLUMN_SALE + " integer not null,"
			+ DBMealManager.COLUMN_ORDER + " integer not null);";
	private static final String DATABASE_CREATE_TABLE_RESTAURANT = "create table "
			+ DBRestaurantManager.TABLE_RESTAURANT + "( " + DBRestaurantManager.COLUMN_ID + " integer primary key autoincrement, " 
			+ DBRestaurantManager.COLUMN_IMAGE + " text not null,"
			+ DBRestaurantManager.COLUMN_NAME + " text not null,"
			+ DBRestaurantManager.COLUMN_LONGT + " double not null,"
			+ DBRestaurantManager.COLUMN_LADT + " double not null,"
			+ DBRestaurantManager.COLUMN_PRICELEVEL + " text not null,"
			+ DBRestaurantManager.COLUMN_RATING + " double not null," 
			+DBRestaurantManager.COLUMN_OPEN + " text not null);";
	
	private static final String DATABASE_CREATE_TABLE_ORDER = "create table "
			+ DBOrderManager.TABLE_ORDER + "( " + DBOrderManager.COLUMN_ID + ", " // integer primary key autoincrement
			+ DBOrderManager.COLUMN_MEALID + " int not null,"
			+ DBOrderManager.COLUMN_PRICE + " double not null,"
			+ DBOrderManager.COLUMN_COUNT + " int not null);";
		
	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_TABLE_USER);
		database.execSQL(DATABASE_CREATE_TABLE_MEAL);
		database.execSQL(DATABASE_CREATE_TABLE_RESTAURANT);
		database.execSQL(DATABASE_CREATE_TABLE_ORDER);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBOpenHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DBUserManager.TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + DBMealManager.TABLE_MEAL);
		db.execSQL("DROP TABLE IF EXISTS " + DBRestaurantManager.TABLE_RESTAURANT);
		db.execSQL("DROP TABLE IF EXISTS " + DBOrderManager.TABLE_ORDER);
		onCreate(db);
	}

}