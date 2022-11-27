package org.unibl.etf.mdp.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.unibl.etf.mdp.gui.Main;
import org.unibl.etf.mdp.protocol.ChatProtocolMessages;

public class UserReceiverThread extends Thread {

	private SSLSocket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String username;
	private boolean end = false;
	
	private Consumer<String> messageReceivedCallback;
	private Consumer<Boolean> close;

	public UserReceiverThread(String username, Consumer<String> messageReceivedCallback, Consumer<Boolean> close) {
		System.setProperty("javax.net.ssl.trustStore", Main.getKeystorePath());
		System.setProperty("javax.net.ssl.trustStorePassword", Main.getKeystorePassword());
		this.username = username;
		this.messageReceivedCallback = messageReceivedCallback;
		this.close = close;
		start();
	}

	@Override
	public void run() {
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try {
			socket = (SSLSocket) sf.createSocket(Main.getHost(), Main.getSenderPort());

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

			out.println(ChatProtocolMessages.HELLO.getMessage() + username);
			String response = in.readLine();
			if (response.equals(ChatProtocolMessages.TERMINAL_CLOSED.getMessage())) {
				System.out.println("Primio");
				close.accept(true);
			}
			while (!end) {
				String message = in.readLine();
				if (!message.equals(ChatProtocolMessages.END.getMessage()))
					messageReceivedCallback.accept(message);
				else 
					end = true;
			}
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
}
