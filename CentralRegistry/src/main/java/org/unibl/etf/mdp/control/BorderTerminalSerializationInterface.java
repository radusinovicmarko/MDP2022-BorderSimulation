package org.unibl.etf.mdp.control;

import org.unibl.etf.mdp.model.BorderTerminal;

public interface BorderTerminalSerializationInterface {
	
	public boolean serialize(BorderTerminal terminal);
	
	public BorderTerminal deserialize(int id);

}
