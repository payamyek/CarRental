package payam.yektamaram.com.SupremeCarRentals;

import java.sql.*;
import java.util.Arrays;

/**
 * The Class SQLite.
 */
public class SQLite {

	/**
	 *  Database Connection 
	 */
	static Connection con = null;

	/**
	 *  Allows for the execution of basic queries 
	 */
	static Statement stmt = null;

	/**
	 * The result of the query 
	 */
	static ResultSet rs = null;

	/** 
	 * Precompiled SQL statement. 
	 */
	PreparedStatement preparedStmt = null;

	/**
	 * Instantiates a new SQLite database.
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
	 * Sets up the databases.
	 */
	public void setUpDatabases() {
		createCustomerTable();
		createReservationTable();
		createCarTable();
	}

	/**
	 * Drops all the database tables
	 */
	public void cleanDatabases() {
		dropTable("car");
		dropTable("reservation");
		dropTable("customer");
	}

	static Object[][] getCarTableData() {
		Object[][] carTable = new Object[24][8];
		int x = 0;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM car ;");

			while (rs.next()) {
				carTable[x][0] = rs.getString("type");
				carTable[x][1] = String.valueOf(rs.getInt("year"));
				carTable[x][2] = rs.getString("manufacturer");
				carTable[x][3] = rs.getString("model");
				carTable[x][4] = String.valueOf(rs.getInt("odometer"));
				carTable[x][5] = rs.getString("licensePlate");
				carTable[x][6] = String.valueOf(rs.getInt("fuelCapacity"));
				carTable[x][7] = String.valueOf(rs.getInt("fuelLevel"));
				x++;

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
		return carTable;
	}

	/**
	 * Gets the reservation table data in the form of an array
	 *
	 * @return the reservation table data
	 */
	static Object[][] getReservationTableData() {

		int numOfRecords = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) from reservation");
			if (rs.next() != false) // Result set is not empty
			{
				numOfRecords = rs.getInt(1);
			}
		} catch (SQLException e) {
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
		
		if (numOfRecords == 0)
		{
			Object[][] temp = {{"No Reservations", "","",""}};
			return temp;
		}
		
		//System.out.println(numOfRecords);
		Object[][] reservationTable = new Object[numOfRecords][4];
		int x = 0;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM reservation ;");

			while (rs.next()) {
				reservationTable[x][0] = rs.getString("pickUp");
				reservationTable[x][1] = rs.getString("dropOff");
				reservationTable[x][2] = rs.getString("licensePlate");
				reservationTable[x][3] = rs.getString("reservationNumber");
				x++;
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
		return reservationTable;
	}
	
	
	/**
	 * Gets the customer table data in the form of an array
	 *
	 * @return the customer table data
	 */
	static Object[][] getCustomerTableData() {

		int numOfRecords = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) from customer");
			if (rs.next() != false) // Result set is not empty
			{
				numOfRecords = rs.getInt(1);
			}
		} catch (SQLException e) {
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
		
		if (numOfRecords == 0)
		{
			Object[][] temp = {{"No Customers", "","",""}};
			return temp;
		}
		
		//System.out.println(numOfRecords);
		Object[][] reservationTable = new Object[numOfRecords][7];
		int x = 0;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customer;");
			
			while (rs.next()) {
				reservationTable[x][0] = rs.getString("firstName");
				reservationTable[x][1] = rs.getString("lastName");
				reservationTable[x][2] = rs.getString("creditCardNumber");
				reservationTable[x][3] = rs.getString("insuranceNumber");
				reservationTable[x][4] = rs.getString("driverLicense");
				reservationTable[x][5] = rs.getString("email");
				reservationTable[x][6] = rs.getString("reservationNumber");
				x++;
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
		return reservationTable;
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
						+ "odometer INT, licensePlate TEXT, fuelCapacity INT, fuelLevel INT);";
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

			insertCar("Economy", 2019, "Toyota", "Prius", 0, "AFYT 754", 40, 40);
			insertCar("Economy", 2019, "Toyota", "Prius", 0, "EFLT 656", 40, 40);
			insertCar("Compact", 2019, "Toyota", "Yaris", 0, "LFRF 541", 44, 44);
			insertCar("Compact", 2019, "Toyota", "Yaris", 0, "PKKT 254", 44, 44);
			insertCar("Intermediate", 2019, "Hyundai", "Accent", 0, "RLYT 252", 43, 43);
			insertCar("Intermediate", 2019, "Hyundai", "Accent", 0, "LLYT 532", 43, 43);
			insertCar("Standard Size", 2019, "Chevrolet", "Cruze", 0, "IFSA 442", 52, 52);
			insertCar("Standard Size", 2019, "Chevrolet", "Cruze", 0, "ILKS 545", 52, 52);
			insertCar("Full Size", 2019, "Chevrolet", "Impala", 0, "ISSA 442", 70, 70);
			insertCar("Full Size", 2019, "Chevrolet", "Impala", 0, "IURE 325", 70, 70);
			insertCar("Mini Van/7 Seater", 2019, "Dodge", "Caravan", 0, "FFSA 442", 75, 75);
			insertCar("Mini Van/7 Seater", 2019, "Dodge", "Caravan", 0, "IRRS 115", 75, 75);
			insertCar("Premium", 2019, "Audi", "A3", 0, "FFFT 123", 55, 55);
			insertCar("Premium", 2019, "Audi", "A3", 0, "OERE 141", 55, 55);
			insertCar("Luxury", 2019, "Mercedes-Benz", "E450", 0, "ASAA 442", 80, 80);
			insertCar("Luxury", 2019, "Mercedes-Benz", "E450", 0, "ASQQ 123", 80, 80);
			insertCar("Supreme", 2019, "Rolls Royce", "Dawn", 0, "MONE 442", 82, 82);
			insertCar("Supreme", 2019, "Rolls Royce", "Dawn", 0, "PEOP 123", 82, 82);
			insertCar("Intermediate SUV", 2019, "Hyundai", "Tucson", 0, "AAAA 442", 62, 62);
			insertCar("Intermediate SUV", 2019, "Hyundai", "Tucson", 0, "QQQQ 123", 62, 62);
			insertCar("Standard SUV", 2019, "Ford", "Edge", 0, "HWUO 252", 70, 70);
			insertCar("Standard SUV", 2019, "Ford", "Edge", 0, "KJWW 213", 70, 70);
			insertCar("Large SUV", 2019, "GMC", "Yukon", 0, "ADDA 123", 98, 98);
			insertCar("Large SUV", 2019, "GMC", "Yukon", 0, "KJKH 123", 98, 98);
		}
	}

	/**
	 * Fill up car fuel to maximum
	 *
	 * @param licensePlate the license plate
	 * @param fuelCapacity the fuel capacity
	 */
	void fillUpFuel(String licensePlate, int fuelCapacity) {
		try {

			preparedStmt = con.prepareStatement("UPDATE car SET fuelLevel = ? WHERE licensePlate = ? ");
			preparedStmt.setInt(1, fuelCapacity);
			preparedStmt.setString(2, licensePlate);

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
	 * Creates the customer table.
	 */
	void createCustomerTable() {
		String sql;

		try {
			stmt = con.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS customer (firstName TEXT, lastName TEXT, creditCardNumber TEXT,"
					+ "insuranceNumber TEXT, driverLicense VARCHAR(17), email TEXT, reservationNumber TEXT , PRIMARY KEY (driverLicense));";
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
			sql = "CREATE TABLE IF NOT EXISTS reservation (pickUp TEXT, dropOff TEXT, licensePlate VARCHAR(8)"
					+ "REFERENCES CAR(licensePlate), reservationNumber TEXT)";
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
	 * Check if table exists.
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
					System.out.print("First Name: " + rs.getString("firstName"));
					System.out.print(" Last Name: " + rs.getString("lastName"));
					System.out.print(" Credit Card: " + rs.getString("creditCardNumber"));
					System.out.print(" Insurance: " + rs.getString("insuranceNumber"));
					System.out.print(" Driver License: " + rs.getString("driverLicense"));
					System.out.print(" Email: " + rs.getString("email"));
					System.out.println(" Reservation Number: " + rs.getString("reservationNumber"));
				}
			} else if (table.equals("car")) {
				while (rs.next()) {
					System.out.print("Type: " + rs.getString("type"));
					System.out.print(" Year: " + rs.getInt("year"));
					System.out.print(" Manufacturer: " + rs.getString("manufacturer"));
					System.out.print(" Model: " + rs.getString("model"));
					System.out.print(" Odometer: " + rs.getInt("odometer"));
					System.out.print(" License Plate: " + rs.getString("licensePlate"));
					System.out.print(" Fuel Capacity: " + rs.getInt("fuelCapacity"));
					System.out.println(" Fuel Level: " + rs.getInt("fuelLevel"));

				}
			} else if (table.equals("reservation")) {
				while (rs.next()) {
					System.out.print("Pick-Up: " + rs.getString("pickUp"));
					System.out.print(" Drop-off: " + rs.getString("dropOff"));
					System.out.print(" License Plate: " + rs.getString("licensePlate"));
					System.out.println(" Reservation Number: " + rs.getString("reservationNumber"));
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
	 * Counts the number of rows in the reservation table and returns a valid
	 * reservation number
	 *
	 * @return the reservation number
	 */
	String reservationNumber() {

		String reservationNumber = "";
		int identifier;

		try {

			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT reservationNumber FROM reservation ORDER BY reservationNumber DESC LIMIT 1;");

			if (rs.next()) {
				reservationNumber = rs.getString("reservationNumber");
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
	 * Checks if customer had personal insurance coverage.
	 *
	 * @param reservationNumber the reservation number
	 * @return true, if successful
	 */
	boolean personalCoverage(String reservationNumber) {

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customer WHERE reservationNumber = '" + reservationNumber + "';");

			if (rs.next()) {
				if (rs.getString("reservationNumber").equals("")) {
					return false;
				}
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
		return false;
	}

	/**
	 * Inserts customer into customer table
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
	 * Deletes customer from customer table
	 *
	 * @param email the email
	 */
	protected void deleteReservation(String reservationNumber) {
		try {

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM reservation WHERE reservationNumber = \"" + reservationNumber + "\";");

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
	 * Gets the car types
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
	 * Checks if a reservation exists
	 *
	 * @param reservationNumber the reservation number
	 * @return true, if successful
	 */
	public boolean reservationSearch(String reservationNumber) {

		boolean exists = false;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM reservation WHERE reservationNumber = \"" + reservationNumber + "\"");

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
	 * Insert reservation into reservation table
	 *
	 * @param pickUp            the pick up
	 * @param dropOff           the drop off
	 * @param licensePlate      the license plate
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
	 * @param type              the type
	 * @param year              the year
	 * @param manufacturer      the manufacturer
	 * @param model             the model
	 * @param odometer          the odometer
	 * @param licensePlate      the license plate
	 * @param fuelCapacity the fuel capacity
	 * @param fuelLevel the fuel level
	 */
	private void insertCar(String type, int year, String manufacturer, String model, int odometer, String licensePlate,
			int fuelCapacity, int fuelLevel) {
		try {

			preparedStmt = con.prepareStatement("INSERT INTO car values (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStmt.setString(1, type);
			preparedStmt.setInt(2, year);
			preparedStmt.setString(3, manufacturer);
			preparedStmt.setString(4, model);
			preparedStmt.setInt(5, odometer);
			preparedStmt.setString(6, licensePlate);
			preparedStmt.setInt(7, fuelCapacity);
			preparedStmt.setInt(8, fuelLevel);

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
	 * Gets the car data 
	 *
	 * @param licensePlate the license plate
	 * @return the car
	 */
	public String[] getCar(String licensePlate) {
		String carInfo[] = new String[8];

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
				carInfo[6] = String.valueOf(rs.getInt("fuelCapacity"));
				carInfo[7] = String.valueOf(rs.getInt("fuelLevel"));
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
	 * Gets the customer data
	 *
	 * @param licensePlate the license plate
	 * @return the customer
	 */
	public String[] getCustomer(String reservationNumber) {
		String customerInfo[] = new String[7];

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customer WHERE reservationNumber = \"" + reservationNumber + "\";");

			if (rs.next()) {
				customerInfo[0] = rs.getString("firstName");
				customerInfo[1] = rs.getString("lastName");
				customerInfo[2] = rs.getString("creditCardNnumber");
				customerInfo[3] = rs.getString("insuranceNumber");
				customerInfo[4] = rs.getString("driverLicense");
				customerInfo[5] = rs.getString("email");
				customerInfo[6] = rs.getString("reservationNumber");
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
		return customerInfo;
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
			rs = stmt
					.executeQuery("SELECT * FROM reservation WHERE reservationNumber = \"" + reservationNumber + "\";");

			if (rs.next()) {
				reservationData[ReservationConstants.PICKUP.getValue()] = rs.getString("pickUp");
				reservationData[ReservationConstants.DROPOFF.getValue()] = rs.getString("dropOff");
				reservationData[ReservationConstants.LICENSEPLATE.getValue()] = rs.getString("licensePlate");
				reservationData[ReservationConstants.RESERVATION_NUMBER.getValue()] = rs.getString("reservationNumber");
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
			rs = stmt
					.executeQuery("SELECT * FROM reservation WHERE reservationNumber = \"" + reservationNumber + "\";");

			if (rs.next()) {
				licensePlate = rs.getString("licensePlate");
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
	 * @param licensePlate      the license plate
	 * @param numberOfScratches the number of scratches
	 * @param accidents         the accidents
	 * @param odometer          the odometer
	 */
	public void updateCarData(String licensePlate, int odometer, int fuelLevel) {
		try {

			preparedStmt = con.prepareStatement("UPDATE car SET odometer = ? , fuelLevel = ? WHERE licensePlate = ? ");
			preparedStmt.setInt(1, odometer);
			preparedStmt.setInt(2, fuelLevel);
			preparedStmt.setString(3, licensePlate);

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
	 * Checks if customer has insurance coverage.
	 *
	 * @param reservationNumber the reservation number
	 * @return true, if successful
	 */
	public boolean customerCoverage(String reservationNumber) {
		boolean coverage = true;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customer WHERE reservationNumber = \"" + reservationNumber + "\";");

			if (rs.next()) {
				if (rs.getString("insuranceNumber").equals("")) {
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
}