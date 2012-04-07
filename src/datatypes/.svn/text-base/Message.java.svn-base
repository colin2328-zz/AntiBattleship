package datatypes;

/**
 * Message class contains the text of the message and the type of the message to be sent
 * Allows for controls to be placed on what type of messages can be sent depending on the game state
 * @author Zach
 * 
 */
public class Message {
	private String messageText;
	private int messageType;
	private boolean outgoing;
	public static final int INIT_GAME = 0;
	public static final int ACCEPT_RESPONSE = 1;
	public static final int DENY_RESPONSE = 2;
	public static final int BOARD_HASH = 3;
	public static final int TARGET = 4;
	public static final int RESULTS = 5;
	public static final int REQ_VICTORY = 6;
	public static final int ACCEPT_VICTORY = 7;
	public static final int DENY_VICTORY = 8;
	public static final int SYNTAX_ERROR = 9; 
	public static final int GAME_ERROR = 10;
	public static final int CHAT = 11;
//	public static final int SALVO_INIT_GAME = 12;
	public static final int SALVO_TARGET = 13;
	public static final int SALVO_RESULTS = 14;
	
	public Message(int messageType,String messageText) {
		this.messageText = messageText;
		this.messageType = messageType;
		this.outgoing = true;
	}
	
	public String getMessageText() {
		return messageText;
	}
	
	public int getMessageType() {
		return messageType;
	}
	
	public void setMessageType(int i) {
		messageType = i;
	}
	
	public void setOutgoing(boolean b) {
		this.outgoing = b;
	}
	
	public boolean getOutgoing() {
		return this.outgoing;
	}
	
	
	
}
