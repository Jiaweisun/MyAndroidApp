package ie.wit.myandroidapp;

import java.util.List;

import ie.wit.adapters.ExtendAdapter;
import ie.wit.adapters.MenusAdapter;
import ie.wit.models.Meal;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuAllFragment extends Fragment{
	
	Context context;
	//protected MenusAdapter mealAdapter;
	protected ExtendAdapter mealAdapter;
	List<Meal> meallist;
	protected Base activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {
		context=this.getActivity();
		View view =inflater.inflate(R.layout.menu_all, container,false);
		meallist= activity.dbManager.getAll();
		if(meallist.size()==0)
			activity.dbManager.setupList();
		mealAdapter=new MenusAdapter();
		mealAdapter.setMealElements(meallist);
		mealAdapter.setCtx(context);
		mealAdapter.setActivity(activity);
		mealAdapter.notifyDataSetChanged();
		ListView listview = (ListView) view.findViewById(R.id.listView);
//	
		listview.setAdapter(mealAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	/**
            	 * bundle data into next activity
            	 */
            	Bundle activityInfo = new Bundle();
            	int mealId=mealAdapter.getMealElements().get(position).getMealid();
            	Log.i("mealid:","mealId"+mealId);
            	activityInfo.putInt("mealId",mealId);
        		Intent detailIntent = new Intent(context, MealActivity.class);
        		detailIntent.putExtras(activityInfo);
        		context.startActivity(detailIntent);
            	
//            	int menuImageId=mealAdapter.getMealElements().get(position).getMealImageIds();
//            	
//                String menuName = mealAdapter.getMealElements().get(position).getMealname();
//               
//               Toast.makeText(context, menuName, Toast.LENGTH_LONG).show();
//               Intent intent = new Intent(context, ie.wit.myandroidapp.Meal.class);
//               intent.putExtra("menuImageId", menuImageId);
//               intent.putExtra("myvalue", menuName);
//               
//               startActivity(intent);                 
            }
        });

		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (Base) activity;
	}
	@Override
	public void onStart() {
		super.onStart();
	}

}
