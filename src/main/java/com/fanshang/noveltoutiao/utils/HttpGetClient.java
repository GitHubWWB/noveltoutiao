package com.fanshang.noveltoutiao.utils;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpGetClient {

    public String sendGetData(String url) throws IOException {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("content-type", "application/json");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            //result = EntityUtils.toString(response.getEntity(), "utf-8");
            result = "success";
        }
        response.close();
        return result;
    }
}
