package payam.yektamaram.com.SupremeCarRentals;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * This class creates the basis of the Car Object.
 * 
 * <p>
 * <h2>Global Variables</h2>
 * <p>
 * <b>type - </b> Holds the car's type.
 * <p>
 * <b>manufacturer - </b> Holds the car's manufacturer.
 * <p>
 * <b>model - </b> Holds the car's model.
 * <p>
 * <b>km - </b> Holds the car's mileage.
 * <p>
 * <b>year - </b> Holds the car year.
 * <p>
 * <b>licensePlate - </b> Holds the car's license plate.
 * <p>
 * <b>repairsNeeded - </b> Holds the repairs the car needs.
 * <p>
 * <b>accident - </b> Holds the amount of accidents the car has been in.
 * <p>
 * <b>numberOfScratches - </b> Holds the amount of scratches the car has.
 * <p>
 * <b>carDatabase - </b> Holds all of the cars in this ArrayList of 'NewCar'
 * objects.
 * <p>
 * 
 * @version 1.0.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
 * @since 2018-05-22
 */

public class NewCar {

	/** The fuel level. */
	private String type, year, manufacturer, model, km, licensePlate, repairsNeeded, accidents, numberOfScratches;

	/** The car database. */
	protected static ArrayList<NewCar> carDatabase = new ArrayList<NewCar>();

	/**
	 * Instantiates a new car. Default constructor.
	 */
	public NewCar() {
	}

	/**
	 * Instantiates a new car.
	 *
	 * @param type
	 *            the type
	 * @param year
	 *            the year
	 * @param manufacturer
	 *            the manufacturer
	 * @param model
	 *            the model
	 * @param km
	 *            the km
	 * @param licensePlate
	 *            the license plate
	 * @param repairsNeeded
	 *            the repairsNeeded
	 * @param accidents
	 *            the amount of accidents
	 * @param numberOfScratches
	 *            the number of scratches
	 */
	public NewCar(String type, String year, String manufacturer, String model, String km, String licensePlate,
			String repairsNeeded, String accidents, String numberOfScratches) {
		carDatabase.add(this);
		this.setType(type);
		this.setYear(year);
		this.setManufacturer(manufacturer);
		this.setModel(model);
		this.setKm(km);
		this.setLicensePlate(licensePlate);
		this.setRepairsNeeded(repairsNeeded);
		this.setAccidents(accidents);
		this.setNumberOfScratches(numberOfScratches);
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

		fileWriter = new FileWriter("/SupremeCarRental/SupremeCarRentals/newCarDatabase.csv");
		csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

		for (int i = 0; i < 36; i++) {
			csvPrinter.printRecord(carDatabase.get(i).getType(), carDatabase.get(i).getYear(),
					carDatabase.get(i).getManufacturer(), carDatabase.get(i).getModel(), carDatabase.get(i).getKm(),
					carDatabase.get(i).getLicensePlate(), carDatabase.get(i).getRepairsNeeded(),
					carDatabase.get(i).getAccidents(), carDatabase.get(i).getNumberOfScratches());
		}
		fileWriter.flush();
		fileWriter.close();
		csvPrinter.close();
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year
	 *            the new year
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Gets the repairs needed.
	 *
	 * @return the repairs needed
	 */
	public String getRepairsNeeded() {
		return repairsNeeded;
	}

	/**
	 * Sets the repairs needed.
	 *
	 * @param repairsNeeded
	 *            the new repairs needed
	 */
	public void setRepairsNeeded(String repairsNeeded) {
		this.repairsNeeded = repairsNeeded;
	}

	/**
	 * Gets the accidents.
	 *
	 * @return the accidents
	 */
	public String getAccidents() {
		return accidents;
	}

	/**
	 * Sets the accidents.
	 *
	 * @param accidents
	 *            the new accidents
	 */
	public void setAccidents(String accidents) {
		this.accidents = accidents;
	}

	/**
	 * Gets the number of scratches.
	 *
	 * @return the number of scratches
	 */
	public String getNumberOfScratches() {
		return numberOfScratches;
	}

	/**
	 * Sets the number of scratches.
	 *
	 * @param numberOfScratches
	 *            the new number of scratches
	 */
	public void setNumberOfScratches(String numberOfScratches) {
		this.numberOfScratches = numberOfScratches;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer
	 *            the new manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 * 
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Gets the KM.
	 * 
	 * @return the km
	 */
	public String getKm() {
		return km;
	}

	/**
	 * Sets the KM.
	 * 
	 * @param km
	 *            the km to set
	 */
	public void setKm(String km) {
		this.km = km;
	}

	/**
	 * Gets the license plate.
	 * 
	 * @return the licensePlate
	 */
	public String getLicensePlate() {
		return licensePlate;
	}

	/**
	 * Sets the license plate.
	 * 
	 * @param licensePlate
	 *            the licensePlate to set
	 */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return (type + "%" + year + "%" + manufacturer + "%" + model + "%" + km + "%" + licensePlate + "%"
				+ repairsNeeded + "%" + accidents + "%" + numberOfScratches);
	}
}

