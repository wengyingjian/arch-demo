package com.wengyingjian.demo.util;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class HttpUtil {

    public static Object get(String url, Map<String, String> params) {
        try {
            org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
            if (params != null) {
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                if (entrySet.size() != 0) {
                    StringBuilder sb = new StringBuilder(url).append("?");
                    for (Map.Entry<String, String> entry : entrySet) {
                        String name = entry.getKey();
                        String value = URLEncoder.encode(entry.getValue(), "utf-8");
                        sb.append(name).append("=").append(value).append("&");
                    }
                    sb.deleteCharAt(sb.length()-1);
                    url = sb.toString();
                }
            }
            System.out.println("request url=[" + url + "]");
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());

            String result = new String(bytes, "utf-8");
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
