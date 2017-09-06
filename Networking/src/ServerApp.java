import packet.PacketReceiveEvent;
import server.Server;
import site.root3287.sudo2.events.Event;
import site.root3287.sudo2.events.Listener;

public class ServerApp {
	public static void main(String[] args) {
		Server s = new Server();
		s.addReceiveListener(new Listener() {
			
			@Override
			public boolean onEvent(Event e) {
				if(new String(((PacketReceiveEvent) e).getPacket().dataToByteArray()).equalsIgnoreCase("ping")){
					
				}
				return false;
			}
		});
	}
}
