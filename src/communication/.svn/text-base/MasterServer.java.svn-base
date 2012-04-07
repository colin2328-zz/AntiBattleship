package communication;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JFrame;

import startingInterface.ConnectionFrame;


import communication.*;

/**
 *MasterServer maintains a ServerSocket at a port number specified in the constructor
 *When in ai mode, MasterServer will automatically accept incoming game requests and place ships
 *
 * @author Zach
 *
 */
public class MasterServer implements Runnable {
	ServerSocket master;
	Map<Integer,ClientManager> clientManagerMap;
	static int userCounter;
	static int port;
	int delay;
	boolean ai;
	private String hostName;
	private String defaultBoardSize;
	private String defaultShipSize;
	private boolean listenOnly;
	
	/**
	 * @requires port, boolean as to whether MasterServer should use ai by default.
	 * @effects opens up the ServerSocket for MasterServer
	 * @param port
	 * @param ai
	 */
	public MasterServer(int port,boolean ai,boolean listenOnly) {
		this.port = port;
		this.ai = ai;
		this.listenOnly = listenOnly;
		try {
			InetAddress addr = InetAddress.getLocalHost();

			// Get IP Address
			byte[] ipAddr = addr.getAddress();

			// Get hostname
			this.hostName = addr.getHostName();
		} catch (UnknownHostException e) {
		}
		
		clientManagerMap = new HashMap<Integer,ClientManager>();
		
		//Set up ServerSocket
		try {
			master = new ServerSocket(port);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		userCounter = 0;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	/**
	 * @requires a valid hostName and port
	 * @effects creates a new thread to handle the ClientManager instance (ai set in accordance with the MasterServer ai variable)
	 * @modifies the delay associated with the ClientManager instance
	 * Basic code for connecting to an IPAddress/Port:
	 * http://www.oracle.com/technetwork/java/socket-140484.html (SocketClient)
	 */
	public void initiateGame(String hostName,int port) {
		ClientManager cm = null;
		try{
			Socket socket = new Socket(hostName, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			cm = new ClientManager(in,out,socket,true,ai);
			cm.setDelay(this.delay);
			if (defaultBoardSize != null) {
				cm.setDefaultBoardSize(defaultBoardSize);
				cm.setDefaultShipSize(defaultShipSize);
			}
			userCounter++;
		} catch (UnknownHostException e) {
//			System.out.printf("Unknown host: port %d at %s \n",port,hostName);
			System.exit(1);
		} catch  (IOException e) {
//			System.out.println("No I/O");
			System.exit(1);
		}
		//need an init-game here. need some sort of "game-state" to indicate what is acceptable behavior
		Thread thread = new Thread(cm);
		thread.start();
	}
	
	public String getHostName() {
		return this.hostName;
	}
	
	/**
	 * @requires a functioning map of ClientManagers and user id's
	 * @effects starts the ClientManager thread
	 * @modifies gets ClientManager started.
	 * @param id
	 */
	public void startGame(int id) {
		ClientManager cm = clientManagerMap.get(new Integer(id));
		Thread thread = new Thread(cm);
		thread.start();
	}
		
	
	public void addClientManagerToMap(int id,ClientManager cm) {
		clientManagerMap.put(new Integer(id),cm);
	}
	
	/**
	 * @requires a valid MasterServer socket
	 * @effects listens for incoming connections on the ServerSocket
	 * @modifies starts a cm thread for every incoming connection
	 */
	public void listenSocket() {
		/**
		 * Basic code for initiating threads from a "MasterServer":
		 * http://www.oracle.com/technetwork/java/socket-140484.html
		 */
		try {
			while (true) {
				System.out.println("Accepting connections...");
				Socket socket = master.accept();
				System.out.println("connected");
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				ClientManager clientManager = new ClientManager(reader, writer, socket,false,ai);
				clientManager.setDelay(delay);
				Thread cmThread = new Thread(clientManager);
				cmThread.start();
				//addClientManagerToMap(new Integer(userCounter),clientManager);
				//startGame(new Integer(userCounter));
				userCounter++;
			}

		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
	}
	
	public void run() {
		//MasterServer masterServer = new MasterServer(1338,false); //Constructor should set up the GUI; GUI buttons should initiate games with outsiders.
	
		if (this.listenOnly) {
			this.listenSocket();
		}
		else {
			ConnectionFrame connectionFrame = new ConnectionFrame(this);
			connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			connectionFrame.pack();
			connectionFrame.setVisible(true);
		}
		//masterServer.initiateGame("6005-abs.xvm.mit.edu",1338);
	}

	public int getPort() {
		// TODO Auto-generated method stub
		return port;
	}
	
	public void setDefaultBoardSize(String s) {
		defaultBoardSize = s;
	}
	
	public void setDefaultShipSize(String s) {
		defaultShipSize = s;
	}

	/**
	 * @modifies port
	 * @effects increments the current valid port to use. Should be no modifications outside of calls to this method
	 * @return a good port number to use
	 */
	public int getNextPort() {
		// TODO Auto-generated method stub
		int newPort = this.port+1;
		
		this.port = newPort;
		return this.port;
	}
}
