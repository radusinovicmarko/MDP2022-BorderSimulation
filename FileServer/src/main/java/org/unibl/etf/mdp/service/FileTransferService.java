package org.unibl.etf.mdp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

public class FileTransferService implements IFileTrasnferService {
	
	public static final String ROOT = "./FileServer/";
	public static final String RESOURCES_PATH = ROOT + "resources/";
	public static final String DOCUMENTS_PATH = RESOURCES_PATH + "documents/";

	@Override
	public boolean send(String personId, byte[] input) throws RemoteException {
		File outFolder = new File(DOCUMENTS_PATH + personId);
		if (!outFolder.exists())
			outFolder.mkdirs();
		try (FileOutputStream fos = new FileOutputStream(outFolder + File.separator + System.currentTimeMillis() + ".zip")) {
			fos.write(input);
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
