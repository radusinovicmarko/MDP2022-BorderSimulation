package org.unibl.etf.mdp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("documents")
public class RESTService {
	
	@GET
	@Path("{userId}")
	public Response getUserDocuments(@PathParam("userId") String id) {
		ArrayList<byte[]> documents = new ArrayList<>();
		File root = new File(FileTransferService.DOCUMENTS_PATH + id);
		if (!root.exists())
			return Response.status(Response.Status.NOT_FOUND).build();
		getDocuments(root, documents);
		return Response.ok(documents).build();
	}
	
	@GET
	public Response getAllDocuments() {
		ArrayList<byte[]> documents = new ArrayList<>();
		File root = new File(FileTransferService.DOCUMENTS_PATH);
		if (!root.exists())
			return Response.status(Response.Status.NOT_FOUND).build();
		getDocuments(root, documents);
		return Response.ok(documents).build();
	}
	
	private void getDocuments(File rootFolder, ArrayList<byte[]> documents) {
		for (File f : rootFolder.listFiles()) {
			if (f.isFile())
				addDocument(f, documents);
			else if (f.isDirectory())
				getDocuments(f, documents);
				
		}
	}
	
	private void addDocument(File file, ArrayList<byte[]> documents) {
		try (FileInputStream fis = new FileInputStream(file)) {
			documents.add(fis.readAllBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
