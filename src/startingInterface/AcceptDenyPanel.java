package startingInterface;

import info.clearthought.layout.TableLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import communication.ClientManager;

// Panel that displays accepting and denying buttons for incoming requests
public class AcceptDenyPanel extends JPanel {
	private ClientManager cm;
	private JButton acceptGame = new JButton("Accept");
	private JButton denyGame = new JButton("Deny");

	/**
	 * Constructor for AcceptDenyPanel
	 * 
	 * @param cm
	 *            ClientManager to base messages off of
	 */
	public AcceptDenyPanel(ClientManager cm) {
		this.cm = cm;
		double sizeCP[][] = { { TableLayout.PREFERRED, TableLayout.PREFERRED },
				{ TableLayout.PREFERRED } };
		this.setLayout(new TableLayout(sizeCP));
		this.add(acceptGame, "0,0");
		this.add(denyGame, "1,0");
		acceptGame.setEnabled(false);
		denyGame.setEnabled(false);

		acceptGame.addActionListener(new AcceptGameListener(this.cm));
		denyGame.addActionListener(new DenyGameListener(this.cm));
	}
	/**
	 * Toggles accept and deny to enabled
	 */
	public void toggleAcceptDeny() {
		acceptGame.setEnabled(true);
		denyGame.setEnabled(true);
	}

}
