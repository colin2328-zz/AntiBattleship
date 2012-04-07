package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

// ActionListener that allows for setting the board
public class SetBoardListener implements ActionListener {
	private InitialPanel i;
	private File f = new File("tbawht00.wav");
	private AudioStream s;
	private FileInputStream fs;

	public SetBoardListener(InitialPanel i) {
		this.i = i;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Checks if board size is valid and unlocks add ship functionality
		try {
			if ((Integer.parseInt(i.xField.getText()) > 0)
					&& (Integer.parseInt(i.yField.getText()) > 0)) {
				i.getAddShipButton().setEnabled(true);
				i.getSizeShip().setEnabled(true);
				i.xField.setEnabled(false);
				i.yField.setEnabled(false);
				i.getSetBoardButton().setEnabled(false);
				i.getCurrentAction().setText("");
				i.getInstructions().setText(
						"Enter a ship size ranging from 1 to "
								+ Math.min(
										Integer.parseInt(i.xField.getText()),
										Integer.parseInt(i.yField.getText()))
								+ " and hit 'Enter'.");
				i.createdShipsLabel.setText("(Maximum of "
						+ Math.max(Integer.parseInt(i.xField.getText()),
								Integer.parseInt(i.yField.getText())) / 2
						+ " ships): ");
				try {
					fs = new FileInputStream(f);
					s = new AudioStream(fs);
					AudioPlayer.player.start(s);
				} catch (IOException er) {
					er.printStackTrace();
				}
				i.getSizeShip().requestFocus();

			} else {
				i.getCurrentAction().setText(
						"INVALID ENTRY: Entries must be positive integers.");
			}
		} catch (java.lang.NumberFormatException e) {
			i.getCurrentAction().setText(
					"INVALID: Entries must be positive integers.");
		}
	}
}
