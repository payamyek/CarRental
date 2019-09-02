package payam.yektamaram.com.SupremeCarRentals;

public enum CarConstants{

	TYPE(0), YEAR(1), MANUFACTURER(2), MODEL(3), ODOMETER(4), LICENSEPLATE(5), STATUS(6), NUMBEROFSCRATCHES(7),
	ACCIDENTS(8);
	private int value;

	private CarConstants(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
