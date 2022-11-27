package org.unibl.etf.mdp.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class RESTUtils {
	
	public static int jsonHttpRequest(String urlString, String httpMethod, Object content) throws Exception {
		int responseCode = -1;
		try {
			HttpURLConnection conn = prepareHttpRequest(urlString, httpMethod);

			ObjectWriter jsonMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();

			String jsonObject = jsonMapper.writeValueAsString(content);

			OutputStream os = conn.getOutputStream();
			os.write(jsonObject.getBytes());
			os.flush();

			responseCode = conn.getResponseCode();

			os.close();
			conn.disconnect();
		} catch (Exception ex) {
			throw ex;
		}
		return responseCode;
	}

	public static JSONObject jsonObjectHttpRequest(String urlString) throws Exception {
		JSONObject responseBody = null;
		try {
			HttpURLConnection conn = prepareHttpRequest(urlString, "GET");

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String jsonString = jsonStringReader(conn, br);

			responseBody = new JSONObject(jsonString);

			br.close();
			conn.disconnect();
		} catch (Exception ex) {
			throw ex;
		}
		return responseBody;
	}

	public static JSONArray jsonArrayHttpRequest(String urlString) throws Exception {
		JSONArray responseBody = null;
		try {
			HttpURLConnection conn = prepareHttpRequest(urlString, "GET");

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String jsonString = jsonStringReader(conn, br);

			responseBody = new JSONArray(jsonString);

			br.close();
			conn.disconnect();
		} catch (Exception ex) {
			throw ex;
		}
		return responseBody;
	}

	private static String jsonStringReader(HttpURLConnection conn, BufferedReader reader) throws Exception {
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String jsonString = "", line = null;
		while ((line = rd.readLine()) != null)
			jsonString += line;
		return jsonString;
	}

	private static HttpURLConnection prepareHttpRequest(String urlString, String method) throws Exception {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		return conn;
	}

}
