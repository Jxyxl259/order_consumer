package com.local.service;

import com.local.config.RequestTemplateInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="ORDER-SERVICE-API-PROVIDER")
public interface OrderClientService {

    @RequestMapping(value = "/order/order-service.do", method = RequestMethod.POST)
    public String handleRequestInternal(String requestBodyStr) throws Exception;

}
