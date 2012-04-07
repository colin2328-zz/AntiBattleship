package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Clear button in in InitialPanel
public class ClearButtonListener implements ActionListener {
	private InitialPanel i;

	public ClearButtonListener(InitialPanel initialPanel) {
		this.i = initialPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Clears shipSize and clears list
		i.shipSize.clear();
		i.setOutput("");
		i.getCreatedShipsList().setText("");
		// Locks SendGameParameters
		i.getSendGameParameters().setEnabled(false);
		
		// Clears/locks SizeShip as well addShipButton
		i.getSizeShip().setText("");
		i.getSizeShip().setEnabled(false);
		i.getAddShipButton().setEnabled(false);
		
		// Unlocks setBoardButton and restores original
		i.getSetBoardButton().setEnabled(true);
		i.xField.setEnabled(true);
		i.yField.setEnabled(true);
		i.xField.setText("10");
		i.yField.setText("10");
		
		// Updates instructions
		i.getCurrentAction().setText("All entries cleared.");
		i.getInstructions().setText("Please enter board size and click on 'Set Board'.");
	}
}
