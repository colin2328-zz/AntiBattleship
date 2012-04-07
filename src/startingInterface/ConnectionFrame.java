package startingInterface;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import communication.MasterServer;
import info.clearthought.layout.TableLayout;

public class ConnectionFrame extends JFrame {
	// Declares components of ConnectionFrame
	private Container cp = this.getContentPane();
	final JPanel bottom = new JPanel();
	final ImageIcon titleBanner = createImageIcon("TitleBanner.jpg");
	final ImageIcon shipGraphic = createImageIcon("battleship.jpg");

	final JLabel hostLabel = new JLabel("Host: ", JLabel.CENTER);
	final JLabel portLabel = new JLabel("Port: ", JLabel.CENTER);
	final JLabel title = new JLabel(titleBanner, JLabel.CENTER);
	final JLabel ship = new JLabel(shipGraphic, JLabel.CENTER);

	final JTextField addressField = new JTextField("6005-abs.xvm.mit.edu");
	final JTextField portField = new JTextField("1338");
	final JButton initiateButton = new JButton("Initiate game with: ");
	final JButton playAIButton = new JButton("Play Against Computer");

	private MasterServer masterServer;

	/**
	 * Constructor of ConnectionFrame
	 * 
	 * @param ms
	 *            MasterServer to run game
	 */
	public ConnectionFrame(MasterServer ms) {
		// Sets title of JFrame
		super("ANTI-BATTLESHIP");
		this.masterServer = ms;

		// Sizes for layouts in ConnectionFrame
		double sizeCP[][] = {
				{ 10, TableLayout.PREFERRED, 10 },
				{ TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10,
						TableLayout.PREFERRED, 10 } };
		double sizeBottom[][] = {
				{ 50, TableLayout.FILL, 25, TableLayout.FILL, 50 },
				{ TableLayout.FILL, TableLayout.FILL, TableLayout.FILL } };

		// Sets layouts and background colors
		cp.setLayout(new TableLayout(sizeCP));
		cp.setBackground(Color.black);
		bottom.setLayout(new TableLayout(sizeBottom));
		bottom.setBackground(Color.black);
		hostLabel.setForeground(Color.lightGray);
		portLabel.setForeground(Color.lightGray);

		// Adds everything into panels
		bottom.add(hostLabel, "0,1");
		bottom.add(portLabel, "0,2");
		bottom.add(addressField, "1,1");
		bottom.add(portField, "1,2");
		bottom.add(initiateButton, "1,0");
		bottom.add(playAIButton, "3,0");
		cp.add(title, "1,0");
		cp.add(ship, "1,2");
		cp.add(bottom, "1,4");

		// Adds ConnectListener to initiateButton
		initiateButton.addActionListener(new ConnectListener(this.masterServer,
				this));

		playAIButton.addActionListener(new StartAIListener(this.masterServer));
	}

	/**
	 * Graphic generator method
	 * 
	 * @param path
	 *            String that specifies file location
	 * @return returns ImageIcon with specified path for graphical use
	 */
	protected ImageIcon createImageIcon(String path) {
		return new ImageIcon(path);
	}

	// Getters/Setters
	public String getIPAddress() {
		return addressField.getText();
	}

	public int getPort() {
		return Integer.parseInt(portField.getText().trim());
	}
}
