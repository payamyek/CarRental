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
 * <b>columnNames - </b> Holds column names.
 * <p>
 * <b>tableModel - </b> JTable table model.
 * <p>
 * <b>table - </b> Instance of JTable.
 * <p>
 * <b>myList - </b> Holds car data.
 * <p>
 * <b>c1 - </b> Instantiates a new instance of class Car.
 * <p>
 * 
 * @version 1.2.0
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
 * @since 2018-05-14
 */

@SuppressWarnings("serial")
public class ReservationJTable extends JFrame {

	/** The data. */
	protected static Object[][] data;
	
	/** The column names. */
	private String[] columnNames = { "Pick-up Date", "Drop-off Date", "License Plate", "Reservation Number"};

	/** The table model. */
	@SuppressWarnings("unused")
	private DefaultTableModel tableModel;

	/** The table. */
	@SuppressWarnings("unused")
	private JTable table;
	

	private JTextField jtfFilter = new JTextField();

	/**
	 * Instantiates a new car J table.
	 *
	 * @param title
	 *            the title
	 * @throws ParseException
	 */
	public ReservationJTable() throws ParseException {
		setBounds(10, 10, 1400, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		data = SQLite.getReservationTableData();
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		final TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());

		table.setRowSorter(rowSorter);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Search for a record:"), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);

		setLayout(new BorderLayout());

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
	 * Searches for a reservation using a reservation number as a parameter.
	 * 
	 * <p>
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>check - </b> Boolean that is true only when a reservation has been found.
	 * <p>
	 *
	 * @param reservationNumber
	 *            the reservation number
	 * @throws ParseException
	 */
	public static boolean searchForReservation(String reservationNumber) throws ParseException {
		new ReservationJTable();
		boolean check = false;
		for (int i = 0; i < ReservationData.reservationList.size(); i++) {
			if ((data[i][12].equals(reservationNumber))) {
				check = true;
			}
		}
		return check;
	}
}
