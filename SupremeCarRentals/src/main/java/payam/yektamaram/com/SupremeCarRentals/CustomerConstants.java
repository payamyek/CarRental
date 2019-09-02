package payam.yektamaram.com.SupremeCarRentals;

public enum CustomerConstants {

	FNAME(0) , LNAME(1) , EMAIL(2) , CREDIT (3) , INSURANCE (4), TYPE (5) , PICKUPDATE (6) , PICKUPTIME (7),
			DROPOFFDATE (8) , DROPOFFTIME (9) , PLATE (10) , LICENSE (11), RESERVATION_NUMBER (12);
	
	private int value;

	private CustomerConstants(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
