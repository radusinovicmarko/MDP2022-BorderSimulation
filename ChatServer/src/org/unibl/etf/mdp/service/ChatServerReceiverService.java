package org.unibl.etf.mdp.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ChatServerReceiverService extends Thread {

	private int port;

	public ChatServerReceiverService(int port) {
		this.port = port;
		start();
	}

	@Override
	public void run() {
		SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try (SSLServerSocket ss = (SSLServerSocket) factory.createServerSocket(port)) {
			while (true) {
				SSLSocket sock = (SSLSocket) ss.accept();
				new ChatServerReceiverThread(sock);
			}
		} catch (IOException ex) {
			Logger.getLogger(ChatServerService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

}
