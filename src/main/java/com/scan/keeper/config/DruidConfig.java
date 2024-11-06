package com.scan.keeper.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.oms")
    @Bean("omsDataSource")
    public DataSource omsDataSource(){
       return  new DruidDataSource();
    }

//    @ConfigurationProperties(prefix = "spring.datasource.baojia")
//    @Bean("baojiaDataSource")
//    public DataSource baojiaDataSource(){
//       return  new DruidDataSource();
//    }

//    @ConfigurationProperties(prefix = "spring.datasource.contract")
//    @Bean("contractDataSource")
//    public DataSource contractDataSource(){
//       return new DruidDataSource();
//    }

    //Druid
    //1、Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");//
        initParams.put("deny","192.168.15.21");

        bean.setInitParameters(initParams); //servlet
        return bean;
    }


    //2、webfilter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }
}
