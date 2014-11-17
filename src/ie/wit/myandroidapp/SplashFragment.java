package ie.wit.myandroidapp;


import ie.wit.adapters.ExtendAdapter;
import ie.wit.adapters.RestaurantAdapter;
import ie.wit.models.DBRestaurantManager;
import ie.wit.util.HorizontialListView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;



public class SplashFragment extends Fragment {


	Context context;
	ExtendAdapter resAdapter;
	DBRestaurantManager dbResManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context=this.getActivity();
		View view =inflater.inflate(R.layout.splash, container,false);
		HorizontialListView listview = (HorizontialListView) view.findViewById(R.id.listView);
		
		dbResManager=new DBRestaurantManager(context);
		dbResManager.open();

		resAdapter=new RestaurantAdapter();
		resAdapter.setCtx(context);
		Log.i("all",""+dbResManager.getAll());
		resAdapter.setResElements(dbResManager.getAll());
		dbResManager.close();
		listview.setAdapter(resAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                /**
                 * to Login page with resName
                 */
            	Bundle activityInfo = new Bundle();
            	int resId=resAdapter.getResElements().get(position).getResId();
            	activityInfo.putInt("resId",resId);
        		Intent detailIntent = new Intent(context, LoginActivity.class);
        		detailIntent.putExtras(activityInfo);
        		context.startActivity(detailIntent);
        		
//                String resName = resAdapter.getActivity();
//                
//                Toast.makeText(ctx, resName, Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ctx, LoginActivity.class);
//
//                intent.putExtra("resName", resName);
//                startActivity(intent);               
            }
        });

		return view;
	}
}
