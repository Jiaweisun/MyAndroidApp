package ie.wit.myandroidapp;

import ie.wit.adapters.ExtendAdapter;
import ie.wit.adapters.OrderAdapter;
import ie.wit.models.Meal;
import ie.wit.models.Order;

import java.math.BigDecimal;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class OrderFragment extends Fragment {

	Context context;
	protected ExtendAdapter orderAdapter;
	List<Meal> meallist;
	protected Base activity;
	protected View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {
		context = this.getActivity();
		 view = inflater.inflate(R.layout.order, container, false);
		this.createOrder();
		return view;
	}

	/**
	 * get total price
	 * 
	 * @param count
	 * @param price
	 * @return
	 */
	public double getTotalPrice(int count, double price) {
		return count * price;
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
	ListView listview;
	public void createOrder() {
//		meallist = activity.dbOrderManager.getOrders();
		getOrderedMeal();
		orderAdapter = new OrderAdapter();
		orderAdapter.setMealElements(meallist);
		orderAdapter.setActivity(activity);
		orderAdapter.setCtx(context);
		Log.i("orderAdapter", orderAdapter+"");
		listview = (ListView) view.findViewById(R.id.orderList);
		Log.i("listview", listview+"");
		listview.setAdapter(orderAdapter);
		orderAdapter.notifyDataSetChanged();
		TextView totalPrice = (TextView) view
				.findViewById(R.id.totalPriceValue);
		double tp = 0.00;
		for (Order order : orderlist) {

			Meal oneMeal=activity.dbManager.getEachMeal(order.getMealId());		
			tp += getTotalPrice(order.getCount(), oneMeal.getMealprice());
			
			BigDecimal b = new BigDecimal(tp);
			tp = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
		}
		Log.i("totalPrice", totalPrice.getText()+"");
		totalPrice.setText("€" + tp);
	}
	
	List<Order> orderlist;
	public void getOrderedMeal()
	{
		orderlist=activity.dbOrderManager.getAll();
		Log.i("orderlist", ""+orderlist);
		meallist=activity.dbManager.getOrders();
		Log.i("meallist", ""+meallist);
		if((orderlist.size()==0&&meallist.size()>0)||(orderlist.size()>0&&meallist.size()>0&&orderlist.size()!=meallist.size()))
		{
			orderlist.clear();
			for(Meal meal: meallist)
			{	//orderId++;
				Order order=new Order(meal.getMealid(),this.getTotalPrice(1, meal.getMealprice()),1);
				activity.dbOrderManager.insert(order);
			}
			Log.i("orderlist222222s", ""+orderlist);
			orderlist=activity.dbOrderManager.getAll();	
		}		
		
	}
}
