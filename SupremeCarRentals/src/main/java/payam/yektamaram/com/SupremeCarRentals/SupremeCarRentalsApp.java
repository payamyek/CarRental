package payam.yektamaram.com.SupremeCarRentals;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * Driver Class Of Application.
 *
 *
 *
 * <p>
 * <h2>Global Variables</h2>
 * <p>
 * <b>a</b> Creates an instance of the ProgramInterfaceClass
 * <p>
 * @author Payam Yektamaram, Daniel Peravalov, and Ian Smyth.
 * @version 2.0.0
 * @since 2018-05-11
 */

@SuppressWarnings("serial")
public class SupremeCarRentalsApp extends JFrame 
{
	/**
	 * Adds program interface to JFrame and sets size to 650 by 500 and makes it
	 * visible.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public SupremeCarRentalsApp() {
		super("Toronto Supreme Car Rentals");
		add(new ProgramInterface());
		setSize(650, 500);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ignored) {
		}

		SplashScreen a = new SplashScreen();
		a.setVisible(true);
		a.update();
		if (a.progressBar.getValue() == 100) {
			a.setVisible(false);
		}
	
		new SupremeCarRentalsApp();
	}
}

