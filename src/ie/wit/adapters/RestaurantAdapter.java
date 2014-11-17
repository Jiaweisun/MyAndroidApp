package ie.wit.adapters;

import ie.wit.myandroidapp.R;
import ie.wit.util.DownloadImageTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantAdapter extends ExtendAdapter {
	
	@Override
	public int getCount() {
		return getResElements().size();
	}

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		 
	   View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurantitem, null);
	   TextView title = (TextView) retval.findViewById(R.id.title_res);
	   TextView priceLevel = (TextView) retval.findViewById(R.id.priceLevel_res);
	   TextView rating = (TextView) retval.findViewById(R.id.rating_res);
	   TextView open = (TextView) retval.findViewById(R.id.open_res);
	   
	   new DownloadImageTask((ImageView) retval.findViewById(R.id.image_res)).execute(getResElements().get(position).getResImage());
//	   ImageView image = (ImageView) retval.findViewById(R.id.image_res);
	   title.setText(getResElements().get(position).getResName());
	   priceLevel.setText(getResElements().get(position).getPriceLevel());
	   rating.setText(getResElements().get(position).getRating()+"");
	   if(getResElements().get(position).getOpen().equals("false"))
		   open.setText("Close");
	   else if(getResElements().get(position).getOpen().equals("true"))
		   open.setText("Open");
	   else
		   open.setText(getResElements().get(position).getOpen());
	   
	   return retval;
	  }
	 
}
