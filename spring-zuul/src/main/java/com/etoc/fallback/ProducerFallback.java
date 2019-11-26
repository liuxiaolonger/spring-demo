package com.etoc.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * 自定义Zuul回退机制处理器。
 * <Provides fallback when a failure occurs on a route 英文意思就是说提供一个回退机制当路由后面的服务发生故障时。>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年3月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//@Component
public class ProducerFallback implements FallbackProvider {
    private final Logger logger = LoggerFactory.getLogger(FallbackProvider.class);

    //指定要处理的 service。
    @Override
    public String getRoute() {
    	// api服务id，如果需要所有调用都支持回退，则return "*"或return null
        return "system-manager";
    }

    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            /**
             * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的，
             * 不应该把api的404,500等问题抛给客户端
             * 网关和api服务集群对于客户端来说是黑盒子
            */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("{\"message\": \"The service is unavailable.\"}".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause)
    {
        if (cause != null && cause.getCause() != null) {
            String reason = cause.getCause().getMessage();
            logger.error("Excption {}",reason);
        }
        return fallbackResponse();
    }
    
    /**
     * 当  微服务出现宕机后，客户端再请求时候就会返回 fallback 等字样的字符串提示；
     *
     * 但对于复杂一点的微服务，我们这里就得好好琢磨该怎么友好提示给用户了；
     * 
     * 如果请求用户服务失败，返回什么信息给消费者客户端
     * @return
     * @throws IOException
     
    @Override
    public InputStream getBody() throws IOException {
        JSONObject r = new JSONObject();
        try {
            r.put("state", "9999");
            r.put("msg", "系统错误，请求失败");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(r.toString().getBytes("UTF-8"));
     //   return new ByteArrayInputStream((getRoute() + " :fallback").getBytes());
    }*/

}
