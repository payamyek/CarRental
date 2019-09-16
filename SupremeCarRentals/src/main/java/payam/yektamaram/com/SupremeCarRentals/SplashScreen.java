package payam.yektamaram.com.SupremeCarRentals;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * SplashScreen of program, newly added this week. Total hours spent on program: 1
 *
 * 
 * <p>
 * <h2>Global Variables</h2>
 * <p>
 * <b>contentPane - </b> An instance of the JPanel class.
 * <p>
 * <b>progressBar - </b> An instance of the JProgressBar class.
 * <p>
 * <b>loadingText - </b> An instance of the JLabel class.
 * <p>
 * <b>backgroundImage - </b> An instance of the JLabel class and is the
 * background image of the splash screen.
 * <p>
 * 
 *
 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
 * @version 2.0.0
 * @since 2018-05-22
 */

@SuppressWarnings("serial")
public class SplashScreen extends JFrame {

	private JPanel contentPane;
	JProgressBar progressBar;
	private JLabel loadingText;
	private JLabel backgroundImage;

	/* Creates the splash screen.
	 * 
	 * 
	 * <h2>Local Variables</h2>
	 * <p>
	 * <b>panel - </b> Holds the JComponents.
	 * <p>
	 * <b>progressBar - </b> The progressBar in the splash screen.
	 * <p>
	 * <b>loadingText - </b> A JLabel holding the loading text.
	 * <p>
	 * <b>backgroundImage - </b> An instance of JLabel that holds the background image.
	 * <p>
	 * 
	 * @version 1.2.0
	 * @author Payam Yektamaram, Daniel Perevalov, and Ian Smyth.
	 * @since 2018-05-14
	 */
	public SplashScreen() {
		super("Loading Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 102, 102));
		panel.setBackground(Color.BLACK);
		panel.setBounds(-12, 357, 658, 158);
		contentPane.add(panel);
		panel.setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);

		progressBar.setBackground(Color.WHITE);
		progressBar.setForeground(Color.BLUE);
		progressBar.setBounds(41, 51, 562, 71);
		panel.add(progressBar);

		loadingText = new JLabel("Loading Progam Files...");
		loadingText.setBounds(41, 0, 562, 52);
		panel.add(loadingText);
		loadingText.setHorizontalAlignment(SwingConstants.CENTER);
		loadingText.setFont(new Font("Verdana", Font.PLAIN, 20));
		loadingText.setForeground(Color.WHITE);

		backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 632, 357);
		//Icon img = new ImageIcon(getClass().getResource("bmw_bmw_m4_car_car_wallpapers_.jpg"));
		Icon img = new ImageIcon(getClass().getClassLoader().getResource("bmw_bmw_m4_car_car_wallpapers_.jpg"));
		backgroundImage.setIcon(img);
		contentPane.add(backgroundImage);
	}

	/**
	 * Updates the Splash Screen every 100 milliseconds.
	 */
	public void update() {
		int i = 0;

		while (i <= 100) {
			progressBar.setValue(i);
			i++;
			if (progressBar.getValue() > 20) {
				loadingText.setText("Setting Up Program Environment...");
			}
			if (progressBar.getValue() > 40) {
				loadingText.setText("Configuring Program Settings...");
			}
			if (progressBar.getValue() > 60) {
				loadingText.setText("Connecting to Local Database...");
			}
			if (progressBar.getValue() > 80) {
				loadingText.setText("Starting Application...");
			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}

}
