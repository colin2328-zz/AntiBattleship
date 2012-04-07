package communication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TournamentMain {
	public static void main(String[] args) {
		Pattern p = Pattern.compile("--([a-z_]+)=(.*)");
		
		Integer port = null;
		String hostname = null;
		String board_size = null;
		String ship_size = null;
		Integer delay = null;
		
		//MasterServer ms = new MasterServer(1338,true);
		
		for (int i = 0; i < args.length; ++i) {
			Matcher m = p.matcher(args[i]);
			if (!m.matches()) {
//				System.out.println("ERROR: Unrecognized input argument: " + args[i]);
				System.exit(-1);
			}
			if (m.group(1).equals("port")) {
				port = Integer.parseInt(m.group(2));
			} else if (m.group(1).equals("hostname")) {
				hostname = m.group(2);
			} else if (m.group(1).equals("board_size")) {
				board_size = m.group(2);
			} else if (m.group(1).equals("ship_size")) {
				ship_size = m.group(2);
			} else if (m.group(1).equals("delay")) {
				delay = Integer.parseInt(m.group(2));
			} else {
//				System.out.println("ERROR: Unrecognized flag: " + m.group(1));
				System.exit(-1);
			}
		}
		
		//MasterServer ms = new MasterServer(port,true,true);
		//ms.setDelay(delay);
		//ms.setDefaultShipSize(ship_size);
		//ms.setDefaultBoardSize(board_size);
		if (board_size != null) {
			ABOutgoingGameServer ab = new ABOutgoingGameServer(true);
			ab.setDefaultBoardSize(board_size);
			ab.setDefaultShipSize(ship_size);
			ab.setDelay(delay);
			ab.initiateGame(hostname,port);
		}
		else {
			ABIncomingGameServer in = new ABIncomingGameServer(port,false);
			in.listenSocket();
			in.setDelay(delay);
		}
		// Delete this line before you submit! This is here just for debugging!
//		System.out.printf("Port: %d Hostname: %s Board size: %s Ship size: %s Delay: %d", port, hostname, board_size, ship_size, delay);
	}
}