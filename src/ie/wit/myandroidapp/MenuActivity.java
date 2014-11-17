package ie.wit.myandroidapp;

import ie.wit.models.Meal;
import ie.wit.models.Restaurant;
import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/***
 * This is MenuActivity, which include three tab with 3 fragments(MenuSaleFragment, MenuAllFragment, OrderFragment)
 * @author Jiawei
 *
 */
public class MenuActivity extends Base {

	private TabHost tabHost;
	private static int currentlayout = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menus);
		SharedPreferences sharedsettings = getSharedPreferences("loginPrefs", 0);
		String resName=sharedsettings.getString("resName","Menu");
		Log.i("name:",resName+"");
		this.setTitle(resName);
		setFragment();
		changeLayout();
	}
	
	private void changeLayout() {
		// display current tab according to user
		tabHost.setCurrentTab(currentlayout);
	}

	private void setFragment() {
	
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		//tabHost.setup(this.getLocalActivityManager());  
//		tabHost.addTab(tabHost.newTabSpec("menu_sale").setIndicator(
//                "Order", this.getResources().getDrawable(
//						R.drawable.abc_ic_search))
//                .setContent(new Intent().setClass(this, MenuSaleActivity.class)));
//		tabHost.addTab(tabHost.newTabSpec("menu_all").setIndicator(
//                "Order", this.getResources().getDrawable(
//						R.drawable.abc_ic_search))
//                .setContent(new Intent().setClass(this, MenuAllActivity.class)));
		tabHost.addTab(tabHost
				.newTabSpec("menu_sale")
				.setIndicator(
						"sale",
						this.getResources().getDrawable(
								R.drawable.abc_ic_search))
				.setContent(R.id.menu_sale_Fragment));
		tabHost.addTab(tabHost
				.newTabSpec("menu_all")
				.setIndicator(
						"all",
						this.getResources().getDrawable(
								R.drawable.action_people))
				.setContent(R.id.menu_all_Fragment));
		tabHost.addTab(tabHost
				.newTabSpec("order")
				.setIndicator(
						"order",
						this.getResources().getDrawable(
								R.drawable.action_eating))
				.setContent(R.id.orderFragment));
//		//Intent intent = new Intent().setClass(this, LoginActivity.class);
//		TabSpec spec2 = tabHost.newTabSpec("tabtwo");
//		spec2.setIndicator(
//	                "Order", this.getResources().getDrawable(
//							R.drawable.abc_ic_search));
//			Intent inte = new Intent(this,OrderActivity.class);
//			inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			spec2.setContent(inte);
//			tabHost.addTab(spec2);
		tabHost.setCurrentTab(0);
	}
	
}
