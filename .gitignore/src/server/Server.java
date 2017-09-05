package server;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import packet.Packet;
import packet.PacketReceiveEventType;
import site.root3287.sudo2.events.EventDispatcher;
import site.root3287.sudo2.events.Listener;

public class Server implements Runnable{
	
	private DatagramSocket socket;
	private int port;
	
	private Thread run, send, receive;
	private boolean running;
	
	private EventDispatcher receiveEvent = new EventDispatcher(new PacketReceiveEventType());
	
	public Server(){
		this(4445);
	}
	public Server(int port){
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.running = false;
		this.port = port;
	}
	@Override
	public void run() {
		running = true;
		running();
	}
	private void running() {
		run = new Thread("Running"){
			@Override
			public void run() {
				while(running){
					Packet p = new Packet();
					p.receive(socket);
				}
			}
		};
	}
	public void send(final String data, InetAddress dest, int port){
		send = new Thread("Send"){
			@Override
			public void run() {
				Packet p = new Packet(data, dest, port);
				p.send(socket);
			}
		};
				
	}
	public void addReceiveListener(Listener l){
		receiveEvent.addListener(l);
	}
}
