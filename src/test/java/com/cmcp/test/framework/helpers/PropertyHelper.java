package com.cmcp.test.framework.helpers;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Roshan
 *
 * This is a property helper class which reads common and base configurations
 * regarding the application and its environment details are read
 * and accessible via get property method
 */
public class PropertyHelper {

    private static final String PROPERTY_FILE_PATH = System.getProperty("user.dir").replaceAll("\\\\", "/")+"/src/test/resources/config.properties";
    private static Properties properties;

    private PropertyHelper() throws Exception {
        properties = new Properties();
        FileInputStream input = new FileInputStream(PROPERTY_FILE_PATH);
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new Exception("There was a problem in reading the property file \n" + e.getMessage());
        }
        properties.putAll(System.getProperties());
    }


    /**
     * Author - Roshan
     * To fetch given property from config.properties
     *
     * @return String
     * @throws Exception
     * @param property  property name in config file
     */
    public static String getProperty(String property) throws Exception {
        if(properties == null){
            new PropertyHelper();
        }
        return properties.getProperty(property);
    }

}
