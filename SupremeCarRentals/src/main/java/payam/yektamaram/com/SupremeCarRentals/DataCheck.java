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
	public static boolean emailCheck(String email) 
	{
		return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") ? true : false ;
	}
	

	/**
	 * Credit card check.
	 *
	 * @param String
	 *            credit the credit
	 * @return true, if successful
	 */
	public static boolean creditCardCheck(String credit) 
	{	
		return (credit.matches("^4[0-9]{12}(?:[0-9]{3})?$") || credit.matches("^5[1-5][0-9]{14}$")) ? true : false ;
	}

	
	/**
	 * Insurance number check.
	 * 
	 * @param String
	 *            insurance the insurance
	 * @return true, if successful
	 */
	public static boolean insuranceNumberCheck(String insurance) 
	{
		return insurance.matches("^[A-Z]{5}[0-9]{2}$") ? true : false ;
	}

	
	/**
	 * Driver license number check.
	 *
	 * @param String
	 *            license the license
	 * @return true, if successful
	 */
	public static boolean driverLicenseNumberCheck(String license) 
	{
		return (license.matches("^[A-Z][0-9]{4}-[0-9]{5}-[0-9]{5}$") || license.matches("^[A-Z][0-9]{4}[0-9]{5}[0-9]{5}$")) ? true : false;
	}

}
