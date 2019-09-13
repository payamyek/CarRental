package payam.yektamaram.com.SupremeCarRentals;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Price {

	
	/**
	 * Duration calcuator.
	 *
	 * @param pickUp
	 *            the pick up
	 * @param dropOff
	 *            the drop off
	 * @return the int
	 */
	public static int durationCalcuator(Date pickUp, Date dropOff) {
		long diff = dropOff.getTime() - pickUp.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return (int) (days);
	}
	
	
	/**
	 * Final price calculator.
	 *
	 * @param carType the car type
	 * @param pickUp the pick up date
	 * @param dropOff the drop off date
	 * @param distance the distance
	 * @param fuelLevel the fuel level
	 * @param insurance the insurance
	 * @return the double
	 */
	public static double finalPriceCalculator(String carType, Date pickUp, Date dropOff, int distance, int fuelDifference, double fuelPrice, boolean insurance) {

		double price = 0;
		int gasFine = 0;
		
		long diff = dropOff.getTime() - pickUp.getTime();
		double days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		if (carType.equals("Economy"))
			price = 23.94;
		else if (carType.equals("Compact"))
			price = 29.50;
		else if (carType.equals("Intermediate"))
			price = 34.50;
		else if (carType.equals("Standard Size"))
			price = 39.50;
		else if (carType.equals("Full Size"))
			price = 45.00;
		else if (carType.equals("Mini Van/7 Seater"))
			price = 75.99;
		else if (carType.equals("Premium"))
			price = 85.50;
		else if (carType.equals("Luxury"))
			price = 95.50;
		else if (carType.equals("Supreme"))
			price = 100.45;
		else if (carType.equals("Intermediate SUV"))
			price = 60.45;
		else if (carType.equals("Standard SUV"))
			price = 65.45;
		else if (carType.equals("Large SUV"))
			price = 75.45;

		if (insurance == false) {
			return (days * price + distance * 0.25 + gasFine + fuelDifference * fuelPrice) * 1.13;
		} else {
			return (days * (price + 20.00) + distance * 0.25 + gasFine * fuelPrice) * 1.13;
		}
	}
}
