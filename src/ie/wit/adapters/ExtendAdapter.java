package ie.wit.adapters;

import ie.wit.models.Meal;
import ie.wit.models.Restaurant;
import ie.wit.myandroidapp.Base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ExtendAdapter extends BaseAdapter {

	private List<Meal> mealElements;
	private List<Restaurant> resElements;
	private Context ctx;
	private Base activity;

	public Base getActivity() {
		return activity;
	}

	public void setActivity(Base activity) {
		this.activity = activity;
	}

	public List<Meal> getMealElements() {
		return mealElements;
	}

	public void setMealElements(List<Meal> mealElements) {
		this.mealElements = mealElements;
	}

	public List<Restaurant> getResElements() {
		return resElements;
	}

	public void setResElements(List<Restaurant> resElements) {
		this.resElements = resElements;
	}

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		return getMealElements().size();
	}

	@Override
	public Meal getItem(int position) {
		return getMealElements().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
