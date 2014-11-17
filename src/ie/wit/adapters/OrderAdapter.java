package ie.wit.adapters;

import ie.wit.models.Meal;
import ie.wit.models.Order;
import ie.wit.myandroidapp.R;

import java.text.DecimalFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;

public class OrderAdapter  extends ExtendAdapter{
	
	private final int MIN_COUNT=1;
	private final int ZERO=0;
//	private List<Order> orderlist=null;;
	private Order oneOrder;

//	public Order getOneOrder() {
//		return oneOrder;
//	}
//
//	public void setOneOrder(Order oneOrder) {
//		this.oneOrder = oneOrder;
//	}

	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {

		final Meal meal= getMealElements().get(position);
		 Log.i("meal",""+meal.toString());
	   convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, null);
	   TextView title = (TextView) convertView.findViewById(R.id.title_order);
	   TextView price = (TextView) convertView.findViewById(R.id.price_order);
	   TextView count = (TextView) convertView.findViewById(R.id.ct_order);
	   ImageView image = (ImageView) convertView.findViewById(R.id.image_order);
	   image.setImageResource(meal.getMealImageIds());
	   title.setText(meal.getMealname());
	   price.setText("€"+new DecimalFormat("0.00").format(meal.getMealprice()));
		   oneOrder=this.getActivity().dbOrderManager.getEachOrder(meal.getMealid());
	   count.setText(""+oneOrder.getCount());
	   Log.i("count",""+count);
	   final ImageView iv=(ImageView) convertView.findViewById(R.id.imgDelete);
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onMealDelete(meal);
				int count=0;
				if(oneOrder.getCount()>MIN_COUNT)
				{
					count=oneOrder.getCount()-MIN_COUNT;
					oneOrder.setCount(count);
				}
				if(oneOrder.getCount()==ZERO)
				{
					getActivity().dbManager.updateOrder(meal, ZERO);
				}
					
				getActivity().dbOrderManager.updateCount(oneOrder, oneOrder.getCount());
				Toast.makeText(getCtx(), "You've remove"+oneOrder.getCount()+" "+meal.getMealname(), Toast.LENGTH_LONG).show();
			}
		});
		    return convertView;
	  }

	public void onMealDelete(final Meal meal) {
		//this.setMealElements(this.getMealElements());//)activity.dbManager.getAll());
		String stringName = meal.getMealname();
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setMessage("Are you sure you want to Delete the \'Meal\' "
				+ stringName + "?");
		builder.setCancelable(false);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) 
			{
				getMealElements().remove(meal); // remove from our list
				notifyDataSetChanged(); // refresh adapter
				getActivity().dbOrderManager.delete(meal.getMealid());
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}	

}
