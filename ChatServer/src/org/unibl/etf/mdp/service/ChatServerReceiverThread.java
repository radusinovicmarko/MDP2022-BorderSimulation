package org.unibl.etf.mdp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;

import org.unibl.etf.mdp.protocol.ChatProtocolMessages;

public class ChatServerReceiverThread extends Thread {

	private SSLSocket sock;
	private BufferedReader in;
	private PrintWriter out;

	public ChatServerReceiverThread(SSLSocket sock) {
		try {
			this.sock = sock;
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
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
			while (!(request = in.readLine()).equals(ChatProtocolMessages.END.getMessage())) {
				String[] requestParts = request.split(ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage());
				HashMap<String, ArrayList<String>> messages = ChatServerService.getMessages();
				if (request.startsWith(ChatProtocolMessages.UNICAST_REQUEST.getMessage())) {
					String receiver = requestParts[1] + ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage()
							+ requestParts[2] + ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage()
							+ requestParts[3];
					// synchronized (messages) {
					if (!ChatServerService.getMessages().containsKey(receiver))
						ChatServerService.getMessages().put(receiver, new ArrayList<>());
					ArrayList<String> receiverMessages = messages.get(receiver);
					synchronized (receiverMessages) {
						receiverMessages.add(requestParts[0] + ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage()
								+ username + ":" + requestParts[4]);
						receiverMessages.notifyAll();
					}
					// }
				} else if (request.startsWith(ChatProtocolMessages.MULTICAST_REQUEST.getMessage())) {
					String terminalID = requestParts[1];
					if (request.contains(ChatProtocolMessages.POLICE_NOTIFICATION_CLOSE.getMessage())) {
						HashSet<String> closedTerminals = ChatServerService.getCurrentlyclosedterminals();
						synchronized (closedTerminals) {
							closedTerminals.add(terminalID);
						}
					} else if (request.contains(ChatProtocolMessages.POLICE_NOTIFICATION_OPEN.getMessage())) {
						HashSet<String> closedTerminals = ChatServerService.getCurrentlyclosedterminals();
						synchronized (closedTerminals) {
							closedTerminals.remove(terminalID);
						}
					}
					// synchronized (messages) {
					for (String client : messages.keySet()) {
						if (client.split(ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage())[0]
								.equals(terminalID) && !client.equals(username)) {
							ArrayList<String> receiverMessages = messages.get(client);
							synchronized (receiverMessages) {
								receiverMessages
										.add(requestParts[0] + ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage()
												+ username + ":" + requestParts[2]);
								receiverMessages.notifyAll();
							}
						}
					}
					// }
				} else if (request.startsWith(ChatProtocolMessages.BROADCAST_REQUEST.getMessage())) {
					// synchronized (messages) {
					for (String client : messages.keySet()) {
						if (!client.equals(username)) {
							ArrayList<String> receiverMessages = messages.get(client);
							synchronized (receiverMessages) {
								receiverMessages
										.add(requestParts[0] + ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage()
												+ username + ":" + requestParts[1]);
								receiverMessages.notifyAll();
							}
						}
					}
					// }
				}
			}
			ArrayList<String> myMessages = ChatServerService.getMessages().get(username);
			synchronized (myMessages) {
				myMessages.add(ChatProtocolMessages.END_INTERNAL.getMessage());
				myMessages.notifyAll();
			}
			sock.close();
		} catch (Exception ex) {
			Logger.getLogger(ChatServerService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

}
