package org.unibl.etf.mdp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarrantsService implements IWarrantsService {

	@Override
	public boolean isPersonWanted(String personID) throws RemoteException {
		boolean isWanted = false;
		try {
			List<String> wantedPersons = Files.readAllLines(Paths.get(RMIService.getWantedPersonsPath()));
			for (String line : wantedPersons) 
				if (line.equals(personID))
					isWanted = true;
		} catch (IOException ex) {
			Logger.getLogger(RMIService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
			return false;
		}
		return isWanted;
	}

}
