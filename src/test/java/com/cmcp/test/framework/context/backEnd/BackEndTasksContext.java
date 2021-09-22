package com.cmcp.test.framework.context.backEnd;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;


/**
 * Created by Roshan
 *
 * This is a context class which has class variables which will be populated
 * when cucumber steps are invoked and saved so that they act as data exchange
 * between cucumber steps implementations
 *
 */
public class BackEndTasksContext {

    public static List<Map<String, ?>> elemList;
    public static String infoId;
    public static Response infoResponse;
    public static String multiInfoIds;
    public static Response multiInfoResponse;
    public static List<String> mineableIdsList;

}
