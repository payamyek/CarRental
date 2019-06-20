package payam.yektamaram.com.SupremeCarRentals;

import java.util.ArrayList;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class extracts the data from the CSV file and was newly added this week.
 * Total hours spent on program: 1
 * 
 * <p>
 * <h2>Global Variables</h2>
 * <p>
 * <b>lines - </b> Holds the number of records in the CSV file.
 * <p>
 * <b>carList - </b> Holds the data from the CSV file.
 * <p>
 * 
 * @version 1.2.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
 * @since 2018-05-22
 */

public class ReservationData {

	/** The df. */
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/** The car list. */
	protected static ArrayList<Customer> reservationList;

	/** The lines. */
	static int lines = 0;

	/**
	 * Instantiates a new car data.
	 */
	public ReservationData() {
		reservationList = new ArrayList<Customer>();
	}

	/**
	 * Adds a car object to the array list.
	 *
	 * @param c1
	 *            the c1
	 */
	public void add(Customer c1) {
		reservationList.add(c1); 
	}

	/**
	 * Read from CSV file that is chosen and stores the data that is present under a
	 * NewCar constructor.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>file - </b> Creates an instance of File that is attributed to the '.csv'
	 * file.
	 * <p>
	 * <b>reader - </b> Creates an instance of FileReader that is used inside of
	 * 'infile'.
	 * <p>
	 * <b>infile - </b> Creates an instance of BufferedReader that is used to read
	 * the inside of the '.csv' file.
	 * <p>
	 * <b>line - </b> Creates a String instance that stores each read line in the
	 * file temporarily.
	 * <p>
	 * <b>done - </b> Creates a boolean instance that is flagged as true when the
	 * program is done reading the '.csv' file.
	 * <p>
	 * <b>tokens - </b> Creates an instance of a String array that is used to all
	 * information present in line without the ',' character.
	 * <p>
	 * <b>type - </b> Creates a String instance that stores only 'type' from the
	 * file, it takes this information from the tokens array.
	 * <p>
	 * <b>year - </b> Creates a String instance that stores only 'year' from the
	 * file, it takes this information from the tokens array.
	 * <p>
	 * <b>manufacturer - </b> Creates a String instance that stores only
	 * 'manufacturer' from the file, it takes this information from the tokens
	 * array.
	 * <p>
	 * <b>model - </b> Creates a String instance that stores only 'model' from the
	 * file, it takes this information from the tokens array.
	 * <p>
	 * <b>km - </b> Creates a String instance that stores only 'km' from the file,
	 * it takes this information from the tokens array.
	 * <p>
	 * <b>licensePlate - </b> Creates a String instance that stores only
	 * 'licensePlate' from the file, it takes this information from the tokens
	 * array.
	 * <p>
	 * <b>repairsNeeded - </b> Creates a String instance that stores only
	 * 'repairsNeeded' from the file, it takes this information from the tokens
	 * array.
	 * <p>
	 * <b>accidents - </b> Creates a String instance that stores only 'accidents'
	 * from the file, it takes this information from the tokens array.
	 * <p>
	 * <b>numberScratches - </b> Creates a String instance that stores only
	 * 'numberScratches' from the file, it takes this information from the tokens
	 * array.
	 * <p>
	 * <b>c1 - </b> Creates an instance of NewCar based on the previous String
	 * instances that were taken from the file.
	 * <p>
	 * 
	 * @param filename
	 *            the filename
	 * @throws ParseException
	 */
	public static void readFromCSV(String filename) throws ParseException {
		File file = new File(filename);
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
		}
		BufferedReader infile = new BufferedReader(reader);
		String line = "";
		try {
			boolean done = false;
			infile.readLine();
			while (!done) {
				line = infile.readLine();
				if (line == null) {
					done = true;
				} else {
					String[] tokens = line.trim().split(",");
					String firstName = tokens[0].trim();
					String lastName = tokens[1].trim();
					String emailAddress = tokens[2].trim();
					String licenseNumber = tokens[3].trim();
					String insuranceNumber = tokens[4].trim();
					String creditCardNumber = tokens[5].trim();
					String pickUpDate = tokens[6].trim();
					String dropOffDate = tokens[7].trim();
					String carType = tokens[8].trim();
					String carMake = tokens[9].trim();
					String carModel = tokens[10].trim();
					String licensePlate = tokens[11].trim();
					String reservationNumber = tokens[12].trim();
					String customerProblems = tokens[13].trim();
					String customerRating = tokens[14].trim();
					Customer c1 = new Customer(firstName, lastName, emailAddress, licenseNumber, insuranceNumber,
							creditCardNumber, df.parse(pickUpDate), df.parse(dropOffDate), carType, carMake, carModel,
							licensePlate, reservationNumber, customerProblems, Integer.parseInt(customerRating));
					reservationList.add(c1);
				}
			}
			while (infile.readLine() != null)
				lines++;
			reader.close();
		} catch (IOException e) {
		}
	}

	/**
	  * Converts the data in the reservationList to Objects.
	  * 
	  * <p>
	  * <h2>Local Variables</h2>
	  * <p>
	  * <b>data - </b> Creates a 2D array of Objects that stores the reservationList's data.
	  * <p>
	  */
	public Object[][] convert2Data() {
		Object[][] data = new Object[reservationList.size()][15];
		for (int i = 0; i < reservationList.size(); i++) {
			data[i][0] = reservationList.get(i).getFirstName();
 			data[i][1] = reservationList.get(i).getLastName();
 			data[i][2] = reservationList.get(i).getEmailAddress();
 			data[i][3] = reservationList.get(i).getLicenseNumber();
			data[i][4] = reservationList.get(i).getInsuranceNumber();
			data[i][5] = reservationList.get(i).getCreditCardNumber();
			data[i][6] = reservationList.get(i).getUserPickUpDate();
			data[i][7] = reservationList.get(i).getUserDropOffDate();
			data[i][8] = reservationList.get(i).getCarType();
			data[i][9] = reservationList.get(i).getCarMake();
			data[i][10] = reservationList.get(i).getCarModel();
			data[i][11] = reservationList.get(i).getLicensePlate();
			data[i][12] = reservationList.get(i).getReservationNumber();
			data[i][13] = reservationList.get(i).getCustomerProblems();
			data[i][14] = reservationList.get(i).getCustomerRating();
		}
		return data;
	}
}