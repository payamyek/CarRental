package payam.yektamaram.com.SupremeCarRentals;

import java.util.ArrayList;
import java.io.*;

/**
 * This class extracts the data from the newCarDatabase.csv file.
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

public class NewCarData {

	/** The car list. */
	protected static ArrayList<NewCar> carList;

	/** The lines. */
	static int lines = 0;

	/**
	 * Instantiates a new car data.
	 */
	public NewCarData() {
		carList = new ArrayList<NewCar>();
	}

	/**
	 * Adds a car object to the array list.
	 *
	 * @param c1
	 *            the c1
	 */
	public void add(NewCar c1) {
		carList.add(c1);
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
	 */
	public static void readFromCSV(String filename) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
		InputStream is = classLoader.getResourceAsStream(filename);
		Reader reader = new InputStreamReader(is);
		
		BufferedReader infile = new BufferedReader(reader);
		String line = "";
		try {
			boolean done = false;
			while (!done) {
				line = infile.readLine();
				if (line == null) {
					done = true;
				} else {
					String[] tokens = line.trim().split(",");
					String type = tokens[0].trim();
					String year = tokens[1].trim();
					String manufacturer = tokens[2].trim();
					String model = tokens[3].trim();
					String km = tokens[4].trim();
					String licensePlate = tokens[5].trim();
					String repairsNeeded = tokens[6].trim();
					String accidents = tokens[7].trim();
					String numberScratches = tokens[8].trim();
					NewCar c1 = new NewCar(type, year, manufacturer, model, km, licensePlate, repairsNeeded, accidents,
							numberScratches);
					carList.add(c1);
				}
			}
			while (infile.readLine() != null)
				lines++;
			reader.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Using a for loop the data from the CSV file is restored inside of a 2D array
	 * in order to later on be used as the rows and columns of a JTable.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>data - </b> Creates a 2D array of type Object which stores the data
	 * retrieved from the CSV file properly.
	 * <p>
	 */
	public Object[][] convert2Data() {
		Object[][] data = new Object[carList.size()][9];
		for (int i = 0; i < carList.size(); i++) {
			data[i][0] = carList.get(i).getType();
			data[i][1] = carList.get(i).getYear();
			data[i][2] = carList.get(i).getManufacturer();
			data[i][3] = carList.get(i).getModel();
			data[i][4] = carList.get(i).getKm();
			data[i][5] = carList.get(i).getLicensePlate();
			data[i][6] = carList.get(i).getRepairsNeeded();
			data[i][7] = carList.get(i).getAccidents();
			data[i][8] = carList.get(i).getNumberOfScratches(); 
		}
		return data; 
	}
}
