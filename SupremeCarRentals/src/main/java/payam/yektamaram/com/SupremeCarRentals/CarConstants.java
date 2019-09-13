package payam.yektamaram.com.SupremeCarRentals;

public enum CarConstants{

	TYPE(0), YEAR(1), MANUFACTURER(2), MODEL(3), ODOMETER(4), LICENSEPLATE(5), FUELCAPACITY(6) , FUELLEVEL(7);
	private int value;

	private CarConstants(int value) {	
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
