package tests;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import communication.ConstructMessage;

import datatypes.Coordinate;
import datatypes.InitMessage;
import datatypes.Message;

public class ConstructMessageTests {
	
	
	
	@Test
	public void testAcceptVictory(){
		Assert.assertTrue(ConstructMessage.isValidMessage(new Integer(Message.ACCEPT_VICTORY), ConstructMessage.makeAcceptVictoryMessage().getMessageText()));
		Assert.assertFalse(ConstructMessage.isValidMessage(new Integer(Message.ACCEPT_VICTORY), ConstructMessage.makeGameAcceptMessage().getMessageText()));
		
		
	}
	@Test
	public void testAcceptResponse(){
		Assert.assertTrue(ConstructMessage.isValidMessage(new Integer(Message.ACCEPT_RESPONSE), ConstructMessage.makeGameAcceptMessage().getMessageText()));
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.ACCEPT_RESPONSE, null));
	}
	@Test
	public void testBoardHash(){
		Assert.assertTrue(ConstructMessage.isValidMessage(new Integer(Message.BOARD_HASH), ConstructMessage.makeBoardHashMessage("10101010", 4, 2, 10).getMessageText()));
		Assert.assertFalse(ConstructMessage.isValidMessage(new Integer(Message.BOARD_HASH), "board-hash"));
	}
	@Test
	public void testDenyResponse(){
		Assert.assertTrue(ConstructMessage.isValidMessage(new Integer(Message.DENY_RESPONSE), ConstructMessage.makeGameDenyMessage().getMessageText()));		
	}
	
	@Test
	public void testDenyVictory(){
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.DENY_VICTORY, ConstructMessage.makeDenyVictoryMessage().getMessageText()));

	}
	
	@Test
	public void testGameError() {
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.GAME_ERROR, ConstructMessage.makeGameErrorMessage("offending message").getMessageText()));
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.GAME_ERROR, "game-error"));
	}
	/**
	 * test initGameMessages , and parseInitMessage, for each of the following combinations
	 * enough rows, not enough cols, (max) ship size OK
	 * not enough rows, enough cols, ship size OK
	 * not enough rows or cols, ship size OK
	 * enough rows, enough cols, ship size not OK (big ship)
	 * enough rows, not enough cols, ship size  OK (big ship)
	 * invalid sytax, no init-game header
	 * invalid syntax, correct init-game header (diff varieties)
	 * valid sytanx, only one ship
	 */
	@Test
	public void testInitGame(){
		ArrayList<Integer> ships = new ArrayList<Integer>();
		ships.add(3);
		ships.add(3);
		ships.add(1);		
		
		String msg = ConstructMessage.makeInitGameMessage(6, 5, ships).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		msg = ConstructMessage.makeInitGameMessage(5, 5, ships).getMessageText();
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		msg = ConstructMessage.makeInitGameMessage(5,6, ships).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		ArrayList<Integer> ships2 = new ArrayList<Integer>();
		ships2.add(6);
		ships2.add(4);
		ships2.add(5);
		
		msg = ConstructMessage.makeInitGameMessage(6, 4, ships2).getMessageText();
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		msg = ConstructMessage.makeInitGameMessage(6, 6, ships2).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		msg = ConstructMessage.makeBoardHashMessage("100101", 3, 2, 10).getMessageText();
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		msg = "init-game 3x5 [1]";
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		msg = "init-game 4X4 [1]";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		msg = "init-game 3x3 (1,1)";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));		
	}
	/**
	 * similiar to init game tests, but also tests salvo mode
	 * tests the flag to make sure that salvo is checked
	 */
	@Test
	public void testSalvoInitGame(){
		ArrayList<Integer> ships = new ArrayList<Integer>();
		ships.add(3);
		ships.add(3);
		ships.add(1);		
		
		String msg = ConstructMessage.makeSalvoInitGameMessage(6, 5, ships).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		
		InitMessage intmsg = ConstructMessage.parseInitMessage(msg);
		Assert.assertTrue(intmsg.getSalvoMode());
		
		msg = ConstructMessage.makeInitGameMessage(5,6, ships).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		intmsg = ConstructMessage.parseInitMessage(msg);
		Assert.assertFalse(intmsg.getSalvoMode());
		
		ArrayList<Integer> ships2 = new ArrayList<Integer>();
		ships2.add(6);
		ships2.add(4);
		ships2.add(5);
		
		msg = ConstructMessage.makeSalvoInitGameMessage(6, 4, ships2).getMessageText();
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		intmsg = ConstructMessage.parseInitMessage(msg);
		Assert.assertTrue(intmsg.getSalvoMode());
		
		
		msg = "init-game 3x5 [1]";
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		intmsg = ConstructMessage.parseInitMessage(msg);
		Assert.assertFalse(intmsg.getSalvoMode());
		
		msg = "init-game 3x5 [1] not-salvo";
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		intmsg = ConstructMessage.parseInitMessage(msg);
		Assert.assertFalse(intmsg.getSalvoMode());
		
		
		msg = "init-game 3x5 [1] salvo";
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.INIT_GAME, msg));
		intmsg = ConstructMessage.parseInitMessage(msg);
		Assert.assertTrue(intmsg.getSalvoMode());
		
	}
	
	/**
	 * Tests to isValidMessage, makeVictoryMessage and parseVictoryMessage to make sure that
	 * the message needs a correct header and the correct number/type of args
	 */
	@Test
	public void testVictory(){
		String msg = ConstructMessage.makeVictoryMessage(10, "100001111").getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.REQ_VICTORY, msg));
		
		msg = "victory 20 1000001";
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.REQ_VICTORY, msg));
		
		msg = "victory 25.3 1000001";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.REQ_VICTORY, msg));
		
		msg = "victory";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.REQ_VICTORY, msg));
		msg = "victory 13";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.REQ_VICTORY, msg));
		msg = "accept-victory";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.REQ_VICTORY, msg));
	}
	
	@Test
	public void testResults(){
		String msg = ConstructMessage.makeResultsMessage("fire").getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.RESULTS, msg));
		
		msg = ConstructMessage.makeResultsMessage("water").getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.RESULTS, msg));
		
		msg = ConstructMessage.makeResultsMessage("sunk").getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.RESULTS, msg));
		
		msg = ConstructMessage.makeResultsMessage("not-valid").getMessageText();
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.RESULTS, msg));
		
		msg = "results";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.RESULTS, msg));
	}
	
	@Test
	public void testSyntaxError(){
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.SYNTAX_ERROR, ConstructMessage.makeSyntaxErrorMessage("offending message").getMessageText()));
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.SYNTAX_ERROR, "syntax-error"));
	}
	
	@Test
	public void testTarget(){
		String msg = ConstructMessage.makeTargetMessage(3, 3).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.TARGET, msg));
		
		msg = "target";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.TARGET, msg));
		
		msg = "target [3,3]";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.TARGET, msg));
		
		msg = "target (3.4, 3)";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.TARGET, msg));
	}
	
	@Test
	public void testSalvoTarget(){
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		coords.add(new Coordinate(3,4));
		coords.add(new Coordinate(4,4));
		coords.add(new Coordinate(5,5));
		
		String msg = ConstructMessage.makeSalvoTargetMessage(coords).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.SALVO_TARGET, msg));
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.TARGET, msg));
		
		msg = "target";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.SALVO_TARGET, msg));
		
		msg = "target (3,3) [3,3]";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.SALVO_TARGET, msg));
		
		msg = "target (3,3) (3.4, 3)";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.SALVO_TARGET, msg));
	}
	
	@Test public void testSalvoResults(){
		ArrayList<String> results = new ArrayList<String>();
		results.add("water");
		results.add("fire");
		results.add("sunk");
		
		String msg = ConstructMessage.makeSalvoResultsMessage(results).getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.SALVO_RESULTS, msg));
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.RESULTS, msg));
		
		msg = ConstructMessage.makeResultsMessage("water").getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.SALVO_RESULTS, msg));
		
		msg = ConstructMessage.makeResultsMessage("sunk").getMessageText();
		Assert.assertTrue(ConstructMessage.isValidMessage(Message.SALVO_RESULTS, msg));
		
		results.add("not-valid");
		msg = ConstructMessage.makeSalvoResultsMessage(results).getMessageText();
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.SALVO_RESULTS, msg));
		
		msg = "results";
		Assert.assertFalse(ConstructMessage.isValidMessage(Message.SALVO_RESULTS, msg));

		
	}
	
	

}
