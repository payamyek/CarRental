package payam.yektamaram.com.SupremeCarRentals;

import java.sql.*;
import java.util.Arrays;


/**
 * The Class SQLite.
 */
public class SQLite {

	/** The con. */
	/* Database Connection */
	Connection con = null;

	/** The stmt. */
	/* Allows for the execution of basic queries */
	Statement stmt = null;

	/** The rs. */
	/* The result of the query */
	ResultSet rs = null;

	/** The prepared stmt. */
	/* Precompiled SQL statement. */
	PreparedStatement preparedStmt = null;

	/**
	 * Instantiates a new SQ lite.
	 */
	public SQLite() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:EliteCarRentals.db"); // Checks if Database exists, if not it
																					// will be created
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Sets the up databases.
	 */
	public void setUpDatabases() {
		createCustomerTable();
		createReservationTable();
		createCarTable();
	}

	/**
	 * Clean databases.
	 */
	public void cleanDatabases() {
		dropTable("car");
		dropTable("reservation");
		dropTable("customer");
	}
	
	/**
	 * Creates the car table.
	 */
	private void createCarTable() {
		String sql;

		if (!checkTableExists("car")) {

			try {

				stmt = con.createStatement();
				sql = "CREATE TABLE car (type TEXT, year INT, manufacturer TEXT, model TEXT,"
						+ "odometer INT,licensePlate TEXT, status TEXT, numberOfScratches INT, accidents TEXT);";
				stmt.executeUpdate(sql);
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore
					stmt = null;
				}
			}

			insertCar("Economy", 2019, "Toyota", "Prius", 0, "AFYT 754", "ACTIVE", 0, "None");
			insertCar("Economy", 2019, "Toyota", "Prius", 0, "EFLT 656", "ACTIVE", 0, "None");
			insertCar("Compact", 2019, "Toyota", "Yaris", 0, "LFRF 541", "ACTIVE", 0, "None");
			insertCar("Compact", 2019, "Toyota", "Yaris", 0, "PKKT 254", "ACTIVE", 0, "None");
			insertCar("Intermediate", 2019, "Hyundai", "Accent", 0, "RLYT 252", "ACTIVE", 0, "None");
			insertCar("Intermediate", 2019, "Hyundai", "Accent", 0, "LLYT 532", "ACTIVE", 0, "None");
			insertCar("Standard Size", 2019, "Chevrolet", "Cruze", 0, "IFSA 442", "ACTIVE", 0, "None");
			insertCar("Standard Size", 2019, "Chevrolet", "Cruze", 0, "ILKS 545", "ACTIVE", 0, "None");
			insertCar("Full Size", 2019, "Chevrolet", "Impala", 0, "ISSA 442", "ACTIVE", 0, "None");
			insertCar("Full Size", 2019, "Chevrolet", "Impala", 0, "IURE 325", "ACTIVE", 0, "None");
			insertCar("Mini Van/7 Seater", 2019, "Dodge", "Caravan", 0, "FFSA 442", "ACTIVE", 0, "None");
			insertCar("Mini Van/7 Seater", 2019, "Dodge", "Caravan", 0, "IRRS 115", "ACTIVE", 0, "None");
			insertCar("Premium", 2019, "Audi", "A3", 0, "FFFT 123", "ACTIVE", 0, "None");
			insertCar("Premium", 2019, "Audi", "A3", 0, "OERE 141", "ACTIVE", 0, "None");
			insertCar("Luxury", 2019, "Mercedes-Benz", "E450", 0, "ASAA 442", "ACTIVE", 0, "None");
			insertCar("Luxury", 2019, "Mercedes-Benz", "E450", 0, "ASQQ 123", "ACTIVE", 0, "None");
			insertCar("Supreme", 2019, "Rolls Royce", "Dawn", 0, "MONE 442", "ACTIVE", 0, "None");
			insertCar("Supreme", 2019, "Rolls Royce", "Dawn", 0, "PEOP 123", "ACTIVE", 0, "None");
			insertCar("Intermediate SUV", 2019, "Hyundai", "Tucson", 0, "AAAA 442", "ACTIVE", 0, "None");
			insertCar("Intermediate SUV", 2019, "Hyundai", "Tucson", 0, "QQQQ 123", "ACTIVE", 0, "None");
			insertCar("Standard SUV", 2019, "Ford", "Edge", 0, "HWUO 252", "ACTIVE", 0, "None");
			insertCar("Standard SUV", 2019, "Ford", "Edge", 0, "KJWW 213", "ACTIVE", 0, "None");
			insertCar("Large SUV", 2019, "GMC", "Yukon", 0, "ADDA 123", "ACTIVE", 0, "None");
			insertCar("Large SUV", 2019, "GMC", "Yukon", 0, "KJKH 123", "ACTIVE", 0, "None");
		}
	}
	
	/**
	 * Creates the customer table.
	 */
	void createCustomerTable() {
		String sql;

		try {
			stmt = con.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS customer (first_name TEXT, last_name TEXT, credit_card_number TEXT,"
					+ "insurance_number TEXT, driver_license VARCHAR(17), email TEXT, reservation_number TEXT , PRIMARY KEY (driver_license));";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	/**
	 * Creates the reservation table.
	 */
	private void createReservationTable() {
		String sql;
		try {
			stmt = con.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS reservation (pick_up TEXT, drop_off TEXT, license_plate VARCHAR(8)"
					+ "REFERENCES CAR(license_plate), reservation_number TEXT)";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}
	
	/**
	 * Check table exists.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	private boolean checkTableExists(String name) {

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=\"" + name + "\"");
			if (rs.next() != false) // Result set is not empty
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}

		return false;
	}

	/**
	 * Prints the table.
	 *
	 * @param table the table
	 */
	void printTable(String table) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM \"" + table + "\" ;");

			if (table.equals("customer")) {
				while (rs.next()) {
					System.out.print("First Name: " + rs.getString("first_name"));
					System.out.print(" Last Name: " + rs.getString("last_name"));
					System.out.print(" Credit Card: " + rs.getString("credit_card_number"));
					System.out.print(" Insurance: " + rs.getString("insurance_number"));
					System.out.print(" Driver License: " + rs.getString("driver_license"));
					System.out.println(" Email: " + rs.getString("email"));
				}
			} else if (table.equals("car")) {
				while (rs.next()) {
					System.out.print("Type: " + rs.getString("type"));
					System.out.print(" Year: " + rs.getInt("year"));
					System.out.print(" Manufacturer: " + rs.getString("manufacturer"));
					System.out.print(" Model: " + rs.getString("model"));
					System.out.print(" Odometer: " + rs.getInt("odometer"));
					System.out.print(" License Plate: " + rs.getString("licensePlate"));
					System.out.print(" Status: " + rs.getString("status"));
					System.out.print(" Scratches: " + rs.getInt("numberOfScratches"));
					System.out.println(" Accidents: " + rs.getString("accidents"));

				}
			} else if (table.equals("reservation")) {
				while (rs.next()) {
					System.out.print("Pick-Up: " + rs.getString("pick_up"));
					System.out.print(" Drop-off: " + rs.getString("drop_off"));
					System.out.print(" License Plate: " + rs.getString("license_plate"));
					System.out.println(" Reservation Number: " + rs.getString("reservation_number"));
				}
			}
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	/**
	 * Check if customer exists.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	private boolean checkIfCustomerExists(String email) {
		boolean userExists = false;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customer WHERE email = \"" + email + "\"");

			if (rs.next() != false) // Result set is not empty
			{
				userExists = true;
			}

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}

		return userExists;
	}

	/**
	 * Reservation number.
	 *
	 * @return the string
	 */
	/*
	 * Counts the number of rows in the reservation table and returns a valid
	 * reservation number
	 */
	String reservationNumber() {

		String reservationNumber = "";
		int identifier;

		try {

			stmt = con.createStatement();
			rs = stmt.executeQuery(
					"SELECT reservation_number FROM reservation ORDER BY reservation_number DESC LIMIT 1;");

			if (rs.next()) {
				reservationNumber = rs.getString("reservation_number");
				identifier = Integer.parseInt(reservationNumber.substring((reservationNumber.indexOf("-") + 1)));
				stmt.close();
				rs.close();
				reservationNumber = "INT-" + (identifier + 1);
			} else if (!rs.next()) // Reservation table is empty
			{
				stmt.close();
				rs.close();
				reservationNumber = "INT-1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
		return reservationNumber;
	}

	/**
	 * Insert customer.
	 *
	 * @param customerData the customer data
	 */
	public void insertCustomer(String customerData[]) {

		try {
			preparedStmt = con.prepareStatement("INSERT INTO customer values (?, ?, ?, ?, ?, ?, ?)");
			preparedStmt.setString(1, customerData[CustomerConstants.FNAME.getValue()]);
			preparedStmt.setString(2, customerData[CustomerConstants.LNAME.getValue()]);
			preparedStmt.setString(3, customerData[CustomerConstants.CREDIT.getValue()]);
			preparedStmt.setString(4, customerData[CustomerConstants.INSURANCE.getValue()]);
			preparedStmt.setString(5, customerData[CustomerConstants.LICENSE.getValue()]);
			preparedStmt.setString(6, customerData[CustomerConstants.EMAIL.getValue()]);
			preparedStmt.setString(7, customerData[CustomerConstants.RESERVATION_NUMBER.getValue()]);
			// execute the preparedstatement
			preparedStmt.execute();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

	/**
	 * Insert customer.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param creditCardNumber the credit card number
	 * @param insuranceNumber the insurance number
	 * @param driverLicense the driver license
	 * @param email the email
	 */
	private void insertCustomer(String firstName, String lastName, String creditCardNumber, String insuranceNumber,
			String driverLicense, String email) {
		try {
			// pass.setPassword("secretshh");
			preparedStmt = con.prepareStatement("INSERT INTO customer values (?, ?, ?, ?, ?, ?)");
			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, lastName);
			preparedStmt.setString(3, creditCardNumber);
			preparedStmt.setString(4, insuranceNumber);
			preparedStmt.setString(5, driverLicense);
			preparedStmt.setString(6, email);
			// execute the preparedstatement
			preparedStmt.execute();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

	/**
	 * Delete customer.
	 *
	 * @param email the email
	 */
	private void deleteCustomer(String email) {
		try {

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM customer WHERE email = \"" + email + "\";");

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	/**
	 * Gets the car types.
	 *
	 * @param carType the car type
	 * @return the car types
	 */
	protected String[] getCarTypes(String carType) {
		String cars[] = new String[2];
		int index = 0;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM car WHERE type = '" + carType + "';");

			while (rs.next()) {
				String data = rs.getInt("year") + " - " + rs.getString("manufacturer") + " - " + rs.getString("model")
						+ " - " + rs.getString("licensePlate");
				// System.out.println(data);
				cars[index] = data;
				index++;
			}

			stmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
		return cars;
	}

	/**
	 * Validate login.
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if successful
	 */
	private boolean validateLogin(String email, String password) {

		boolean check = false;
		// pass.setPassword("secretshh");

		try {

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customer WHERE email = \"" + email + "\"");

			if (rs.next() != false) {
				if (rs.getString("password").equals(password)) {
					check = true;
				}
			}

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}

		return check;
	}

	

	/**
	 * Reservation search.
	 *
	 * @param reservationNumber the reservation number
	 * @return true, if successful
	 */
	public boolean reservationSearch(String reservationNumber) {

		boolean exists = false;

		try {
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT * FROM reservation WHERE reservation_number = \"" + reservationNumber + "\"");

			if (rs.next() != false) // Result set is not empty
			{
				exists = true;
			}

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}

		return exists;
	}


	/**
	 * Insert reservation.
	 *
	 * @param pickUp the pick up
	 * @param dropOff the drop off
	 * @param licensePlate the license plate
	 * @param reservationNumber the reservation number
	 */
	void insertReservation(String pickUp, String dropOff, String licensePlate, String reservationNumber) {
		try {
			preparedStmt = con.prepareStatement("INSERT INTO reservation values (?, ?, ?, ?)");
			preparedStmt.setString(1, pickUp);
			preparedStmt.setString(2, dropOff);
			preparedStmt.setString(3, licensePlate);
			preparedStmt.setString(4, reservationNumber);

			// execute the preparedstatement
			preparedStmt.execute();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

	/**
	 * Drop table.
	 *
	 * @param table the table
	 */
	private void dropTable(String table) {
		String sql;
		try {
			stmt = con.createStatement();
			sql = "Drop TABLE IF EXISTS " + table + ";";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	/**
	 * Insert car.
	 *
	 * @param type the type
	 * @param year the year
	 * @param manufacturer the manufacturer
	 * @param model the model
	 * @param odometer the odometer
	 * @param licensePlate the license plate
	 * @param status the status
	 * @param numberOfScratches the number of scratches
	 * @param accidents the accidents
	 */
	private void insertCar(String type, int year, String manufacturer, String model, int odometer, String licensePlate,
			String status, int numberOfScratches, String accidents) {
		try {

			preparedStmt = con.prepareStatement("INSERT INTO car values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStmt.setString(1, type);
			preparedStmt.setInt(2, year);
			preparedStmt.setString(3, manufacturer);
			preparedStmt.setString(4, model);
			preparedStmt.setInt(5, odometer);
			preparedStmt.setString(6, licensePlate);
			preparedStmt.setString(7, status);
			preparedStmt.setInt(8, numberOfScratches);
			preparedStmt.setString(9, accidents);

			// execute the preparedstatement
			preparedStmt.execute();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

	/**
	 * Gets the car.
	 *
	 * @param licensePlate the license plate
	 * @return the car
	 */
	public String[] getCar(String licensePlate) {
		String carInfo[] = new String[9];

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM car WHERE licensePlate = \"" + licensePlate + "\";");

			if (rs.next()) {
				carInfo[0] = rs.getString("type");
				carInfo[1] = String.valueOf(rs.getInt("year"));
				carInfo[2] = rs.getString("manufacturer");
				carInfo[3] = rs.getString("model");
				carInfo[4] = String.valueOf(rs.getInt("odometer"));
				carInfo[5] = rs.getString("licensePlate");
				carInfo[6] = rs.getString("status");
				carInfo[7] = String.valueOf(rs.getInt("numberOfScratches"));
				carInfo[8] = rs.getString("accidents");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
		return carInfo;
	}

	
	/**
	 * Gets the reservation.
	 *
	 * @param reservationNumber the reservation number
	 * @return the reservation
	 */
	public String[] getReservation(String reservationNumber) {
		String reservationData[] = new String[4];

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM reservation WHERE reservation_number = \"" + reservationNumber + "\";");

			if (rs.next()) {
				reservationData[ReservationConstants.PICKUP.getValue()] = rs.getString("pick_up");
				reservationData[ReservationConstants.DROPOFF.getValue()] = rs.getString("drop_off");
				reservationData[ReservationConstants.LICENSEPLATE.getValue()] = rs.getString("license_plate");
				reservationData[ReservationConstants.RESERVATION_NUMBER.getValue()] = rs.getString("reservation_number");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
		return reservationData;
	}

	/**
	 * Gets the reservation license plate.
	 *
	 * @param reservationNumber the reservation number
	 * @return the reservation license plate
	 */
	public String getReservationLicensePlate(String reservationNumber) {
		String licensePlate = "";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(
					"SELECT * FROM reservation WHERE reservation_number = \"" + reservationNumber + "\";");

			if (rs.next()) {
				licensePlate = rs.getString("license_plate");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
		}
		return licensePlate;
	}

	/**
	 * Update car data.
	 *
	 * @param licensePlate the license plate
	 * @param numberOfScratches the number of scratches
	 * @param accidents the accidents
	 * @param odometer the odometer
	 */
	public void updateCarData(String licensePlate, int numberOfScratches, String accidents, int odometer) {
		try {

			preparedStmt = con.prepareStatement(
					"UPDATE car SET numberOfScratches = ? , accidents = ? , odometer = ? WHERE licensePlate = ? ");
			preparedStmt.setInt(1, numberOfScratches);
			preparedStmt.setString(2, accidents);
			preparedStmt.setInt(3, odometer);
			preparedStmt.setString(4, licensePlate);

			// execute the preparedstatement
			preparedStmt.execute();

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

	public boolean customerCoverage(String reservationNumber)
	{
		boolean coverage = true;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(
					"SELECT * FROM customer WHERE reservation_number = \"" + reservationNumber + "\";");

			if (rs.next()) {
				if (rs.getString("insurance").equals(""))
				{
					coverage = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
		}
		return coverage;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SQLite db = new SQLite();
		//db.printTable("customer");
		db.printTable("reservation");
		//db.cleanDatabases();
		//db.setUpDatabases();
		// db.printTable("car");
		//db.insertReservation("Monday", "Tuesday", "AFYT 754", "INT-1");
	}

}