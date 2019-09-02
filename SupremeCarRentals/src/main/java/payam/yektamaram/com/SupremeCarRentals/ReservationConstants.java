package payam.yektamaram.com.SupremeCarRentals;

public enum ReservationConstants {

	PICKUP(0), DROPOFF(1), LICENSEPLATE(2), RESERVATION_NUMBER(3);
	private int value;
	
	private ReservationConstants(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
