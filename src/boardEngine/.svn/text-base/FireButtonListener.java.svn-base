package boardEngine;

/*@author Kevin and Zach*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import communication.ClientManager;
import communication.ConstructMessage;
import datatypes.Coordinate;

// ActionListener that triggers 'fire' with update/sound
public class FireButtonListener implements ActionListener {
	private OpponentBoardGUI g;
	private ClientManager cm;

	// Audio capability
	private ArrayList<File> f = new ArrayList<File>();
	private AudioStream s;
	private FileInputStream fs;

	public FireButtonListener(ClientManager cm, OpponentBoardGUI g) {
		this.g = g;
		this.cm = cm;
		// Various audio files that are selected at random
		f.add(new File("explosion-01.wav"));
		f.add(new File("explosion-02.wav"));
		f.add(new File("explosion-03.wav"));
		f.add(new File("explosion-04.wav"));
		f.add(new File("explosion-06.wav"));
	}

	// Plays audio, constructs message, and locks FireButton afterwards
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!g.getMute().isSelected()) {
			int i = (int) (Math.random() * f.size());
			try {
				fs = new FileInputStream(f.get(i));
				s = new AudioStream(fs);
				AudioPlayer.player.start(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (g.getCm().isSalvoMode()) {
			cm.addToQueue(ConstructMessage.makeSalvoTargetMessage(g.listOfTargets));
			g.getFireButton().setEnabled(false);
			g.listOfTargets.clear();
		}

		else {

			// Sets last targeted to the 'fired' cell
			cm.getOpponentBoardModel().setLastTargeted(g.getTargetedCell());

			// Sends message to queue
			cm.addToQueue(ConstructMessage.makeTargetMessage(g
					.getTargetedCell().getRow(), g.getTargetedCell()
					.getColumn()));

			// Locks fire button
			g.getFireButton().setEnabled(false);
		}
	}
}
