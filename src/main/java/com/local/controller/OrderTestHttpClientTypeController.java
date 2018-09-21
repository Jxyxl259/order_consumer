package com.local.controller;

import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName OrderTestHttpClientTypeController
 * @Description
 * @Author jiangxy
 * @Date 2018\9\20 0020 11:41
 * @Version 1.0.0
 */
@Controller
public class OrderTestHttpClientTypeController {

    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    @RequestMapping(value = "/httpget",method = RequestMethod.GET)
    @ResponseBody
    public String helloController2() {
        String jsonString = "httpget11111";
        try {
            HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
            httpPostWrapper.setServerUrl("http://localhost:8082/order/order-service.do?token=JXY0225");
            httpPostWrapper.setCharset("UTF-8");
            httpPostWrapper.setUrlEncodeEnabled(true);
            httpPostWrapper.setUrlDecodeEnabled(true);
            httpPostWrapper.setContentEncoding(APPLICATION_JSON);
            httpPostWrapper.setMimeType(CONTENT_TYPE_TEXT_JSON);
            httpPostWrapper.setRawBody(jsonString);
            HttpResponseWrapper httpResponseWrapper =  HttpTransceiver.doPost(httpPostWrapper);

            if (httpResponseWrapper.getStatus()) {
                System.out.println((String)httpResponseWrapper.getContent());
                return (String)httpResponseWrapper.getContent();
            } else {
                System.out.println(httpResponseWrapper.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }


}
