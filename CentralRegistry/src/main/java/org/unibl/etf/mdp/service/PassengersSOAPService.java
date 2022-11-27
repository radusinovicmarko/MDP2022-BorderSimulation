package org.unibl.etf.mdp.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.unibl.etf.mdp.control.BorderTerminalSerializationFactory;

public class PassengersSOAPService {
	
	public static final String LOG_PATH = BorderTerminalSerializationFactory.RESOURCES_PATH + "logPassengers.txt";
	
	public byte[] getLog() {
		try {
			return Files.readAllBytes(Paths.get(LOG_PATH));
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}
	}
	
	public void addLog(String personID) {
		try {
			List<String> logs = Files.readAllLines(Paths.get(LOG_PATH));
			logs.add(personID + " - " + new Date().toString());
			Files.write(Paths.get(LOG_PATH), logs);
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
		}
	}
	
	public void addOnWarantLog(String personID) {
		try {
			Document doc = new SAXBuilder().build(new File(RESTService.WARANTS_PATH));
			Element passenger = new Element("passenger");
			passenger.setAttribute("id", personID);
			doc.getRootElement().addContent(passenger);
			XMLOutputter xml = new XMLOutputter();
	        xml.setFormat(Format.getPrettyFormat());
	        xml.output(doc, new FileWriter(RESTService.WARANTS_PATH));
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
		}
	}
	
}
