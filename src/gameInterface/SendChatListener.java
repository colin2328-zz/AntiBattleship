package gameInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JTextField;

import communication.ConstructMessage;

import datatypes.Message;

// Send chats from chat textbox
public class SendChatListener implements ActionListener {
	ArrayBlockingQueue<Message> queue;
	
	public SendChatListener(ArrayBlockingQueue<Message> queue) {
		this.queue = queue;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println("in action listener");
		JTextField ab = (JTextField) e.getSource();
		Message chatMessage = ConstructMessage.makeChatMessage(ab.getText());
		try {
			queue.put(chatMessage);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ab.setText("");
	}

}
