package communication;

import java.util.ArrayList;

import boardEngine.HostBoardGUI;
import boardEngine.OpponentBoardGUI;

import gameInterface.inGame;
import startingInterface.PendingPanel;
import communication.ClientManager.States;

import datatypes.Coordinate;
import datatypes.InitMessage;
import datatypes.Message;
import datatypes.VictoryMessage;

public class MessageProcessor {
	ClientManager cm;
	
	public MessageProcessor(ClientManager cm) {
		this.cm = cm;
	}
	
	public void handleIncomingMessage(Message m) {
		int j = m.getMessageType();
		String text = m.getMessageText();
		
		update(false,m);
		
		switch (j) {
		
		case -1: ;
			cm.addToQueue(ConstructMessage.makeSyntaxErrorMessage(text)); break;//NEED TO UPDATE THIS
		
		case Message.INIT_GAME:
			//change state of game to States.B_PEND_ACCEPT (you are not the initiator)
			InitMessage initMessage = ConstructMessage.parseInitMessage(text);
			cm.setupBoardModels(initMessage.getWidth(),initMessage.getHeight(),initMessage.getShipSizes());
			cm.setCurrentState(States.B_PEND_ACCEPT);
			cm.gameFrame.setContentPane(new ChatOnlyPanel(cm,true));
			cm.gameFrame.pack();
//			System.out.println("in INIT_GAME");
			cm.setSalvoMode(initMessage.getSalvoMode());
			if (cm.ai) {
				cm.addToQueue(ConstructMessage.makeGameAcceptMessage());
			}
			break;
		case Message.ACCEPT_RESPONSE:
			//enter States.A_BOARD_HASH (you are the initiator)
			cm.setCurrentState(States.A_PREP_HASH);
			cm.shipPlacement();
			//setup BoardModel
			break;
		case Message.DENY_RESPONSE:
			//exit the game (close the connection?)
			cm.addToQueue(ConstructMessage.makeGameDenyMessage());
			//there might be something better here, possibly, or something additional?
			//more cleanup to do before exit?
			System.exit(0);
			break;
		case Message.BOARD_HASH:
			//change state to B_PREP_HASH, store hash for cheating purposes (if player B)
			String hashText = text;
			if (cm.initiator == false) {
				cm.setOpponentHash(hashText.split(" ")[1]); 
				cm.setCurrentState(States.B_PREP_HASH);
				//
				cm.shipPlacement();
			}
			else {
				cm.setOpponentHash(hashText.split(" ")[1]);
				cm.setCurrentState(States.MY_TURN);
				if (cm.ai) {
						if (!cm.isSalvoMode()){
						Coordinate move = cm.aiPlayer.calculateMove();
					
						try {
							Thread.sleep(cm.getDelay());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cm.addToQueue(ConstructMessage.makeTargetMessage(move.getRow(), move.getColumn()));
					} else {//is salvo mode
						ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
						int numMoves = cm.getHostBoardModel().numShipsLeftToSink();
						for (int i = 0; i < numMoves; i++){
							Coordinate move = cm.aiPlayer.calculateMoveSalvo(numMoves);
							moves.add(move);
							try {
								Thread.sleep(cm.getDelay());
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						cm.addToQueue(ConstructMessage.makeSalvoTargetMessage(moves));
					}
				}
			};
			break;
			//change state to MY_TURN, store hash for cheating purposes (if player A)
		case Message.CHAT:
			//System.out.println("in here");
			cm.gameUpdatePanel.addChatMessage(m.getMessageText());
			cm.setCurrentState(cm.getCurrentState()); break;
			
		case Message.SALVO_TARGET:
			ArrayList<Coordinate> targetCoords = ConstructMessage.getSalvoTargetMessageCoordinate(text);
			ArrayList<String> results = new ArrayList<String>();
			
			for (Coordinate targetCoord : targetCoords){
				String resultString = cm.hostBoardModel.updateModel(targetCoord.getRow(), targetCoord.getColumn());
				cm.hostBoardGUI.updateGUI(targetCoord.getRow(),targetCoord.getColumn(), resultString);
				
				if (resultString.equals("game-error")) {
					try {
						cm.sendMessageQueue.put(ConstructMessage.makeGameErrorMessage("Duplicate shot!"));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cm.setCurrentState(States.VICTORY);
				}else {
					results.add(resultString);					
				}
			}
			try {				
				cm.sendMessageQueue.put(ConstructMessage.makeSalvoResultsMessage(results));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
			
		case Message.TARGET:
			//check board model and send appropriate response indicating result message
			//cm.sendMessage()
			Coordinate targetCoord = ConstructMessage.getTargetMessageCoordinate(text);
			String resultString = cm.hostBoardModel.updateModel(targetCoord.getRow(), targetCoord.getColumn());
			cm.hostBoardGUI.updateGUI(targetCoord.getRow(),targetCoord.getColumn(), resultString);
			//System.out.println(resultString);
			if (resultString.equals("game-error")) {
				try {
					cm.sendMessageQueue.put(ConstructMessage.makeGameErrorMessage("Duplicate shot!"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cm.setCurrentState(States.VICTORY);
			}
			else {
				try {
					cm.sendMessageQueue.put(ConstructMessage.makeResultsMessage(resultString));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			break;			
			//change the state of the game to PEND_UPDATE
			
		case Message.SALVO_RESULTS:
			ArrayList<Coordinate> coorespondingCoords = cm.opponentBoardModel.getLastTargetedSalvo();
			ArrayList<String> targetResultsString = ConstructMessage.getSalvoResultsMessage(text);
			
			int i = 0;
			for (String targetResultString: targetResultsString){
				Coordinate correspondingCoord = coorespondingCoords.get(i);	
				if (targetResultString.equals("fire") || targetResultString.equals("sunk")) {
					cm.opponentBoardModel.updateModel(correspondingCoord.getRow(),correspondingCoord.getColumn(), true);
					cm.opponentBoardGUI.updateGUI(correspondingCoord.getRow(), correspondingCoord.getColumn(), true);
				}
				if (targetResultString.equals("water")) {
					cm.opponentBoardModel.updateModel(correspondingCoord.getRow(),correspondingCoord.getColumn(),false);
					cm.opponentBoardGUI.updateGUI(correspondingCoord.getRow(), correspondingCoord.getColumn(), false);
				}	
				
//				if (cm.opponentBoardModel.getSquaresTargeted() == cm.opponentBoardModel.nSquares()) {
//					cm.addToQueue(ConstructMessage.makeTargetMessage(0, 0));
//				}
				i++;	
			}
			cm.setCurrentState(States.YOUR_TURN);
			break;
			
		case Message.RESULTS:
			//update the YourBoardModel to reflect the result of your fire
			//change the state of your game to YOUR_TURN
			Coordinate correspondingCoord = cm.opponentBoardModel.getLastTargeted();
			String targetResultString = ConstructMessage.getFireResults(text);
			if (targetResultString.equals("fire") || targetResultString.equals("sunk")) {
				cm.opponentBoardModel.updateModel(correspondingCoord.getRow(),correspondingCoord.getColumn(), true);
				cm.opponentBoardGUI.updateGUI(correspondingCoord.getRow(), correspondingCoord.getColumn(), true);
				cm.setCurrentState(States.MY_TURN);
				
				if (cm.opponentBoardModel.getSquaresTargeted() == cm.opponentBoardModel.nSquares()) {
					cm.addToQueue(ConstructMessage.makeTargetMessage(0, 0));
				}
				
				if (cm.ai) {
					Coordinate move = cm.aiPlayer.calculateMove();
//					System.out.println("found my move");
					try {
//						System.out.printf("Delay is: %d",cm.getDelay());
						Thread.currentThread().sleep(cm.getDelay());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cm.addToQueue(ConstructMessage.makeTargetMessage(move.getRow(), move.getColumn()));	
				}
			}
			if (targetResultString.equals("water")) {
				cm.opponentBoardModel.updateModel(correspondingCoord.getRow(),correspondingCoord.getColumn(),false);
				cm.opponentBoardGUI.updateGUI(correspondingCoord.getRow(), correspondingCoord.getColumn(), false);
				cm.setCurrentState(States.YOUR_TURN);
			}	
			break;
		case Message.REQ_VICTORY:
			
			VictoryMessage victoryMsg = ConstructMessage.getVictoryMessage(text);
			if (cm.verifyVictory(victoryMsg.getBoardState(), victoryMsg.getSalt())) {
				//cm.setCurrentState(States.LOSS);
				cm.addToQueue(ConstructMessage.makeAcceptVictoryMessage());
				cm.setCurrentState(States.LOSS);
//				System.out.println(cm.getCurrentState());
			}
			else {
				cm.setCurrentState(States.CONTESTED);
				cm.addToQueue(ConstructMessage.makeDenyVictoryMessage());
			}
			
			break;
			//update the state of the game to CALC_VICTORY; see whether hashes are equivalent
			
		case Message.ACCEPT_VICTORY:
			cm.setCurrentState(States.VICTORY);
			break;
		case Message.DENY_VICTORY:
			cm.setCurrentState(States.CONTESTED);
			//update the state of the game to CONTESTED:
			break;
		
		case Message.GAME_ERROR:
			cm.setCurrentState(States.LOSS);
//			System.out.println("Lost due to game error");
			System.exit(0);
			
		}
	}
	
	private void update(boolean host,Message m) {
		// TODO Auto-generated method stub
		ArrayList<Integer> updateList = new ArrayList<Integer>();
		updateList.add(Message.TARGET);
		updateList.add(Message.SALVO_TARGET);
		updateList.add(Message.SALVO_RESULTS);
		updateList.add(Message.RESULTS);
		updateList.add(Message.REQ_VICTORY);
		updateList.add(Message.GAME_ERROR);
		updateList.add(Message.SYNTAX_ERROR);
		updateList.add(Message.DENY_VICTORY);
		updateList.add(Message.ACCEPT_VICTORY);
		if (updateList.contains(m.getMessageType())) {
			if (host) {
				cm.gameUpdatePanel.addUpdate("<host> "+m.getMessageText());
			}
			else {
				cm.gameUpdatePanel.addUpdate("<opponent> "+m.getMessageText());
			}
		}
	}

	public void handleOutgoingMessage(Message m) {
		
		update(true,m);
		switch (m.getMessageType()) {

		case Message.CHAT:
			cm.gameUpdatePanel.addChatMessage("<host> "+ConstructMessage.getChatMessage(m.getMessageText()));
			cm.setCurrentState(cm.getCurrentState()); break;
		case Message.ACCEPT_RESPONSE:
			cm.setCurrentState(States.B_AWAIT_HASH); break;
		case Message.ACCEPT_VICTORY:
			cm.setCurrentState(States.LOSS); break;
		case Message.BOARD_HASH:
			//cm.setOpponentHash(m.getMessageText().split(" ")[1]); 
			if (cm.initiator) {
				cm.setCurrentState(States.A_AWAIT_HASH);
			}
			else {
				cm.setCurrentState(States.YOUR_TURN);
			}
			cm.opponentBoardGUI = new OpponentBoardGUI(cm);
			cm.hostBoardGUI = new HostBoardGUI(cm.hostBoardModel,cm.sendMessageQueue);
			//cm.gameUpdatePanel = new GameUpdatePanel(cm.sendMessageQueue);
			cm.gameFrame.setContentPane(new inGame(cm.hostBoardGUI,cm.opponentBoardGUI,cm.gameUpdatePanel));
			cm.gameFrame.pack();
			break;
		case Message.DENY_RESPONSE:
			System.exit(0); break;
		case Message.DENY_VICTORY:
			cm.setCurrentState(States.CONTESTED); break;
		case Message.GAME_ERROR:
			cm.setCurrentState(States.VICTORY); break;
		case Message.INIT_GAME:
			InitMessage initMessage = ConstructMessage.parseInitMessage(m.getMessageText());
			cm.setSalvoMode(initMessage.getSalvoMode());
			cm.setupBoardModels(initMessage.getWidth(), initMessage.getHeight(), initMessage.getShipSizes());
			cm.setCurrentState(States.A_PEND_ACCEPT); 
			cm.gameFrame.setContentPane(new PendingPanel(cm.socket.getInetAddress().getHostAddress()));
			cm.gameFrame.pack();
			//System.out.println("in init-game message sent");
			break;
		case Message.REQ_VICTORY:
			cm.setCurrentState(States.AWAIT_VICTORY); break;
		case Message.SALVO_RESULTS:
//			System.out.println("sent salvo results Message " + m.getMessageText());
			ArrayList<Coordinate> moves = new ArrayList<Coordinate>();
			int numMoves = cm.getHostBoardModel().numShipsLeftToSink();
			cm.setCurrentState(States.MY_TURN);
			if (cm.getHostBoardModel().numShipsLeftToSink() == 0) {
				//cm.setCurrentState(States.AWAIT_VICTORY);
				cm.addToQueue(ConstructMessage.makeVictoryMessage(cm.getHostBoardModel().getSalt(), cm.getHostBoardModel().boardRepFromArray()));
				//cm.setCurrentState(States.AWAIT_VICTORY);
//				System.out.println("I'm awaiting victory here");
			}
			else if (cm.ai){
				for (int i = 0; i < numMoves; i++){
					Coordinate move = cm.aiPlayer.calculateMoveSalvo(numMoves);
					moves.add(move);
					try {
						Thread.sleep(cm.getDelay());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				cm.addToQueue(ConstructMessage.makeSalvoTargetMessage(moves));
			}
			break;
		case Message.RESULTS:
			if (m.getMessageText().contains("fire") || m.getMessageText().contains("sunk")) {
				cm.setCurrentState(States.YOUR_TURN);
//				System.out.println("Opponent's turn: ");
			}
			else {
				cm.setCurrentState(States.MY_TURN); 
				//System.out.println("Host's turn: ");
				if (cm.getHostBoardModel().numShipsLeftToSink() == 0) {
					//cm.setCurrentState(States.AWAIT_VICTORY);
					cm.addToQueue(ConstructMessage.makeVictoryMessage(cm.getHostBoardModel().getSalt(), cm.getHostBoardModel().boardRepFromArray()));
					//cm.setCurrentState(States.AWAIT_VICTORY);
//					System.out.println("I'm awaiting victory here");
				}
				else {
				if (cm.ai) {
					Coordinate move = cm.aiPlayer.calculateMove();
					//System.out.println("found my move");
					try {
						Thread.sleep(cm.getDelay());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cm.addToQueue(ConstructMessage.makeTargetMessage(move.getRow(), move.getColumn()));	
				}
			}
			}
			break;
		case Message.SYNTAX_ERROR:
			cm.setCurrentState(cm.getCurrentState()); break;
		case Message.TARGET:
//			System.out.println("Sent Target Message : " + m.getMessageText());
			cm.opponentBoardModel.setLastTargeted(ConstructMessage.getTargetMessageCoordinate(m.getMessageText()));
			cm.setCurrentState(States.PEND_UPDATE); break;
		case Message.SALVO_TARGET:

			cm.opponentBoardModel.setLastTargetedSalvo(ConstructMessage.getSalvoTargetMessageCoordinate(m.getMessageText()));
			cm.setCurrentState(States.PEND_UPDATE); break;
		}
	}


}
