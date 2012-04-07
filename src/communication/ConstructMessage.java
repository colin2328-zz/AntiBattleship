package communication;


import java.util.ArrayList;


import datatypes.Coordinate;
import datatypes.InitMessage;
import datatypes.Message;
import datatypes.SHAHash;
import datatypes.VictoryMessage;

/**
 * 
 * @author Colin
 *Protocol class has parameters that generates the message String
 *syntax specific to the protocol- crucual in decoupling protocol from our code!
 
 */
public class ConstructMessage {
	
	/*
	 * syntax-error-msg | game-error-msg | init-game-msg
    | game-accept-msg | game-deny-msg | board-hash-msg
    | target-msg | results-msg
    | victory-msg | accept-victory-msg | reject-victory-msg
	 */
	
	/**
	 * make __message:
	 * *@param Unparsed message elements
	 *@return Message that contains the message string and type of message (integer)
	 */
	public static Message makeSyntaxErrorMessage(String offendingMessage){
		return new Message(Message.SYNTAX_ERROR, "syntax-error " + offendingMessage);
	}
	
	public static Message makeGameErrorMessage(String offendingMessage){
		return new Message(Message.GAME_ERROR, "game-error " + offendingMessage);
	}
	
	public static Message makeGameAcceptMessage(){
		return new Message(Message.ACCEPT_RESPONSE, "accept-game");
	}
	
	public static Message makeGameDenyMessage(){
		return new Message(Message.DENY_RESPONSE, "deny-game");
	}
	
	public static Message makeInitGameMessage(int width,int height,ArrayList<Integer> ships) {
		String concatString = "[";
		for (int i = 0; i < ships.size(); i++) {
			int element = ships.get(i);
			concatString = new String(concatString+ element);
			if (i < ships.size() -1) {
				concatString = new String(concatString+",");
			}
		}
		concatString = new String(concatString + "]");
		//System.out.println(concatString);
		String boardString = String.format("init-game %dx%d %s",height,width,concatString);
		return new Message(Message.INIT_GAME, boardString);
	}
	
	public static Message makeSalvoInitGameMessage(int width, int height, ArrayList<Integer> ships){
		String msgStr = makeInitGameMessage(width, height, ships).getMessageText();
		msgStr = msgStr + " salvo";
		return new Message(Message.INIT_GAME, msgStr);
	}
	
	public static Message makeBoardHashMessage(String boardRep, int rows, int columns, int salt){
		String hash = SHAHash.computeBoardHash(boardRep, rows, columns, salt);
		if (hash.equals ("no algo")){
			System.exit(0);
		}
		return new Message(Message.BOARD_HASH, "board-hash " + hash);		
	}
	
	public static Message makeTargetMessage(int row, int column){
		return new Message(Message.TARGET, "target (" + row + "," + column + ")");
	}
	
	public static Message makeSalvoTargetMessage(ArrayList<Coordinate> coords){
		String returnMsg = "target"; 
		for (Coordinate coord : coords){
			returnMsg = returnMsg + " (" + coord.getRow() + "," + coord.getColumn() + ")";
		}
		return new Message(Message.SALVO_TARGET, returnMsg);

	}
	public static Message makeSalvoResultsMessage(ArrayList<String> results){
		String rtn = "results";
		for (String result : results){
			rtn = rtn + " " + result;
		}
		return new Message(Message.SALVO_RESULTS, rtn);
	}
	
	public static Message makeResultsMessage(String result){
		return new Message(Message.RESULTS, "results " + result);
	}
	
	public static Message makeVictoryMessage(int salt, String boardState){
		return new Message(Message.REQ_VICTORY, "victory " + salt + " " + boardState);
	}
	
	public static Message makeAcceptVictoryMessage(){
		return new Message(Message.ACCEPT_VICTORY, "accept-victory");
	}
	
	public static Message makeDenyVictoryMessage(){
		return new Message(Message.DENY_VICTORY, "reject-victory");
	}
	

	public static Message makeChatMessage(String text) {
		return new Message(Message.CHAT,"<chat> "+text);
	}
	
	public static String getChatMessage(String text) {
		return text.split("<chat>")[1];
	}
	
	
	
	/**
	 * These messages extract parts of incoming messages
	 * @param msg
	 * @return
	 */
	public static Coordinate getTargetMessageCoordinate(String msg){
		String[] msgComponents = msg.split(" ");		
		String noParanCoord = msgComponents[1].replace("(", "");
		noParanCoord = noParanCoord.replace(")", "");
		String[] coords = noParanCoord.split(",");
		int row = Integer.parseInt(coords[0]);
		int col = Integer.parseInt(coords[1]);
		try {
			@SuppressWarnings("unused")
			String tryString = msgComponents[2];
			//System.out.println("entering");
			return null;

		}catch (RuntimeException e){
			return new Coordinate(row, col);
		} 
	}
	
	public static ArrayList<Coordinate> getSalvoTargetMessageCoordinate(String msg){

		ArrayList<Coordinate> rtn = new ArrayList<Coordinate>();
		String [] msgComps = msg.split(" ");
		// target (3,4) (4,5)
		for (int i = 1; i < msgComps.length; i++){
			String noParanCoord = msgComps[i].replace("(", "");
			noParanCoord = noParanCoord.replace(")", "");
			String[] coords = noParanCoord.split(",");
			int row = Integer.parseInt(coords[0]);
			int col = Integer.parseInt(coords[1]);
			rtn.add( new Coordinate(row, col));
		}
		return rtn;

	}
	
	public static ArrayList<String> getSalvoResultsMessage(String msg){
		String[] components = msg.split(" ");
		ArrayList<String> rtn = new ArrayList<String>();
		for (int i = 1; i < components.length; i++){
			rtn.add(components[i]);
		}
		return rtn;
	}
	/**
	 * 
	 * @param msg
	 * @return the results of the target
	 * note: this is included to decouple the syntax specific protocol from our code!!
	 */
	public static String getFireResults(String msg){
		String[] components = msg.split(" ");
		try {
			@SuppressWarnings("unused")
			String tryString = components[2];
			//System.out.println("entering");
			return null;

		}catch (RuntimeException e){
			return components[1];
		} 
		
	}
	
	/**
	 * 
	 * @param vicMessage
	 * @returns a Victory Message (datatype) that is obtained by parsing the vic message string
	 */
	public static VictoryMessage getVictoryMessage(String vicMessage){
		// "victory" salt board-state"
		String[] msgComponents = vicMessage.split(" ");
		int salt = Integer.parseInt(msgComponents[1]);
		String boardState = msgComponents[2];
		return new VictoryMessage(salt, boardState);
		
	}
	
	/**
	 * 
	 * @param parseInitMessage
	 * @return takes a initGameMessage and parses it into its necessary components
	 * also handles salvo init messages
	 */
		public static InitMessage parseInitMessage(String initGameMsg) {
			String[] msgComponents = initGameMsg.split(" ");
			String[] dimensionComponents = msgComponents[1].split("x");
			int width = Integer.parseInt(dimensionComponents[1]);
			int height = Integer.parseInt(dimensionComponents[0]);
			ArrayList<Integer> ships = new ArrayList<Integer>();
			String noBracketShips = msgComponents[2].replace("[", "");
			noBracketShips = noBracketShips.replace("]", "");
			String[] shipStrings = noBracketShips.split(",");
			for (String s : shipStrings) {
				int shipSize = Integer.parseInt(s);
				ships.add(new Integer(shipSize));
			}
			try {
				String optional = msgComponents[3];
				if (optional.trim().equals("salvo")) return new InitMessage(width, height, ships, true);
				else return new InitMessage(width, height, ships, false);
				
			} catch (RuntimeException e){
				return new InitMessage(width, height, ships, false);
			}
		}	

	
	
	/**
	 * 
	 * @param i
	 * @param str
	 * @returns true if this is a valid parse for the message type (i)
	 */
	public static boolean isValidMessage(Integer i, String str){
		try {
			String[] splitted_string = str.split(" ");
			String header = splitted_string[0];
			switch(i){
			case Message.ACCEPT_VICTORY:
//				System.out.println("In accept-victory parsing");
				return str.equals("accept-victory");
				
				
			case Message.ACCEPT_RESPONSE:
				return str.equals("accept-game");
				
				
			case Message.BOARD_HASH:
				try{
					String second = splitted_string[1];
					return (header.equals("board-hash") && !(second == null)); 
				} catch (RuntimeException e) {
					return false;
				}
			
			case Message.CHAT:
				
				if (splitted_string[0].equals("<chat>") ) {
					return true;
				}
				else return false;
				
			case Message.DENY_RESPONSE:
				return str.equals("deny-game");
			
			case Message.DENY_VICTORY:
				return str.equals("reject-victory");
			case Message.GAME_ERROR:
				return header.equals("game-error");
				
			case Message.INIT_GAME:
				//needs to have a valid header
				if (!header.equals("init-game")){
					return false;
				} else { //needs to parse correctly
					InitMessage initMsg = parseInitMessage(str);
					int row = initMsg.getHeight();
					int col = initMsg.getWidth();
					int numShips = initMsg.getShipSizes().size();
					int longShipSize = initMsg.getLongestShipSize();
					
					boolean rowsValid = row >= 2 * numShips;
					boolean colsValid = col >= 2 * numShips;
					boolean shipSizeValid = longShipSize<= Math.min(row, col);					
					if (!((rowsValid || colsValid) && shipSizeValid)){
						return false;
						
					} else return true;
				}
				
			case Message.REQ_VICTORY:
				if (!header.equals("victory")){
					return false;
				} else { //need to parse
					getVictoryMessage(str);
					return true;
				}
			case Message.RESULTS:
				if (!header.equals("results")){
					return false;
				} else { //need to parse
					String results = getFireResults(str);
					if (results.equals("water") || results.equals("fire") || results.equals("sunk")){
						return true;
					} else return false;
				}
			case Message.SYNTAX_ERROR:
				return header.equals("syntax-error");
				
			case Message.TARGET:
				if (!header.equals("target")){
					return false;
				} else { //need to parse
					Coordinate coord = getTargetMessageCoordinate(str);
					if (coord == null) return false;
					return true;
				}
			case Message.SALVO_TARGET:
				if (str.equals("target")) return false;
				if (!header.equals("target")){
					return false;
				} else {
					getSalvoTargetMessageCoordinate(str);
					return true;
				}
			case Message.SALVO_RESULTS:
				if (str.equals("results")) return false;
				if (!header.equals("results")){
					return false;
				} else { //need to parse
					ArrayList<String> results = getSalvoResultsMessage(str);
					for (String result : results){
						if (!(result.equals("water") || result.equals("fire") || result.equals("sunk"))) return false;
					}
					return true;
				}
			}
		//System.out.println("should not reach here: trying to validate a message with no predicted type");
		return false; //default case... not a message!
		}

		catch (RuntimeException e){
//			System.out.println("runtime exception during parsing: msg '" + str + "' is not a valid message and cannot be parsed as a message of type " + i);

			return false;
		}
	}
	



	
	public static void main(String[] args) {

//		System.out.println(makeTargetMessage(3,4).getMessageText());
//		System.out.println(isValidMessage(new Integer(Message.ACCEPT_VICTORY), ConstructMessage.makeAcceptVictoryMessage().getMessageText()));
//		System.out.println(isValidMessage(new Integer(Message.ACCEPT_RESPONSE), ConstructMessage.makeGameAcceptMessage().getMessageText()));
//		System.out.println(isValidMessage(new Integer(Message.BOARD_HASH), ConstructMessage.makeBoardHashMessage("10101010", 4, 2, 10).getMessageText()));
//		System.out.println(isValidMessage(new Integer(Message.BOARD_HASH), "board-hash"));
//		System.out.println(isValidMessage(new Integer(Message.DENY_RESPONSE), ConstructMessage.makeGameDenyMessage().getMessageText()));
//		Coordinate coord = getTargetMessageCoordinate("target (5,4)");
//		System.out.println(coord.getX() + " " + coord.getY());

	}


}
