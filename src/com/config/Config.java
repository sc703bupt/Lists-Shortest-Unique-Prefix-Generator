package com.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
	private static Config config = new Config();
	private static Map<String, String> attriMap;
    private static Properties p;

	private Config()
	{
		p = new Properties();
		attriMap = new HashMap<String, String>();
		try
		{
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("com/config/config.properties");
			p.load(in);
			in.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.print("Configure file not found \n");
			ex.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.print("loading failed\n");
			e.printStackTrace();
		}	
		
		// only for package:shorestuniqueprefix
		setAttri("WORK_PLACE_PATH", p.getProperty("WORK_PLACE_PATH"));
		setAttri("MAX_COUNT_OF_SEQUENCES_IN_SINGLE_FILE", p.getProperty("MAX_COUNT_OF_SEQUENCES_IN_SINGLE_FILE"));
		setAttri("INIT_RECURSION_LEVEL", p.getProperty("INIT_RECURSION_LEVEL"));
		setAttri("FILE_PATH_LENGTH_LIMIT", p.getProperty("FILE_PATH_LENGTH_LIMIT"));
	}

	public static void setAttri(String attriName, String attriValue) {
		attriMap.put(attriName, attriValue);
	}
	
	public static String getAttri(String attriName) {
		return attriMap.get(attriName);
	}
	
}
