package packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Packet {
	private int destPort;
	private InetAddress destAddress;
	
	private int srcPort;
	private InetAddress srcAddress;
	
	private DatagramPacket packet;
	private List<Byte> data;
	
	public Packet(){
		this.data = new ArrayList<>();
	}
	public Packet(String data, InetAddress address, int port, InetAddress srcAddress, int srcPort) {
		this.destAddress = address;
		this.destPort = port;
		this.data = new ArrayList<>();
		this.srcAddress = srcAddress;
		this.srcPort = srcPort;
		appendData(data);
	}
	
	public Packet(DatagramPacket p){
		this.packet = p;
	}
	
	public void appendData(String data){
		appendData(data.getBytes());
	}
	
	public void appendData(byte[] data){
		for(byte b : data){
			this.data.add(b);
		}
	}
	
	public byte[] dataToByteArray(){
		Byte[] buffer = new Byte[data.size()];
		data.toArray(buffer);
		byte[] buff = new byte[buffer.length];
		for(int i = 0; i < buffer.length; i++){
			buff[i] = buffer[i];
		}
		return buff;
	}
	
	public void send(DatagramSocket s){
		send(s, this.destAddress, this.destPort);
	}
	public void send(DatagramSocket s, InetAddress address, int port){
		packet = new DatagramPacket(dataToByteArray(), dataToByteArray().length, address, port);
	}
	public void receive(DatagramSocket s){
		packet = new DatagramPacket(dataToByteArray(), dataToByteArray().length);
		try {
			s.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		appendData(packet.getData());
	}
	
	public List<Byte> getData(){
		return data;
	}
}
