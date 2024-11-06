package com.scan.keeper.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: amber
 * Date: 2018-05-16
 * Time: 16:17
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
    	ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();  
        ticketPar.name("Authorization").description("")
    	.modelRef(new ModelRef("string")).parameterType("header") 
    	.required(false).build(); //headerticket，
    	pars.add(ticketPar.build());    //
 
        if("prod".equals(AppProperties.SYSTEM_ENVIRONMENT)){
            return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.none())//，，
                .build()  
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
        }else{
            return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scan.keeper.controller"))
                .build()  
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
        }
    }

    // api,
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //
                .title("TDNC")
                //
//                .contact(" Developer")
                //
                .version("1.0")
                //
                .description("DESC")
                .build();
    }

}
