package com.local.config;

import java.util.Map;

/**
 * @ClassName RequestTemplateInfo
 * @Description
 * @Author jiangxy
 * @Date 2018\9\20 0020 16:10
 * @Version 1.0.0
 */
public class RequestTemplateInfo {

    /**
     * 请求路径参数
     */
    private Map<String,String> queries;

    /**
     * 请求头部信息
     */
    private Map<String,String> headers;

    /**
     * 请求体
     */
    private String requestBody;

    public RequestTemplateInfo(Map<String, String> queries, Map<String, String> headers, String requestBody) {
        this.queries = queries;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public void setQueries(Map<String, String> queries) {
        this.queries = queries;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
