package startingInterface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import communication.ClientManager;
import communication.ConstructMessage;
import datatypes.Message;

/**
 * Sends an init-game message after the user has entered a valid board
 * configuration
 * 
 * @author Zach
 * 
 */
public class SendParametersListener implements ActionListener {
	private ClientManager cm;
	private InitialPanel initPanel;
	private File f = new File("tbawht02.wav");
	private AudioStream s;
	private FileInputStream fs;

	public SendParametersListener(InitialPanel initialPanel, ClientManager cm) {
		this.cm = cm;
		this.initPanel = initialPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			fs = new FileInputStream(f);
			s = new AudioStream(fs);
			AudioPlayer.player.start(s);
		} catch (IOException er) {
			er.printStackTrace();
		}

		cm.setupBoardModels(initPanel.getGameWidth(),
				initPanel.getGameHeight(), initPanel.getShips());
		
		Message initMsg;
		if (initPanel.getSalvo()) {
			cm.setSalvoMode(true);
			initMsg = ConstructMessage.makeSalvoInitGameMessage(
					initPanel.getGameWidth(), initPanel.getGameHeight(),
					initPanel.getShips());
			cm.addToQueue(initMsg);
		}
		else {
			cm.setSalvoMode(false);
			cm.addToQueue(ConstructMessage.makeInitGameMessage(
					initPanel.getGameWidth(), initPanel.getGameHeight(),
					initPanel.getShips()));
		}
	}
}
