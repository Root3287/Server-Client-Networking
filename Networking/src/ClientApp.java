import Client.Client;

public class ClientApp {
	public static void main(String[] args) {
		Client c = new Client("127.0.0.1", 4445);
		c.start();
	}
}
