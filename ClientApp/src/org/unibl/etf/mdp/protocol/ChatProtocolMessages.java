package org.unibl.etf.mdp.protocol;


public enum ChatProtocolMessages {
	
	MESSAGE_SEPARATOR("#"),
	TERMINAL_USER_SEPARATOR("-"),
	HELLO("HELLO" + MESSAGE_SEPARATOR.getMessage()),
    OK("OK"),
    INVALID_REQUEST("INVALID REQUEST"),
    UNICAST_REQUEST("UNI" + MESSAGE_SEPARATOR.getMessage()),
    MULTICAST_REQUEST("MULTI" + MESSAGE_SEPARATOR.getMessage()),
    BROADCAST_REQUEST("BROAD" + MESSAGE_SEPARATOR.getMessage()),
    MESSAGE("MESSAGE" + MESSAGE_SEPARATOR.getMessage()),
    POLICE_NOTIFICATION_CLOSE("POLICE NOTIFICATION CLOSE"),
    POLICE_NOTIFICATION_OPEN("POLICE NOTIFICATION OPEN"),
    TERMINAL_CLOSED("TERMINAL_CLOSED"),
    END("END");

    private final String value;

    private ChatProtocolMessages(String value) {
        this.value = value;
    }

    public String getMessage() {
        return value;
    }
    
}
