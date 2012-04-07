package communication;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import boardEngine.BoardModel;

import communication.ClientManager.States;
import datatypes.Coordinate;
import datatypes.InitMessage;
import datatypes.Message;
import datatypes.VictoryMessage;

/**
 *  
 * @author Zach
 * OpponentReader is designed to handle incoming text from the opponent; it is run as its own thread 
 * and has access to the ClientManager.
 * 
 */
public class OpponentReader implements Runnable {
	//BufferedReader 
	BufferedReader reader;
	ClientManager cm;

	/**
	 * OpponentReader reads 
	 * @param reader
	 * @param cm
	 */
	public OpponentReader(BufferedReader reader,ClientManager cm) {
		// TODO Auto-generated constructor stub
		this.reader = reader;
		this.cm = cm;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int k = 0;
		while (true) {
			try {
				k++;
				String text = reader.readLine();
				if (text == null) continue;
//				System.out.println(text);
				States state = cm.getCurrentState();
				//System.out.println(cm.getCurrentState());
				if (state == null) {
//					System.out.println("State is null");
				}
				ArrayList<Integer> viableTypes = cm.stateMessageTypeMap.get(cm.getCurrentState());
//				System.out.println(cm.getCurrentState());
				int j = -1;
				
				if (viableTypes == null) {
					//System.out.println("Associated array list is null");
				}
				for (Integer i : viableTypes) {
					boolean validParse = ConstructMessage.isValidMessage(i,text);	
					if (validParse) {
						j = i;
//						System.out.printf("Message: %s is of type: %d \n",text,i);
					}
				}
				
				Message currentMessage = new Message(j,text);
				currentMessage.setOutgoing(false);
				cm.addToQueue(currentMessage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
