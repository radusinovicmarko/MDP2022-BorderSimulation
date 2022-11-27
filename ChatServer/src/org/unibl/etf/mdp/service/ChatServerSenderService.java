package org.unibl.etf.mdp.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ChatServerSenderService extends Thread {

	private int port;

	public ChatServerSenderService(int port) {
		this.port = port;
		start();
	}

	@Override
	public void run() {
		SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try (SSLServerSocket ss = (SSLServerSocket) factory.createServerSocket(port)) {
			while (true) {
				SSLSocket sock = (SSLSocket) ss.accept();
				new ChatServerSenderThread(sock);
			}
		} catch (IOException ex) {
			Logger.getLogger(ChatServerService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

}
