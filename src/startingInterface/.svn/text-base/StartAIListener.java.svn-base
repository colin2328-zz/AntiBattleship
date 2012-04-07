package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import communication.MasterServer;

// Begins AI program
public class StartAIListener implements ActionListener {
	private MasterServer originalMS;
	private File f = new File("tbayes03.wav");
	private AudioStream s;
	private FileInputStream fs;

	public StartAIListener(MasterServer ms) {
		this.originalMS = ms;
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
		// Sets information to be passed along
//		System.out.println("In the AIListener");
		int finalPortID = originalMS.getNextPort();
//		System.out.printf("trying portID %d", finalPortID);
		boolean invalidPort = true;
		boolean ai = true;
		MasterServer aiMS;

		// Sets new MasterSever
		aiMS = new MasterServer(finalPortID, ai,false);
		Thread aiThread = new Thread(aiMS);
		aiThread.start();
		originalMS.initiateGame(originalMS.getHostName(), finalPortID);

	}
}
