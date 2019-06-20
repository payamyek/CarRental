package payam.yektamaram.com.SupremeCarRentals;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * This class displays the data from CSV file in a JTabel.
 * 
 * <p>
 * <h2>Global Variables</h2>
 * <p>
 * <b>data - </b> Holds the data.
 * <p>
 * <b>data2 - </b> Holds the changed data.
 * <p>
 * <b>columnNames - </b> Holds column names.
 * <p>
 * <b>myList - </b> Holds car data.
 * <p>
 * <b>c1 - </b> Instantiates a new instance of class Car.
 * <p>
 * <b>transData - </b> ArrayList of transferred data from the data array.
 * <p>
 * <b>jtfFilter - </b> JTextField for filtered text.
 * <p>
 * 
 * @version 1.2.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
 * @since 2018-05-14
 */


@SuppressWarnings("serial")
public class InfoJTable extends JFrame {

	protected static Object[][] data;

	protected static String[][] data2;

	/** The column names. */
	private String[] columnNames = { "License Number", "Frequency Of Use", "Average Rating", "Past Problems",
			"Car Preference" };

	private ReservationData myList;

	/** The instanced Car class. */

	Customer c1 = new Customer();

	@SuppressWarnings("static-access")
	private ArrayList<String> transData = new ArrayList<String>(c1.customerDatabase.size());

	private JTextField jtfFilter = new JTextField();

	
	/**
	   * Instantiates an InfoJTable
	   * 
	   * <p>
	   * <h2>Local Variables</h2>
	   * <p>
	   * <b>model - </b> Instance of DefaultTableModel
	   * <p>
	   * <b>table - </b> Instance of JTable.
	   * <p>
	   * <b>rowSorter - </b> Instance of TableRowSorter that sorts the table.
	   * <p>
	   * <b>panel - </b> Stores all of the JComponents.
	   * <p>
	   * <b>nextButton - </b> Brings the user back to the main menu.
	   * <p>
	   */
	public InfoJTable() throws ParseException {

		setBounds(10, 10, 655, 655);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myList = new ReservationData();
		ReservationData.readFromCSV("reservationDatabase.csv");
		data = myList.convert2Data();

		int counter = 0;

		for (int a = 0; a <= data.length - 1; a++) {
			transData.add(String.valueOf(data[a][3]));
			transData.add(String.valueOf(getFreq(data, String.valueOf(data[a][3]))));
			transData.add(String.valueOf(getAvgRating(data, String.valueOf(data[a][3]))));
			transData.add(String.valueOf(data[a][13]));
			transData.add(String.valueOf(data[a][8] + " " + data[a][9]));
			if (data.length - 1 == 1 && !(transData.get(counter).equals(data[a + 1][3]))) {
				transData.add(String.valueOf(data[a + 1][3]));
				transData.add(String.valueOf(getFreq(data, String.valueOf(data[a + 1][3]))));
				transData.add(String.valueOf(getAvgRating(data, String.valueOf(data[a + 1][3]))));
				transData.add(String.valueOf(data[a + 1][13]));
				transData.add(String.valueOf(data[a + 1][8] + " " + data[a + 1][9]));
				break;
			} else if (data.length - 1 == 0) {
				break;
			}
			if (a <= data.length - 1 && a >= 0) {
				if (transData.get(counter).equals(data[a + 1][3])) {
					counter = counter + 5;
					a = a + 1;
				}
			}

		}
		data2 = convertAgain2Data();

		DefaultTableModel model = new DefaultTableModel(data2, columnNames);
		JTable table = new JTable(model);
		final TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());

		table.setRowSorter(rowSorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		add(panel, BorderLayout.SOUTH);
		add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
				// choose Tools | Templates.
			}

		});
		table.setSelectionBackground(Color.blue);
		table.setFocusable(false);
		table.setRowSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JButton nextButton = new JButton("Back To Main Menu");
		nextButton.setForeground(Color.WHITE);
		nextButton.setBackground(Color.RED);
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Back To Main Menu"))
					setVisible(false);
			}
		});
		panel.add(nextButton, BorderLayout.EAST);
		add(panel, BorderLayout.SOUTH);
		add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

	}

	
	 /**
	   * Returns the frequency of a license number.
	   * 
	   * <p>
	   * <h2>Local Variables</h2>
	   * <p>
	   * <b>frequency - </b> The amount of times a license number appears.
	   * <p>
	   * 
	   * @param objArray
	   *           2D Array
	   * @param license
	   *           The String license
	   */
	public static int getFreq(Object[][] objArray, String license) {
		int frequency = 0;
		for (int a = 0; a < objArray.length - 1; a++) {
			if (objArray[a][3].equals(license)) {
				frequency++;
			}
		}
		return frequency;
	}

	/**
	   * Returns the average rating of a customer.
	   * 
	   * <p>
	   * <h2>Local Variables</h2>
	   * <p>
	   * <b>avgRating - </b> The average rating of a customer.
	   * <p>
	   * 
	   * @param objArray
	   *           2D Array
	   * @param license
	   *           The String license
	   */
	public static int getAvgRating(Object[][] objArray, String license) {
		int avgRating = 0;
		for (int a = 0; a < objArray.length - 1; a++) {
			if (objArray[a][3].equals(license)) {
				avgRating = avgRating + (int) objArray[a][14];
			}
		}
		if (avgRating == 0) {
			return 0;
		} else {
			return avgRating / (getFreq(data, license));
		}
	}

	
	/**
	   * Returns the data but shortened to the InfoJTable
	   * 
	   * <p>
	   * <h2>Local Variables</h2>
	   * <p>
	   * <b>data2 - </b> The shortened version of data.
	   * <p> 
	   */
	public String[][] convertAgain2Data() {
		String[][] data2 = new String[transData.size()][5];
		int a = 0;
		for (int i = 0; i < 5; i++) {
			a = a + i;
			for (int b = 0; b <= data.length - 1; b++, a = a + 5) {
				if (a > transData.size()) {
					break;
				}
				data2[b][i] = transData.get(a);
			}
			a = 0;
		}
		return data2;
	}
}