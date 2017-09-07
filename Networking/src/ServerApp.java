import java.util.Scanner;

import server.Server;

public class ServerApp {
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
		
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		while(!line.equalsIgnoreCase("quit")){
			s.send("127.0.0.1", 
					Integer.parseInt(line.split(":")[0]), 
					line.split(":")[1].getBytes());
		}
		scan.close();
	}
}
