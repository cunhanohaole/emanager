package br.com.tscpontual.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.tscpontual.exception.TechnicalException;

public class Configurations {

	public static final String FILE_PATH = "/home/cunha/apps/emanager/resources/";
	
	public static final String FILE_NAME = "config.properties"; 
	
	public static String getProperty(String key) throws TechnicalException {
		Properties prop = loadProperties();
		return prop.getProperty(key);
	}
	
	public static Properties loadProperties() throws TechnicalException {
		Properties prop = null;
		try {
			File file = new File(FILE_PATH + FILE_NAME);
			InputStream is = new FileInputStream(file);
			prop = new Properties();
			prop.load(is);
		} catch (FileNotFoundException e) {
			throw new TechnicalException(Messages.CONFIG_FILE_NOT_FOUND, e);
		} catch (IOException e) {
			throw new TechnicalException(Messages.CONFIG_FILE_LOAD_ERROR, e);
		}
		return prop;
	}
	
}
