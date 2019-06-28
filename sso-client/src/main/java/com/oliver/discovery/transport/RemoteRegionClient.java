package com.oliver.discovery.transport;

import com.sun.jersey.client.apache4.ApacheHttpClient4;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * com.oliver.discovery.transport RemoteRegionClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/13 10:44
 */
public class RemoteRegionClient {

    private ApacheHttpClient4 httpClient4;

    public RemoteRegionClient() {
        httpClient4 = ApacheHttpClient4.create();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://172.18.15.84/summer-ssoserver/login?service=http://172.18.40.213:6080/znsdgj/nplogin?ajID=94220F70EF2B04F46CBFF5C5B7B9806B&nAjlb=2&nFyid=2400&loginId=ZGVuZ3poaWdhbmc=";
        String zn = nn(url, "service=", false);
        String sso = nn(url, "service=", true);
        String encode = URLEncoder.encode(zn, "UTF-8");
        System.out.println(sso+encode);
    }

    public static  String nn(String url, String subPoint, boolean subIsBefore){
        String resultUrl = StringUtils.EMPTY;
        if (StringUtils.isBlank(url) || StringUtils.isEmpty(subPoint)) {
            return resultUrl;
        }
        int i = url.indexOf(subPoint) + subPoint.length();
        if (subIsBefore) {
            resultUrl = url.substring(0, i);
        } else {
            resultUrl = url.substring(i, url.length());
        }
        //容错
        if (resultUrl.endsWith("/")) {
            resultUrl = resultUrl.substring(0, resultUrl.length() - 1);
        }
        return resultUrl;
    }


}
