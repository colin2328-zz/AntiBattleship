package communication;

import info.clearthought.layout.TableLayout;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import startingInterface.AcceptDenyPanel;

/**
 * ChatOnlyPanel is a panel to display for player B while player B is awaiting incoming parameters
 * It shows chat and the GameUpdatePanel.
 * @author Zach
 *
 */

public class ChatOnlyPanel extends JPanel {
	JButton acceptButton = new JButton("Accept");
	JButton denyButton = new JButton("Deny");
	JLabel acceptDeny = new JLabel("Waiting for parameters...");
	ClientManager cm;
	public ChatOnlyPanel(ClientManager cm,boolean what) {
		//denyButton.setEnabled(what);
		//acceptButton.setEnabled(what);
		
		double sizeCP[][] = { { TableLayout.PREFERRED, TableLayout.PREFERRED},{TableLayout.PREFERRED} };
		
		double sizeLeft[][] = { {TableLayout.PREFERRED }, {30, TableLayout.PREFERRED, 30, TableLayout.PREFERRED, 30, TableLayout.PREFERRED, 30 } };
		
		this.setLayout(new TableLayout(sizeCP));
		
		AcceptDenyPanel acceptDenyPanel = new AcceptDenyPanel(cm);
		if (what) {
//			System.out.println("in here");
		 acceptDenyPanel.toggleAcceptDeny();
		}
		this.add(acceptDenyPanel,"0,0");
		this.add(cm.gameUpdatePanel,"1,0");
	}
}
