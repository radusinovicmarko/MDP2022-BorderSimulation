package org.unibl.etf.mdp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;

public class WarrantsService implements IWarrantsService {

	public static final String RESOURCES_PATH = "./resources/";
	public static final String WANTED_PERSONS_FILE_PATH = RESOURCES_PATH + "wantedPersons.txt";

	@Override
	public boolean isPersonWanted(String personID) throws RemoteException {
		boolean isWanted = false;
		try {
			List<String> wantedPersons = Files.readAllLines(Paths.get(WANTED_PERSONS_FILE_PATH));
			for (String line : wantedPersons) 
				if (line.equals(personID))
					isWanted = true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		return isWanted;
	}

}
