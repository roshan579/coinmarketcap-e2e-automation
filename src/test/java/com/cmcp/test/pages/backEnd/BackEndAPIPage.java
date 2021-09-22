package com.cmcp.test.pages.backEnd;

import com.cmcp.test.framework.context.backEnd.BackEndTasksContext;
import com.cmcp.test.framework.helpers.ReadServiceConfigFile;
import com.cmcp.test.pages.BaseAPIPage;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.*;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;

public class BackEndAPIPage extends BaseAPIPage {


    /**
     * setup base uris and path values defined based on run environment (dev,tst,stg)
     *
     * @return
     */
    public void setupTheBaseUriAndPathForTesting() throws Exception {
        setup();
    }


    /**
     * construct map request call and get full element list based on BTC,USDT,ETH crypto currencies
     * save elements in context so that it can be reused across other step definitions
     *
     * @return
     */
    public void doGETCallToService(String serviceInfo) throws Exception{
        RequestSpecBuilder mapRequest = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON);
        Response mapResponse = executeAPI(mapRequest, ReadServiceConfigFile.getProperty(serviceInfo));
        assertEquals(200, mapResponse.getStatusCode());
        JsonPath jsonPath = JsonPath.with(mapResponse.asString());
        BackEndTasksContext.elemList = jsonPath.get("data.findAll { data ->  data.symbol == 'BTC' || data.symbol == 'USDT' || data.symbol == 'ETH' }");
    }

    /**
     * construct price info request call with BTC,USDT,ETH crypto currencies
     * and print the amount values
     *
     * @return
     */
    public void convertDesiredAmountForEveryIdIntoBolivian() throws Exception{
        for(Map<String, ?> elem : BackEndTasksContext.elemList){
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("amount", 10.43);
            queryParams.put("id", elem.get("id").toString());
            queryParams.put("convert", "BOB");
            RequestSpecBuilder request = new RequestSpecBuilder()
                    .setBaseUri(baseURI)
                    .setBasePath(basePath)
                    .setContentType(ContentType.JSON);
            Response finalResponse =
                    executeAPIWithQueryParams(request, ReadServiceConfigFile.getProperty("TOOLS_PRICE_CONVERSION_SERVICE_INFO"),
                            queryParams);
            assertEquals(200, finalResponse.getStatusCode());
            Float convertedValue = finalResponse.then().extract().path("data.quote.BOB.price");
            System.out.println("Converted Value for ID : "+queryParams.get("id")+" for amount : 10.43 is : "+convertedValue);
        }
    }

    /**
     * construct currency info request call with given id
     * and save response in context fur further checks
     *
     * @return
     */
    public void doGETCallToServiceWithId(String serviceInfo, int id) throws Exception{
        RequestSpecBuilder infoRequest = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id", String.valueOf(id));
        BackEndTasksContext.infoId = String.valueOf(id);
        Response infoResponse = executeAPIWithQueryParams(infoRequest, ReadServiceConfigFile.getProperty(serviceInfo), queryParams);
        assertEquals(200, infoResponse.getStatusCode());
        BackEndTasksContext.infoResponse = infoResponse;
    }


    public void confirmThatTheFollowingLogoURLIsPresent(String logo) throws Exception{
        assertEquals(BackEndTasksContext.infoResponse.then().extract().path("data."+BackEndTasksContext.infoId+".logo"), logo);
    }

    public void confirmThatTheTechnical_docUrlIsPresent(String technicalDoc) throws Exception{
        BackEndTasksContext.infoResponse.then().assertThat().body("data."+BackEndTasksContext.infoId+".urls.technical_doc", hasItem(technicalDoc));
    }

    public void confirmThatTheSymbolOfTheCurrencyIs(String symbol) throws Exception{
        assertEquals(BackEndTasksContext.infoResponse.then().extract().path("data."+BackEndTasksContext.infoId+".symbol"), symbol);
    }

    public void confirmThatTheDateAddedIs(String dateAdded) throws Exception{
        assertEquals(BackEndTasksContext.infoResponse.then().extract().path("data."+BackEndTasksContext.infoId+".date_added"), dateAdded);
    }

    public void confirmThatTheCurrencyHasTheMineableTagAssociatedWithIt(String tag) throws Exception{
        BackEndTasksContext.infoResponse.then().assertThat().body("data."+BackEndTasksContext.infoId+".tags", hasItem(tag));
    }


    /**
     * construct currency info request call with multiple ids
     * and save response in context fur further checks
     *
     * @return
     */
    public void doGETCallToServiceWithIds(String serviceInfo, String idsCommaSeparated) throws Exception{
        RequestSpecBuilder infoMultiRequest = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("id", idsCommaSeparated);
        BackEndTasksContext.multiInfoIds = idsCommaSeparated;
        Response multiInfoResponse = executeAPIWithQueryParams(infoMultiRequest, ReadServiceConfigFile.getProperty(serviceInfo), queryParams);
        assertEquals(200, multiInfoResponse.getStatusCode());
        BackEndTasksContext.multiInfoResponse = multiInfoResponse;
    }

    /**
     * check which currencies have mineable tag
     *
     * @return
     */
    public void checkWhichCurrenciesHaveTheTagAssociatedWithThem(String tagName) throws Exception {
        List<String> idList = Arrays.asList(BackEndTasksContext.multiInfoIds.split(","));
        List<String> mineableIdsList = new ArrayList<>();
        for(String id : idList){
            List<String> tagsList = BackEndTasksContext.multiInfoResponse.then().extract().path("data."+id+".tags");
            if(tagsList.contains(tagName)){
                mineableIdsList.add(id);
            }
        }
        BackEndTasksContext.mineableIdsList = mineableIdsList;
        System.out.println(mineableIdsList);
    }

    /**
     * verify and print only which are mineable currencies
     *
     * @return
     */
    public void verifyThatTheCorrectCryptocurrenciesHaveBeenPrintedOut() throws Exception {
        List<String> idList = Arrays.asList(BackEndTasksContext.multiInfoIds.split(","));
        for(String id : idList){
            if(BackEndTasksContext.mineableIdsList.contains(id)){
                String name = BackEndTasksContext.multiInfoResponse.then().extract().path("data."+id+".name");
                System.out.println(name);
            }
        }
    }

}
