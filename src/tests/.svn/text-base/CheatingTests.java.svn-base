package tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


import boardEngine.OpponentBoardModel;

import datatypes.TestVictory;

public class CheatingTests {
	
	/**
	 * test various scenarios for opponent having an invalid board
	 * they try to play with a diff size board than agreed
	 * they try to play with a diff num or type of ships than agreed
	 * they try to place ships next to each other
	 */
	@Test
	public void testValidBoard(){
		
		ArrayList<Integer> ships = new ArrayList<Integer>();
		ships.add(5);
		ships.add(5);
		ships.add(5);
		
		OpponentBoardModel oppModel = new OpponentBoardModel(10, 10, ships);
		//not enough ones
		String reportedBoard = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";		
		Assert.assertFalse(TestVictory.validBoardPlacement(reportedBoard, oppModel));
		
		//
		ships.clear();
		ships.add(1);
		ships.add(1);
		oppModel = new OpponentBoardModel(2,2,ships);
		reportedBoard = "1001";
		Assert.assertTrue(TestVictory.validBoardPlacement(reportedBoard, oppModel));
		
		ships.clear();
		ships.add(2);
		ships.add(2);
		oppModel = new OpponentBoardModel(4,4,ships);
		reportedBoard = "1100000000000011";
		Assert.assertTrue(TestVictory.validBoardPlacement(reportedBoard, oppModel));
		
		reportedBoard = "0100010000000110";
		Assert.assertTrue(TestVictory.validBoardPlacement(reportedBoard, oppModel));
		
		reportedBoard = "0100000000000111";
		Assert.assertFalse(TestVictory.validBoardPlacement(reportedBoard, oppModel));
		
		reportedBoard = "1100011000000000";
		Assert.assertFalse(TestVictory.validBoardPlacement(reportedBoard, oppModel));
		
		reportedBoard = "0010001100010000";
		Assert.assertFalse(TestVictory.validBoardPlacement(reportedBoard, oppModel));

		
		ships.clear();
		ships.add(1);
		ships.add(1);
		oppModel = new OpponentBoardModel(2,2, ships);
		reportedBoard = "1100";
		Assert.assertFalse(TestVictory.validBoardPlacement(reportedBoard, oppModel));
		
		reportedBoard = "1010";
		Assert.assertFalse(TestVictory.validBoardPlacement(reportedBoard, oppModel));

		reportedBoard = "0101";
		Assert.assertFalse(TestVictory.validBoardPlacement(reportedBoard, oppModel));



	}
	
	/**
	 * test various scenarios:
	 * they try to fake a hit
	 * they try to fake a miss
	 * the array sizes are not equal
	 */
	@Test
	public void testCongruency(){
		ArrayList<Integer> ships = new ArrayList<Integer>();
		ships.add(1);
		OpponentBoardModel oppModel = new OpponentBoardModel(2, 2, ships);
		String reportedBoard = "1000";
		
		Assert.assertTrue(TestVictory.congruentWithCommunication(reportedBoard, oppModel));
		
		oppModel.updateModel(0, 0, true);
		Assert.assertTrue(TestVictory.congruentWithCommunication(reportedBoard, oppModel));
		
		oppModel.updateModel(0, 1, false);
		Assert.assertTrue(TestVictory.congruentWithCommunication(reportedBoard, oppModel));
		
		oppModel.updateModel(0, 0, false);
		Assert.assertFalse(TestVictory.congruentWithCommunication(reportedBoard, oppModel));
		
		oppModel.updateModel(1, 0, true);
		oppModel.updateModel(0, 0, true);
		Assert.assertFalse(TestVictory.congruentWithCommunication(reportedBoard, oppModel));
		
		reportedBoard = "100000";
		Assert.assertFalse(TestVictory.congruentWithCommunication(reportedBoard, oppModel));
		
	}
	
//	public static void main(String[] args) {		
//	}

}
