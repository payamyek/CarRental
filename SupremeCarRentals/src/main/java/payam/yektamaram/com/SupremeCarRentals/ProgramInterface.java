package payam.yektamaram.com.SupremeCarRentals;

import javax.swing.border.EmptyBorder;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.Color;
import javax.swing.*;
import java.awt.Font;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * This class holds the User Interface of the Program.
 * 
 * <h2>Global Variables</h2>
 * <p>
 * <b>db - </b> An instance of the SQLite class that enables connection to the database
 * <p>
 * <b>dropOffReservationData - </b> Array that holds information regarding reservation that is being returned
 * <p>
 * <b>dropOffCarInfo - </b> Array that holds information regarding car that is being returned
 * <p>
 * <b>distanceTravelled - </b> Holds how far the customer has driven in kilometers upon return
 * <p>
 * <b>df - </b> An instance of the SimpleDateFormat class that specifies the date format
 * <p>
 * <b>timestamp - </b> Current data and time using df format
 * <p>
 * <b>formatter - </b> An instance of the NumberFormat class which is used for price formatting using local currency
 * <p>
 * <b>passwordField - </b> An instance of the JPasswordField class which is entered by user on login screen
 * <p>
 * <b>userField - </b> An instance of the JTextField used for employee's user name on login screen
 * <p>
 * <b>LOGIN_KEY - </b> Username that is valid to gain entry to application
 * <p>
 * <b>PASSWORD_KEY - </b> Password that is valid to gain entry to application
 * <p>
 * <b>pickUpDatePicker - </b> An instance of the JDatePickerImpl Class that holds
 * the pick up date of a rental vehicle.
 * <p>
 * <b>dropOffDatePicker- </b> An instance of the JDatePickerImpl Class that holds
 * the drop off date of a rental vehicle.
 * <p>
 * <p>
 * <b>pickUpSelectedDate - </b> Holds date obtained from pickUpDatePicker
 * <p>
 * <b>dropOffSelectedDate - </b> Holds date obtained from dropOffDatePicker
 * <p>
 * <b>userPickUpDate - </b> Properly formated date of pickUpSelectedDate using df
 * <p>
 * <b>userDropOffDate - </b> Properly formated date of dropOffSelectedDate using using df
 * <p>
 * <b>customerData - </b> String array that holds information regarding customer that is currently renting a car
 * <p>
 * <b>searchReservationNumber - </b> Reservation number of car that is being returned
 * <p>
 * <b>dropOffLicensePlate - </b> License plate of car that is being returned
 * <p>
 * <b>availableCarsResult - </b> An instance of JComboBox that shows available cars given renters conditions
 * <p>
 * <b>dropOffFuelDifference - </b> Amount of fuel in liters that have been used by driver and not refilled
 * <p>
 * <b>gasPrice - </b> Price per liter to be charged if car is not returned with full gas
 * <p>
 * <b>carInfo - </b> Array that holds information regarding car that is being rented
 * <p>
 * 
 * @version 2.4.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
 * @since 2018-05-07
 */

@SuppressWarnings("serial")
public class ProgramInterface extends JPanel implements ActionListener {

	private SQLite db = new SQLite(); 

	private String dropOffReservationData[];

	private String dropOffCarInfo[];

	private int distanceTravelled;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    String timeStamp = df.format(Calendar.getInstance().getTime()); 

	NumberFormat formatter = NumberFormat.getCurrencyInstance();

	private JPasswordField passwordField; 

	private JTextField userField; 

	private final static String LOGIN_KEY = "Admin", PASSWORD_KEY = "password123";

	private JDatePickerImpl pickUpDatePicker, dropOffDatePicker;

	private String customerData[] = new String[13];

	private Date pickUpSelectedDate, dropOffSelectedDate, userPickUpDate, userDropOffDate;

	private String searchReservationNumber;

	private String dropOffLicensePlate;

	static JComboBox availableCarsResult = new JComboBox();

	private int dropOffFuelDifference;

	final double gasPrice = 1.29;

	private String carInfo[] = new String[8];


	/**
	 * Calls login menu and sets JPanel layout to null.
	 */
	public ProgramInterface() {
		setLayout(null);
		loginMenu();
		//db.cleanDatabases();
		//db.setUpDatabases();
	}

	/**
	 * Refreshes and repaints JPanel
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void refresh() {
		removeAll();
		revalidate();
		repaint();
	}

	/**
	 * Controls Program Flow
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Login")) {
			if (LOGIN_KEY.equals(userField.getText())
					&& PASSWORD_KEY.equals(String.valueOf(passwordField.getPassword()))) {
				mainMenu();
			} else {
				JOptionPane.showMessageDialog(null, "Username and Password Combination Doesn't Exist",
						"Login Unsuccessful", JOptionPane.WARNING_MESSAGE);
				loginMenu();
			}
		} else if (ae.getActionCommand().equals("Rent Car")) {
			try {
				rentCar();
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getActionCommand().equals("Car Database")) {
			NewCarJTable myApp = new NewCarJTable();
			myApp.setVisible(true);
		} else if (ae.getActionCommand().equals("Reservation Database")) {
			try {
				ReservationJTable myApp = new ReservationJTable();
				myApp.setVisible(true);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getActionCommand().equals("About")) {
			JOptionPane.showMessageDialog(null,
					"Toronto Supreme Car Rentals Program Designed and Developed By Payam, Daniel, and Ian.", "About",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (ae.getActionCommand().equals("Help")) {
			JOptionPane.showMessageDialog(null, "Refer To Provided Instructions Manual For Support", "Help",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (ae.getActionCommand().equals("Customer Database")) {
			try {
				InfoJTable myApp = new InfoJTable();
				myApp.setVisible(true);
			} catch (ParseException e) {
			}
		}
	}

	/**
	 * Login Menu where user is expected to enter a valid username and password
	 * combination in order to gain access to the program.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>passwordField_1 - </b> Password Field which is entered by user.
	 * <p>
	 * <b>panel - </b>Creates an instance of JPanel which is coloured blue.
	 * <p>
	 * <b>logoImage - </b> Company Logo Image
	 * <p>
	 * <b>lblUsername - </b> JLabel asking for username.
	 * <p>
	 * <b>lblPassword - </b> JLabel asking for password.
	 * <p>
	 * <b>btnLogin - </b> JButton that allows user to gain access to program.
	 * <p>
	 */
	public void loginMenu() {
		refresh();
		setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel = new JPanel();
		panel.setBounds(0, -38, 320, 650);
		panel.setBackground(new Color(51, 153, 255));
		panel.setLayout(null);
		add(panel);

		JLabel logoImage = new JLabel("");
		logoImage.setBounds(-35, 100, 493, 251);
		panel.add(logoImage);

		Icon img = new ImageIcon(getClass().getClassLoader().getResource("CompanyLogo.png"));
		logoImage.setIcon(img);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Segoe UI Semilight", Font.BOLD, 24));
		lblUsername.setBounds(372, 58, 136, 45);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI Semilight", Font.BOLD, 24));

		lblPassword.setBounds(372, 181, 136, 45);
		add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 17));
		passwordField.setBounds(372, 239, 187, 33);
		passwordField.setText("");
		add(passwordField);

		userField = new JTextField();
		userField.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
		userField.setBounds(372, 118, 187, 33);
		userField.setText("");
		add(userField);

		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.RED);
		btnLogin.addActionListener(this);

		btnLogin.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 20));
		btnLogin.setBounds(383, 325, 155, 35);
		add(btnLogin);
	}

	/**
	 * Program main menu where user can either rent car, return car, or exit
	 * program.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>programTitle - </b> JLabel displaying company name
	 * <p>
	 * <b>rentCarButton - </b>JButton that provides access to renting cars.
	 * <p>
	 * <b>pickUpCarButton - </b>JButton that provides access to picking up rental
	 * cars.
	 * <p>
	 * <b>dropOffCarButton - </b>JButton that provides access to dropping-off rental
	 * cars.
	 * <p>
	 * <b>exitButton - </b> JButton that provides access to exiting program.
	 * <p>
	 * <b>menuBar - </b> Instance of JMenuBar.
	 * <p>
	 * <b>mnNewMenu - </b>Instance of JMenu with option 'View'.
	 * <p>
	 * <b>mntmCustomerDatabase - </b> Instance of JMenuItem with the option to view
	 * customer database.
	 * <p>
	 * <b>mntmFleetDatabase - </b> Instance of JMenuItem with the option to view
	 * fleet database.
	 * <p>
	 * <b>mntmReservationDatabase - </b> Instance of JMenuItem with the option to
	 * view reservation database.
	 * <p>
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	public void mainMenu() {
		refresh();

		setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel logoImage = new JLabel("");
		logoImage.setBounds(120, -40, 493, 251);
		add(logoImage);

		Icon img = new ImageIcon(getClass().getClassLoader().getResource("CompanyLogo.png"));
		logoImage.setIcon(img);

		JButton rentCarButton = new JButton("Rent Car");
		rentCarButton.setForeground(Color.WHITE);
		rentCarButton.setBackground(new Color(255, 0, 0));
		rentCarButton.addActionListener(this);
		rentCarButton.setBounds(226, 200, 162, 50);
		add(rentCarButton);

		JButton dropOffCarButton = new JButton("Drop Off Car");
		dropOffCarButton.setForeground(Color.WHITE);
		dropOffCarButton.setBackground(Color.RED);
		dropOffCarButton.setBounds(226, 206, 162, 50);
		dropOffCarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Drop Off Car")) {
					searchReservation();
				}
			}
		});
		add(dropOffCarButton);

		JButton modifyReservationButton = new JButton("Modify Reservation");
		modifyReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		dropOffCarButton.setForeground(Color.WHITE);
		dropOffCarButton.setBackground(Color.RED);
		dropOffCarButton.setBounds(226, 283, 162, 50);
		add(dropOffCarButton);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setForeground(Color.WHITE);
		exitButton.setBackground(Color.RED);
		exitButton.setBounds(226, 361, 162, 50);
		add(exitButton);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 130, 26);
		add(menuBar);

		JMenu mnNewMenu = new JMenu("View");
		menuBar.add(mnNewMenu);

		JMenu mntmHelp = new JMenu("Help");
		menuBar.add(mntmHelp);

		JMenu mntmAbout = new JMenu("About");
		menuBar.add(mntmAbout);

		JMenuItem mntmReservationDatabase = new JMenuItem("Reservation Database");
		mntmReservationDatabase.addActionListener(this);
		mntmReservationDatabase.setActionCommand("Reservation Database");
		mnNewMenu.add(mntmReservationDatabase);

		JMenuItem mntmCustomerDatabase = new JMenuItem("Customer Database");
		mntmCustomerDatabase.addActionListener(this);
		mntmCustomerDatabase.setActionCommand("Customer Database");
		mnNewMenu.add(mntmCustomerDatabase);

		JMenuItem mntmFleetDatabase = new JMenuItem("Car Database");
		mntmFleetDatabase.addActionListener(this);
		mntmFleetDatabase.setActionCommand("Car Database");
		mnNewMenu.add(mntmFleetDatabase);

		JMenuItem mntmAboutMe = new JMenuItem("About");
		mntmAboutMe.addActionListener(this);
		mntmAboutMe.setActionCommand("About");
		mntmAbout.add(mntmAboutMe);

		JMenuItem mntmHelpMe = new JMenuItem("Help");
		mntmHelpMe.addActionListener(this);
		mntmHelpMe.setActionCommand("Help");
		mntmHelp.add(mntmHelpMe);
	}

	/**
	 * Allows user to rent a car by entering the pick up and drop-off date/time as
	 * well as the car type.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>lblTorontoSupremeCar - </b> JLabel displaying company name
	 * <p>
	 * <b>pickUpDateTime - </b>JLabel prompt for pick-up date and time.
	 * <p>
	 * <b>dropOffDateTime - </b>JLabel prompt for drop-off date and time.
	 * <p>
	 * <b>vechicelTypeLabel - </b>JLabel prompt for vehicle type.
	 * <p>
	 * <b>listOfCarTypes - </b>Instance of JComboBox with a list of available car
	 * types.
	 * <p>
	 * <b>pickUpHourComboBox - </b>Instance of JComboBox with a list of available
	 * pickup hours.
	 * <p>
	 * <b>pickUpMinuteComboBox - </b>Instance of JComboBox with a list of available
	 * pickup minutes.
	 * <p>
	 * <b>dropOffHourComboBox- </b> Instance of JComboBox with a list of available
	 * drop-off hours.
	 * <p>
	 * <b>dropOffMinuteComboBox- </b> Instance of JComboBox with a list of available
	 * drop-off minutes.
	 * <p>
	 * <b>submitButton- </b> Instance of JButton that allows user to search for
	 * available cars.
	 * <p>
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void rentCar() throws IOException, ParseException {
		refresh();
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		JLabel lblTorontoSupremeCar = new JLabel("Toronto Supreme Car Rentals");
		lblTorontoSupremeCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblTorontoSupremeCar.setForeground(Color.RED);
		lblTorontoSupremeCar.setFont(new Font("Trajan Pro", Font.PLAIN, 26));
		lblTorontoSupremeCar.setBackground(Color.BLACK);
		lblTorontoSupremeCar.setBounds(0, 9, 632, 105);
		add(lblTorontoSupremeCar);

		JLabel pickUpDateTime = new JLabel("Pick-Up Date and Time:");
		pickUpDateTime.setFont(new Font("Segoe UI Semilight", Font.BOLD, 17));
		pickUpDateTime.setBounds(10, 127, 209, 62);
		add(pickUpDateTime);

		JLabel lblDropoffDateAnd = new JLabel("Drop-Off Date and Time:");
		lblDropoffDateAnd.setFont(new Font("Segoe UI Semilight", Font.BOLD, 16));
		lblDropoffDateAnd.setBounds(12, 204, 209, 62);
		add(lblDropoffDateAnd);

		JLabel vechicelTypeLabel = new JLabel("Vehicle Type: ");
		vechicelTypeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		vechicelTypeLabel.setFont(new Font("Segoe UI Semilight", Font.BOLD, 17));
		vechicelTypeLabel.setBounds(91, 299, 117, 27);
		add(vechicelTypeLabel);

		final JComboBox listOfCarTypes = new JComboBox();
		listOfCarTypes.setModel(new DefaultComboBoxModel(
				new String[] { "Economy", "Compact", "Intermediate", "Standard Size", "Full Size", "Mini Van/7 Seater",
						"Premium", "Luxury", "Supreme", "Intermediate SUV", "Standard SUV", "Large SUV" }));
		listOfCarTypes.setBounds(220, 305, 146, 21);
		add(listOfCarTypes);

		final JComboBox pickUpHourComboBox = new JComboBox();
		pickUpHourComboBox.setModel(new DefaultComboBoxModel(new String[] { "09:", "10:", "11:", "12:", "13:", "14:",
				"15:", "16:", "17:", "18:", "19:", "20:", "21:" }));
		pickUpHourComboBox.setBounds(429, 147, 56, 27);
		add(pickUpHourComboBox);

		final JComboBox pickUpMinuteComboBox = new JComboBox();
		pickUpMinuteComboBox.setModel(new DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
		pickUpMinuteComboBox.setBounds(497, 147, 56, 27);
		add(pickUpMinuteComboBox);

		final JComboBox dropOffHourComboBox = new JComboBox();
		dropOffHourComboBox.setModel(new DefaultComboBoxModel(new String[] { "09:", "10:", "11:", "12:", "13:", "14:",
				"15:", "16:", "17:", "18:", "19:", "20:", "21:" }));
		dropOffHourComboBox.setBounds(429, 224, 56, 27);
		add(dropOffHourComboBox);

		final JComboBox dropOffMinuteComboBox = new JComboBox();
		dropOffMinuteComboBox.setModel(new DefaultComboBoxModel(new String[] { "00", "15", "30", "45" }));
		dropOffMinuteComboBox.setBounds(497, 224, 56, 27);
		add(dropOffMinuteComboBox);

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		pickUpDatePicker = new JDatePickerImpl(datePanel);
		pickUpDatePicker.setBounds(231, 147, 167, 30);
		add(pickUpDatePicker);

		UtilDateModel model1 = new UtilDateModel();
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
		dropOffDatePicker = new JDatePickerImpl(datePanel1);
		dropOffDatePicker.setBounds(231, 224, 167, 30);
		add(dropOffDatePicker);

		JButton submitButton = new JButton("Search\r\n");
		submitButton.setActionCommand("Search");
		submitButton.setForeground(Color.WHITE);
		submitButton.setBackground(Color.RED);
		submitButton.setBounds(258, 386, 140, 38);
		add(submitButton);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Search") && !((Date) pickUpDatePicker.getModel().getValue() == null)
						&& !((Date) dropOffDatePicker.getModel().getValue() == null)) {

					customerData[CustomerConstants.TYPE.getValue()] = String.valueOf(listOfCarTypes.getSelectedItem()); // gets
																														// selected
																														// car
																														// type

					LocalDate currentDate = LocalDate.now(); // instance of current date

					pickUpSelectedDate = (Date) pickUpDatePicker.getModel().getValue();
					dropOffSelectedDate = (Date) dropOffDatePicker.getModel().getValue();

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

					customerData[CustomerConstants.PICKUPDATE.getValue()] = df.format(pickUpSelectedDate); // get
																											// selected
																											// pick up
																											// date
					customerData[CustomerConstants.DROPOFFDATE.getValue()] = df.format(dropOffSelectedDate); // get
																												// selected
																												// drop
																												// off
																												// date

					customerData[CustomerConstants.PICKUPTIME.getValue()] = String
							.valueOf(pickUpHourComboBox.getSelectedItem()) // get selected pick
							// up time
							+ String.valueOf(pickUpMinuteComboBox.getSelectedItem());
					customerData[CustomerConstants.DROPOFFTIME.getValue()] = String
							.valueOf(dropOffHourComboBox.getSelectedItem()) // get selected
							// drop off tie
							+ String.valueOf(dropOffMinuteComboBox.getSelectedItem());

					if (pickUpSelectedDate.after(dropOffSelectedDate)) {
						JOptionPane.showMessageDialog(null, "Pick Up Date Cannot Be After Drop-Off Date!",
								"Invalid Date Range", JOptionPane.ERROR_MESSAGE);
						pickUpDatePicker.getJFormattedTextField().setText("");
						dropOffDatePicker.getJFormattedTextField().setText("");
					} else if (customerData[CustomerConstants.PICKUPDATE.getValue()]
							.equals(customerData[CustomerConstants.DROPOFFDATE.getValue()])) {
						JOptionPane.showMessageDialog(null, "Rental Duration Must Be Longer Than 1 Day!",
								"Invalid Date Range", JOptionPane.ERROR_MESSAGE);
						pickUpDatePicker.getJFormattedTextField().setText("");
						dropOffDatePicker.getJFormattedTextField().setText("");
					} else if (pickUpSelectedDate
							.before(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
						JOptionPane.showMessageDialog(null, "Rental Date is Before Current Date!", "Invalid Date Range",
								JOptionPane.ERROR_MESSAGE);
						pickUpDatePicker.getJFormattedTextField().setText("");
						dropOffDatePicker.getJFormattedTextField().setText("");
					} else if (customerData[CustomerConstants.PICKUPDATE.getValue()]
							.equals(String.valueOf(currentDate))) {
						JOptionPane.showMessageDialog(null, "Reservations Must Be Made A Day Before!",
								"Invalid Date Range", JOptionPane.ERROR_MESSAGE);
						pickUpDatePicker.getJFormattedTextField().setText("");
						dropOffDatePicker.getJFormattedTextField().setText("");
					} else {
						try {
							userPickUpDate = df.parse(customerData[CustomerConstants.PICKUPDATE.getValue()] + " "
									+ customerData[CustomerConstants.PICKUPTIME.getValue()]);
							userDropOffDate = df.parse(customerData[CustomerConstants.DROPOFFDATE.getValue()] + " "
									+ customerData[CustomerConstants.DROPOFFTIME.getValue()]);
							availableCars();

						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "All Fields Must Be Filled.", "Missing Fields",
							JOptionPane.ERROR_MESSAGE);
					try {
						rentCar();
					} catch (IOException | ParseException e1) {
					}
				}
			}
		});

	}

	/**
	 * Creates A Customer Object.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>programTitle - </b> JLabel displaying company name
	 * <p>
	 * <b>firstNameLabel - </b>JLabel prompt for customer first name.
	 * <p>
	 * <b>firstNameInput - </b>JTextField input for customer first name.
	 * <p>
	 * <b>lastNameLabel - </b>JLabel prompt for customer last name.
	 * <p>
	 * <b>lastNameInput- </b>JTextField input for customer last name.
	 * <p>
	 * <b>creditCardNumberLabel - </b>JLabel prompt for customer credit card number.
	 * <p>
	 * <b>creditCardNumberInput - </b>JTextField input for customer credit card
	 * number.
	 * <p>
	 * <b>insuranceNumberLabel - </b>JLabel prompt for customer insurance number.
	 * <p>
	 * <b>insuranceNumberInput - </b>JTextField input for customer insurance number.
	 * <p>
	 * <b>emailAddressLabel - </b>JLabel prompt for customer email address.
	 * <p>
	 * <b>emailAddressInput - </b>JTextField input for customer email address.
	 * <p>
	 * <b>driverAgeLabel- </b>JLabel prompt for customer age.
	 * <p>
	 * <b>driverAgeInput - </b>JComboBox for customer age.
	 * <p>
	 * <b>driverLicenseLabel - </b>JLabel prompt for driver license number.
	 * <p>
	 * <b>driverLicenseInput - </b>JTextField input for driver license number.
	 * <p>
	 * <b>submitButton- </b> Instance of JButton that creates record of customer.
	 * <p>
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	public void createRecord() throws IOException, ParseException {
		refresh();

		JLabel programTitle = new JLabel("Toronto Supreme Car Rentals");
		programTitle.setHorizontalAlignment(SwingConstants.CENTER);
		programTitle.setForeground(Color.RED);
		programTitle.setFont(new Font("Dialog", Font.PLAIN, 26));
		programTitle.setBackground(Color.BLACK);
		programTitle.setBounds(0, 0, 632, 105);
		add(programTitle);

		JLabel firstNameLabel = new JLabel("First Name: ");
		firstNameLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 21));
		firstNameLabel.setBounds(22, 118, 157, 31);
		add(firstNameLabel);

		final JTextField firstNameInput = new JTextField();
		firstNameInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		firstNameInput.setBounds(147, 123, 145, 35);
		add(firstNameInput);
		firstNameInput.setColumns(10);

		JLabel lastNameLabel = new JLabel("Last Name: ");
		lastNameLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 21));
		lastNameLabel.setBounds(316, 118, 157, 31);
		add(lastNameLabel);

		final JTextField lastNameInput = new JTextField();
		lastNameInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lastNameInput.setColumns(10);
		lastNameInput.setBounds(438, 123, 145, 35);
		add(lastNameInput);

		JLabel creditCardNumberLabel = new JLabel("Credit Card Number:");
		creditCardNumberLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 21));
		creditCardNumberLabel.setBounds(22, 187, 225, 31);
		add(creditCardNumberLabel);

		final JTextField creditCardNumberInput = new JTextField();
		creditCardNumberInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		creditCardNumberInput.setColumns(10);
		creditCardNumberInput.setBounds(241, 192, 260, 35);
		add(creditCardNumberInput);

		JLabel insuranceNumberLabel = new JLabel("Insurance Number:");
		insuranceNumberLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 21));
		insuranceNumberLabel.setBounds(22, 244, 192, 31);
		add(insuranceNumberLabel);

		final JTextField insuranceNumberInput = new JTextField();
		insuranceNumberInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		insuranceNumberInput.setColumns(10);
		insuranceNumberInput.setBounds(241, 249, 260, 35);
		add(insuranceNumberInput);

		JLabel emailAddressLabel = new JLabel("Email Address: ");
		emailAddressLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 21));
		emailAddressLabel.setBounds(22, 300, 192, 40);
		add(emailAddressLabel);

		final JTextField emailAddressInput = new JTextField();
		emailAddressInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		emailAddressInput.setColumns(10);
		emailAddressInput.setBounds(241, 305, 260, 35);
		add(emailAddressInput);

		JLabel driverLicenseLabel = new JLabel("Driver License Number: ");
		driverLicenseLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 19));
		driverLicenseLabel.setBounds(22, 353, 260, 40);
		add(driverLicenseLabel);

		final JTextField driverLicenseInput = new JTextField();
		driverLicenseInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		driverLicenseInput.setColumns(10);
		driverLicenseInput.setBounds(241, 357, 260, 35);
		add(driverLicenseInput);

		JButton submitButton = new JButton("Submit\r\n");
		submitButton.setActionCommand("createRecord");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("createRecord")) {

					customerData[CustomerConstants.FNAME.getValue()] = firstNameInput.getText().trim();
					customerData[CustomerConstants.LNAME.getValue()] = lastNameInput.getText().trim();
					customerData[CustomerConstants.EMAIL.getValue()] = emailAddressInput.getText().trim();
					customerData[CustomerConstants.INSURANCE.getValue()] = insuranceNumberInput.getText().trim();
					customerData[CustomerConstants.CREDIT.getValue()] = creditCardNumberInput.getText().trim();
					customerData[CustomerConstants.LICENSE.getValue()] = driverLicenseInput.getText().trim();
					if (!DataCheck.nameCheck(customerData[CustomerConstants.FNAME.getValue()])) {
						JOptionPane.showMessageDialog(null, "First Name Can Only Contain Letters!", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						firstNameInput.setText("");
						firstNameInput.requestFocus();
					} else if (!DataCheck.nameCheck(customerData[CustomerConstants.LNAME.getValue()])) {
						JOptionPane.showMessageDialog(null, "Last Name Can Only Contain Letters!", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						lastNameInput.setText("");
						lastNameInput.requestFocus();
					} else if (!DataCheck.creditCardCheck(customerData[CustomerConstants.CREDIT.getValue()])) {
						JOptionPane.showMessageDialog(null,
								"Credit Card Number Is Invalid! Enter mastercard or visa number!", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						creditCardNumberInput.setText("");
						creditCardNumberInput.requestFocus();
					} else if (!DataCheck.emailCheck(customerData[CustomerConstants.EMAIL.getValue()])) {
						JOptionPane.showMessageDialog(null, "Email Address Is Invalid", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						emailAddressInput.setText("");
						emailAddressInput.requestFocus();
					} else if (!DataCheck.insuranceNumberCheck(customerData[CustomerConstants.INSURANCE.getValue()])
							&& !customerData[CustomerConstants.INSURANCE.getValue()].equals("")) {
						JOptionPane.showMessageDialog(null, "Insurance Number Is Invalid!", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						insuranceNumberInput.setText("");
						insuranceNumberInput.requestFocus();
					} else if (!DataCheck
							.driverLicenseNumberCheck(customerData[CustomerConstants.LICENSE.getValue()])) {
						JOptionPane.showMessageDialog(null, "Driver License Number Is Invalid!", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						driverLicenseInput.setText("");
						driverLicenseInput.requestFocus();
					} else {
						/*
						 * String carMake2 = "", carModel2 = ""; for (int x = 0; x < 36; x++) { if
						 * (NewCarJTable.data[x][5].equals(licensePlate)) { carMake2 = (String)
						 * NewCarJTable.data[x][2]; carModel2 = (String) NewCarJTable.data[x][3]; } }
						 */

						customerData[CustomerConstants.RESERVATION_NUMBER.getValue()] = db.reservationNumber();
						db.insertCustomer(customerData);
						db.insertReservation(df.format(userPickUpDate), df.format(userDropOffDate),
								customerData[CustomerConstants.PLATE.getValue()],
								customerData[CustomerConstants.RESERVATION_NUMBER.getValue()]);

						JOptionPane.showMessageDialog(null, "Reservation Succesfully Made", "Booked",
								JOptionPane.INFORMATION_MESSAGE);

						if (customerData[CustomerConstants.INSURANCE.getValue()].equals("")) {
							JOptionPane.showMessageDialog(null,
									"Insurance Number Not Provided. Customer Opted For Full-Coverage at $20/day.",
									"Full Coverage", JOptionPane.INFORMATION_MESSAGE);
						} else if (!customerData[CustomerConstants.INSURANCE.getValue()].equals("")) {
							customerData[CustomerConstants.INSURANCE.getValue()] = "N/A";
							JOptionPane.showMessageDialog(null, "Customer Opted To Use Their Own Insurance Coverage.",
									"Personal Coverage", JOptionPane.INFORMATION_MESSAGE);
						}
						carInfoReview();
					}
				}
			}
		});

		submitButton.setForeground(Color.WHITE);
		submitButton.setBackground(Color.RED);
		submitButton.setBounds(258, 402, 140, 44);

		add(submitButton);
	}

	/**
	 * Display's all the available cars in a JComboBox. Reads the contents of the
	 * local Excel CSV and will determine which cars are available for rent based on
	 * the customer's request.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>companyNameLabel - </b> Creates an instance of JLabel displaying the
	 * company label.
	 * <p>
	 * <b>carTypeLabel - </b> Creates an instance of JLabel displaying the car type.
	 * <p>
	 * <b>customerCarType - </b> Creates an instance of JLabel displaying the
	 * customer car type.
	 * <p>
	 * <b>lblAvailableCars - </b> Creates an instance of JLabel displaying the
	 * available cars.
	 * <p>
	 * <b>btnReserve - </b> Creates an instance of JButton that prompts the user to
	 * reserve.
	 * <p>
	 * 
	 * @throws ParseException
	 */
	public void availableCars() throws ParseException {
		refresh();

		JLabel companyNameLabel = new JLabel("Toronto Supreme Car Rentals");
		companyNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		companyNameLabel.setForeground(Color.RED);
		companyNameLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 29));
		companyNameLabel.setBackground(Color.BLACK);
		companyNameLabel.setBounds(0, 26, 632, 70);
		add(companyNameLabel);

		JLabel carTypeLabel = new JLabel("Car Type: ");
		carTypeLabel.setFont(new Font("Segoe UI Semilight", Font.BOLD, 21));
		carTypeLabel.setBounds(33, 129, 108, 39);
		add(carTypeLabel);

		JLabel customerCarType = new JLabel(customerData[CustomerConstants.TYPE.getValue()]);
		customerCarType.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 21));
		customerCarType.setBounds(153, 129, 266, 39);
		add(customerCarType);

		JLabel lblAvailableCars = new JLabel("Available Cars:\r\n");
		lblAvailableCars.setFont(new Font("Segoe UI Semilight", Font.BOLD, 21));
		lblAvailableCars.setBounds(32, 207, 175, 39);
		add(lblAvailableCars);

		availableCarsResult.setBounds(195, 216, 425, 28);

		for (String car : db.getCarTypes(customerData[CustomerConstants.TYPE.getValue()])) {
			if (car != null) {
				availableCarsResult.addItem(car); // add car to combo box
			}
		}

		add(availableCarsResult);

		JButton btnReserve = new JButton("Reserve");
		btnReserve.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getActionCommand().equals("Reserve")) {

					String valueTemp = "Available";
					customerData[CustomerConstants.PLATE.getValue()] = String
							.valueOf(availableCarsResult.getSelectedItem());

					int temp = StringUtils.ordinalIndexOf(customerData[CustomerConstants.PLATE.getValue()], "-", 3);
					customerData[CustomerConstants.PLATE.getValue()] = customerData[CustomerConstants.PLATE.getValue()]
							.substring(temp + 2, customerData[CustomerConstants.PLATE.getValue()].length()); // Get
					// license
					// plate
					// of
					// chosen
					// car
					try {
						createRecord();
					} catch (IOException | ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnReserve.setForeground(Color.WHITE);
		btnReserve.setBackground(Color.RED);
		btnReserve.setBounds(257, 320, 162, 50);
		add(btnReserve);
	}

	/**
	 * Customer info review.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>lblCustomerInformation - </b> Creates an instance of JLabel with customer
	 * information.
	 * <p>
	 * <b>numberOfReservationsLabel - </b> Creates an instance of JLabel with number
	 * of reservations.
	 * <p>
	 * <b>numberOfReservationText - </b>JTextField with the number of reservations.
	 * <p>
	 * <b>carPreferenceLabel - </b>JLabel with text car preference.
	 * <p>
	 * <b>carPreferenceText - </b>JTextField with the user's car preference.
	 * <p>
	 * <b>pastProblemsText - </b>JTextArea with the user's past problems.
	 * <p>
	 * <b>pastProblemsLabel - </b>JLabel with text past problems.
	 * <p>
	 * <b>averageRatingLabel - </b>JLabel with text average rating.
	 * <p>
	 * <b>averageRatingText - </b>JTextField with user's average rating.
	 * <p>
	 * <b>nextButton - </b>JButton that goes on to the next step.
	 * <p>
	 * 
	 * @throws ParseException
	 */
	/*
	 * public void customerInfoReview() throws ParseException { refresh();
	 * 
	 * JLabel lblCustomerInformation = new JLabel("Customer Information");
	 * lblCustomerInformation.setHorizontalAlignment(SwingConstants.CENTER);
	 * lblCustomerInformation.setFont(new Font("Gadugi", Font.PLAIN, 28));
	 * lblCustomerInformation.setBounds(0, 23, 632, 57);
	 * add(lblCustomerInformation);
	 * 
	 * JLabel numberOfReservationsLabel = new JLabel("Number of Reservations: ");
	 * numberOfReservationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
	 * numberOfReservationsLabel.setBounds(12, 119, 233, 27);
	 * add(numberOfReservationsLabel);
	 * 
	 * JTextField numberOfReservationText = new JTextField();
	 * numberOfReservationText.setEditable(false);
	 * numberOfReservationText.setText(String.valueOf(InfoJTable.getFreq(InfoJTable.
	 * data, driverLicense))); numberOfReservationText.setBounds(244, 122, 64, 27);
	 * add(numberOfReservationText); numberOfReservationText.setColumns(10);
	 * 
	 * JLabel carPreferenceLabel = new JLabel("Car Preference: \r\n");
	 * carPreferenceLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
	 * carPreferenceLabel.setBounds(320, 119, 167, 27); add(carPreferenceLabel);
	 * 
	 * JTextField carPreferenceText = new JTextField();
	 * carPreferenceText.setEditable(false);
	 * carPreferenceText.setText(String.valueOf(InfoJTable.getFreq(InfoJTable.data,
	 * driverLicense))); carPreferenceText.setColumns(10);
	 * carPreferenceText.setBounds(475, 122, 145, 27); add(carPreferenceText);
	 * 
	 * JTextArea pastProblemsText = new JTextArea();
	 * pastProblemsText.setEditable(false); pastProblemsText.setRows(3);
	 * pastProblemsText.setBounds(159, 260, 410, 84); add(pastProblemsText);
	 * 
	 * JLabel pastProblemsLabel = new JLabel("Past Problems:");
	 * pastProblemsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
	 * pastProblemsLabel.setBounds(12, 255, 167, 27); add(pastProblemsLabel);
	 * 
	 * JLabel averageRatingLabel = new JLabel("Average Rating:");
	 * averageRatingLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
	 * averageRatingLabel.setBounds(12, 184, 233, 27); add(averageRatingLabel);
	 * 
	 * JTextField averageRatingText = new JTextField();
	 * averageRatingText.setEditable(false); averageRatingText.setColumns(10);
	 * averageRatingText.setBounds(181, 189, 97, 27); add(averageRatingText);
	 * 
	 * JButton nextButton = new JButton("Next\r\n");
	 * nextButton.setActionCommand("Next"); nextButton.setForeground(Color.WHITE);
	 * nextButton.setBackground(Color.RED); nextButton.setFont(new Font("Tahoma",
	 * Font.PLAIN, 18)); nextButton.addActionListener(new ActionListener() { public
	 * void actionPerformed(ActionEvent e) { if
	 * (e.getActionCommand().equals("Next")) { carInfoReview(); } } });
	 * nextButton.setBounds(267, 384, 117, 39); add(nextButton); }
	 */

	/**
	 * Shows information about car that has been booked.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>lblCarInformation - </b> Creates an instance of JLabel with car
	 * information.
	 * <p>
	 * <b>lblManufacturer - </b> Creates an instance of JLabel with car
	 * manufacturer.
	 * <p>
	 * <b>manufacturerText - </b> Creates an instance of JTextField with
	 * manufacturer text.
	 * <p>
	 * <b>modelText - </b> Creates an instance of JTextField with car model text.
	 * <p>
	 * <b>manufacturerText - </b> Creates an instance of JTextField with
	 * manufacturer text.
	 * <p>
	 * <b>carTypeText - </b> Creates an instance of JTextField with car type text.
	 * <p>
	 * 
	 * @throws ParseException
	 */
	public void carInfoReview() {
		refresh();

		JLabel lblCarInformation = new JLabel("Car Information");
		lblCarInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarInformation.setFont(new Font("Gadugi", Font.PLAIN, 28));
		lblCarInformation.setBounds(6, 26, 632, 57);
		add(lblCarInformation);

		JLabel lblManufacturer = new JLabel("Manufacturer: ");
		lblManufacturer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblManufacturer.setBounds(22, 127, 133, 27);
		add(lblManufacturer);

		JTextField manufacturerText = new JTextField();
		manufacturerText.setEditable(false);
		manufacturerText.setColumns(10);
		manufacturerText.setBounds(165, 129, 153, 27);
		add(manufacturerText);

		JLabel lblModel = new JLabel("Model: ");
		lblModel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModel.setBounds(344, 127, 77, 27);
		add(lblModel);

		JTextField modelText = new JTextField();
		modelText.setEditable(false);
		modelText.setColumns(10);
		modelText.setBounds(418, 129, 158, 27);
		add(modelText);

		JLabel lblCarType = new JLabel("Car Type:");
		lblCarType.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCarType.setBounds(22, 194, 93, 27);
		add(lblCarType);

		JTextField carTypeText = new JTextField();
		carTypeText.setEditable(false);
		carTypeText.setColumns(10);
		carTypeText.setBounds(164, 196, 153, 27);
		add(carTypeText);

		JLabel lblFuelLevel = new JLabel("Fuel Level:");
		lblFuelLevel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFuelLevel.setBounds(344, 194, 210, 27);
		add(lblFuelLevel);

		JTextField fuelLevelText = new JTextField();
		fuelLevelText.setEditable(false);
		fuelLevelText.setColumns(10);
		fuelLevelText.setBounds(450, 196, 57, 27);
		add(fuelLevelText);

		JLabel lblMileage = new JLabel("Odometer Reading:");
		lblMileage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMileage.setBounds(22, 258, 210, 27);
		add(lblMileage);

		JTextField mileageText = new JTextField();
		mileageText.setEditable(false);
		mileageText.setColumns(10);
		mileageText.setBounds(208, 260, 100, 27);
		add(mileageText);

		JLabel lblFuelCapacity = new JLabel("Fuel Capacity:");
		lblFuelCapacity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFuelCapacity.setBounds(344, 258, 210, 27);
		add(lblFuelCapacity);

		JTextField fuelCapacityText = new JTextField();
		fuelCapacityText.setEditable(false);
		fuelCapacityText.setColumns(10);
		fuelCapacityText.setBounds(475, 260, 57, 27);
		add(fuelCapacityText);

		carInfo = db.getCar(customerData[CustomerConstants.PLATE.getValue()]);

		manufacturerText.setText(carInfo[CarConstants.MANUFACTURER.getValue()]);
		modelText.setText(carInfo[CarConstants.MODEL.getValue()]);
		carTypeText.setText(carInfo[CarConstants.TYPE.getValue()]);
		mileageText.setText(carInfo[CarConstants.ODOMETER.getValue()] + " KM(s)");
		fuelLevelText.setText(carInfo[CarConstants.FUELLEVEL.getValue()] + " L");
		fuelCapacityText.setText(carInfo[CarConstants.FUELCAPACITY.getValue()] + " L");

		JButton button = new JButton("Next\r\n");
		button.setActionCommand("Next Page");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		button.setBackground(Color.RED);
		button.setBounds(250, 368, 117, 39);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Next Page")) {
					reservationInfoReview();
				}
			}
		});
		add(button);
	}

	/**
	 * Displays a summary of the reservation.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>lblReservationSummary- </b> Creates an instance of JLabel displaying the
	 * reservation summary.
	 * <p>
	 * <b>lblPickUpDate- </b> Creates an instance of JLabel displaying the
	 * pickUpDate.
	 * <p>
	 * <b>lblPickUpDateText- </b> Creates an instance of JTextField diplaying
	 * pickUpDate.
	 * <p>
	 * <b>lblDuration </b> Creates an instance of JLabel displaying the duration of
	 * reservation.
	 * <p>
	 * <b>lblPricePerDay </b> Creates an instance of JLabel displaying the price per
	 * day of the car rental.
	 * <p>
	 * <b>lblTotalPrice </b> Creates an instance of JLabel displaying the the total
	 * price of the reservation.
	 * <p>
	 */
	public void reservationInfoReview() {
		refresh();

		JLabel lblReservationSummary = new JLabel("Reservation Summary");
		lblReservationSummary.setHorizontalAlignment(SwingConstants.CENTER);
		lblReservationSummary.setFont(new Font("Gadugi", Font.PLAIN, 28));
		lblReservationSummary.setBounds(0, 25, 632, 57);
		add(lblReservationSummary);

		JLabel lblPickupDate = new JLabel("Pick-Up Date & Time: ");
		lblPickupDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPickupDate.setBounds(12, 113, 234, 27);
		add(lblPickupDate);

		JTextField pickUpDateText = new JTextField();
		pickUpDateText.setText(df.format(userPickUpDate));
		pickUpDateText.setEditable(false);
		pickUpDateText.setColumns(10);
		pickUpDateText.setBounds(222, 115, 194, 27);
		add(pickUpDateText);

		JLabel lblDropoffDate = new JLabel("Drop-Off Date & Time: ");
		lblDropoffDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDropoffDate.setBounds(12, 175, 234, 27);
		add(lblDropoffDate);

		JTextField dropOffDateText = new JTextField();
		dropOffDateText.setText(df.format(userDropOffDate));
		dropOffDateText.setEditable(false);
		dropOffDateText.setColumns(10);
		dropOffDateText.setBounds(222, 175, 194, 27);
		add(dropOffDateText);

		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDuration.setBounds(12, 238, 111, 27);
		add(lblDuration);

		JTextField durationText = new JTextField();
		long diff = userDropOffDate.getTime() - userPickUpDate.getTime();
		durationText.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " day(s)");
		durationText.setEditable(false);
		durationText.setColumns(10);
		durationText.setBounds(149, 240, 122, 27);
		add(durationText);

		JLabel lblPricePerDay = new JLabel("Price Per Day:");
		lblPricePerDay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPricePerDay.setBounds(292, 238, 138, 27);
		add(lblPricePerDay);

		JTextField pricePerDayText = new JTextField();
		pricePerDayText.setEditable(false);
		pricePerDayText.setColumns(10);
		pricePerDayText.setBounds(493, 240, 122, 27);
		add(pricePerDayText);

		JLabel lblHoldAmount = new JLabel("Hold Amount: ");
		lblHoldAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHoldAmount.setBounds(12, 298, 138, 27);
		add(lblHoldAmount);

		JTextField holdAmountText = new JTextField();
		holdAmountText.setEditable(false);
		holdAmountText.setColumns(10);
		holdAmountText.setBounds(149, 300, 122, 27);
		add(holdAmountText);

		JLabel lblEstimatedTotalPrice = new JLabel("Estimated Total Price:");
		lblEstimatedTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEstimatedTotalPrice.setBounds(288, 298, 211, 27);
		add(lblEstimatedTotalPrice);

		JTextField totalPriceText = new JTextField();
		totalPriceText.setEditable(false);
		totalPriceText.setColumns(10);
		totalPriceText.setBounds(493, 300, 122, 27);
		add(totalPriceText);

		JButton btnSaveAndPrint = new JButton("Save and Print Invoice");
		btnSaveAndPrint.setForeground(Color.WHITE);
		btnSaveAndPrint.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveAndPrint.setBackground(Color.RED);
		btnSaveAndPrint.setBounds(428, 109, 186, 39);
		btnSaveAndPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Save and Print Invoice")) {
					try {
						pickUpInvoiceCreator();
						new PickUpPrintInvoice();
					} catch (IOException | PrinterException e1) {
					}
				}
			}
		});
		add(btnSaveAndPrint);

		JButton btnOk = new JButton("Ok");
		btnOk.setActionCommand("Ok");
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOk.setBackground(Color.RED);
		btnOk.setBounds(253, 377, 117, 39);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Ok")) {
					mainMenu();
				}
			}
		});
		add(btnOk);

		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getLicensePlate().equals(licensePlate)) {
		 * String priceDay = formatter
		 * .format(Customer.dailyPrice((Customer.customerDatabase.get(x).getCarType())))
		 * ; double estimatedPrice = 0;
		 * 
		 * if (Customer.customerDatabase.get(x).getInsuranceNumber().equals("")) {
		 * estimatedPrice =
		 * Customer.estimatedPriceCalculator(NewCar.carDatabase.get(x).getType(),
		 * userPickUpDate, userDropOffDate, true); } else { estimatedPrice =
		 * Customer.estimatedPriceCalculator(NewCar.carDatabase.get(x).getType(),
		 * userPickUpDate, userDropOffDate, false); } pricePerDayText.setText(priceDay);
		 * totalPriceText.setText(formatter.format(estimatedPrice));
		 * holdAmountText.setText(formatter.format(Customer.holdCaculator(
		 * userPickUpDate, userDropOffDate))); } }
		 */

		String priceDay = formatter.format(Customer.dailyPrice(carInfo[CarConstants.TYPE.getValue()]));
		double estimatedPrice = 0;

		if (customerData[CustomerConstants.INSURANCE.getValue()].equals("")) {
			estimatedPrice = Customer.estimatedPriceCalculator(carInfo[CarConstants.TYPE.getValue()], userPickUpDate,
					userDropOffDate, true);
		} else {
			estimatedPrice = Customer.estimatedPriceCalculator(carInfo[CarConstants.TYPE.getValue()], userPickUpDate,
					userDropOffDate, false);
		}

		pricePerDayText.setText(priceDay);
		totalPriceText.setText(formatter.format(estimatedPrice));
		holdAmountText.setText(formatter.format(Customer.holdCaculator(userPickUpDate, userDropOffDate)));
	}

	/**
	 * Creates invoice for pick-up reservation in PDF format.
	 *
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>file </b>Creates an instance of the File Object.
	 * <p>
	 * <b>document </b>Creates an instance of the PDFocument Object.
	 * <p>
	 * <b>firstName</b>Creates an instance of the PDPageContentStream that writes
	 * first name to file.
	 * <p>
	 * <b>lastName</b>Creates an instance of the PDPageContentStream that writes
	 * last name to file.
	 * <p>
	 * <b>email</b>Creates an instance of the PDPageContentStream that writes email
	 * to file.
	 * <p>
	 * <b>creditCard</b>Creates an instance of the PDPageContentStream that writes
	 * credit card to file.
	 * <p>
	 * <b>creditCard</b>Creates an instance of the PDPageContentStream that writes
	 * credit card to file.
	 * <p>
	 * <b>dropOff</b>Creates an instance of the PDPageContentStream that writes drop
	 * Off date to file.
	 * <p>
	 * <b>pickUp</b>Creates an instance of the PDPageContentStream that writes pick
	 * up date to file.
	 * <p>
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void pickUpInvoiceCreator() throws IOException {

		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

		File file = new File("/SupremeCarRental/SupremeCarRentals/pickUpInvoiceTemplate.pdf");
		PDDocument document = PDDocument.load(file);
		PDPage page = document.getPage(0);

		PDPageContentStream firstName = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		firstName.beginText();
		firstName.setFont(PDType1Font.TIMES_ROMAN, 10);
		firstName.setNonStrokingColor(Color.BLACK);
		firstName.newLineAtOffset(100, 588);

		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * createdReservationNumber)) {
		 * firstName.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getFirstName())); } }
		 */
		firstName.showText(customerData[CustomerConstants.FNAME.getValue()]);
		firstName.endText();
		firstName.close();

		PDPageContentStream lastName = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		lastName.beginText();
		lastName.setFont(PDType1Font.TIMES_ROMAN, 10);
		lastName.setNonStrokingColor(Color.BLACK);
		lastName.newLineAtOffset(100, 573);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * createdReservationNumber)) {
		 * lastName.showText(String.valueOf(Customer.customerDatabase.get(x).getLastName
		 * ())); } }
		 */
		lastName.showText(customerData[CustomerConstants.LNAME.getValue()]);
		lastName.endText();
		lastName.close();

		PDPageContentStream email = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		email.beginText();
		email.setFont(PDType1Font.TIMES_ROMAN, 10);
		email.setNonStrokingColor(Color.BLACK);
		email.newLineAtOffset(115, 559);
		email.showText(customerData[CustomerConstants.EMAIL.getValue()]);
		email.endText();
		email.close();

		PDPageContentStream creditCard = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		creditCard.beginText();
		creditCard.setFont(PDType1Font.TIMES_ROMAN, 10);
		creditCard.setNonStrokingColor(Color.BLACK);
		creditCard.newLineAtOffset(111, 545);
		creditCard.showText(customerData[CustomerConstants.CREDIT.getValue()]);
		creditCard.endText();
		creditCard.close();

		PDPageContentStream insurance = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		insurance.beginText();
		insurance.setFont(PDType1Font.TIMES_ROMAN, 10);
		insurance.setNonStrokingColor(Color.BLACK);
		insurance.newLineAtOffset(104, 531);
		insurance.showText(customerData[CustomerConstants.INSURANCE.getValue()]);
		insurance.endText();
		insurance.close();

		PDPageContentStream driverLicense = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.APPEND, false, true);
		driverLicense.beginText();
		driverLicense.setFont(PDType1Font.TIMES_ROMAN, 10);
		driverLicense.setNonStrokingColor(Color.BLACK);
		driverLicense.newLineAtOffset(121, 518);
		driverLicense.showText(customerData[CustomerConstants.LICENSE.getValue()]);
		driverLicense.endText();
		driverLicense.close();

		PDPageContentStream pickUp = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		pickUp.beginText();
		pickUp.setFont(PDType1Font.TIMES_ROMAN, 10);
		pickUp.setNonStrokingColor(Color.BLACK);
		pickUp.newLineAtOffset(238, 573);
		pickUp.showText(customerData[CustomerConstants.PICKUPDATE.getValue()]);
		pickUp.endText();
		pickUp.close();

		PDPageContentStream dropOff = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		dropOff.beginText();
		dropOff.setFont(PDType1Font.TIMES_ROMAN, 10);
		dropOff.setNonStrokingColor(Color.BLACK);
		dropOff.newLineAtOffset(238, 545);
		dropOff.showText(customerData[CustomerConstants.DROPOFFDATE.getValue()]);
		dropOff.endText();
		dropOff.close();

		PDPageContentStream rentalTime = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		rentalTime.beginText();
		rentalTime.setFont(PDType1Font.TIMES_ROMAN, 10);
		rentalTime.setNonStrokingColor(Color.BLACK);
		rentalTime.newLineAtOffset(238, 518);
		rentalTime.showText(Customer.durationCalcuator(userPickUpDate, userDropOffDate) + " day(s)");
		rentalTime.endText();
		rentalTime.close();

		PDPageContentStream carMake = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carMake.beginText();
		carMake.setFont(PDType1Font.TIMES_ROMAN, 10);
		carMake.setNonStrokingColor(Color.BLACK);
		carMake.newLineAtOffset(460, 588);
		carMake.showText(carInfo[CarConstants.MANUFACTURER.getValue()]);
		carMake.endText();
		carMake.close();

		PDPageContentStream carModel = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carModel.beginText();
		carModel.setFont(PDType1Font.TIMES_ROMAN, 10);
		carModel.setNonStrokingColor(Color.BLACK);
		carModel.newLineAtOffset(460, 573);
		carModel.showText(carInfo[CarConstants.MODEL.getValue()]);
		carModel.endText();
		carModel.close();

		PDPageContentStream carYear = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carYear.beginText();
		carYear.setFont(PDType1Font.TIMES_ROMAN, 10);
		carYear.setNonStrokingColor(Color.BLACK);
		carYear.newLineAtOffset(460, 559);
		carYear.showText(carInfo[CarConstants.YEAR.getValue()]);
		carYear.endText();
		carYear.close();

		PDPageContentStream carPlate = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carPlate.beginText();
		carPlate.setFont(PDType1Font.TIMES_ROMAN, 10);
		carPlate.setNonStrokingColor(Color.BLACK);
		carPlate.newLineAtOffset(460, 545);
		carPlate.showText(carInfo[CarConstants.LICENSEPLATE.getValue()]);
		carPlate.endText();
		carPlate.close();

		PDPageContentStream carType = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carType.beginText();
		carType.setFont(PDType1Font.TIMES_ROMAN, 10);
		carType.setNonStrokingColor(Color.BLACK);
		carType.newLineAtOffset(460, 531);
		carType.showText(carInfo[CarConstants.TYPE.getValue()]);
		carType.endText();
		carType.close();

		PDPageContentStream branchAddress = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.APPEND, false, true);
		branchAddress.beginText();
		branchAddress.setFont(PDType1Font.TIMES_ROMAN, 10);
		branchAddress.setNonStrokingColor(Color.BLACK);
		branchAddress.newLineAtOffset(328, 686);
		branchAddress.showText(customerData[CustomerConstants.RESERVATION_NUMBER.getValue()]);
		branchAddress.endText();
		branchAddress.close();

		PDPageContentStream invoiceDate = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		invoiceDate.beginText();
		invoiceDate.setFont(PDType1Font.TIMES_ROMAN, 10);
		invoiceDate.setNonStrokingColor(Color.BLACK);
		invoiceDate.newLineAtOffset(328, 668);
		invoiceDate.showText(timeStamp);
		invoiceDate.endText();
		invoiceDate.close();

		PDPageContentStream rentalDuration1 = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.APPEND, false, true);
		rentalDuration1.beginText();
		rentalDuration1.setFont(PDType1Font.TIMES_ROMAN, 10);
		rentalDuration1.setNonStrokingColor(Color.BLACK);
		rentalDuration1.newLineAtOffset(510, 462);
		rentalDuration1.showText(Customer.durationCalcuator(userPickUpDate, userDropOffDate) + " day(s)");
		rentalDuration1.endText();
		rentalDuration1.close();

		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		PDPageContentStream dailyCost = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		dailyCost.beginText();
		dailyCost.setFont(PDType1Font.TIMES_ROMAN, 10);
		dailyCost.setNonStrokingColor(Color.BLACK);
		dailyCost.newLineAtOffset(409, 462);
		dailyCost.showText(formatter.format(Customer.dailyPrice(carInfo[CarConstants.TYPE.getValue()])));
		dailyCost.endText();
		dailyCost.close();

		PDPageContentStream insuranceCost = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.APPEND, false, true);
		insuranceCost.beginText();
		insuranceCost.setFont(PDType1Font.TIMES_ROMAN, 9);
		insuranceCost.setNonStrokingColor(Color.BLACK);
		insuranceCost.newLineAtOffset(327, 462);
		/*
		 * for (int x1 = 0; x1 < Customer.customerDatabase.size(); x1++) { if
		 * (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * createdReservationNumber)) { if
		 * (Customer.customerDatabase.get(x1).getInsuranceNumber().equals("")) {
		 * insuranceCost.showText(formatter
		 * .format(Customer.durationCalcuator(Customer.customerDatabase.get(x1).
		 * getUserPickUpDate(), Customer.customerDatabase.get(x1).getUserDropOffDate())
		 * * 20)); } else { insuranceCost.showText("Personal Coverage"); } } }
		 */
		if (customerData[CustomerConstants.INSURANCE.getValue()].equals("")) {
			insuranceCost.showText(formatter.format(Customer.durationCalcuator(userPickUpDate, userDropOffDate) * 20));
		} else {
			insuranceCost.showText("Personal Coverage");
		}
		insuranceCost.endText();
		insuranceCost.close();

		PDPageContentStream totalCost = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		totalCost.beginText();
		totalCost.setFont(PDType1Font.TIMES_ROMAN, 10);
		totalCost.setNonStrokingColor(Color.BLACK);
		totalCost.newLineAtOffset(219, 462);
		/*
		 * for (int x1 = 0; x1 < Customer.customerDatabase.size(); x1++) { if
		 * (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * createdReservationNumber)) { double estimatedPrice = 0; if
		 * (Customer.customerDatabase.get(x1).getInsuranceNumber().equals("")) {
		 * estimatedPrice =
		 * Customer.estimatedPriceCalculator(NewCar.carDatabase.get(x1).getType(),
		 * userPickUpDate, userDropOffDate, true); } else { estimatedPrice =
		 * Customer.estimatedPriceCalculator(NewCar.carDatabase.get(x1).getType(),
		 * userPickUpDate, userDropOffDate, false); }
		 * totalCost.showText(formatter.format(estimatedPrice)); } }
		 */
		double estimatedPrice = 0;
		if (customerData[CustomerConstants.INSURANCE.getValue()].equals("")) {
			estimatedPrice = Customer.estimatedPriceCalculator(carInfo[CarConstants.TYPE.getValue()], userPickUpDate,
					userDropOffDate, true);
		} else {
			estimatedPrice = Customer.estimatedPriceCalculator(carInfo[CarConstants.TYPE.getValue()], userPickUpDate,
					userDropOffDate, false);
		}
		totalCost.showText(formatter.format(estimatedPrice));
		totalCost.endText();
		totalCost.close();

		PDPageContentStream estimatedTotalCost = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.APPEND, false, true);
		estimatedTotalCost.beginText();
		estimatedTotalCost.setFont(PDType1Font.TIMES_ROMAN, 10);
		estimatedTotalCost.setNonStrokingColor(Color.BLACK);
		estimatedTotalCost.newLineAtOffset(408, 442);

		/*
		 * for (int x1 = 0; x1 < Customer.customerDatabase.size(); x1++) { if
		 * (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * createdReservationNumber)) { double estimatedPrice = 0; if
		 * (Customer.customerDatabase.get(x1).getInsuranceNumber().equals("")) {
		 * estimatedPrice =
		 * Customer.estimatedPriceCalculator(NewCar.carDatabase.get(x1).getType(),
		 * userPickUpDate, userDropOffDate, true); } else { estimatedPrice =
		 * Customer.estimatedPriceCalculator(NewCar.carDatabase.get(x1).getType(),
		 * userPickUpDate, userDropOffDate, false); }
		 * estimatedTotalCost.showText(formatter.format(estimatedPrice)); } }
		 */
		estimatedTotalCost.showText(formatter.format(estimatedPrice));
		estimatedTotalCost.endText();
		estimatedTotalCost.close();

		PDPageContentStream holdAmount = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		holdAmount.beginText();
		holdAmount.setFont(PDType1Font.TIMES_ROMAN, 10);
		holdAmount.setNonStrokingColor(Color.BLACK);
		holdAmount.newLineAtOffset(408, 420);
		/*
		 * for (int x1 = 0; x1 < Customer.customerDatabase.size(); x1++) { if
		 * (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * createdReservationNumber)) { holdAmount.showText(
		 * formatter.format(Customer.holdCaculator(Customer.customerDatabase.get(x1).
		 * getUserPickUpDate(),
		 * Customer.customerDatabase.get(x1).getUserDropOffDate()))); } }
		 */
		holdAmount.showText(formatter.format(Customer.holdCaculator(userPickUpDate, userDropOffDate)));
		holdAmount.endText();
		holdAmount.close();

		PDPageContentStream totalDueNow = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
				false, true);
		totalDueNow.beginText();
		totalDueNow.setFont(PDType1Font.TIMES_ROMAN, 10);
		totalDueNow.setNonStrokingColor(Color.WHITE);
		totalDueNow.newLineAtOffset(408, 400);
		/*
		 * for (int x1 = 0; x1 < Customer.customerDatabase.size(); x1++) { if
		 * (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * createdReservationNumber)) { totalDueNow.showText(
		 * formatter.format(Customer.holdCaculator(Customer.customerDatabase.get(x1).
		 * getUserPickUpDate(),
		 * Customer.customerDatabase.get(x1).getUserDropOffDate()))); } }
		 */
		totalDueNow.showText(formatter.format(Customer.holdCaculator(userPickUpDate, userDropOffDate)));
		totalDueNow.endText();
		totalDueNow.close();

		document.save(new File("/SupremeCarRental/SupremeCarRentals/pickUpInvoice.pdf"));

		// Closing the document
		document.close();
	}

	/**
	 * Creates invoice for drop off in PDF format.
	 *
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>file </b>Creates an instance of the File Object.
	 * <p>
	 * <b>document </b>Creates an instance of the PDFocument Object.
	 * <p>
	 * <b>firstName</b>Creates an instance of the PDPageContentStream that writes
	 * first name to file.
	 * <p>
	 * <b>lastName</b>Creates an instance of the PDPageContentStream that writes
	 * last name to file.
	 * <p>
	 * <b>email</b>Creates an instance of the PDPageContentStream that writes email
	 * to file.
	 * <p>
	 * <b>creditCard</b>Creates an instance of the PDPageContentStream that writes
	 * credit card to file.
	 * <p>
	 * <b>creditCard</b>Creates an instance of the PDPageContentStream that writes
	 * credit card to file.
	 * <p>
	 * <b>dropOff</b>Creates an instance of the PDPageContentStream that writes drop
	 * Off date to file.
	 * <p>
	 * <b>pickUp</b>Creates an instance of the PDPageContentStream that writes pick
	 * up date to file.
	 * <p>
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void dropOffInvoiceCreator() throws InvalidPasswordException, IOException {
		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
		@SuppressWarnings("unused")
		double damagePrice = 0, distancePrice = 0, insurancePrice = 0, dailyPrice = 0;
		String dropOffCustomerInfo[] = db.getCustomer(searchReservationNumber);

		/*
		 * for (int x1 = 0; x1 < NewCar.carDatabase.size(); x1++) { if
		 * (NewCar.carDatabase.get(x1).getLicensePlate().equals(dropOffLicensePlate)) {
		 * numberOfScratches =
		 * Integer.parseInt(NewCar.carDatabase.get(x1).getNumberOfScratches());
		 * distanceTravelled = Integer.parseInt(NewCar.carDatabase.get(x1).getKm());
		 * type = String.valueOf(NewCar.carDatabase.get(x1).getType()); } }
		 */

		// int numberOfScratches =
		// Integer.parseInt(dropOffCarInfo[CarConstants.NUMBEROFSCRATCHES.getValue()]);
		int distanceTravelled = Integer.parseInt(dropOffCarInfo[CarConstants.ODOMETER.getValue()]);
		String type = carInfo[CarConstants.TYPE.getValue()];

		File file1 = new File("/SupremeCarRental/SupremeCarRentals/dropOffInvoiceTemplate.pdf");
		PDDocument document1 = PDDocument.load(file1);

		PDPage page1 = document1.getPage(0);

		PDPageContentStream firstName1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		firstName1.beginText();
		firstName1.setFont(PDType1Font.TIMES_ROMAN, 10);
		firstName1.setNonStrokingColor(Color.BLACK);
		firstName1.newLineAtOffset(100, 588);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * firstName1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getFirstName())); break; } }
		 */
		firstName1.showText(dropOffCustomerInfo[0]);
		firstName1.endText();
		firstName1.close();

		PDPageContentStream lastName1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		lastName1.beginText();
		lastName1.setFont(PDType1Font.TIMES_ROMAN, 10);
		lastName1.setNonStrokingColor(Color.BLACK);
		lastName1.newLineAtOffset(100, 573);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * lastName1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getLastName())); break; } }
		 */
		lastName1.showText(dropOffCustomerInfo[1]);
		lastName1.endText();
		lastName1.close();

		PDPageContentStream email1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		email1.beginText();
		email1.setFont(PDType1Font.TIMES_ROMAN, 10);
		email1.setNonStrokingColor(Color.BLACK);
		email1.newLineAtOffset(115, 559);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * email1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getEmailAddress())); break; } }
		 */
		email1.showText(dropOffCustomerInfo[5]);
		email1.endText();
		email1.close();

		PDPageContentStream creditCard1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		creditCard1.beginText();
		creditCard1.setFont(PDType1Font.TIMES_ROMAN, 10);
		creditCard1.setNonStrokingColor(Color.BLACK);
		creditCard1.newLineAtOffset(111, 545);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * creditCard1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getCreditCardNumber())); break; } }
		 */
		creditCard1.showText(dropOffCustomerInfo[2]);
		creditCard1.endText();
		creditCard1.close();

		PDPageContentStream insurance1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		insurance1.beginText();
		insurance1.setFont(PDType1Font.TIMES_ROMAN, 10);
		insurance1.setNonStrokingColor(Color.BLACK);
		insurance1.newLineAtOffset(104, 531);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * insurance1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getInsuranceNumber())); break; } }
		 */
		insurance1.showText(dropOffCustomerInfo[3]);
		insurance1.endText();
		insurance1.close();

		PDPageContentStream driverLicense1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		driverLicense1.beginText();
		driverLicense1.setFont(PDType1Font.TIMES_ROMAN, 10);
		driverLicense1.setNonStrokingColor(Color.BLACK);
		driverLicense1.newLineAtOffset(121, 518);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * driverLicense1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getLicenseNumber())); break; } }
		 */
		driverLicense1.showText(dropOffCustomerInfo[4]);
		driverLicense1.endText();
		driverLicense1.close();

		PDPageContentStream pickUp1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		pickUp1.beginText();
		pickUp1.setFont(PDType1Font.TIMES_ROMAN, 10);
		pickUp1.setNonStrokingColor(Color.BLACK);
		pickUp1.newLineAtOffset(238, 573);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * pickUp1.showText(df.format(Customer.customerDatabase.get(x).getUserPickUpDate
		 * ())); break; } }
		 */
		pickUp1.showText(dropOffReservationData[ReservationConstants.PICKUP.getValue()]);
		pickUp1.endText();
		pickUp1.close();

		PDPageContentStream dropOff1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		dropOff1.beginText();
		dropOff1.setFont(PDType1Font.TIMES_ROMAN, 10);
		dropOff1.setNonStrokingColor(Color.BLACK);
		dropOff1.newLineAtOffset(238, 545);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * dropOff1.showText(df.format(Customer.customerDatabase.get(x).
		 * getUserDropOffDate())); break; } }
		 */
		dropOff1.showText(dropOffReservationData[ReservationConstants.DROPOFF.getValue()]);
		dropOff1.endText();
		dropOff1.close();

		PDPageContentStream rentalTime1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		rentalTime1.beginText();
		rentalTime1.setFont(PDType1Font.TIMES_ROMAN, 10);
		rentalTime1.setNonStrokingColor(Color.BLACK);
		rentalTime1.newLineAtOffset(238, 518);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * rentalTime1.showText(Customer.durationCalcuator(Customer.customerDatabase.get
		 * (x).getUserPickUpDate(),
		 * Customer.customerDatabase.get(x).getUserDropOffDate()) + " day(s)"); break; }
		 * }
		 */
		try {
			rentalTime1.showText(
					Customer.durationCalcuator(df.parse(dropOffReservationData[ReservationConstants.PICKUP.getValue()]),
							df.parse(dropOffReservationData[ReservationConstants.DROPOFF.getValue()])) + " day(s)");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rentalTime1.endText();
		rentalTime1.close();

		PDPageContentStream carMake1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carMake1.beginText();
		carMake1.setFont(PDType1Font.TIMES_ROMAN, 10);
		carMake1.setNonStrokingColor(Color.BLACK);
		carMake1.newLineAtOffset(460, 588);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * carMake1.showText(String.valueOf(Customer.customerDatabase.get(x).getCarMake(
		 * ))); break; } }
		 */
		carMake1.showText(dropOffCarInfo[CarConstants.MANUFACTURER.getValue()]);
		carMake1.endText();
		carMake1.close();

		PDPageContentStream carModel1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carModel1.beginText();
		carModel1.setFont(PDType1Font.TIMES_ROMAN, 10);
		carModel1.setNonStrokingColor(Color.BLACK);
		carModel1.newLineAtOffset(460, 573);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * carModel1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getCarModel())); break; } }
		 */
		carModel1.showText(dropOffCarInfo[CarConstants.MODEL.getValue()]);
		carModel1.endText();
		carModel1.close();

		PDPageContentStream carYear1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carYear1.beginText();
		carYear1.setFont(PDType1Font.TIMES_ROMAN, 10);
		carYear1.setNonStrokingColor(Color.BLACK);
		carYear1.newLineAtOffset(460, 559);
		/*
		 * for (int x = 0; x < 36; x++) { if
		 * (NewCarJTable.data[x][5].equals(dropOffLicensePlate)) {
		 * carYear1.showText(String.valueOf(NewCarJTable.data[x][1])); break; } }
		 */
		carYear1.showText(dropOffCarInfo[CarConstants.YEAR.getValue()]);
		carYear1.endText();
		carYear1.close();

		PDPageContentStream carPlate1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carPlate1.beginText();
		carPlate1.setFont(PDType1Font.TIMES_ROMAN, 10);
		carPlate1.setNonStrokingColor(Color.BLACK);
		carPlate1.newLineAtOffset(460, 545);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * carPlate1.showText(String.valueOf(Customer.customerDatabase.get(x).
		 * getLicensePlate())); break; } }
		 */
		carPlate1.showText(dropOffCarInfo[CarConstants.LICENSEPLATE.getValue()]);
		carPlate1.endText();
		carPlate1.close();

		PDPageContentStream carType1 = new PDPageContentStream(document1, page1, PDPageContentStream.AppendMode.APPEND,
				false, true);
		carType1.beginText();
		carType1.setFont(PDType1Font.TIMES_ROMAN, 10);
		carType1.setNonStrokingColor(Color.BLACK);
		carType1.newLineAtOffset(460, 531);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * carType1.showText(String.valueOf(Customer.customerDatabase.get(x).getCarType(
		 * ))); break; } }
		 */
		carType1.showText(dropOffCarInfo[CarConstants.TYPE.getValue()]);
		carType1.endText();
		carType1.close();

		PDPageContentStream branchAddress1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		branchAddress1.beginText();
		branchAddress1.setFont(PDType1Font.TIMES_ROMAN, 10);
		branchAddress1.setNonStrokingColor(Color.BLACK);
		branchAddress1.newLineAtOffset(328, 686);
		// branchAddress1.showText(String.valueOf(searchReservationNumber));
		branchAddress1.endText();
		branchAddress1.close();

		PDPageContentStream invoiceDate1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		invoiceDate1.beginText();
		invoiceDate1.setFont(PDType1Font.TIMES_ROMAN, 10);
		invoiceDate1.setNonStrokingColor(Color.BLACK);
		invoiceDate1.newLineAtOffset(328, 668);
		invoiceDate1.showText(timeStamp);
		invoiceDate1.endText();
		invoiceDate1.close();

		PDPageContentStream rentalDuration1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		rentalDuration1.beginText();
		rentalDuration1.setFont(PDType1Font.TIMES_ROMAN, 10);
		rentalDuration1.setNonStrokingColor(Color.BLACK);
		rentalDuration1.newLineAtOffset(510, 462);
		/*
		 * for (int x = 0; x < Customer.customerDatabase.size(); x++) { if
		 * (Customer.customerDatabase.get(x).getReservationNumber().equals(
		 * searchReservationNumber)) { rentalDuration1
		 * .showText(Customer.durationCalcuator(Customer.customerDatabase.get(x).
		 * getUserPickUpDate(), Customer.customerDatabase.get(x).getUserDropOffDate()) +
		 * " day(s)"); break; } }
		 */
		try {
			rentalDuration1.showText(
					Customer.durationCalcuator(df.parse(dropOffReservationData[ReservationConstants.PICKUP.getValue()]),
							df.parse(dropOffReservationData[ReservationConstants.DROPOFF.getValue()])) + " day(s)");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rentalDuration1.endText();
		rentalDuration1.close();

		PDPageContentStream insuranceCost1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		insuranceCost1.beginText();
		insuranceCost1.setFont(PDType1Font.TIMES_ROMAN, 9);
		insuranceCost1.setNonStrokingColor(Color.BLACK);
		insuranceCost1.newLineAtOffset(328, 462);
		/*
		 * for (int x1 = 0; x1 < Customer.customerDatabase.size(); x1++) { if
		 * (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * searchReservationNumber)) { if
		 * (Customer.customerDatabase.get(x1).getInsuranceNumber().equals("")) {
		 * insuranceCost1.showText(formatter
		 * .format(Customer.durationCalcuator(Customer.customerDatabase.get(x1).
		 * getUserPickUpDate(), Customer.customerDatabase.get(x1).getUserDropOffDate())
		 * * 20)); break; } else { insuranceCost1.showText("Personal Coverage"); break;
		 * } } }
		 */
		if (dropOffCustomerInfo[3].equals("")) {
			try {
				insuranceCost1.showText(formatter.format(Customer.durationCalcuator(
						df.parse(dropOffReservationData[ReservationConstants.PICKUP.getValue()]),
						df.parse(dropOffReservationData[ReservationConstants.DROPOFF.getValue()])) * 20));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			insuranceCost1.showText("Personal Coverage");
		}
		insuranceCost1.endText();
		insuranceCost1.close();

		PDPageContentStream dailyCost1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		dailyCost1.beginText();
		dailyCost1.setFont(PDType1Font.TIMES_ROMAN, 10);
		dailyCost1.setNonStrokingColor(Color.BLACK);
		dailyCost1.newLineAtOffset(409, 462);
		/*
		 * for (int x1 = 0; x1 < Customer.customerDatabase.size(); x1++) { if
		 * (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * searchReservationNumber)) { dailyPrice =
		 * Customer.dailyPrice(Customer.customerDatabase.get(x1).getCarType());
		 * dailyCost1.showText(formatter.format(dailyPrice)); break; } }
		 */
		dailyCost1.showText(formatter.format(Customer.dailyPrice(dropOffCarInfo[CarConstants.TYPE.getValue()])));
		dailyCost1.endText();
		dailyCost1.close();

		PDPageContentStream distanceCost1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		distanceCost1.beginText();
		distanceCost1.setFont(PDType1Font.TIMES_ROMAN, 10);
		distanceCost1.setNonStrokingColor(Color.BLACK);
		distanceCost1.newLineAtOffset(219, 462);

		distancePrice = distanceTravelled * 0.25;
		distanceCost1.showText(formatter.format(distancePrice));
		distanceCost1.endText();
		distanceCost1.close();

		PDPageContentStream totalCost1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		totalCost1.beginText();
		totalCost1.setFont(PDType1Font.TIMES_ROMAN, 10);
		totalCost1.setNonStrokingColor(Color.WHITE);
		totalCost1.newLineAtOffset(408, 442);
		/*
		 * double estimatedPrice = 0; for (int x1 = 0; x1 <
		 * Customer.customerDatabase.size(); x1++) {
		 * 
		 * if (Customer.customerDatabase.get(x1).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * 
		 * if (Customer.customerDatabase.get(x1).getInsuranceNumber().equals("")) {
		 * estimatedPrice = Customer.finalPriceCalculator(type,
		 * Customer.customerDatabase.get(x1).getUserPickUpDate(),
		 * Customer.customerDatabase.get(x1).getUserDropOffDate(), numberOfScratches,
		 * distanceTravelled, true); } else { estimatedPrice =
		 * Customer.finalPriceCalculator(type,
		 * Customer.customerDatabase.get(x1).getUserPickUpDate(),
		 * Customer.customerDatabase.get(x1).getUserDropOffDate(), numberOfScratches,
		 * distanceTravelled, false); } } }
		 * totalCost1.showText(formatter.format(estimatedPrice));
		 */
		totalCost1.endText();
		totalCost1.close();

		PDPageContentStream damageCost1 = new PDPageContentStream(document1, page1,
				PDPageContentStream.AppendMode.APPEND, false, true);
		damageCost1.beginText();
		damageCost1.setFont(PDType1Font.TIMES_ROMAN, 10);
		damageCost1.setNonStrokingColor(Color.BLACK);
		damageCost1.newLineAtOffset(110, 462);
		// damagePrice = numberOfScratches * 10.00;
		damageCost1.showText(formatter.format(damagePrice));
		damageCost1.endText();
		damageCost1.close();

		document1.save(new File("/SupremeCarRental/SupremeCarRentals/dropOffInvoice.pdf"));
		document1.close();

		/*
		 * for (int y = 0; y < Customer.customerDatabase.size(); y++) { if
		 * (Customer.customerDatabase.get(y).getReservationNumber().equals(
		 * searchReservationNumber)) {
		 * Customer.customerDatabase.get(y).setReservationNumber("Dropped-Off"); } }
		 */
	}

	/**
	 * Drop off car, where employee enters how far the car has traveled and the
	 * number of scratches it has encountered.
	 *
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>lblTorontoSupremeCar </b>Title of program
	 * <p>
	 * <b>lblOdometerReading </b> Creates an instance of JLabel.
	 * <p>
	 * <b>lblNumberOfScratches </b> Creates an instance of JLabel.
	 * <p>
	 * <b>lblAccident</b> Creates an instance of JLabel.
	 * <p>
	 * <b>lblCustomerProblem </b> Creates an instance of JLabel.
	 * <p>
	 * <b>lblCustomersRating</b> Creates an instance of JLabel.
	 * <p>
	 * <b>accidentComboBox </b> Creates an instance of JCombobox.
	 * <p>
	 * <b>customerProblemComboBox </b> Creates an instance of JCombobox.
	 * <p>
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void dropOffCar() {
		refresh();

		dropOffLicensePlate = db.getReservationLicensePlate(searchReservationNumber);
		dropOffCarInfo = db.getCar(dropOffLicensePlate);

		JLabel lblTorontoSupremeCar = new JLabel("Toronto Supreme Car Rentals");
		lblTorontoSupremeCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblTorontoSupremeCar.setForeground(Color.RED);
		lblTorontoSupremeCar.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		lblTorontoSupremeCar.setBounds(0, 29, 632, 63);
		add(lblTorontoSupremeCar);

		JLabel lblOdometerReading = new JLabel("Distance Travelled (KM) :");
		lblOdometerReading.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOdometerReading.setBounds(22, 124, 250, 27);
		add(lblOdometerReading);

		final JTextField odometerInput = new JTextField();
		odometerInput.setColumns(10);
		odometerInput.setBounds(262, 126, 100, 27);
		add(odometerInput);

		JLabel lblFuelCapacity = new JLabel("Fuel Capacity:");
		lblFuelCapacity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFuelCapacity.setBounds(22, 194, 200, 27);
		add(lblFuelCapacity);

		final JTextField fuelCapacityInput = new JTextField();
		fuelCapacityInput.setEditable(false);
		fuelCapacityInput.setColumns(10);
		fuelCapacityInput.setBounds(155, 196, 55, 27);
		fuelCapacityInput.setText(dropOffCarInfo[CarConstants.FUELCAPACITY.getValue()] + " L");
		add(fuelCapacityInput);

		JLabel lblFuelLevel = new JLabel("Fuel Level :");
		lblFuelLevel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFuelLevel.setBounds(22, 264, 200, 27);
		add(lblFuelLevel);

		final JTextField fuelLevelInput = new JTextField();
		fuelLevelInput.setColumns(10);
		fuelLevelInput.setBounds(130, 266, 55, 27);
		add(fuelLevelInput);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSubmit.setBackground(Color.RED);
		btnSubmit.setBounds(259, 330, 117, 39);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Submit")) {
					if (!odometerInput.getText().trim().matches("^\\d*[1-9]\\d*$")
							|| odometerInput.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Distance Travelled Must Be Positive Integer",
								"Input Error", JOptionPane.ERROR_MESSAGE);
						odometerInput.setText("");
						odometerInput.requestFocus();
					} else if (!fuelLevelInput.getText().trim().matches("^\\d*[1-9]\\d*$")
							|| fuelLevelInput.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Fuel Level Must Be Postive Integer!", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						fuelLevelInput.setText("");
						fuelLevelInput.requestFocus();
					} else if (Integer.parseInt(dropOffCarInfo[CarConstants.FUELCAPACITY.getValue()]) <= Integer
							.parseInt(fuelLevelInput.getText().trim())) {
						JOptionPane.showMessageDialog(null, "Fuel Level Must Be Less Than Fuel Capacity", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						fuelLevelInput.setText("");
						fuelLevelInput.requestFocus();
					} else {
						try {
							dropOffFuelDifference = Integer.parseInt(dropOffCarInfo[CarConstants.FUELCAPACITY.getValue()]) - 	
									Integer.parseInt(fuelLevelInput.getText().trim());
							db.updateCarData(dropOffLicensePlate,
									Integer.parseInt(odometerInput.getText().trim())
											+ Integer.parseInt(dropOffCarInfo[CarConstants.ODOMETER.getValue()]),
									Integer.parseInt(fuelLevelInput.getText().trim()));
							distanceTravelled = Integer.parseInt(odometerInput.getText().trim());
							dropOffReservationInfoReview();
						} catch (IOException e1) {
						}
					}
				}
			}
		});
		add(btnSubmit);
	}

	/**
	 * Search's for a reservation to see if it exists.
	 *
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>label</b>Title of program
	 * <p>
	 * <b>lblReservationNumber</b> Creates an instance of JLabel.
	 * <p>
	 * <b>btnNewButton</b> Creates an instance of JButton.
	 * <p>
	 * <b>reservationNumberInput</b> Creates an instance of JTextField.
	 * <p>
	 * <b>btnNewButton</b> Creates an instance of JButton
	 * <p>
	 */
	public void searchReservation() {
		refresh();

		JLabel label = new JLabel("Toronto Supreme Car Rentals");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 26));
		label.setBounds(0, 13, 632, 63);
		add(label);

		JLabel lblReservationNumber = new JLabel("Reservation Number: ");
		lblReservationNumber.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblReservationNumber.setBounds(12, 175, 244, 27);
		add(lblReservationNumber);

		final JTextField reservationNumberInput = new JTextField();
		reservationNumberInput.setFont(new Font("SansSerif", Font.PLAIN, 19));
		reservationNumberInput.setColumns(10);
		reservationNumberInput.setBounds(253, 175, 311, 34);
		add(reservationNumberInput);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.setActionCommand("Search");
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Search")) {
					searchReservationNumber = reservationNumberInput.getText().trim();

					if (db.reservationSearch(searchReservationNumber)) {
						dropOffCar();
					} else {
						JOptionPane.showMessageDialog(null, "Reservation Was Not Found!", "Try Again!",
								JOptionPane.ERROR_MESSAGE);
						reservationNumberInput.setText("");
						reservationNumberInput.requestFocus();
					}
				}
			}
		});
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(265, 294, 126, 51);
		add(btnNewButton);
		reservationNumberInput.requestFocus();
	}

	/**
	 * Drop off car info review.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>lblCarInformation</b>Creates an instance of JLabel.
	 * <p>
	 * <b>lblManufacturer</b> Creates an instance of JLabel.
	 * <p>
	 * <b>manufacturerText</b> Creates an instance of JTextField.
	 * <p>
	 * <b>lblModel</b> Creates an instance of JLabel.
	 * <p>
	 * <b>lblRepairsNeeded</b> Creates an instance of JLabel.
	 * <p>
	 * <b>repairsNeededText</b> Creates an instance of JTextField.
	 * <p>
	 * <b>lblNumberOfScratches</b> Creates an instance of JLabel.
	 * <p>
	 * <b>btnSaveAndPrint</b> Creates an instance of JButton.
	 * <p>
	 */
	public void dropOffCarInfoReview() {
		refresh();

		JLabel lblCarInformation = new JLabel("Car Information");
		lblCarInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarInformation.setFont(new Font("Gadugi", Font.PLAIN, 28));
		lblCarInformation.setBounds(6, 26, 632, 57);
		add(lblCarInformation);

		JLabel lblManufacturer = new JLabel("Manufacturer: ");
		lblManufacturer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblManufacturer.setBounds(22, 117, 140, 27);
		add(lblManufacturer);

		JTextField manufacturerText = new JTextField();
		manufacturerText.setEditable(false);
		manufacturerText.setColumns(10);
		manufacturerText.setBounds(164, 119, 160, 27);
		add(manufacturerText);

		JLabel lblModel = new JLabel("Model: ");
		lblModel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModel.setBounds(22, 177, 77, 27);
		add(lblModel);

		JTextField modelText = new JTextField();
		modelText.setEditable(false);
		modelText.setColumns(10);
		modelText.setBounds(164, 179, 158, 27);
		add(modelText);

		JLabel lblCarType = new JLabel("Car Type:");
		lblCarType.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCarType.setBounds(22, 227, 93, 27);
		add(lblCarType);

		JTextField carTypeText = new JTextField();
		carTypeText.setEditable(false);
		carTypeText.setColumns(10);
		carTypeText.setBounds(164, 229, 153, 27);
		add(carTypeText);

		JLabel lblMileage = new JLabel("Odometer:");
		lblMileage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMileage.setBounds(22, 279, 210, 27);
		add(lblMileage);

		JTextField mileageText = new JTextField();
		mileageText.setEditable(false);
		mileageText.setColumns(10);
		mileageText.setBounds(164, 281, 140, 27);
		add(mileageText);

		mileageText.setText(dropOffCarInfo[CarConstants.ODOMETER.getValue()] + " KM(s)");
		manufacturerText.setText(dropOffCarInfo[CarConstants.MANUFACTURER.getValue()]);
		modelText.setText(dropOffCarInfo[CarConstants.MODEL.getValue()]);
		carTypeText.setText(dropOffCarInfo[CarConstants.TYPE.getValue()]);
		
		JButton btnSaveAndPrint = new JButton("Save and Print Invoice");
		btnSaveAndPrint.setActionCommand("Print");
		btnSaveAndPrint.setForeground(Color.WHITE);
		btnSaveAndPrint.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveAndPrint.setBackground(Color.RED);
		btnSaveAndPrint.setBounds(428, 39, 186, 39);
		btnSaveAndPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Print")) {
					try {
						dropOffInvoiceCreator();
						new DropOffPrintInvoice();
					} catch (IOException | PrinterException e1) {
					}
				}
			}
		});
		add(btnSaveAndPrint);

		JButton button = new JButton("Done\r\n");
		button.setActionCommand("Next Page");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		button.setBackground(Color.RED);
		button.setBounds(270, 358, 117, 39);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Next Page")) {
					db.deleteReservation(dropOffReservationData[ReservationConstants.RESERVATION_NUMBER.getValue()]);
					mainMenu();
				}
			}
		});
		add(button);
	}

	/**
	 * Drop off reservation info review.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>lblReservationSummary</b>Creates an instance of JLabel.
	 * <p>
	 * <b>lblDropOffDate</b> Creates an instance of JLabel.
	 * <p>
	 * <b>insuranceCostText</b> Creates an instance of JTextField.
	 * <p>
	 * <b>lblPickUpDate</b> Creates an instance of JLabel.
	 * <p>
	 * <b>durationText</b> Creates an instance of JLabel.
	 * <p>
	 * <b>lblPricePerDay</b> Creates an instance of JLabel.
	 * <p>
	 * <b>pricePerDayText</b> Creates an instance of JTextField.
	 * <p>
	 * <b>damageCostText</b> Creates an instance of JTextField.
	 * <p>
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void dropOffReservationInfoReview() throws IOException {
		refresh();

		int numberOfScratches = 0;
		String insuranceCost = "";
		double estimatedPrice = 0;

		dropOffCarInfo = db.getCar(dropOffLicensePlate);
		dropOffReservationData = db.getReservation(searchReservationNumber);

		try {
			if (db.personalCoverage(dropOffReservationData[ReservationConstants.RESERVATION_NUMBER.getValue()])) {
				estimatedPrice = Price.finalPriceCalculator(dropOffCarInfo[CarConstants.TYPE.getValue()],
						df.parse(dropOffReservationData[ReservationConstants.PICKUP.getValue()]),
						df.parse(dropOffReservationData[ReservationConstants.DROPOFF.getValue()]),
						Integer.parseInt(dropOffCarInfo[CarConstants.ODOMETER.getValue()]), dropOffFuelDifference,
						gasPrice, true);

			} else {

				estimatedPrice = Price.finalPriceCalculator(dropOffCarInfo[CarConstants.TYPE.getValue()],
						df.parse(dropOffReservationData[ReservationConstants.PICKUP.getValue()]),
						df.parse(dropOffReservationData[ReservationConstants.DROPOFF.getValue()]),
						Integer.parseInt(dropOffCarInfo[CarConstants.ODOMETER.getValue()]), dropOffFuelDifference,
						gasPrice, false);
			}

		} catch (NumberFormatException | ParseException e1) {
			e1.printStackTrace();
		}

		// If gas in vehicle not full, fill it up
		if (Integer.parseInt(dropOffCarInfo[CarConstants.FUELLEVEL.getValue()]) != Integer
				.parseInt(dropOffCarInfo[CarConstants.FUELCAPACITY.getValue()])) {
			db.fillUpFuel(dropOffLicensePlate, Integer.parseInt(dropOffCarInfo[CarConstants.FUELCAPACITY.getValue()]));
		}

		if (!db.customerCoverage(searchReservationNumber)) {
			try {
				insuranceCost = formatter.format(Price.durationCalcuator(
						df.parse(dropOffReservationData[ReservationConstants.PICKUP.getValue()]),
						df.parse(dropOffReservationData[ReservationConstants.DROPOFF.getValue()])) * 20);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			insuranceCost = "Personal Coverage";
		}

		JLabel lblReservationSummary = new JLabel("Reservation Summary");
		lblReservationSummary.setHorizontalAlignment(SwingConstants.CENTER);
		lblReservationSummary.setFont(new Font("Gadugi", Font.PLAIN, 28));
		lblReservationSummary.setBounds(0, 25, 632, 57);
		add(lblReservationSummary);

		JLabel lblPickupDate = new JLabel("Pick-Up Date & Time: ");
		lblPickupDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPickupDate.setBounds(12, 113, 234, 27);
		add(lblPickupDate);

		JTextField pickUpDateText = new JTextField();
		pickUpDateText.setEditable(false);
		pickUpDateText.setColumns(10);
		pickUpDateText.setBounds(222, 115, 194, 27);
		add(pickUpDateText);

		JLabel lblDropoffDate = new JLabel("Drop-Off Date & Time: ");
		lblDropoffDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDropoffDate.setBounds(12, 175, 234, 27);
		add(lblDropoffDate);

		JTextField dropOffDateText = new JTextField();
		dropOffDateText.setEditable(false);
		dropOffDateText.setColumns(10);
		dropOffDateText.setBounds(222, 175, 194, 27);
		add(dropOffDateText);

		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDuration.setBounds(12, 238, 111, 27);
		add(lblDuration);

		JTextField durationText = new JTextField();
		durationText.setEditable(false);
		durationText.setColumns(10);
		durationText.setBounds(149, 240, 122, 27);
		add(durationText);

		JLabel lblPricePerDay = new JLabel("Price Per Day:");
		lblPricePerDay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPricePerDay.setBounds(300, 238, 138, 27);
		add(lblPricePerDay);

		JTextField pricePerDayText = new JTextField();
		pricePerDayText.setEditable(false);
		pricePerDayText.setColumns(10);
		pricePerDayText.setBounds(493, 240, 122, 27);
		add(pricePerDayText);

		JLabel lblDistanceCost = new JLabel("Distance Cost: ");
		lblDistanceCost.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDistanceCost.setBounds(12, 298, 138, 27);
		add(lblDistanceCost);

		JTextField distanceCostText = new JTextField();
		distanceCostText.setEditable(false);
		distanceCostText.setText(formatter.format((distanceTravelled) * 0.25));
		distanceCostText.setColumns(10);
		distanceCostText.setBounds(149, 300, 122, 27);
		add(distanceCostText);

		JLabel lblEstimatedTotalPrice = new JLabel("Total Price:");
		lblEstimatedTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEstimatedTotalPrice.setBounds(300, 350, 211, 27);
		add(lblEstimatedTotalPrice);

		JTextField totalPriceText = new JTextField();
		totalPriceText.setEditable(false);
		totalPriceText.setText(formatter.format(estimatedPrice));
		totalPriceText.setColumns(10);
		totalPriceText.setBounds(493, 350, 122, 27);
		add(totalPriceText);

		JLabel lblFuelCost = new JLabel("Fuel Cost:");
		lblFuelCost.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFuelCost.setBounds(300, 300, 211, 27);
		add(lblFuelCost);

		JTextField fuelCostText = new JTextField();
		fuelCostText.setEditable(false);
		fuelCostText.setText(formatter.format(numberOfScratches * 10.00));
		fuelCostText.setColumns(10);
		fuelCostText.setBounds(493, 300, 122, 27);
		add(fuelCostText);

		JLabel lblInsuranceCost = new JLabel("Insurance Cost:");
		lblInsuranceCost.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInsuranceCost.setBounds(12, 350, 211, 27);
		add(lblInsuranceCost);

		JTextField insuranceCostText = new JTextField();
		insuranceCostText.setEditable(false);
		insuranceCostText.setText(insuranceCost);
		insuranceCostText.setColumns(10);
		insuranceCostText.setBounds(160, 350, 122, 27);
		add(insuranceCostText);

		fuelCostText.setText(formatter.format(dropOffFuelDifference * gasPrice));
		pickUpDateText.setText(dropOffReservationData[ReservationConstants.PICKUP.getValue()]);
		dropOffDateText.setText(dropOffReservationData[ReservationConstants.DROPOFF.getValue()]);
		try {
			durationText
					.setText(""
							+ Customer.durationCalcuator(
									df.parse(dropOffReservationData[ReservationConstants.PICKUP.getValue()]),
									df.parse(dropOffReservationData[ReservationConstants.DROPOFF.getValue()]))
							+ " day(s)");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String priceDay = formatter.format(Customer.dailyPrice(dropOffCarInfo[CarConstants.TYPE.getValue()]));
		pricePerDayText.setText(priceDay);

		JButton btnOk = new JButton("Ok");
		btnOk.setActionCommand("Ok");
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOk.setBackground(Color.RED);
		btnOk.setBounds(253, 400, 117, 39);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Ok")) {
					dropOffCarInfoReview();
				}
			}
		});
		add(btnOk);

	}
}