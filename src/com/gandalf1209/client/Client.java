package com.gandalf1209.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.gandalf1209.yge2.util.CrashHandler;

public class Client extends Thread {

	private static InetAddress ip;
	private static int port;
	private static DatagramSocket socket;
	private static Client client;
	
	public static void main(String[] args) {
		try {
			System.out.println("Loading...");
			client = new Client();
			ip = InetAddress.getByName("localhost");
			port = 12098;
			socket = new DatagramSocket();
			client.start();
			sendData("/c/".getBytes());
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
					if (args[1].equals("OK")) {
						System.out.println("Connected!");
					} else {
						System.out.println("ERROR: (" + args[1] + ") " + args[2]);
						System.exit(0);
					}
				}
			} catch (Exception e) {
				CrashHandler.logCrash(e, this.getClass());
				CrashHandler.printCrash(e, this.getClass());
			}
		}
	}
	
	public static void sendData(byte[] data) {
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}