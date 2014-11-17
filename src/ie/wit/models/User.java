package ie.wit.models;

public class User {

	private static int autoid = 1;
	private int userid;
	private String name;//set name unique
	private String password;
	private String phoneNumber;
	private String address;
	
	public User()
	{}
	
	
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
public User(String name, String password, String phoneNumber, String address) {
		super();
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
//	public static int getAutoid() {
//		return autoid;
//	}
//	public static void setAutoid(int autoid) {
//		User.autoid = autoid;
//	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
