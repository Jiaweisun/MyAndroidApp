package ie.wit.models;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	
	private static int autoid = 1;
	private int orderId;
	private int mealId;//
	private double totalPrice; //the total price of all orders.
	private int count;
	private String date;//current time HH:mm:ss dd-MM-yyyy
	//private int payway;
	
//	private ArrayList<Meal> mealList;
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Order(int mealId, double totalPrice,int count) {
		super();
		this.mealId = mealId;
		this.totalPrice = totalPrice;
		this.count=count;
	}


	
	public int getOrderId() {
		return orderId;
	}


	public int getMealId() {
		return mealId;
	}


	public void setMealId(int mealId) {
		this.mealId = mealId;
	}


	
	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public double getTotalPrice() {
//		for(int i=0;i<mealList.size();i++)
//		{
//			totalPrice=+mealList.get(i).getCount()*mealList.get(i).getMealprice();
//		}
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
//		for(int i=0;i<this.getMealList().size();i++)
//		{
//			totalPrice=+this.getMealList().get(i).getCount()*this.getMealList().get(i).getMealprice();
//		}
		this.totalPrice = totalPrice;
	}
	public String getDate() {
		Date date=new Date();
		return date.getDay()+","+date.getMonth()+1+","+date.getYear();
	}
//	public void setDate(Date date) {
//		this.date = d
//	}
//	public int getPayway() {
//		return payway;
//	}
//	public void setPayway(int payway) {
//		this.payway = payway;
//	}
//	public ArrayList<Meal> getMealList() {
//		return mealList;
//	}
//	public void setMealList(ArrayList<Meal> mealList) {
//		this.mealList = mealList;
//	}
	
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", mealId=" + mealId
				+ ", totalPrice=" + totalPrice + ", count=" + count + ", date="
				+ date + "]";
	}
	
	
	
	
}
