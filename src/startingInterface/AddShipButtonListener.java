package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

// ActionsListener that adds ships to shipSizes 
public class AddShipButtonListener implements ActionListener {
	private InitialPanel i;
	private File f = new File("drill.wav");
	private AudioStream s;
	private FileInputStream fs;

	public AddShipButtonListener(InitialPanel initialPanel) {
		this.i = initialPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Checks valid parameters of ships and allows for ships to be added
		try {
			if (i.shipSize.size() < Math.max(
					Integer.parseInt(i.xField.getText()),
					Integer.parseInt(i.yField.getText())) / 2) {
				if (Integer.parseInt(i.getSizeShip().getText()) > 0
						&& Integer.parseInt(i.getSizeShip().getText()) <= Math
								.min(Integer.parseInt(i.xField.getText()),
										Integer.parseInt(i.yField.getText()))) {
					i.shipSize.add(Integer.parseInt(i.getSizeShip().getText()));
					try {
						fs = new FileInputStream(f);
						s = new AudioStream(fs);
						AudioPlayer.player.start(s);
					} catch (IOException er) {
						er.printStackTrace();
					}
					if (i.getOutput() == "")
						i.setOutput(i.getOutput() + i.getSizeShip().getText());
					else {
						i.setOutput(i.getOutput()
								+ (", " + i.getSizeShip().getText()));
					}

					i.getCurrentAction().setText("");
					i.getCreatedShipsList().setText(i.getOutput());
					i.getSizeShip().setText("");
					i.getSizeShip().requestFocus();
					i.getSendGameParameters().setEnabled(true);
				} else {
					i.getCurrentAction()
							.setText(
									"INVALID: Entry must be a positive integer within ship size range.");
					i.getSizeShip().setText("");
				}
			} else {
				i.getCurrentAction()
						.setText(
								"INVALID: Maximum number of ships reached. Either clear or send.");
				i.getAddShipButton().setEnabled(false);
			}
		} catch (java.lang.NumberFormatException e1) {
			i.getCurrentAction()
					.setText(
							"INVALID: Entry must be a positive integer within ship size range.");
			i.getSizeShip().setText("");
		}
	}
}