package gameInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import communication.ClientManager;
import communication.ConstructMessage;
import datatypes.Message;
/**
 * listens for the user clicking a victory button and sends a victory message
 * @author Zach
 *
 */
public class DeclareVictoryListener implements ActionListener {
	ClientManager cm;
	ArrayBlockingQueue<Message> queue;
	public DeclareVictoryListener(ArrayBlockingQueue<Message> msgQueue) {
		//this.cm = cm;
		this.queue = msgQueue;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		queue.add(ConstructMessage.makeVictoryMessage(cm.getHostBoardModel().getSalt(), cm.getHostBoardModel().boardRepFromArray()));
	}

}
