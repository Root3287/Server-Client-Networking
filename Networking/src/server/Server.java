package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import packet.Packet;
import packet.PacketReceiveEvent;
import packet.PacketReceiveEventType;
import site.root3287.sudo2.events.EventDispatcher;
import site.root3287.sudo2.events.Listener;

public class Server{
	
	private DatagramSocket socket;
	private int port;
	
	private Thread send, receive;
	private boolean running;
	
	private EventDispatcher receiveEvent = new EventDispatcher(new PacketReceiveEventType());
	
	private byte[] receiveBuffer = new byte[1024*10];
	
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
	public void start(){
		this.running = true;
		this.receive = new Thread(()->listen(), "listen");
		this.receive.start();
	}
	private void listen() {
		System.out.println("Listenening on port " + this.port);
		while(running){
			DatagramPacket p = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			try {
				this.socket.receive(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			receiveEvent.execute(new PacketReceiveEvent(new Packet(p)));
		}
	}
	public void send(String destAddress, int port, final byte[] data){
		try {
			socket.send(new DatagramPacket(data, data.length, InetAddress.getByName(destAddress), port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addReceiveListener(Listener l){
		receiveEvent.addListener(l);
	}
}
