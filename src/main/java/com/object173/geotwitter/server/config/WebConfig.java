package com.object173.geotwitter.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ������� on 29.04.2017.
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.object173.geotwitter.server")
public class WebConfig extends WebMvcConfigurerAdapter{

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converters.add(converter);
        converters.add(byteArrayHttpMessageConverter());
    }

    @Bean(name="multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(5242880);//5MB
        return resolver;
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.IMAGE_JPEG);
        list.add(MediaType.IMAGE_PNG);
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        return list;
    }
}
