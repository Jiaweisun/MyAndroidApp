package ie.wit.myandroidapp;

import ie.wit.models.DBMealManager;
import ie.wit.models.DBOrderManager;
import ie.wit.models.DBRestaurantManager;
import ie.wit.models.DBUserManager;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * This is BaseActivity, foundation activity.
 * @author Jiawei
 *
 */
public class Base extends FragmentActivity {
	public DBRestaurantManager dbResManager = new DBRestaurantManager(this);
	public DBMealManager dbManager = new DBMealManager(this);
	public DBOrderManager dbOrderManager = new DBOrderManager(this);
	public DBUserManager dbuserManager = new DBUserManager(this);
	protected Bundle 	activityInfo;
//	public Fragment 	mealAllFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setOverFlowMenu(true, false); // true,false turns in on
		dbManager.open();
		dbuserManager.open();
		dbResManager.open();
		dbOrderManager.open();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbManager.close();
		dbuserManager.close();
		dbResManager.close();
		dbOrderManager.close();
	}

//	public Fragment getMealAllFragment() {
//		return mealAllFragment;
//	}
	
	protected void goToActivity(Activity current,Class<? extends Activity> activity,
			 Bundle bundle) {
		Intent newActivity = new Intent(current, activity);

		if (bundle != null)
			newActivity.putExtras(bundle);

		current.startActivity(newActivity);
	}

	private void openInfoDialog(Activity current) {
		Dialog dialog = new Dialog(current);
		dialog.setTitle("About InstantMenu");
		dialog.setContentView(R.layout.info);

		TextView currentVersion = (TextView) dialog
				.findViewById(R.id.versionTextView);
		currentVersion.setText(getString(R.string.version));

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			goToActivity(this,MainActivity.class, null);
			break;
		case R.id.help:
			goToActivity(this,Help.class,  null);
			break;
		case R.id.info:
			openInfoDialog(this);
			break;
		case R.id.logout:
			logout();
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater i = getMenuInflater();
		i.inflate(R.menu.optionsmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public EditText getEditText(int id) {
		return ((EditText) findViewById(id));
	}

	protected String getEditString(int id) {
		return (getEditText(id)).getText().toString();
	}

	protected void setEditString(int id, String str) {
		(getEditText(id)).setText(str);
	}

	protected void setTextViewString(int id, String str) {
		((TextView) findViewById(id)).setText(str);
	}

	protected void setEditDouble(int id, Double d) {
		((EditText) findViewById(id)).setText(d.toString());
	}

	protected double getRatingBarValue(int id) {
		RatingBar bar = (RatingBar) findViewById(id);
		return bar.getRating();
	}

	protected void setRatingBarValue(int id, float d) {
		RatingBar bar = (RatingBar) findViewById(id);
		bar.setRating(d);
	}

	protected double getEditDouble(int id) {
		return Double.parseDouble(getEditString(id));
	}

	protected Button getButton(int id) {
		return (Button) findViewById(id);
	}

	protected CheckBox getCheckBox(int id) {
		return (CheckBox) findViewById(id);
	}

	public void setSpinnerListener(int id, int data,
			OnItemSelectedListener listener) {
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, data,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner) findViewById(id)).setAdapter(spinnerAdapter);
		((Spinner) findViewById(id)).setOnItemSelectedListener(listener);
	}

	protected void setEditTextListener(int id, TextWatcher listener) {
		((EditText) findViewById(id)).addTextChangedListener(listener);
	}

	protected void toastMessage(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
	
	public void setOverFlowMenu(boolean accessibleValue, boolean configValue) {
		// Hack for Overflow Menu
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(accessibleValue);
				menuKeyField.setBoolean(config, configValue);
			}
		} catch (Exception ex) {
			// Ignore
		}
	}
	
	private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", 0)
                .edit();
       /***
        * 
        */
        editor.putBoolean("loggedin", false);
        editor.commit();

        startActivity(new Intent(Base.this,MainActivity.class)//LoginActivity.class
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
	
}
