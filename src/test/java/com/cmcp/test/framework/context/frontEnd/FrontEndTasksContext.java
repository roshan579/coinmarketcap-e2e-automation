package com.cmcp.test.framework.context.frontEnd;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;


/**
 * Created by Roshan
 *
 * This is a context class which has class variables which will be populated
 * when cucumber steps are invoked and saved so that they act as data exchange
 * between cucumber steps implementations
 *
 */
public class FrontEndTasksContext {

    public static String marketCap;
    public static String marketCapValues;
    public static String price;
    public static String priceValues;

}
