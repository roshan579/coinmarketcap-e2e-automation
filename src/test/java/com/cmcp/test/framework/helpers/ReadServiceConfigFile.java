package com.cmcp.test.framework.helpers;

import org.apache.commons.lang3.StringUtils;
import java.io.FileInputStream;


/**
 * Created by Roshan
 *
 * This is a Service Config file reader class which reads the system environment variable
 * or if not available it takes maven command line argument variable
 * so that the logic is derived to go to the specific environment properties file
 * and access propeties via get property method
 */
public class ReadServiceConfigFile {

    private static PropertyParser properties;

    private ReadServiceConfigFile() throws Exception {
        String PROPERTY_FILE_PATH = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/src/test/resources/serviceConfig/";
        try {
            properties = new PropertyParser();
            String host = System.getProperty("environment.env");
            if (StringUtils.isBlank(host)) {
                host = PropertyHelper.getProperty("local.env").toLowerCase();
            }
            if (host.equalsIgnoreCase("dev")) {
                PROPERTY_FILE_PATH = PROPERTY_FILE_PATH + "dev_config.properties";
            } else if (host.equalsIgnoreCase("tst")) {
                PROPERTY_FILE_PATH = PROPERTY_FILE_PATH + "tst_config.properties";
            } else if (host.equalsIgnoreCase("stg")) {
                PROPERTY_FILE_PATH = PROPERTY_FILE_PATH + "stg_config.properties";
            } else {
                throw new Exception("Environment value : " + host);
            }
            properties.load(new FileInputStream(PROPERTY_FILE_PATH));
        } catch (Exception ex) {
            throw new Exception("There was a problem in reading the property file " + PROPERTY_FILE_PATH + " due to : " + ex.getMessage());
        }
    }

    public static String getProperty(String property) throws Exception {
        if (properties == null) {
            new ReadServiceConfigFile();
        }
        return properties.getProperty(property);
    }


}
