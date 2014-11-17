package ie.wit.adapters;

import java.text.DecimalFormat;

import ie.wit.models.Meal;
import ie.wit.models.Order;
import ie.wit.myandroidapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MenusAdapter extends ExtendAdapter{

	private final int  MAX_COUNT=3;

	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		final Meal meal= getMealElements().get(position);
	   convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_all_item, null);
	   TextView title = (TextView) convertView.findViewById(R.id.title_menu_all);
	   TextView price = (TextView) convertView.findViewById(R.id.price_menu_all);
	   ImageView image = (ImageView) convertView.findViewById(R.id.image_menu_all);
	   image.setImageResource(meal.getMealImageIds());
	   title.setText(meal.getMealname());
	   price.setText("€"+new DecimalFormat("0.00").format(meal.getMealprice()));
	   final ImageView iv=(ImageView) convertView.findViewById(R.id.imgAdd);
		iv.setOnClickListener(new OnClickListener() {
			Order order=null;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//onMealAdd(meal);
				if(meal.getOrder()!=1||getActivity().dbOrderManager.getAll().size()==0)
				{
					meal.setOrder(1);
					getActivity().dbManager.updateOrder(meal, meal.getOrder());//set the meal to sale	
					order=new Order(meal.getMealid(),meal.getMealprice(),meal.getOrder());
					getActivity().dbOrderManager.insert(order);//insert into order table
				}
				else
				{
					//update meal count in order table
					order=getActivity().dbOrderManager.getEachOrder(meal.getMealid());
					int count=order.getCount()+1;
					if(count<=3)
						order.setCount(count);
					else
						order.setCount(MAX_COUNT);
					getActivity().dbOrderManager.updateCount(order, order.getCount());
				}
				
				
				Toast.makeText(getCtx(), "You've order"+order.getCount()+" "+meal.getMealname(), Toast.LENGTH_SHORT).show();
			}
		});
//		 MealItem item = new MealItem(context, parent, addListener, mealElements.get(position));
		    return convertView;
	  }	 
	
	public void onMealAdd(final Meal meal) {
		
				getMealElements().add(meal); // add in our list
				notifyDataSetChanged(); // refresh adapter
				Order order=getActivity().dbOrderManager.getEachOrder(meal.getMealid());
				getActivity().dbOrderManager.insert(order);
		
	}	
	}
