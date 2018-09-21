package com.local.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @ClassName FeignConfiguration
 * @Description
 * @Author jiangxy
 * @Date 2018\9\20 0020 11:56
 * @Version 1.0.0
 */
@Configuration
public class FeignConfiguration {
    private Logger logger = LoggerFactory.getLogger(FeignConfiguration.class);

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        template.header(name, values);
                    }
                }
                Enumeration<String> bodyNames = request.getParameterNames();
                StringBuilder queryString =new StringBuilder();
                if (bodyNames != null) {
                    queryString.append("?");
                    while (bodyNames.hasMoreElements()) {
                        String name = bodyNames.nextElement();
                        String values = request.getParameter(name);
                        queryString.append(name).append("=").append(values).append("&");
                    }
                }

                if(queryString.length()!=0) {
                    queryString.deleteCharAt(queryString.length()-1);
                    //在 请求url 后面追加路径参数
                    template.append(queryString.subSequence(0, queryString.length()));
                    logger.info("feign interceptor url:{},   body:{}" ,template.url(),new String(template.body()));
                }
            }
        };
    }
}