package ie.wit.models;

public class Meal {
	
	private static int autoid = 1;
	private int mealid;
	private int mealImageIds;
	private String mealname;
	private double mealprice;
	private String ingredient;
	private double energer;
	private int sale;//1, default is 0
	//private int count;
	private int order;//1, default is 0. if the value==1, then the count should >=1;
	
	public Meal()
	{}

	public Meal(int mealImageIds, String mealname, double mealprice,
			 double energer) {
		super();
		this.mealImageIds = mealImageIds;
		this.mealname = mealname;
		this.mealprice = mealprice;
		this.energer = energer;
	}
	
	
	
	
	public Meal(int mealImageIds, String mealname, double mealprice,
			String ingredient, double energer, int sale,int order) {//,int count
		super();
		this.mealImageIds = mealImageIds;
		this.mealname = mealname;
		this.mealprice = mealprice;
		this.ingredient = ingredient;
		this.energer = energer;
		this.sale = sale;
		this.order=order;
		//this.count=count;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getMealid() {
		return mealid;
	}

	public void setMealid(int mealid) {
		this.mealid = mealid;
	}
	
	public int getMealImageIds() {
		return mealImageIds;
	}
	public void setMealImageIds(int mealImageIds) {
		this.mealImageIds = mealImageIds;
	}
	public String getMealname() {
		return mealname;
	}
	public void setMealname(String mealname) {
		this.mealname = mealname;
	}
	public double getMealprice() {
		return mealprice;
	}
	public void setMealprice(double mealprice) {
		this.mealprice = mealprice;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public double getEnerger() {
		return energer;
	}
	public void setEnerger(double energer) {
		this.energer = energer;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}
	
//	public int getCount() {
//		return count;
//	}
//
//	public void setCount(int count) {
//		this.count = count;
//	}


	@Override
	public String toString() {
		return "Meal [mealid=" + mealid + ", mealImageIds=" + mealImageIds
				+ ", mealname=" + mealname + ", mealprice=" + mealprice
				+ ", ingredient=" + ingredient + ", energer=" + energer
				+ ", sale=" + sale + ", order=" + order + "]";
	}

	
	
	
	
	
	

}
