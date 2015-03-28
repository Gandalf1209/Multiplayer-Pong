package com.gandalf1209.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.gandalf1209.yge2.util.CrashHandler;

public class Server extends Thread {

	private static int port;
	private static DatagramSocket socket;
	private static Server server;
	
	public static void main(String[] args) {
		try {
			System.out.println("Loading...");
			server = new Server();
			port = 12098;
			socket = new DatagramSocket(port);
			server.start();
			System.out.println("Server started on port " + port + "!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				byte[] data = new byte[1024];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				socket.receive(packet);
				String message = new String(data).trim();
				String[] args = message.split("~");
				if (args[0].equals("/c/")) {
					System.out.println(packet.getAddress().getHostAddress() + ":" + packet.getPort() + " connected.");
					sendData(packet.getAddress(), packet.getPort(), "/c/~OK".getBytes());
				}
			} catch (Exception e) {
				CrashHandler.logCrash(e, this.getClass());
				CrashHandler.printCrash(e, this.getClass());
			}
		}
	}
	
	public static void sendData(InetAddress ip, int port, byte[] data) {
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}