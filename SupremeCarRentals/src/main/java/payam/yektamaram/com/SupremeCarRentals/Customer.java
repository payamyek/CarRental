package payam.yektamaram.com.SupremeCarRentals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Stores all information about the customer that is available and that might
 * have been provided when the record about the customer was created in
 * CreateRecord
 * 
 * <p>
 * <h2>Global Variables</h2>
 * <p>
 * <b>firstName - </b> Creates an instance of the JLabel class which is the
 * title of the company at the top of screen.
 * <p>
 * <b>lastName - </b> Creates an instance of the JLabel class which asks for the
 * user's first name.
 * <p>
 * <b>emailAddress - </b> Creates an instance of the JTextField class which
 * takes the user's first name.
 * <p>
 * <b>licenseNumber - </b> Creates an instance of the JLabel class which asks
 * for the user's last name.
 * <p>
 * <b>creditCardNumber - </b> Creates an instance of the JTextField class which
 * takes the user's last name.
 * <p>
 * <b>pickUpDate - </b> Creates an instance of the JLabel class which asks for
 * the user's CC number.
 * <p>
 * <b>dropOffDate - </b> Creates an instance of the JTextField class which takes
 * the user's CC number.
 * <p>
 * <b>dropOffTime - </b> Creates an instance of the JLabel class which asks for
 * the user's insurance number.
 * <p>
 * <b>pickUpTime - </b> Creates an instance of the JTextField class which takes
 * the user's insurance number.
 * <p>
 * <b>carType - </b> Creates an instance of the JLabel class which asks for the
 * user's email address.
 * <p>
 * <b>licensePlate - </b> Creates an instance of the JTextField class which
 * takes the user's email address.
 * <p>
 * <b>carFound - </b> Creates an instance of the JLabel class which asks for the
 * user's age.
 * <p>
 * <b>recordNumber - </b> Creates an instance of the JComboBox class which takes
 * the user's age.
 * <p>
 * <b>customerFound - </b> Creates an instance of the JButton class which
 * submits the information and creates a record with it.
 * <p>
 * <b>reservationNumber - </b> String reservationNumber that is used to track a
 * reservation.
 * <p>
 * <b>customerDatabase - </b> ArrayList of customers that is used as a database.
 * <p>
 * <b>CSV_HEADER - </b> The constant headers for the '.csv' file.
 * <p>
 * 
 * 
 * @version 2.4.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
 * @since 2018-05-07
 */
public class Customer {

	/** The df. */
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/** The reservation number. */
	String firstName, lastName, emailAddress, licenseNumber, insuranceNumber, creditCardNumber, carType, carMake,
			carModel, licensePlate, customerProblems, reservationNumber;

	/** The total price. */
	static double dailyPrice, totalPrice;

	/** The customer rating. */
	int customerRating;

	/** The record number. */
	static int recordNumber;

	/** The car found. */
	private boolean carFound = false;

	/** The customer found. */
	private static boolean customerFound;

	/** The user pick up date. */
	private Date userPickUpDate, userDropOffDate;

	/** The loaded files. */
	static int numberOfRecords;

	/** The customer database. */
	protected static ArrayList<Customer> customerDatabase = new ArrayList<Customer>();

	/** The Constant CSV_HEADER. */
	private static final String[] CSV_HEADER = { "First Name", "Last Name", "Email Address", "License Number",
			"Insurance Number", "Credit Card Number", "Pick Up Date", "Drop Off Date", "Car Type", "Car Make",
			"Car Model", "License Plate", "Reservation Number", "Customer Problems", "Customer Rating" };

	/**
	 * Instantiates a new customer.
	 *
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailAddress
	 *            the email address
	 * @param licenseNumber
	 *            the license number
	 * @param creditCardNumber
	 *            the credit card number
	 * @param userPickUpDate
	 *            the user pick up date
	 * @param userDropOffDate
	 *            the user drop off date
	 * @param carType
	 *            the car type
	 * @param licensePlate
	 *            the license plate
	 */
	public Customer(String firstName, String lastName, String emailAddress, String licenseNumber,
			String creditCardNumber, Date userPickUpDate, Date userDropOffDate, String carType, String licensePlate) {

		customerDatabase.add(this);
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.licenseNumber = licenseNumber;
		this.creditCardNumber = creditCardNumber;
		this.userPickUpDate = userPickUpDate;
		this.userDropOffDate = userDropOffDate;
		this.carType = carType;
		this.licensePlate = licensePlate;
	}

	/**
	 * Instantiates a new customer.
	 *
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailAddress
	 *            the email address
	 * @param licenseNumber
	 *            the license number
	 * @param insuranceNumber
	 *            the insurance number
	 * @param creditCardNumber
	 *            the credit card number
	 * @param userPickUpDate
	 *            the user pick up date
	 * @param userDropOffDate
	 *            the user drop off date
	 * @param carType
	 *            the car type
	 * @param carMake
	 *            the car make
	 * @param carModel
	 *            the car model
	 * @param licensePlate
	 *            the license plate
	 * @param reservationNumber
	 *            the reservation number
	 * @param customerProblems
	 *            the customer problems
	 * @param customerRating
	 *            the customer rating
	 */
	public Customer(String firstName, String lastName, String emailAddress, String licenseNumber,
			String insuranceNumber, String creditCardNumber, Date userPickUpDate, Date userDropOffDate, String carType,
			String carMake, String carModel, String licensePlate, String reservationNumber, String customerProblems,
			int customerRating) {

		numberOfRecords++;
		customerDatabase.add(this);
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.licenseNumber = licenseNumber;
		this.creditCardNumber = creditCardNumber;
		this.userPickUpDate = userPickUpDate;
		this.userDropOffDate = userDropOffDate;
		this.carType = carType;
		this.carMake = carMake;
		this.insuranceNumber = insuranceNumber;
		this.carModel = carModel;
		this.licensePlate = licensePlate;
		this.reservationNumber = reservationNumber;
		this.customerProblems = customerProblems;
		this.customerRating = customerRating;
	}

	/**
	 * Instantiates a new customer.
	 *
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param emailAddress
	 *            the email address
	 * @param licenseNumber
	 *            the license number
	 * @param insuranceNumber
	 *            the insurance number
	 * @param creditCardNumber
	 *            the credit card number
	 * @param userPickUpDate
	 *            the user pick up date
	 * @param userDropOffDate
	 *            the user drop off date
	 * @param carType
	 *            the car type
	 * @param carMake
	 *            the car make
	 * @param carModel
	 *            the car model
	 * @param licensePlate
	 *            the license plate
	 * @param customerProblems
	 *            the customer problems
	 * @param customerRating
	 *            the customer rating
	 */
	public Customer(String firstName, String lastName, String emailAddress, String licenseNumber,
			String insuranceNumber, String creditCardNumber, Date userPickUpDate, Date userDropOffDate, String carType,
			String carMake, String carModel, String licensePlate, String customerProblems, int customerRating) {

		numberOfRecords++;
		customerDatabase.add(this);
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.licenseNumber = licenseNumber;
		this.creditCardNumber = creditCardNumber;
		this.userPickUpDate = userPickUpDate;
		this.userDropOffDate = userDropOffDate;
		this.carType = carType;
		this.carMake = carMake;
		this.insuranceNumber = insuranceNumber;
		this.carModel = carModel;
		this.licensePlate = licensePlate;
		setReservationNumber("INT-1" + numberOfRecords);
		this.customerProblems = customerProblems;
		this.customerRating = customerRating;
	}

	/**
	 * Daily price.
	 *
	 * @param carType
	 *            the car type
	 * @return the double
	 */
	public static double dailyPrice(String carType) {
		double price = 0;

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
		return price;
	}

	/**
	 * Total price.
	 *
	 * @param pickUp
	 *            the pick up
	 * @param dropOff
	 *            the drop off
	 * @param dailyPrice
	 *            the daily price
	 * @return the double
	 */
	public static double totalPrice(Date pickUp, Date dropOff, double dailyPrice) {
		long diff = dropOff.getTime() - pickUp.getTime();
		double days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return days * dailyPrice * 1.13;
	}

	/**
	 * Hold caculator.
	 *
	 * @param pickUp
	 *            the pick up
	 * @param dropOff
	 *            the drop off
	 * @return the int
	 */
	public static int holdCaculator(Date pickUp, Date dropOff) {
		long diff = dropOff.getTime() - pickUp.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return (int) (days * 20.00);
	}

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
	 * @param pickUp
	 *            the pick up
	 * @param dropOff
	 *            the drop off
	 * @param dailyPrice
	 *            the daily price
	 * @param numberOfScratches
	 *            the number of scratches
	 * @param insurance
	 *            the insurance
	 * @return the double
	 */
	// $10.00 per scratch
	public static double finalPriceCalculator(String carType, Date pickUp, Date dropOff, int numberOfScratches,
			int distance, boolean insurance) {

		double price = 0;
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
			return (days * price + numberOfScratches * 10.00 + distance * 0.25) * 1.13;
		} else {
			return (days * (price + 20.00) + numberOfScratches * 10.00 + distance * 0.25) * 1.13;
		}
	}

	/**
	 * Estimated price calculator.
	 *
	 * @param carType
	 *            the car type
	 * @param pickUp
	 *            the pick up
	 * @param dropOff
	 *            the drop off
	 * @param insurance
	 *            the insurance
	 * @return the double
	 */
	public static double estimatedPriceCalculator(String carType, Date pickUp, Date dropOff, boolean insurance) {

		double price = 0;
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
			return (days * price) * 1.13;
		} else {
			return (days * (price + 20.00)) * 1.13;
		}
	}

	/**
	 * Read database.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 */
	public static void readDatabase() throws IOException, ParseException {
		
		//Reader reader = Files.newBufferedReader(Paths.get("/SupremeCarRental/SupremeCarRentals/Database/reservationDatabase.csv"));
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
		InputStream is = classLoader.getResourceAsStream("reservationDatabase.csv");
		Reader reader = new InputStreamReader(is);
		
		@SuppressWarnings("resource")
		CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
		for (CSVRecord csvRecord : csvParser) {
			numberOfRecords++;
			String firstName = csvRecord.get("First Name");
			String lastName = csvRecord.get("Last Name");
			String email = csvRecord.get("Email Address");
			String driverLicense = csvRecord.get("License Number");
			String insuranceNumber = csvRecord.get("Insurance Number");
			String creditCard = csvRecord.get("Credit Card Number");
			String pickUpDate = csvRecord.get("Pick Up Date");
			String dropOffDate = csvRecord.get("Drop Off Date");
			String carSelection = csvRecord.get("Car Type");
			String carMake1 = csvRecord.get("Car Make");
			String carModel1 = csvRecord.get("Car Model");
			String licensePlate = csvRecord.get("License Plate");
			String reservationNumber = csvRecord.get("Reservation Number");
			String customerProblems = csvRecord.get("Customer Problems");
			int customerRating = Integer.parseInt(csvRecord.get("Customer Rating"));

			new Customer(firstName, lastName, email, driverLicense, insuranceNumber, creditCard, df.parse(pickUpDate),
					df.parse(dropOffDate), carSelection, carMake1, carModel1, licensePlate, reservationNumber,
					customerProblems, customerRating);
		}
	}

	/**
	 * Save to database.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void saveToDatabase() throws IOException {
		FileWriter fileWriter = null;
		CSVPrinter csvPrinter = null;
		
		fileWriter = new FileWriter("/SupremeCarRental/SupremeCarRentals/reservationDatabase.csv");
		csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(CSV_HEADER));
		for (int i = 0; i < customerDatabase.size(); i++) {
			csvPrinter.printRecord(customerDatabase.get(i).getFirstName(), customerDatabase.get(i).getLastName(),
					customerDatabase.get(i).getEmailAddress(), customerDatabase.get(i).getLicenseNumber(),
					customerDatabase.get(i).getInsuranceNumber(), customerDatabase.get(i).getCreditCardNumber(),
					df.format(customerDatabase.get(i).getUserPickUpDate()),
					df.format(customerDatabase.get(i).getUserDropOffDate()), customerDatabase.get(i).getCarType(),
					customerDatabase.get(i).getCarMake(), customerDatabase.get(i).getCarModel(),
					customerDatabase.get(i).getLicensePlate(), customerDatabase.get(i).getReservationNumber(),
					customerDatabase.get(i).getCustomerProblems(), customerDatabase.get(i).getCustomerRating());
		}
		fileWriter.flush();
		fileWriter.close();
		csvPrinter.close();
	}

	/**
	 * Search for a proper car that does not overlap with the existing dates.
	 *
	 * @param Date
	 *            pD the pick up date
	 * @param Date
	 *            dD the drop off date
	 * @param carT
	 *            the car type
	 * @return true, if successful
	 * @throws ParseException
	 *             the parse exception
	 */
	public static boolean searchForCarDate(Date pD, Date dD, String carT) throws ParseException {
		@SuppressWarnings("unused")
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Boolean check = false;

		Date userPickUpDate = pD;
		Date userDropOffDate = dD;

		ArrayList<Date> reservedPickUpDate = new ArrayList<Date>();
		ArrayList<Date> reservedDropOffDate = new ArrayList<Date>();

		for (int i = 0; i < customerDatabase.size(); i++) {
			if (carT.equals(Customer.customerDatabase.get(i).getCarType())) {
				reservedPickUpDate.add(Customer.customerDatabase.get(i).getUserPickUpDate());
				reservedDropOffDate.add(Customer.customerDatabase.get(i).getUserDropOffDate());
			}
		}

		Collections.sort(reservedPickUpDate);
		Collections.sort(reservedDropOffDate);

		for (int i = 0; i < reservedDropOffDate.size(); i++) {
			if (userPickUpDate.after(reservedDropOffDate.get(reservedDropOffDate.size() - 1))) {
				check = true;
			} else if (userPickUpDate.after(reservedDropOffDate.get(i))
					&& userDropOffDate.before(reservedPickUpDate.get(i + 1))) {
				check = true;
			}
		}
		return check;
	}

	/**
	 * Gets the daily price.
	 *
	 * @return the daily price
	 */
	public double getDailyPrice() {
		return dailyPrice;
	}

	/**
	 * Sets the daily price.
	 *
	 * @param dailyP
	 *            the new daily price
	 */
	public static void setDailyPrice(double dailyP) {
		dailyPrice = dailyP;
	}

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Sets the total price.
	 *
	 * @param totalP
	 *            the new total price
	 */
	public static void setTotalPrice(double totalP) {
		totalPrice = totalP;
	}

	/**
	 * Gets the customer problems.
	 *
	 * @return the customer problems
	 */
	public String getCustomerProblems() {
		return customerProblems;
	}

	/**
	 * Sets the customer problems.
	 *
	 * @param customerProblems
	 *            the new customer problems
	 */
	public void setCustomerProblems(String customerProblems) {
		this.customerProblems = customerProblems;
	}

	/**
	 * Gets the customer rating.
	 *
	 * @return the customer rating
	 */
	public int getCustomerRating() {
		return customerRating;
	}

	/**
	 * Sets the customer rating.
	 *
	 * @param customerRating
	 *            the new customer rating
	 */
	public void setCustomerRating(int customerRating) {
		this.customerRating = customerRating;
	}

	/**
	 * Gets the reservation number.
	 *
	 * @return the reservation number
	 */
	public String getReservationNumber() {
		return reservationNumber;
	}

	/**
	 * Sets the reservation number.
	 *
	 * @param reservationNumber
	 *            the new reservation number
	 */
	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	/**
	 * Instantiates a new customer.
	 */
	public Customer() {
	}

	/**
	 * Checks if is customer found.
	 *
	 * @return true, if is customer found
	 */
	public static boolean isCustomerFound() {
		return customerFound;
	}

	/**
	 * Sets the customer found.
	 *
	 * @param customerFou
	 *            the new customer found
	 */
	public static void setCustomerFound(boolean customerFou) {
		customerFound = customerFou;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the licenseNumber
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}

	/**
	 * @param licenseNumber
	 *            the licenseNumber to set
	 */
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * @param creditCardNumber
	 *            the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Gets the car type.
	 *
	 * @return the car type
	 */
	public String getCarType() {
		return carType;
	}

	/**
	 * Sets the car type.
	 *
	 * @param carType
	 *            the new car type
	 */
	public void setCarType(String carType) {
		this.carType = carType;
	}

	/**
	 * Gets the license plate.
	 *
	 * @return the license plate
	 */
	public String getLicensePlate() {
		return licensePlate;
	}

	/**
	 * Sets the license plate.
	 *
	 * @param licensePlate
	 *            the new license plate
	 */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	/**
	 * Checks if is car found.
	 *
	 * @return true, if is car found
	 */
	public boolean isCarFound() {
		return carFound;
	}

	/**
	 * Sets the car found.
	 *
	 * @param carF
	 *            the new car found
	 */
	public void setCarFound(boolean carF) {
		carFound = carF;
	}

	/**
	 * Gets the insurance number.
	 *
	 * @return the insurance number
	 */
	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	/**
	 * Sets the insurance number.
	 *
	 * @param insuranceNumber
	 *            the new insurance number
	 */
	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	/**
	 * Gets the car make.
	 *
	 * @return the car make
	 */
	public String getCarMake() {
		return carMake;
	}

	/**
	 * Sets the car make.
	 *
	 * @param carMake
	 *            the new car make
	 */
	public void setCarMake(String carMake) {
		this.carMake = carMake;
	}

	/**
	 * Gets the car model.
	 *
	 * @return the car model
	 */
	public String getCarModel() {
		return carModel;
	}

	/**
	 * Sets the car model.
	 *
	 * @param carModel
	 *            the new car model
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	/**
	 * @return the userPickUpDate
	 */
	public Date getUserPickUpDate() {
		return userPickUpDate;
	}

	/**
	 * @param userPickUpDate
	 *            the userPickUpDate to set
	 */
	public void setUserPickUpDate(Date userPickUpDate) {
		this.userPickUpDate = userPickUpDate;
	}
	

	/**
	 * @return the userDropOffDate
	 */
	public Date getUserDropOffDate() {
		return userDropOffDate;
	}
	

	/**
	 * @param userDropOffDate
	 *            the userDropOffDate to set
	 */
	public void setUserDropOffDate(Date userDropOffDate) {
		this.userDropOffDate = userDropOffDate;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return (firstName + "%" + lastName + "%" + emailAddress + "%" + licenseNumber + "%" + insuranceNumber + "%"
				+ creditCardNumber + "%" + df.format(userPickUpDate) + "%" + df.format(userDropOffDate) + "%" + carType
				+ "%" + carMake + "%" + carModel + "%" + licensePlate + "%" + reservationNumber + "%" + customerProblems
				+ "%" + customerRating);
	}
}
