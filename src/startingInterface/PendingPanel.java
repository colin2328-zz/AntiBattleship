package startingInterface;

import javax.swing.JLabel;
import javax.swing.JPanel;

import info.clearthought.layout.TableLayout;

/**
 * Dummy class to show a pending screen
 * @author Zach
 * 
 */
public class PendingPanel extends JPanel {
	JLabel pendingLabel;
	
	public PendingPanel(String text) {
		double sizeCP[][]  = { {TableLayout.PREFERRED,TableLayout.PREFERRED } , {TableLayout.PREFERRED} };
		pendingLabel = new JLabel("Game Pending.");
		this.setLayout(new TableLayout(sizeCP));
		this.add(pendingLabel,"0,0");
	}
}
