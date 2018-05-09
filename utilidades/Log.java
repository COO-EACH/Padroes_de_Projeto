package utilidades;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	private static final Logger LOGGER = Logger.getLogger(Logger.class
			.getName());
	private static FileHandler logFileHandler;

	public static void gravaLog(Exception e) {
		try {
			if (logFileHandler == null) {
				logFileHandler = new FileHandler("BibliotecaCOO2018.log");
				LOGGER.addHandler(logFileHandler);
			}
		} catch (SecurityException | IOException ex) {
			throw new RuntimeException(ex);
		}

		LOGGER.log(Level.SEVERE, e.toString(), e);
	}
}
