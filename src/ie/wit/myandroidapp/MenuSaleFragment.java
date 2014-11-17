package ie.wit.myandroidapp;

import java.util.List;

import ie.wit.adapters.ExtendAdapter;
import ie.wit.adapters.SaleMenusAdapter;
import ie.wit.models.Meal;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuSaleFragment extends Fragment {

	Context context;
	protected ExtendAdapter mealAdapter;
	List<Meal> meallist;
	protected Base activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context=this.getActivity();
		View view =inflater.inflate(R.layout.menu_sale, container,false);
		meallist= activity.dbManager.getSales();
			if(meallist.size()==0)
				activity.dbManager.setupList();
		mealAdapter=new SaleMenusAdapter();
		mealAdapter.setMealElements(meallist);
		GridView gridView = (GridView) view.findViewById(R.id.gridView);
		gridView.setAdapter(mealAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	
            	Bundle activityInfo = new Bundle();
            	int mealId=mealAdapter.getMealElements().get(position).getMealid();
            	activityInfo.putInt("mealId",mealId);
        		Intent detailIntent = new Intent(context, MealActivity.class);
        		detailIntent.putExtras(activityInfo);
        		context.startActivity(detailIntent);  
            }
        });

		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (Base) activity;
	}
}
