package communication;

/**
 * This is the main method that we use frequently to run the game (outside of tournament mode) 
 * @author Zach
 *
 */
public class BasicMain {
	public static void main(String[] args) {
		ABOutgoingGameServer initiate = new ABOutgoingGameServer(false);
		//ABIncomingGameServer listener = new ABIncomingGameServer(1338,false);
		//listener.setDelay(5000);
		initiate.setDelay(5000);
		//Thread listenerThread = new Thread(listener);
		Thread initiateThread = new Thread(initiate);
		//listenerThread.start();
		initiateThread.start();
	}
}
