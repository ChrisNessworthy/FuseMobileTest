package com.fusemobiletest.network;

/**
 * Created by Chris on 2016/09/20.
 */

//Class to handle the URLs that the application can use.
public class URLConstants {

    public static final String CALL_FUSION_CHECK = "company_check";

    public static class URLS {
        public static final String URL_PREFIX = "https://";
        public static final String URL_PARAMS = ".fusion-universal.com/api/v1/company.json";

        public static String COMPLETE_URL(String company_name){return URL_PREFIX + company_name + URL_PARAMS;}

    }
}
