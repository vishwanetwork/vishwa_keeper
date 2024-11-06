package com.scan.keeper.common.interetpor;

import com.scan.keeper.common.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.unit.DataSize;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: amber
 * Date: 2018-05-17
 * Time: 10:51
 */
@SuppressWarnings("deprecation")
@Configuration
public class AppInterceptors extends WebMvcConfigurerAdapter {

    public static String REQUEST_TIME = "http_request_time";

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders(CorsConfiguration.ALL)
                //.allowedHeaders("Token", "Agent", "Content-Type", "Access-Control-Allow-Origin", "origin", "accept", "x-requested-with")
                .allowCredentials(false);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                String ipAddress = IPUtils.getRealIP(request);

                //2B，ip
//                ApiService apiService = (ApiService) ApplicationContextProvider.getBean(ApiService.class);
//                int count = apiService.queryIsInIpWhiteList(ipAddress);
                return true;
//                if (count < 1) {
//                    response.getWriter().append("403 Forbidden");
//                    return false;
//                } else {
//                    return true;
//                }
            }
        }).addPathPatterns("/api/**", "/auth/**");
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //
        factory.setMaxFileSize(DataSize.ofMegabytes(10)); //KB,MB
        /// 
        //factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
