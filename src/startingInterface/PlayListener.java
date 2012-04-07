package startingInterface;

import gameInterface.GameUpdatePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlayListener implements ActionListener {
	ABOutgoingConnectionFrame c;

	public PlayListener(ABOutgoingConnectionFrame c) {
		this.c = c;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (c.play.getText().equals("Play")) {
			try {
				c.fs = new FileInputStream(c.f);
				c.st = new AudioStream(c.fs);
				AudioPlayer.player.start(c.st);
				c.play.setText("Stop");
			} catch (IOException er) {
				er.printStackTrace();
			}
		}

		else {
			AudioPlayer.player.stop(c.st);
			c.play.setText("Play");
		}
	}
}
