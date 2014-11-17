package ie.wit.myandroidapp;

import java.util.ArrayList;

import ie.wit.models.Restaurant;
import ie.wit.models.User;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;

/**
 * This is LoginActivity, next is MenuActivity
 * @author Jiawei
 *
 */
public class LoginActivity extends Base {
	Context ctx;
	private boolean isResumed = false;// This flag is used to enable session
										// state change checks
	private UiLifecycleHelper uiHelper;// use the UiLifecycleHelper to track the session
	
	// trigger a session state change listener
	private Session.StatusCallback callback = new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				onSessionStateChange(session, state, exception);
			}
	};
			
	private static SharedPreferences sharedsettings;
//	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		/**
		 * recording the user performance
		 */
		ctx=this.getApplicationContext();
		sharedsettings = getSharedPreferences("loginPrefs", 0);
		
//		isFacebookLogin=true;=sharedsettings.getBoolean("loggedin", true);
		if (sharedsettings.getBoolean("loggedin", false))
			startMenuScreen();
		
		Button btn_login = (Button) findViewById(R.id.btn_login);
		Button btn_register = (Button)findViewById(R.id.btn_register);

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("vvvvvvv", "this ::::"+v);
				login();
				
				Log.i("hello", "this login again");

			}
		});
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// to register page
				Intent intent = new Intent(ctx, RegisterActivity.class);
				startActivity(intent);
				finishActivity();

			}
		});
	}	
	    
	public String sharedResName()
	{
		activityInfo = getIntent().getExtras();
		String resName="";
		for (Restaurant res :dbResManager.getAll())
		{
			if (res.getResId()== activityInfo.getInt("resId"))
				resName= res.getResName();
		}
		return resName;
	}

	public void login() {

		CharSequence name = ((TextView) findViewById(R.id.username))
				.getText();
		Log.i("login username:", "" + name);
		CharSequence password = ((TextView) findViewById(R.id.password))
				.getText();
		Log.i("login password:", "" + password);

		if (name.length() <= 0 || password.length() <= 0)
			Log.i("loginin", "user information can't be null");
		else		
		{
			ArrayList<User> users=(ArrayList<User>) dbuserManager.getAll();
			if(users.size()>0)
			{
				for(int i=0;i<users.size();i++)
				{
					//Log.i("not enter", ""+users.size()+",n:"+users.get(i).getName()+",ps:"+users.get(i).getPassword());

					if(name.toString().equals(users.get(i).getName())&&password.toString().equals(users.get(i).getPassword()))
					{
						SharedPreferences.Editor editor = sharedsettings.edit();
						editor.putBoolean("loggedin", true);
						//editor.putString("resName",this.sharedResName());
						editor.commit();
						startMenuScreen();
					}
					else
					{
						Toast.makeText(this, "regist fist", Toast.LENGTH_LONG).show();
					}
				}
			}			
		}
	}
	
	public void finishActivity()
	{
		setResult(RESULT_OK);
		finish();
	}

	/**
	 * clear or set the flag when the activity is paused or resumed
	 */
	    
	  @Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		isResumed = false;
	}

	/**
	 * The method shows the relevant fragment based on the person's authenticated state
	 * @param session, which is from com.facebook.Session
	 * @param state
	 * @param exception
	 */
	
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		// Only make changes if the activity is visible
		if (isResumed) {
			if (state.isOpened()) 
			{
//				isFacebookLogin=true;
				startMenuScreen();	
				//finishActivity();
				
			}
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();

		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
//			isFacebookLogin=true;
			startMenuScreen();
			//finishActivity();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 Session.getActiveSession().onActivityResult(this, requestCode,
		 resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	public void startMenuScreen()
	{
//		SharedPreferences.Editor editor = sharedsettings.edit();
//		//editor.putBoolean("loggedin", true);
//		editor.putString("resName",this.sharedResName());
//		editor.commit();
		Intent intent=new Intent(ctx,MenuActivity.class);
		startActivity(intent);
		finishActivity();
	}


}
