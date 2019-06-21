package payam.yektamaram.com.SupremeCarRentals;

import java.sql.*;

public class SQLite {

	/* Database Connection */
	Connection con = null;

	/* Allows for the execution of basic queries */
	Statement stmt = null;

	/* The result of the query */
	ResultSet rs = null;

	/* Precompiled SQL statement. */
	PreparedStatement preparedStmt = null;

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

	void createCustomerTable() {
		String sql;

		try {
			stmt = con.createStatement();
			sql = "CREATE TABLE customer (first_name TEXT, last_name TEXT, credit_card_number TEXT,"+ 
                    "insurance_number TEXT, driver_license VARCHAR(17), email TEXT, PRIMARY KEY (driver_license));";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
			}
			else if (table.equals("car"))
			{
				while (rs.next()) {
					System.out.print("Type: " + rs.getString("type"));
					System.out.print(" Year: " + rs.getInt("year"));
					System.out.print(" Manufacturer: " + rs.getString("manufacturer"));
					System.out.print(" Model: " + rs.getString("model"));
					System.out.print(" Odometer: " + rs.getInt("odometer"));
					System.out.print(" License Plate: " + rs.getString("licensePlate"));
					System.out.println(" Status: " + rs.getString("status"));

				}
			}
			else if (table.equals("reservation"))
			{
				while (rs.next()) {
					System.out.print("Pick-Up: " + rs.getString("pick_up"));
					System.out.print(" Drop-off: " + rs.getString("drop_off"));
					System.out.print(" License Plate: " + rs.getString("license_plate"));
					System.out.print(" Reservation Number: " + rs.getString("reservation_number"));
				}
			}
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	private void insertCustomer(String firstName, String lastName, String creditCardNumber, String insuranceNumber, String driverLicense, String email) {
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

	private void deleteCustomer(String email) {
		try {

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM customer WHERE email = \"" + email + "\";");

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
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

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

	private void createCarTable() {
		String sql;
		try {
			stmt = con.createStatement();
			sql = "CREATE TABLE car " + "(type TEXT," + "year INT," + "manufacturer TEXT," + "model TEXT,"
					+ "odometer INT," + "licensePlate TEXT," + "status TEXT);";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createReservationTable() {
		String sql;
		try {
			stmt = con.createStatement();
			sql = "CREATE TABLE reservation (pick_up TEXT, drop_off TEXT, license_plate VARCHAR(8)"+ 
	                   "REFERENCES CAR(license_plate), reservation_number TEXT)";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertReservation (String pickUp, String dropOff, String licensePlate, String reservationNumber) {
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}
	
	
	private void dropTable(String table)
	{
		String sql;
		try {
			stmt = con.createStatement();
			sql = "Drop TABLE " + table+";";
			stmt.executeUpdate(sql);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertCar(String type, int year, String manufacturer, String model, int odometer,
			String licensePlate, String status) {
		try {

			preparedStmt = con.prepareStatement("INSERT INTO car values (?, ?, ?, ?, ?, ?, ?)");
			preparedStmt.setString(1, type);
			preparedStmt.setInt(2, year);
			preparedStmt.setString(3, manufacturer);
			preparedStmt.setString(4, model);
			preparedStmt.setInt(5, odometer);
			preparedStmt.setString(6, licensePlate);
			preparedStmt.setString(7, status);

			// execute the preparedstatement
			preparedStmt.execute();

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
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				preparedStmt = null;
			}
		}
	}

	public static void main(String args[]) {
		SQLite db = new SQLite();
		db.dropTable("reservation");
		db.createReservationTable();
		db.insertReservation("2018-06-15 09:00", "2018-06-23 09:00", "AFYT 741", "INT-01");
		db.printTable("reservation");
	}
}