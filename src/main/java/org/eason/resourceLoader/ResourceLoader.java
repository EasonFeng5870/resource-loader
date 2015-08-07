package org.eason.resourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);
	
	private static final Properties properties = new Properties();
	
	private static volatile Boolean isLoaded = false;
	
	/**
	 * load properties file into memory
	 */
	public static Properties getConfigurations(String configFile) {
		synchronized (isLoaded) {
			if(!isLoaded){
				InputStream in = null;
				try {
					in = ResourceLoader.class.getClassLoader().getResourceAsStream(configFile);
					properties.load(in);
				} catch (Exception e) {
					logger.error("couldn't load properties file, please check " + configFile + " file.", e);
				} finally {
					if (null != in) {
						try {
							in.close();
						} catch (IOException e) {
							logger.warn("couldn't close the inputstream!", e);
						}
					}
				}
				logger.debug("configuration info:" + properties.toString());
				return properties;
			} else {
				return properties;
			}
		}
	}
	
	
}
