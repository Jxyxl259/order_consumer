package com.local.controller;

import com.alibaba.fastjson.JSONObject;
import com.local.config.RequestTemplateInfo;
import com.local.service.OrderClientService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderTestController
 * @Description
 * @Author jiangxy
 * @Date 2018\9\18 0018 14:57
 * @Version 1.0.0
 */
@Controller
public class OrderTestFeignController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderTestFeignController.class);

    private static final String POST_KEY = "jsondata";

    @Autowired
    private OrderClientService orderClientService;

    @RequestMapping(value="/order-service-create")
    @ResponseBody
    public void orderLocalTest(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("requestBody") String requestBody){
        LOG.info("本地模拟服务消费端，调用 order 服务接口 " + requestBody);

        String token = request.getParameter("token");
        LOG.info("token ->" + token);
        String requestBodyStr = "bodybodybodybodybody";
        String responseMessage = null;
        try {
            // TODO
//            Map<String,String> queries = new HashMap<>();
//            queries.put("sign","123456");
//            Map<String,String> headers = new HashMap<>();
//            headers.put("mimeType","text/plain");
//            String body = requestBody;
            //String requestInfoStr = makeRequestInfoStr(queries, headers, body);

            //responseMessage = orderClientService.handleRequestInternal(new RequestTemplateInfo(queries, headers, requestBody));
            responseMessage = orderClientService.handleRequestInternal(requestBodyStr);
        } catch (Exception e) {
            System.out.println("调用服务提供端发生异常");
            e.printStackTrace();
        }

        try {
            System.out.println("接收到服务提供端原始响应信息" + responseMessage);
            responseMessage = URLDecoder.decode(responseMessage, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOG.info("本地模拟服务消费端，收到 order 服务接口返回结果 " + responseMessage);

        byte[] bytes = "调用服务成功".getBytes(StandardCharsets.UTF_8);

        try {
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("text/html; charset=UTF-8");
            response.setContentLength(bytes.length);
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeRequestInfoStr(Map<String,String> queries, Map<String,String> headers, String requestBody) {
        RequestTemplateInfo templateInfo = new RequestTemplateInfo(queries, headers, requestBody);
        String templateInfoStr = JSONObject.toJSONString(templateInfo);
        return templateInfoStr;
    }


    private String getOrderJson(final HttpServletRequest request) {
        final String orderJSON = (String) request.getAttribute(POST_KEY);
        if (orderJSON != null) {
            return orderJSON;
        }
        List<String> lines = null;

        try {
            ServletInputStream is = request.getInputStream();
            lines = IOUtils.readLines(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        lines.forEach(line -> {sb.append(line);});
        if(StringUtils.isEmpty(sb.toString())){
            return null;
        }

        return sb.toString();
    }
}













