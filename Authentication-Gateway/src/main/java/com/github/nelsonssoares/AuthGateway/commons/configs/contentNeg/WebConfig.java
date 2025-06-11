package com.github.nelsonssoares.AuthGateway.commons.configs.contentNeg;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


//    @Value("${cors.originPatterns:default}")
//    private String corsOriginPatterns = "";

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        var aloowedOrigins = corsOriginPatterns.split(",");
//        registry.addMapping("/**")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")//"*"
//                .allowedOrigins(aloowedOrigins)
//                .allowCredentials(true);
//                //.maxAge(3600);
//    }

    //PARAMETROS VIA HEADER ACCEPT

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);

    }


}
