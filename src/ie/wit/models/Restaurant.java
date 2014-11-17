package ie.wit.models;

public class Restaurant {
	
	private static int autoid = 1;
	private int resId;
	//private int resImageId;	
	private String resImage;
	private String resName;
	private double longtitude;
	private double latitude;
	private String priceLevel;// from 0-4
	private double rating; //people rating from 1.0-5.0
	private String open;
	
	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Restaurant(String resImage, String resName,
			double longtitude, double latitude, String priceLevel,double rating,String open) {
		this.resImage = resImage;
		this.resName = resName;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.priceLevel=priceLevel;
		this.rating = rating;
		this.open=open;
	}
	public String getResImage() {
		return resImage;
	}
	public void setResImage(String resImage) {
		this.resImage = resImage;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getPriceLevel() {
		return priceLevel;
	}
	public void setPriceLevel(String priceLevel) {
		this.priceLevel = priceLevel;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	@Override
	public String toString() {
		return "Restaurant [resId=" + resId + ", resImage=" + resImage
				+ ", resName=" + resName + ", longtitude=" + longtitude
				+ ", latitude=" + latitude + ", priceLevel=" + priceLevel
				+ ", rating=" + rating + ", open=" + open + "]";
	}
	
	
	
	
}
