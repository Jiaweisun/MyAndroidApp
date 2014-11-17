package ie.wit.adapters;

import ie.wit.models.Meal;
import ie.wit.myandroidapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SaleMenusAdapter extends ExtendAdapter{
	
		  @Override
	  public View getView(int position, View convertView, ViewGroup parent) { 
	
			  Meal meal= getMealElements().get(position);
	   convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_sale_item, null);
	   TextView title = (TextView) convertView.findViewById(R.id.title_menu_sale);
	  // TextView price = (TextView) convertView.findViewById(R.id.price_menu_sale);
	   ImageView image = (ImageView) convertView.findViewById(R.id.image_menu_sale);
	   image.setImageResource(meal.getMealImageIds());
	   title.setText(meal.getMealname());
	  // price.setText(priceObjects[position]);
	   return convertView;
	  }

}
