package com.cmcp.test.pages;

import com.cmcp.test.framework.helpers.ReadServiceConfigFile;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static com.cmcp.test.framework.hooks.BrowserManagement.featureScenario;
import static io.restassured.RestAssured.*;

/**
 * Created by Roshan
 *
 * This is a Page Object Model BASE API page which is having common methods
 * for all other pages to be reused
 *
 */
public class BaseAPIPage {


    /**
     * setup default values
     *
     * @return
     */
    public void setup() throws Exception {
        baseURI = ReadServiceConfigFile.getProperty("BASE_URI");
        basePath = ReadServiceConfigFile.getProperty("BASE_PATH");
        defaultParser = Parser.JSON;
    }


    /**
     * executeAPI
     *
     * @param requestSpecBuilder
     * @param url
     * @return
     */
    public Response executeAPI(RequestSpecBuilder requestSpecBuilder, String url) throws Exception{
        RequestSpecification requestSpecification = requestSpecBuilder.build();
        RequestSpecification request = RestAssured.given();
        request.spec(requestSpecification);
        request.queryParam("CMC_PRO_API_KEY", ReadServiceConfigFile.getProperty("API_KEY"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        request.log().all().filter(RequestLoggingFilter.logRequestTo(new PrintStream(byteArrayOutputStream)));
        Response response = request.get(url);
        featureScenario.attach(byteArrayOutputStream.toByteArray(), "application/text", "Request");
        byteArrayOutputStream.close();
        featureScenario.attach(response.then().log().all().extract().asByteArray(), "application/text", "Response");
        return response;
    }


    /**
     * executeAPIWithQueryParams
     *
     * @param requestSpecBuilder
     * @param url
     * @param queryParams
     * @return
     */
    public Response executeAPIWithQueryParams(RequestSpecBuilder requestSpecBuilder, String url,
                                             Map<String, Object> queryParams) throws Exception{
        requestSpecBuilder.addQueryParams(queryParams);
        return executeAPI(requestSpecBuilder, url);
    }

}

