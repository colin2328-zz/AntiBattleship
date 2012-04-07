package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import communication.MasterServer;

// Initiates a connected game in InitialPanel
public class ConnectListener implements ActionListener {
	private MasterServer masterServer;
	private ConnectionFrame connectionFrame;
	
	private File f = new File("tbawht03.wav");
	private AudioStream s;
	private FileInputStream fs;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			fs = new FileInputStream(f);
			s = new AudioStream(fs);
			AudioPlayer.player.start(s);
		} catch (IOException er) {
			er.printStackTrace();
		}
		masterServer.initiateGame(connectionFrame.getIPAddress(),connectionFrame.getPort());
	}
	public ConnectListener(MasterServer masterServer,ConnectionFrame connectionFrame) {
		this.masterServer = masterServer;
		this.connectionFrame = connectionFrame;
	}

}
