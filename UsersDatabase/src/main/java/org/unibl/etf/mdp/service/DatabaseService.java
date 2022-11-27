package org.unibl.etf.mdp.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.mdp.model.User;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Path("users")
public class DatabaseService {
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkCredentials(@PathParam("username") String username) {
		String password = "";
		JedisPool pool = new JedisPool("localhost");
		try (Jedis jedis = pool.getResource()) {
			if (jedis.exists(username))
				password = jedis.get(username);
		}
		pool.close();
		if (!password.isEmpty()) 
			return Response.ok(new User(username, password)).build();
		else
			return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {
		boolean success = false;
		JedisPool pool = new JedisPool("localhost");
		try (Jedis jedis = pool.getResource()) {
			if (jedis.exists(user.getUsername()))
				success = false;
			else {
				jedis.set(user.getUsername(), user.getPassword());
				success = true;
			}
		}
		pool.close();
		if (success) 
			return Response.status(Response.Status.CREATED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(User user) {
		boolean success = false;
		JedisPool pool = new JedisPool("localhost");
		try (Jedis jedis = pool.getResource()) {
			String username = user.getUsername();
			if (jedis.exists(username)) {
				jedis.del(username);
				success = true;
			}
		}
		pool.close();
		if (success)
			return Response.status(Response.Status.NO_CONTENT).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {
		boolean success = false;
		JedisPool pool = new JedisPool("localhost");
		try (Jedis jedis = pool.getResource()) {
			if (!jedis.exists(user.getUsername()))
				success = false;
			else {
				jedis.set(user.getUsername(), user.getPassword());
				success = true;
			}
		}
		pool.close();
		if (success)
			return Response.status(Response.Status.NO_CONTENT).build();
		else 
			return Response.status(Response.Status.NOT_FOUND).build();
	}
}
