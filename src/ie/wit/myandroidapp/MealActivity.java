package ie.wit.myandroidapp;

import ie.wit.models.Meal;
import ie.wit.models.Order;

import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MealActivity extends Base implements OnValueChangeListener, Formatter, OnScrollListener {
	
	Context context;
	private NumberPicker mNumberPicker;
	Meal aMeal;
	List<Meal> mealList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meal); 
		context=this.getApplicationContext();  
	   
	    /**
	     * get Meal Details
	     */
		ImageView mealImage=(ImageView)findViewById(R.id.image_menu_meal);
		TextView barString=(TextView)findViewById(R.id.barTextView);
		TextView ingredientValue=(TextView)findViewById(R.id.ingredientValue);
		TextView energyValue=(TextView)findViewById(R.id.energyValue);
		
		
		
		mealList=dbManager.getAll();

		/**
		 * get data
		 */
		activityInfo = getIntent().getExtras();
		aMeal = getMealObject(activityInfo.getInt("mealId"));
		Log.i("aMeal",""+aMeal.toString());
		String name=aMeal.getMealname();
		Double price=aMeal.getMealprice();
		barString.setText(name+"\t\t\t\t\t\t "+"€"+price);
		ingredientValue.setText(aMeal.getIngredient());
		energyValue.setText(aMeal.getEnerger()+"kcal");
		mealImage.setImageResource(aMeal.getMealImageIds());
		
		init();
		final int orginalCount=mNumberPicker.getValue();
		Button btn_ok=(Button) findViewById(R.id.button1);
		
		btn_ok.setOnClickListener(new OnClickListener() {
			// 
			@Override
			public void onClick(View v) {
				/**
				 * in case of the sale meal order
				 */
				if(orginalCount==0&&mNumberPicker.getValue()>0)
				{
					dbManager.updateOrder(aMeal, 1);//set it to order
					Order order=null;
					
						order=new Order(aMeal.getMealid(),mNumberPicker.getValue()*aMeal.getMealprice(),mNumberPicker.getValue());
//						Log.i("order",order.getOrderId()+"");
//					
//						order=dbOrderManager.getEachOrder(aMeal.getMealid());
					Log.i("order2222",order.getOrderId()+"");
					dbOrderManager.insert(order);//adding it into order database.
				}
				dbOrderManager.updateCount(getOrder(aMeal), mNumberPicker.getValue());
				Toast.makeText(context, "update count"+mNumberPicker.getValue(), Toast.LENGTH_LONG).show();
				FinishActivity();
			}
		});
	}
	
	public void FinishActivity()
	{
		setResult(RESULT_OK);
		finish();
	}

	private Meal getMealObject(int id) {
		for (Meal m : mealList)
		{
			if (m.getMealid()== id)
				return m;
		}

		return null;
	}
	private void init() {  
        mNumberPicker = (NumberPicker) findViewById(R.id.numberPicker);  
        mNumberPicker.setFormatter(this);  
        mNumberPicker.setOnValueChangedListener(this);  
        mNumberPicker.setOnScrollListener(this);  
        mNumberPicker.setMaxValue(3);  
        mNumberPicker.setMinValue(0);  
        mNumberPicker.setValue(getOrder(aMeal).getCount());  
    }

	@Override
	public void onScrollStateChange(NumberPicker view, int scrollState) {
//		 switch (scrollState) {  
//	        case OnScrollListener.SCROLL_STATE_FLING:  
//	            Toast.makeText(this, "scroll state fling", Toast.LENGTH_LONG)  
//	                    .show();  
//	            break;  
//	        case OnScrollListener.SCROLL_STATE_IDLE:  
//	            Toast.makeText(this, "scroll state idle", Toast.LENGTH_LONG).show();  
//	            break;  
//	        case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:  
//	            Toast.makeText(this, "scroll state touch scroll", Toast.LENGTH_LONG)  
//	                    .show();  
//	            break;  
//	        }  
	
	}
	public Order getOrder(Meal ameal)
	{
		return new Order(aMeal.getMealid(),aMeal.getMealprice()*mNumberPicker.getValue(),mNumberPicker.getValue());	
	}

	@Override
	public String format(int value) {
		 String tmpStr = String.valueOf(value);  
	        if (value < 10) {  
	            tmpStr = "0" + tmpStr;  
	        }  
	        return tmpStr;  
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
	//	Log.i("tag", "oldValue:" + oldVal + "   ; newValue: " + newVal);  
		mNumberPicker.setValue(newVal);
//        Toast.makeText(  
//                this,  
//                "number changed --> oldValue: " + oldVal + " ; newValue: "  
//                        + mNumberPicker.getValue(), Toast.LENGTH_SHORT).show();  
//		
	}  
	
}
