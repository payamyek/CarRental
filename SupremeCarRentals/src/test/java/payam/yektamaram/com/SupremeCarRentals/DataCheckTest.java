package payam.yektamaram.com.SupremeCarRentals;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataCheckTest extends TestCase{

	
	/*
    * Create the test case
    *
    * @param testName name of the test case
    */
   public DataCheckTest( String testName )
   {
       super( testName );
   }

   /**
    * @return the suite of tests being tested
    */
   public static Test suite()
   {
       return new TestSuite( DataCheckTest.class );
   }

	
	/*
	 * Typical email test
	 */
	public void testStandardEmail() {
		assertTrue(DataCheck.emailCheck("email@gmail.com"));
	}
	
	/*
	 * Email with no signature
	 */
	public void testNoSignatureEmail() {
		assertFalse(DataCheck.emailCheck("@gmail.com"));
	}
	
	/*
	 * Email with no domain
	 */
	public void testNoDomainEmail() {
		assertFalse(DataCheck.emailCheck("email@.com"));
	}
	
	/*
	 * Email with no extension
	 */
	public void testNoExtensionEmail() {
		assertFalse(DataCheck.emailCheck("email@email"));
	}
	

	/*
	 * Email with no characters
	 */
	public void testEmptyEmail() {
		assertFalse(DataCheck.emailCheck(""));
	}
	
	/*
	 * Standard Visa Card
	 */
	public void testVisaCreditCard() {
		assertTrue(DataCheck.creditCardCheck("4012888888881881"));
	}
	
	/*
	 * Standard Visa Card
	 */
	public void testMastercardCreditCard() {
		assertTrue(DataCheck.creditCardCheck("5105105105105100"));
	}
	
	/*
	 * Empty Credit Card
	 */
	public void testEmptyCreditCard() {
		assertFalse(DataCheck.creditCardCheck(""));
	}

	
	/*
	 * Standard Insurance Number
	 */
	public void testStandardInsuranceNumber() 
	{
		assertTrue(DataCheck.insuranceNumberCheck("ABABA02"));
	}
	
	/*
	 * Insurance Number With no Digits
	 */
	public void testNoDigitsInsuranceNumber() 
	{
		assertFalse(DataCheck.insuranceNumberCheck("ABABAAA"));
	}
	
	/*
	 * Insurance Number With No Characters
	 */
	public void testNoCharactersInsuranceNumber() 
	{
		assertFalse(DataCheck.insuranceNumberCheck("0202022"));
	}
	
	/*
	 * Insurance Number With No Characters
	 */
	public void testEmptyInsuranceNumber() 
	{
		assertFalse(DataCheck.insuranceNumberCheck(""));
	}
	
	/*
	 * Standard Driver License Number
	 */
	public void testStandardDriverLicenseNumber() 
	{
		assertTrue(DataCheck.driverLicenseNumberCheck("A1312-21311-13111"));
	}
	
	/*
	 * Driver License Number With No Hyphens
	 */
	public void testNoHyphensDriverLicenseNumber() 
	{
		assertTrue(DataCheck.driverLicenseNumberCheck("A13122131113111"));
	}
	
	/*
	 * Empty Driver License Number
	 */
	public void testEmptyDriverLicenseNumber() 
	{
		assertFalse(DataCheck.driverLicenseNumberCheck(""));
	}
	
	/*
	 * Empty Name
	 */
	public void testEmptyName() 
	{
		assertFalse(DataCheck.nameCheck(""));
	}
	
	/*
	 * Standard Name
	 */
	public void testStandardName() 
	{
		assertTrue(DataCheck.nameCheck("Payam"));
	}
	
	
}
