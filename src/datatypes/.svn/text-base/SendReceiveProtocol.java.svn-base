package datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import communication.ClientManager;
import communication.ClientManager.States;

public class SendReceiveProtocol {
	public Map<ClientManager.States,ArrayList<Integer> > stateMessageTypeMap;
	public Map<ClientManager.States,ArrayList<Integer> > stateSendMessageTypeMap;
	private boolean salvoMode;
	
	public SendReceiveProtocol() {
		
		
		salvoMode = true;
		
		stateMessageTypeMap = new HashMap<ClientManager.States,ArrayList<Integer> >();
		stateSendMessageTypeMap = new HashMap<ClientManager.States,ArrayList<Integer> >();
		
		ArrayList<Integer> aConnected = new ArrayList<Integer>();
		aConnected.add(Message.CHAT);
		aConnected.add(Message.INIT_GAME);
		
		ArrayList<Integer> bConnected = new ArrayList<Integer>();
		bConnected.add(Message.CHAT);
		
	
		
		ArrayList<Integer> awaitVictory = new ArrayList<Integer>();
		awaitVictory.add(Message.CHAT);
		
		ArrayList<Integer> pendUpdate = new ArrayList<Integer>();
		pendUpdate.add(Message.CHAT);
		
		ArrayList<Integer> chatOnly = new ArrayList<Integer>();
		chatOnly.add(Message.CHAT);
		
		ArrayList<Integer> prepHash = new ArrayList<Integer>();
		prepHash.add(Message.CHAT);
		prepHash.add(Message.BOARD_HASH);
		
		ArrayList<Integer> bPendAccept = new ArrayList<Integer>();
		bPendAccept.add(Message.CHAT);
		bPendAccept.add(Message.ACCEPT_RESPONSE);
		bPendAccept.add(Message.DENY_RESPONSE);
		
		
		
		stateSendMessageTypeMap.put(States.A_CONNECTED,aConnected);
		stateSendMessageTypeMap.put(States.B_CONNECTED,bConnected);
	
		stateSendMessageTypeMap.put(States.AWAIT_VICTORY, chatOnly);
		stateSendMessageTypeMap.put(States.PEND_UPDATE,chatOnly);
		stateSendMessageTypeMap.put(States.A_PEND_ACCEPT,chatOnly);
		stateSendMessageTypeMap.put(States.B_PEND_ACCEPT, bPendAccept);
		stateSendMessageTypeMap.put(States.A_AWAIT_HASH, chatOnly);
		stateSendMessageTypeMap.put(States.B_AWAIT_HASH, chatOnly);
		stateSendMessageTypeMap.put(States.B_PREP_HASH,prepHash);
		stateSendMessageTypeMap.put(States.A_PREP_HASH, prepHash);
		stateSendMessageTypeMap.put(States.VICTORY, chatOnly);
		stateSendMessageTypeMap.put(States.CONTESTED, chatOnly);
	
		stateSendMessageTypeMap.put(States.LOSS,chatOnly);
		
		
		stateMessageTypeMap.put(States.A_CONNECTED, chatOnly);
		ArrayList<Integer> bConnectedReceive = new ArrayList<Integer>();
		
		bConnectedReceive.add(Message.INIT_GAME);
		bConnectedReceive.add(Message.CHAT);
		stateMessageTypeMap.put(States.B_CONNECTED,bConnectedReceive);
		
		ArrayList<Integer> aAwaitHash = new ArrayList<Integer>();
		aAwaitHash.add(Message.CHAT);
		aAwaitHash.add(Message.BOARD_HASH);
		
		ArrayList<Integer> aPendAccept = new ArrayList<Integer>();
		aPendAccept.add(Message.ACCEPT_RESPONSE);
		aPendAccept.add(Message.DENY_RESPONSE);
		aPendAccept.add(Message.CHAT);
		
		ArrayList<Integer> awaitVictoryRec = new ArrayList<Integer>();
		awaitVictoryRec.add(Message.ACCEPT_VICTORY);
		awaitVictoryRec.add(Message.DENY_VICTORY);
		awaitVictoryRec.add(Message.CHAT);
		

		ArrayList<Integer> pendUpdateRec = new ArrayList<Integer>();
		if (!salvoMode) pendUpdateRec.add(Message.RESULTS);
		else pendUpdateRec.add(Message.SALVO_RESULTS);
		pendUpdateRec.add(Message.GAME_ERROR);
		pendUpdateRec.add(Message.CHAT);

		
		ArrayList<Integer> yourTurnRec = new ArrayList<Integer>();
		if (!salvoMode) yourTurnRec.add(Message.TARGET);
		else yourTurnRec.add(Message.SALVO_TARGET);
		yourTurnRec.add(Message.CHAT);
		yourTurnRec.add(Message.REQ_VICTORY);


		
		stateMessageTypeMap.put(States.A_AWAIT_HASH, aAwaitHash);
		stateMessageTypeMap.put(States.A_PEND_ACCEPT,aPendAccept);
		stateMessageTypeMap.put(States.A_PREP_HASH,chatOnly);
		stateMessageTypeMap.put(States.AWAIT_VICTORY, awaitVictoryRec);
		stateMessageTypeMap.put(States.B_AWAIT_HASH,aAwaitHash);
		stateMessageTypeMap.put(States.B_PEND_ACCEPT,chatOnly);
		stateMessageTypeMap.put(States.B_PREP_HASH,chatOnly);
		stateMessageTypeMap.put(States.CONTESTED, chatOnly);
		stateMessageTypeMap.put(States.LOSS,chatOnly);
		stateMessageTypeMap.put(States.MY_TURN,chatOnly);
		
		stateMessageTypeMap.put(States.VICTORY,chatOnly);
		
	}
	
	public boolean getSalvoMode(){
		return salvoMode;
	}
	
	public void setSalvoMode(boolean salvo){
		salvoMode = salvo;
//		System.out.println("salvo mode in send receive protocol is: "+salvoMode);
		
		ArrayList<Integer> myTurn = new ArrayList<Integer>();
		if (!salvoMode ) myTurn.add(Message.TARGET);
		else myTurn.add(Message.SALVO_TARGET);
		myTurn.add(Message.REQ_VICTORY);
		myTurn.add(Message.CHAT);
		
		ArrayList<Integer> yourTurn = new ArrayList<Integer>();
		yourTurn.add(Message.CHAT);
		if (!salvoMode) yourTurn.add(Message.RESULTS);
		else yourTurn.add(Message.SALVO_RESULTS);
		//yourTurn.add(Message.REQ_VICTORY);
		
		ArrayList<Integer> pendUpdateRec = new ArrayList<Integer>();
		if (!salvoMode) pendUpdateRec.add(Message.RESULTS);
		else pendUpdateRec.add(Message.SALVO_RESULTS);
		pendUpdateRec.add(Message.GAME_ERROR);
		pendUpdateRec.add(Message.CHAT);
		
		ArrayList<Integer> yourTurnRec = new ArrayList<Integer>();
		if (!salvoMode) yourTurnRec.add(Message.TARGET);
		else yourTurnRec.add(Message.SALVO_TARGET);
		yourTurnRec.add(Message.CHAT);
		yourTurnRec.add(Message.REQ_VICTORY);
		
		stateSendMessageTypeMap.put(States.MY_TURN,myTurn);
		stateSendMessageTypeMap.put(States.YOUR_TURN, yourTurn);
		
		stateMessageTypeMap.put(States.PEND_UPDATE,pendUpdateRec);
		stateMessageTypeMap.put(States.YOUR_TURN,yourTurnRec);
		
	}
}
