package org.unibl.etf.mdp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;

import org.unibl.etf.mdp.protocol.ChatProtocolMessages;

public class ChatServerSenderThread extends Thread {

	private SSLSocket sock;
	private PrintWriter out;
	private BufferedReader in;
	private boolean end = false;

	public ChatServerSenderThread(SSLSocket sock) {
		try {
			this.sock = sock;
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			start();
		} catch (IOException ex) {
			Logger.getLogger(ChatServerService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void run() {
		try {
			boolean auth = false;
			String request = in.readLine();
			String username = "";
			while (!auth) {
				if (!request.startsWith(ChatProtocolMessages.HELLO.getMessage())) {
					out.println(ChatProtocolMessages.INVALID_REQUEST.getMessage());
					request = in.readLine();
				} else {
					auth = true;
					username = request.split(ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage())[1];
					String terminalID = username.split(ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage())[0];
					HashSet<String> closedTerminals = ChatServerService.getCurrentlyclosedterminals();
					boolean closed = false;
					synchronized (closedTerminals) {
						if (closedTerminals.contains(terminalID))
							closed = true;
					}
					if (closed)
						out.println(ChatProtocolMessages.TERMINAL_CLOSED.getMessage());
					else
						out.println(ChatProtocolMessages.OK.getMessage());
				}
			}
			if (!ChatServerService.getMessages().containsKey(username))
				ChatServerService.getMessages().put(username, new ArrayList<>());
			while (!end) {
				ArrayList<String> messagesToSend = ChatServerService.getMessages().get(username);
				if (messagesToSend.isEmpty()) {
					synchronized (messagesToSend) {
						messagesToSend.wait();
					}
				}
				synchronized (messagesToSend) {
					for (String message : messagesToSend) {
						if (!message.equals(ChatProtocolMessages.END_INTERNAL.getMessage()))
							out.println(message);
						else {
							end = true;
							break;
						}
					}
					messagesToSend.clear();
				}
			}
			out.println(ChatProtocolMessages.END.getMessage());
			sock.close();

		} catch (Exception ex) {
			Logger.getLogger(ChatServerService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

}
