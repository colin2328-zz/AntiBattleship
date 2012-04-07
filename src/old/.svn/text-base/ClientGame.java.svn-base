package old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import boardEngine.BoardModel;



public class ClientGame {
	private String connectionAddress;
	private int port;
	
	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public ClientGame(String connectionAddress, int port) {
		this.connectionAddress = connectionAddress;
		this.port = port;
	}
	
	public void listenSocket(){
		//Create socket connection
		try{
			socket = new Socket(connectionAddress,port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.printf("Unknown host: %s at port %d",connectionAddress,port);
			System.exit(1);
		} catch  (IOException e) {
			System.out.println("No I/O");
			System.exit(1);
		}
	}
	
	public void sendText(String text) {
		if (out == null) {
			System.out.println("PrintWriter object is null. Check socket connection. Returning...");
			return;
		}
		
		System.out.println("Sending text: " + text);
		out.println(text);
		return;
	}
	
	public void receiveText() {
		while (true) {
			try{
				String line = in.readLine();
				System.out.println("Text received :" + line);
			} catch (IOException e){
				System.out.println("Read failed");
				System.exit(1);
			}
		}
	}
	
	public static void main(String[] args) {
		ClientGame clientGame = new ClientGame("6005-abs.xvm.mit.edu",1338);
		clientGame.listenSocket();
		//clientGame.sendText("init-game 10x9 [1,1,2,3,5]");	
		clientGame.receiveText();
	}
}
