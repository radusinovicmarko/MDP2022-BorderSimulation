package org.unibl.etf.mdp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.mdp.control.BorderTerminalSerializationFactory;

@Path("warrants")
public class RESTService {
	
	public static final String WARANTS_PATH = BorderTerminalSerializationFactory.RESOURCES_PATH + "warrants.xml"; 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArrestWarants() {
		try (FileInputStream fis = new FileInputStream(WARANTS_PATH)) {
			return Response.ok(fis.readAllBytes()).build();
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
