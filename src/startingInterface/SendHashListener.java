package startingInterface;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import boardEngine.HostBoardModel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import communication.ClientManager;

// Sends hash messages
public class SendHashListener implements ActionListener {
	private ClientManager cm;
	private HostBoardModel hbm;
	private File f = new File("intro.wav");
	private AudioStream st;
	private FileInputStream fs;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			fs = new FileInputStream(f);
			st = new AudioStream(fs);
			AudioPlayer.player.start(st);
		} catch (IOException er) {
			er.printStackTrace();
		}
		
		if (cm.isValidSetup()) {
			cm.prepareHash();
		}
	}
	
	// Constructor for SendHashListner
	public SendHashListener(ClientManager cm) {
		this.cm = cm;
		this.hbm = cm.getHostBoardModel();
	}
	
	
}
