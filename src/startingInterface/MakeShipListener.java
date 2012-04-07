package startingInterface;

/*@author Kevin*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Creates ship to add to board
public class MakeShipListener implements ActionListener {
	public SetUp s;

	public MakeShipListener(SetUp s) {
		this.s = s;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		s.getBoardGUI().getCells().get(s.getFirstCell())
				.setBackground(Color.darkGray);
	}

}