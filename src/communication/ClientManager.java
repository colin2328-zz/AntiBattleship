package communication;

import gameInterface.GameUpdatePanel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import javax.swing.JFrame;
import boardEngine.HostBoardGUI;
import boardEngine.HostBoardModel;
import boardEngine.OpponentBoardGUI;
import boardEngine.OpponentBoardModel;
//import startingInterface.InitialFrame;
import startingInterface.InitialPanel;
import startingInterface.SetUp;
import startingInterface.AcceptDenyPanel;

import datatypes.Message;
import datatypes.SHAHash;
import datatypes.SendReceiveProtocol;
import datatypes.TestVictory;

/**
 * ClientManager threads handle all user interaction with the game
 * @author Zach
 *
 */
public class ClientManager implements Runnable {
	/**
	 * reader will be used in OpponentReader to read incoming messages
	 * writer will be used to write messages out in MessageProcessor
	 * socket provides the connection
	 */
	
	BufferedReader reader;
	PrintWriter writer;
	Socket socket;
	
	//frame through which all panels in the game are visualized
	JFrame gameFrame;
	
	//maps that indicate what messages can be sent at which times.
	Map<ClientManager.States,ArrayList<Integer> > stateMessageTypeMap;
	Map<ClientManager.States,ArrayList<Integer> > stateSendMessageTypeMap;
	
	//boolean indicating who initiated the game (and thus who can send parameter requests)
	boolean initiator;
	
	//hostBoardModel and opponentBoardModel reflect the 
	HostBoardModel hostBoardModel;
	OpponentBoardModel opponentBoardModel;
	
	//should these be members? do we need them?
	HostBoardGUI hostBoardGUI;
	OpponentBoardGUI opponentBoardGUI;
	
	//sendMessageQueue holds incoming/outgoing messages
	ArrayBlockingQueue<Message> sendMessageQueue;
	
	//opponentHash stores what hash was sent at the beginning of the game by the opponent.
	String opponentHash;
	
	//enum class of game States. ClientManager.States are used by MessageProcessor in maps to determine which messages are allowed. 
	public enum States {
		A_CONNECTED, A_PEND_ACCEPT, A_PREP_HASH, A_AWAIT_HASH,
		MY_TURN,PEND_UPDATE, CALC_RESULTS,YOUR_TURN,AWAIT_VICTORY,CALC_VICTORY,VICTORY,CONTESTED,B_CONNECTED,
		B_PEND_ACCEPT,B_AWAIT_HASH, B_PREP_HASH,LOSS;
	}
	
	//Opponent modes
	public static final int MANUAL = 0;
	public static final int BASIC_AI = 1;
	
	AIPlayer aiPlayer;
	
	//Current state of game for this player
	private States currentGameState;
	
	//SendReceiveProtocol contributes the maps
	public SendReceiveProtocol protocol;
	
	public GameUpdatePanel gameUpdatePanel;
	boolean ai;
	private int delay;
	private String defaultBoardSize;
	private String defaultShipSize;
	private boolean salvoMode;
	
	public ClientManager(BufferedReader reader, PrintWriter writer, Socket
			socket,boolean initiator,boolean ai) {
		this.reader = reader;
		this.writer = writer;
		this.socket = socket;
		this.initiator = initiator;
		
		this.defaultShipSize = null;
		this.defaultBoardSize = null;
		
		this.ai = ai;
		//this.aiPlayer = new AIPlayer();
		sendMessageQueue= new ArrayBlockingQueue<Message>(10);
		
		if (this.initiator) {currentGameState = States.A_CONNECTED;}
		else {currentGameState = States.B_CONNECTED; }
		
		protocol = new SendReceiveProtocol();
		
		this.stateMessageTypeMap = protocol.stateMessageTypeMap;
		this.stateSendMessageTypeMap = protocol.stateSendMessageTypeMap;
	}

	/**
	 * @effects calls all anti-cheating checks to determine whether the opponent, having declared victory, actually did win
	 * @param finalBoardState
	 * @param salt
	 * @return
	 */
	public boolean verifyVictory(String finalBoardState,int salt) {
//		System.out.println(finalBoardState);
//		System.out.printf("rows: %d; columns: %d, salt: %d \n",this.hostBoardModel.getHeight(),this.hostBoardModel.getWidth(),salt);
		String finalHash = SHAHash.computeBoardHash(finalBoardState, this.hostBoardModel.getHeight(), this.hostBoardModel.getWidth(), salt);
//		System.out.println("old opponent's hash: " +  this.opponentHash);
//		System.out.println("computed hash: " + finalHash);
		String otherHash = SHAHash.computeBoardHash(finalBoardState, hostBoardModel.getWidth(), hostBoardModel.getHeight(), salt);
//		System.out.println("alternate computed hash " + otherHash);
		//First check; are the hashes identical?
		if (!finalHash.equals(this.opponentHash)) {
//			System.out.println("unequal hashes");
			return false;
		}
		
		if (!TestVictory.validBoardPlacement(finalBoardState, opponentBoardModel)) {
//			System.out.println("invalid board placement");
			return false;
		}
		
		if (!TestVictory.congruentWithCommunication(finalBoardState, opponentBoardModel)) {
//			System.out.println("incongruent with communication");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 */
	public void setSalvoMode(boolean salvoMode) {
        protocol.setSalvoMode(salvoMode);
        this.salvoMode = salvoMode;
        
    }

    public boolean isSalvoMode() {
        return salvoMode;
    }
	/**
	 * @requires a String object representing opponent's board-hash
	 * @modifies this.opponentHash
	 * @param hashText
	 * @effects stores the board-hash sent by the opponent.
	 */
	public void setOpponentHash(String hashText) {
		this.opponentHash = hashText;
	}
	
	/**
	 * @effects returns the opponent's board hash for use in anti-cheating
	 * @return the opponent's board hash
	 */
	public String getOpponentHash() {
		return this.opponentHash;
	}
	
	/**
	 * @effects creates the hostBoardModel and opponentBoardModel
	 * This is called after initializing a game with the opponent, as the information is needed in boardSetup
	 * @modifies the hostBoardModel and opponentBoardModel; this.aiPlayer (creates a new instance of AIPlayer with the hostBoardModel
	 * @requires none
	 * @param width
	 * @param height
	 * @param shipSizes
	 */
	public void setupBoardModels(int width, int height, ArrayList<Integer> shipSizes) {
		//System.out.println(shipSizes);
		
		this.hostBoardModel = new HostBoardModel(width,height,shipSizes);
		this.opponentBoardModel = new OpponentBoardModel(width,height,shipSizes);
		
		if (this.ai) {
			aiPlayer = new AIPlayer(hostBoardModel,opponentBoardModel);
		}
	}
	
	/**
	 * @effects takes a message off the queue; checks to ensure the message is valid
	 * given the current state of the game, and prints it using the PrintWriter
	 * @requires a valid PrintWriter and a valid currentGameState variable (see ClientManager.States for viable states)
	 * @param m, the Message to be sent (see package datatypes)
	 */
	public void sendMessage(Message m){
		
		MessageProcessor mp = new MessageProcessor(this);
		if (m.getOutgoing() == true) {
			//***************************************************//
			//*******ONLY LINE TO BE PRINTED OUT//
			System.out.println(m.getMessageText());
			//***************************************************//
			//***************************************************//
			for (Integer i : this.protocol.stateSendMessageTypeMap.get((getCurrentState()))) {
				if (new Integer(m.getMessageType()).equals(i) ) {
//					System.out.println("match! MessageType is: "+i);
					writer.println(m.getMessageText());}
			}
			mp.handleOutgoingMessage(m);
		}
		
		else {
			ArrayList<Integer> viableTypes = this.protocol.stateMessageTypeMap.get(this.getCurrentState());
			int j = -1;
			if (viableTypes == null) {
//				System.out.println("Associated array list is null");
			}
			
			for (Integer i : viableTypes) {
				boolean validParse = ConstructMessage.isValidMessage(i,m.getMessageText());
				
				if (validParse) {
					j = i;
//					System.out.printf("Message: %s is of type: %d \n",m.getMessageText(),i);
					m.setMessageType(j);
				}
			}
			mp.handleIncomingMessage(m);
		}
		
		
	}
	
	/**
	 * @effects the run thread starts threads to read input from the opponent over the socket. 
	 * It manages the ArrayBlockingQueue of messages being sent to the opponent.
	 * It initializes the GUI, which is updated according to changes in game state
	 * @requires a connection to an opponent over which input can be read
	 * @modifies the state of the game and components of the game are updated through host and opponent inputs
	 */
	@Override
	public void run() {
		gameFrame = new JFrame("Waiting for init-game");
		if (!this.ai) {
			gameFrame.setVisible(true);
		}
		
		gameUpdatePanel = new GameUpdatePanel(this.sendMessageQueue);

		if (this.initiator) {
			gameFrame.setTitle("Game with: "+socket.getInetAddress().getHostAddress());
			gameFrame.setContentPane(new InitialPanel(this));
		}
		else {
			gameFrame.setContentPane(new ChatOnlyPanel(this,false));
		}
	
		//gameFrame.addWindowListener(new EndGameListener(this));
		gameFrame.pack();
		
		OpponentReader oppRead = new OpponentReader(this.reader,this);
		Thread orThread = new Thread(oppRead);
		orThread.start();
		
		if (defaultShipSize != null && defaultBoardSize != null) {
			String initGameMessage = new String("init-game "+ defaultBoardSize.trim() + " " + defaultShipSize.trim());
//			System.out.println(initGameMessage);
			try {
				sendMessageQueue.put(new Message(Message.INIT_GAME,initGameMessage));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		while (true) {
			try {
				Message m = sendMessageQueue.take();
				if (m != null) {
//					System.out.println(m.getMessageText());
					sendMessage(m);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @effects none
	 * @return current ClientManager.States
	 */
	public States getCurrentState() {
		// TODO Auto-generated method stub
		return currentGameState;
	}

	/**
	 * @modifies currentGameState; typically called from MessageProcessor upon sending out a new message or receiving a new message
	 * @param newState
	 */
	public void setCurrentState(States newState) {
		// TODO Auto-generated method stub
		this.currentGameState = newState;
	}

/**
 * @requires hostBoardModel initialized
 * @effects creates the HostBoardGUI and allows the user to place the ship via the GUI
 * Alternatively, in AI mode, the aiPlayer can place the ships according to a specified algorithm
 * @modifies hostBoardModel, gameFrame
 */
	public void shipPlacement() {
		/*TextShipSetup ts = new TextShipSetup(this);
		ts.setVisible(true);
		ts.pack();
		ts.setTitle("Ship Placement");*/
		
		if (this.ai) {
			aiPlayer.placeShips(1);
			
			this.addToQueue(ConstructMessage.makeBoardHashMessage(hostBoardModel.boardRepFromArray(), hostBoardModel.getHeight(), hostBoardModel.getWidth(), hostBoardModel.getSalt()));
			return;
		}
		
		this.hostBoardGUI= new HostBoardGUI(this.hostBoardModel,this.sendMessageQueue);
		gameFrame.setContentPane(new SetUp(this));
		//gameFrame.setDefaultCloseOperation();
		gameFrame.pack();
	}
	
	/**
	 * @requires non-null hostBoardModel
	 * @modifies none
	 * @effects determines whether all ships have been placed
	 * @return boolean indicating whether all ships have been placed
	 */
	public boolean isValidSetup() {
		// TODO Auto-generated method stub
		if (this.hostBoardModel.isValidShipSetup())
			return true;
		else return false;
	}
	
	public HostBoardModel getHostBoardModel() {
		if (hostBoardModel == null ) {
//			System.out.println("null value for hostBoardModel");
		}
		return this.hostBoardModel;
	}

	/**
	 * @requires a valid setup of the hostBoardModel and a salt
	 * @effects calculates the hash and sends it to the opponent (puts it on the queue)
	 * @modifies none
	 */
	public void prepareHash() {
//		System.out.println("Done with ship placement");
		//this.salt = 0;
		Message boardHash = ConstructMessage.makeBoardHashMessage(hostBoardModel.boardRepFromArray(), hostBoardModel.getHeight(), hostBoardModel.getWidth(), hostBoardModel.getSalt());
		//System.out.println(boardHash.getMessageText());
		try {
			sendMessageQueue.put(boardHash);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the hostBoardGUI
	 */
	public HostBoardGUI getHostBoardGUI() {
		return hostBoardGUI;
	}

	/**
	 * @return opponentBoardGUI
	 */
	public OpponentBoardGUI getOpponentBoardGUI() {
		return this.opponentBoardGUI;
	}

	/**
	 * 
	 * @return the opponentBoardModel
	 */
	public OpponentBoardModel getOpponentBoardModel() {
		return this.opponentBoardModel;
	}

	/**
	 * @requires none
	 * @effects initiates the GUI to accept user responses to init-game requests; if AI, game accept message is automatic
	 * @modifies gameFrame
	 */
	public void acceptDenyGame() {
		if (this.ai) {
			this.addToQueue(ConstructMessage.makeGameAcceptMessage());
		}
		
		else {
		if (gameFrame == null) {
//			System.out.println("null gameFrame");
		}
		gameFrame.setContentPane(new AcceptDenyPanel(this));
		gameFrame.pack();
		}
	}

	/**
	 * @effects returns a reference to the message queue
	 * @return a reference to the message queue
	 */
	public ArrayBlockingQueue<Message> getSendMessageQueue() {
		return this.sendMessageQueue;
	}

	/**
	 * @requires a valid incoming message
	 * @effects adds a message to the queue sendMessageQueue
	 * @modifies the queue by adding a message
	 * @param makeGameAcceptMessage
	 */
	public void addToQueue(Message makeGameAcceptMessage) {
		this.sendMessageQueue.add(makeGameAcceptMessage);
	}

	/**
	 * @effects Getters and setters for the delay time, which dictates the wait time for the AI
	 * @param delay
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	/**
	 * @requires none
	 * @effects none
	 * @modifies defaultBoardSize
	 * @param defaultBoardSize
	 */
	public void setDefaultBoardSize(String defaultBoardSize) {
		this.defaultBoardSize = defaultBoardSize;
	}
	
	/**
	 * @requires none
	 * @effects modifies the defaultShipSize to be used by the AI in tournament mode
	 * @modifies defaultshipSize
	 * @param shipSize
	 */
	public void setDefaultShipSize(String shipSize) {
		this.defaultShipSize = shipSize;
	}

	/**
	 * Not in use currently
	 */
	public void end() {
//		System.out.println("at the end.");
		try {
//			System.out.println("hello?");
			
			writer.println("Closing connection... ");
			reader.close();
			writer.close();
			socket.close();
			gameFrame.dispose();
//			System.out.println("done here");
		}
		catch (IOException e) {
//			System.out.println("I/O error: " + e.getMessage());
			System.exit(1);
		}
	}
}
