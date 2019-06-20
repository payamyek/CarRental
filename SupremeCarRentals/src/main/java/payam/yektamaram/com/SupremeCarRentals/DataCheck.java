package payam.yektamaram.com.SupremeCarRentals;

/**
 * This class holds all of the methods for checking information entered by the
 * user.
 * 
 * @version 1.4.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth
 * @since 2018-05-07
 */

public class DataCheck {

	/**
	 * Email check.
	 *
	 * @param String
	 *            email the email
	 * @return true, if successful
	 */
	public static boolean emailCheck(String email) {
		if (email.matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Credit card check.
	 *
	 * @param String
	 *            credit the credit
	 * @return true, if successful
	 */
	public static boolean creditCardCheck(String credit) {
		if (credit.matches("^4[0-9]{12}(?:[0-9]{3})?$") || credit.matches("^5[1-5][0-9]{14}$")) // mastercard
		{
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Insurance number check.
	 * 
	 * @param String
	 *            insurance the insurance
	 * @return true, if successful
	 */
	public static boolean insuranceNumberCheck(String insurance) {

		if (insurance.matches("^[A-Z]{5}[0-9]{2}$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Driver license number check.
	 *
	 * @param String
	 *            license the license
	 * @return true, if successful
	 */
	public static boolean driverLicenseNumberCheck(String license) {
		if (license.matches("^[A-Z][0-9]{4}-[0-9]{5}-[0-9]{5}$")) {
			return true;
		} else {
			return false;
		}
	}

}
