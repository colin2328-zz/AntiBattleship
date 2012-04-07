package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import communication.ABOutgoingGameServer;
import communication.MasterServer;

// Initiates a connected game in InitialPanel
public class OutConnectListener implements ActionListener {
	private ABOutgoingGameServer abOut;
	private ABOutgoingConnectionFrame connectionFrame;
	
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
		abOut.initiateGame(connectionFrame.getIPAddress(),connectionFrame.getPort());
	}
	public OutConnectListener(ABOutgoingGameServer masterServer,ABOutgoingConnectionFrame connectionFrame) {
		this.abOut = masterServer;
		this.connectionFrame = connectionFrame;
	}

}
