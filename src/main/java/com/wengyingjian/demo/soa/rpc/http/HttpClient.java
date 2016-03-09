package com.wengyingjian.demo.soa.rpc.http;

import com.wengyingjian.demo.util.HttpUtil;

import java.util.Map;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class HttpClient {


    public Object call(String url, Map<String, String> params) {
        return HttpUtil.get(url, params);
    }
}
