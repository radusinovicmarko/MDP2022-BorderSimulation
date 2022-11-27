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

public class UserSenderThread {

	private SSLSocket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	private Consumer<Boolean> close;

	public UserSenderThread(String username, Consumer<Boolean> notifyClosed) {
		System.setProperty("javax.net.ssl.trustStore", Main.getKeystorePath());
		System.setProperty("javax.net.ssl.trustStorePassword", Main.getKeystorePassword());

		this.close = notifyClosed;
		
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try {
			socket = (SSLSocket) sf.createSocket(Main.getHost(), Main.getReceiverPort());

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

			out.println(ChatProtocolMessages.HELLO.getMessage() + username);
			String response = in.readLine();
			if (response.equals(ChatProtocolMessages.TERMINAL_CLOSED.getMessage())) {
				System.out.println("Primio");
				close.accept(true);
			}
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	public void send(String message) {
		out.println(message);
	}

	public void close() {
		try {
			out.println(ChatProtocolMessages.END.getMessage());
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

}
