package communication;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import datatypes.Message;

public class CommandReader implements Runnable {
	ArrayBlockingQueue<Message> sendMessageQueue;
	public CommandReader(ArrayBlockingQueue sendMessageQueue) {
		this.sendMessageQueue= sendMessageQueue;
	}
	
	public void run() {
		//ArrayList<Integer> viableTypes = AllowedMessages.stateSendMessageTypeMap.get(this.getCurrentState());
		Scanner in = new Scanner(System.in);

		while (true) {
			if (in.hasNextLine()) {
				String command = in.nextLine();
				int j = -1;
				for (int i = 0; i < 20; ++ i) {
					boolean validParse = ConstructMessage.isValidMessage(i,command);
					if (validParse) {
						j = i;
						try {
							sendMessageQueue.put(new Message(i,command));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}}
