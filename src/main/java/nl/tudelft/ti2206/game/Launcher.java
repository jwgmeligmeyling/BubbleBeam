package nl.tudelft.ti2206.game;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Launcher {

	private static final Logger log = LoggerFactory.getLogger(Launcher.class);
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		// This main method is called when starting your game.
		log.info("Starting game...");
		new GUI();
	}
}
