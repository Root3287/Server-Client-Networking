package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import packet.Packet;
import packet.PacketReceiveEvent;
import packet.PacketReceiveEventType;
import site.root3287.sudo2.events.EventDispatcher;

public class Client {
	private DatagramSocket socket;
	private Thread listen, send;
	private InetAddress destAddress;
	private int destPort;
	private boolean running = false;
	private byte[] receiveDataBuffer = new byte[1024*10];
	private EventDispatcher receiveEvent = new EventDispatcher(new PacketReceiveEventType());
	
	public Client(String address, int port){
		try{
			this.destAddress = InetAddress.getByName(address);
			this.destPort = port;
			this.socket = new DatagramSocket();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void start(){
		System.out.println("Client started on port "+socket.getLocalPort());
		running = true;
		listen = new Thread(()->listen(), "listen");
		listen.start();
	}
	private void listen(){
		while(running){
			DatagramPacket p = new DatagramPacket(receiveDataBuffer, receiveDataBuffer.length);
			try {
				System.out.println("waiting for packet");
				socket.receive(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(new String(p.getData()));
			receiveEvent.execute(new PacketReceiveEvent(new Packet(p)));
			//receiveDataBuffer = new byte[1024*10];
		}
	}
	public void send(final byte[] data){
				try {
					socket.send(new DatagramPacket(data, data.length, destAddress, destPort));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
